package xyz.crediblepulse.crediblepulsebackend.dtos.users;

public record UserProfileRequestDto(
        String firstName, String lastName, String email, String phoneNumber, String description, String timeZone) {}
