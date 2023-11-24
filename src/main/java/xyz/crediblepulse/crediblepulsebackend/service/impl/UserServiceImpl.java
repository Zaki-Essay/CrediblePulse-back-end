package xyz.crediblepulse.crediblepulsebackend.service.impl;

import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xyz.crediblepulse.crediblepulsebackend.config.keycloak.config.Credentials;
import xyz.crediblepulse.crediblepulsebackend.config.keycloak.provider.UserManagementProvider;
import xyz.crediblepulse.crediblepulsebackend.config.security.CurrentUserProvider;
import xyz.crediblepulse.crediblepulsebackend.dtos.users.UserRequestDto;
import xyz.crediblepulse.crediblepulsebackend.exception.dto.ApiErrorCodes;
import xyz.crediblepulse.crediblepulsebackend.exception.exceptions.ApiBusinessException;
import xyz.crediblepulse.crediblepulsebackend.mappers.UserMapper;
import xyz.crediblepulse.crediblepulsebackend.model.entities.user.User;
import xyz.crediblepulse.crediblepulsebackend.model.repository.AuthAccountRepository;
import xyz.crediblepulse.crediblepulsebackend.model.repository.UserRepository;
import xyz.crediblepulse.crediblepulsebackend.service.UserService;
import xyz.crediblepulse.crediblepulsebackend.types.UserStatus;
import xyz.crediblepulse.crediblepulsebackend.validators.UserValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final AuthAccountRepository authAccountRepository;
    private final CurrentUserProvider currentUserProvider;
    private final UserManagementProvider userManagementProvider;
    private final UserMapper userMapper;

    @Override
    public String create(UserRequestDto userRequestDto) throws ApiBusinessException {

        UserValidator.validate(userRequestDto);

        User user = persistUser(userRequestDto);

        createKeycloakUser(userRequestDto);

        return user.getId() != null ? user.getId().toString() : null;
    }

    private void createKeycloakUser(UserRequestDto userRequestDto) throws ApiBusinessException {

        LOGGER.info("begin creating user with userId {} in keycloak ", userRequestDto.email());

        if (Boolean.TRUE.equals(userManagementProvider.existsByEmail(userRequestDto.email()))) {
            LOGGER.error("User with userId {} already exists ", userRequestDto.email());
            throw new ApiBusinessException(ApiErrorCodes.USER_CREATION_ERROR);
        }

        UserRepresentation keycloakUser = userMapper.mapToKeycloakUser(userRequestDto);
        setPassword(keycloakUser, userRequestDto.password());
        try {
            userManagementProvider.createUser(
                    userRequestDto.email(),
                    userRequestDto.firstName(),
                    userRequestDto.lastName(),
                    userRequestDto.email(),
                    userRequestDto.password(),
                    true,
                    null);
        } catch (Exception e) {
            LOGGER.error("User with userId {} cannot be created ", userRequestDto.email());
            throw new ApiBusinessException(ApiErrorCodes.USER_CREATION_ERROR);
        }

        LOGGER.info("user with userId {} created in keycloak ", userRequestDto.email());
    }

    private void setPassword(UserRepresentation keycloakUser, String password) {
        CredentialRepresentation credential = Credentials.createPasswordCredentials(password);
        keycloakUser.setCredentials(Collections.singletonList(credential));
        keycloakUser.setEnabled(true);
    }

    private User persistUser(UserRequestDto userRequestDto) throws ApiBusinessException {

        LOGGER.info("begin creating user with userId {} locally ", userRequestDto.email());

        if (userRepository.findByEmail(userRequestDto.email()).isPresent()) {
            LOGGER.error("User with userId {} already exists ", userRequestDto.email());
            throw new ApiBusinessException(ApiErrorCodes.USER_CREATION_ERROR);
        }

        User user = User.builder()
                .email(userRequestDto.email())
                .firstName(userRequestDto.firstName())
                .lastName(userRequestDto.lastName())
                .userStatus(UserStatus.ACTIVE)
                .verified(false)
                .build();

        LOGGER.info("user with userId {} created locally ", userRequestDto.email());

        return userRepository.save(user);
    }
}
