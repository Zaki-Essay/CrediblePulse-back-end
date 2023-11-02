package xyz.crediblepulse.crediblepulsebackend.config.holders;

import java.util.Set;

public interface CurrentUser {

    String getFirstName();

    String getLastName();

    String getUsername();

    String getFullName();

    String getClientId();

    String getEmail();

    Set<String> getRoles();
}
