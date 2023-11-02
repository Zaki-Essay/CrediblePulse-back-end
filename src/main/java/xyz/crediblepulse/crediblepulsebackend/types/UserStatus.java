package xyz.crediblepulse.crediblepulsebackend.types;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("Active"),
    DISABLED("Disabled"),
    DELETED("Deleted");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }
}
