package xyz.crediblepulse.crediblepulsebackend.config.keycloak.provider;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.crediblepulse.crediblepulsebackend.config.keycloak.exceptions.UserCreationException;
import xyz.crediblepulse.crediblepulsebackend.config.keycloak.exceptions.UserSuspensionException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakUserManagement implements UserManagementProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakUserManagement.class);

    @Value("${keycloakAdmin.clientId}")
    private String clientId;

    @Value("${keycloakAdmin.serverUrl}")
    private String authServerUrl;

    @Value("${keycloakAdmin.realm}")
    private String realmName;

    @Autowired
    private UsersResource usersResource;

    @Autowired
    private RealmResource realmResource;

    @Override
    public void changePassword(String phoneNumber, String oldPassword, String newPassword) throws Exception {
        LOGGER.info("Changing password for user with phone number {}", phoneNumber);

        List<UserRepresentation> search = usersResource.search(phoneNumber);

        if (search != null) {

            LOGGER.info("Found {} users with phone number {}", search.size(), phoneNumber);

            if (!isValidPassword(phoneNumber, newPassword)) {
                LOGGER.error("Invalid old password for user with phone number {}", phoneNumber);
                throw new RuntimeException();
            }

            resetPassword(phoneNumber, newPassword, usersResource, search);
        }
    }

    @Override
    public void resetPassword(String phoneNumber, String newPassword) throws Exception {
        LOGGER.info("Resetting password for user with phone number {}", phoneNumber);

        List<UserRepresentation> search = usersResource.search(phoneNumber);

        LOGGER.info("Found {} users with phone number {}", search.size(), phoneNumber);

        resetPassword(phoneNumber, newPassword, usersResource, search);
    }

    private void resetPassword(
            String phoneNumber, String newPassword, UsersResource usersResource, List<UserRepresentation> search)
            throws Exception {
        if (search.size() > 1) {
            throw new WebApplicationException(
                    "More than one user found with phone number " + phoneNumber, Response.Status.BAD_REQUEST);
        }
        for (UserRepresentation userRepresentation : search) {
            LOGGER.info("Found user with phone number {}", phoneNumber);
            UserResource userResource = usersResource.get(userRepresentation.getId());
            try {
                addPasswordToUser(userResource, newPassword);
            } catch (UserCreationException e) {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public void createUser(
            String username,
            String firstName,
            String lastName,
            String email,
            String password,
            boolean enabled,
            List<String> realmRoles)
            throws UserCreationException {
        LOGGER.info("Creating new user with username {} in keycloak", username);

        UserRepresentation user = toUserRepresentation(username, firstName, lastName, email, enabled, realmRoles);

        try (Response response = usersResource.create(user)) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            LOGGER.info("User created with id {}", userId);

            UserResource userResource = usersResource.get(userId);
            addPasswordToUser(userResource, password);

            LOGGER.info("User password added");

            RoleMappingResource userRoles = userResource.roles();
            LOGGER.info("Adding roles to user");

            if (realmRoles != null && !realmRoles.isEmpty() && userRoles != null) {
                LOGGER.info("Adding roles {} to user", realmRoles);

                List<RoleRepresentation> roleRepresentations =
                        userRoles.realmLevel().listAll();

                LOGGER.info("Found {} roles in keycloak", roleRepresentations.size());

                if (roleRepresentations.isEmpty()
                        || !roleRepresentations.stream()
                                .map(RoleRepresentation::getName)
                                .collect(Collectors.toSet())
                                .containsAll(realmRoles)) {

                    LOGGER.error("Some roles {} are not found in keycloak", realmRoles);

                    RolesResource roles = realmResource.roles();
                    LOGGER.info("Adding roles {} to keycloak", realmRoles);
                    for (String realmRole : realmRoles) {
                        LOGGER.info("Query role {} to keycloak", realmRole);
                        RoleRepresentation roleRepresentation =
                                roles.get(realmRole).toRepresentation();
                        LOGGER.info("Adding role {} to keycloak", realmRole);
                        userRoles.realmLevel().add(List.of(roleRepresentation));
                        LOGGER.info("Role {} added to keycloak", realmRole);
                    }
                }
            }

        } catch (NotAuthorizedException exception) {
            LOGGER.error("Could not create user, because unauthorized [{}]", exception.getMessage());
            throw new UserCreationException();
        } catch (WebApplicationException exception) {
            Response response = exception.getResponse();
            LOGGER.error(
                    "Error creating the user API response status: {}, info: {}",
                    response.getStatus(),
                    response.getStatusInfo());
            throw new UserCreationException();
        } catch (Exception exception) {
            LOGGER.error(
                    "Unknown error happened while creating user in the keycloak server [{}]", exception.getMessage());
            throw new UserCreationException();
        }
    }

    @Override
    public boolean existsByEmail(String email) {

        LOGGER.info("Checking if user with email {} exists in keycloak", email);

        return !usersResource.searchByEmail(email, Boolean.TRUE).isEmpty();
    }

    private boolean isValidPassword(String username, String password) {
        LOGGER.info("Checking isValidPassword for user with username {}", username);

        try (Keycloak buildQuery = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(realmName)
                .clientId(clientId)
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .resteasyClient(ClientBuilder.newBuilder().build())
                .build()) {
            LOGGER.info("Checking if password is valid for user with username {}", username);

            AccessTokenResponse accessToken;

            try {
                accessToken = buildQuery.tokenManager().getAccessToken();
                LOGGER.info("Checking if password is valid for user with username {}", username);

                return accessToken != null && accessToken.getToken() != null;
            } catch (Exception e) {
                LOGGER.error("Error while checking password for user with username {}", username);
                return false;
            }
        }
    }

    private void addPasswordToUser(UserResource userResource, String password) throws UserCreationException {
        LOGGER.info("Setting user password");

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        try {
            userResource.resetPassword(passwordCred);
        } catch (WebApplicationException exception) {
            Response response = exception.getResponse();
            LOGGER.error(
                    "Error while assigning password to user, API response status: {}, info: {}",
                    response.getStatus(),
                    response.getStatusInfo());
            throw new UserCreationException();
        } catch (Exception exception) {
            LOGGER.error(
                    "Unknown error happened while creating user in the keycloak server [{}]", exception.getMessage());
            throw new UserCreationException();
        }
    }

    private UserRepresentation toUserRepresentation(
            String username,
            String firstName,
            String lastName,
            String email,
            boolean enabled,
            List<String> realmRoles) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(enabled);
        userRepresentation.setUsername(username);
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userRepresentation.setRealmRoles(realmRoles);
        userRepresentation.setEmailVerified(true);
        return userRepresentation;
    }

    @Override
    public void updateUser(String email, UserRepresentation userRepresentation) throws UserSuspensionException {

        LOGGER.info("Updating user with email {} in keycloak", email);

        List<UserRepresentation> users = usersResource.searchByEmail(email, true);

        if (users == null || users.isEmpty()) {
            LOGGER.error("User with email {} not found in keycloak", email);
            throw new UserSuspensionException();
        }

        if (users.size() > 1) {
            LOGGER.error("More than one user with email {} found in keycloak", email);
            throw new UserSuspensionException();
        }

        String id = users.get(0).getId();

        usersResource.get(id).update(userRepresentation);
    }

    @Override
    public void changeEmailVerified(String email, boolean verified) throws UserSuspensionException {
        LOGGER.info("Updating user email verification status {} in keycloak", email);

        List<UserRepresentation> users = usersResource.searchByEmail(email, true);

        if (users == null || users.isEmpty()) {
            LOGGER.error("User with email {} not found in keycloak", email);
            throw new UserSuspensionException();
        }

        if (users.size() > 1) {
            LOGGER.error("More than one user with email {} found in keycloak", email);
            throw new UserSuspensionException();
        }

        UserRepresentation user = users.get(0);
        user.setEmailVerified(verified);

        usersResource.get(user.getId()).update(user);
    }
}
