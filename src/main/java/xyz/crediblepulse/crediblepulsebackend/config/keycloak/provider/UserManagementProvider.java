package xyz.crediblepulse.crediblepulsebackend.config.keycloak.provider;

import java.util.List;
import org.keycloak.representations.idm.UserRepresentation;

public interface UserManagementProvider {

    void changePassword(String phoneNumber, String oldPassword, String newPassword) throws Exception;

    void resetPassword(String phoneNumber, String newPassword) throws Exception;

    void createUser(
            String username,
            String firstName,
            String lastName,
            String email,
            String password,
            boolean enabled,
            List<String> realmRoles)
            throws Exception;

    boolean existsByEmail(String email);

    void updateUser(String email, UserRepresentation userRepresentation) throws Exception;

    void changeEmailVerified(String email, boolean verified) throws Exception;
}
