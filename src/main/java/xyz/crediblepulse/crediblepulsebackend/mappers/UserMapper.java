package xyz.crediblepulse.crediblepulsebackend.mappers;


import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;
import xyz.crediblepulse.crediblepulsebackend.dtos.users.UserProfileRequestDto;
import xyz.crediblepulse.crediblepulsebackend.dtos.users.UserProfileResponseDto;
import xyz.crediblepulse.crediblepulsebackend.dtos.users.UserRequestDto;
import xyz.crediblepulse.crediblepulsebackend.model.entities.user.User;

import java.time.ZoneId;
import java.util.List;

@Component
public class UserMapper {




    public UserRepresentation mapToKeycloakUser(UserRequestDto user) {
        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setEmail(user.email());
        keycloakUser.setUsername(user.email());
        keycloakUser.setFirstName(user.firstName());
        keycloakUser.setLastName(user.lastName());
        return keycloakUser;
    }

    public UserRepresentation mapToKeycloakUser(UserProfileRequestDto user) {
        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setFirstName(user.firstName());
        keycloakUser.setLastName(user.lastName());
        return keycloakUser;
    }


    public void update(User user, UserProfileRequestDto userProfileRequestDto) {
        user.setFirstName(userProfileRequestDto.firstName());
        user.setLastName(userProfileRequestDto.lastName());
        user.setDescription(userProfileRequestDto.description());
        user.setPhoneNumber(userProfileRequestDto.phoneNumber());
        if (userProfileRequestDto.timeZone() != null) {
            user.setTimeZone(ZoneId.of(userProfileRequestDto.timeZone()));
        }
    }

    public static UserProfileRequestDto mapToUserProfile(User user) {
        if (user == null) {
            return null;
        }

        return new UserProfileRequestDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getDescription(),
                user.getTimeZone() != null ? user.getTimeZone().toString() : null);
    }
}
