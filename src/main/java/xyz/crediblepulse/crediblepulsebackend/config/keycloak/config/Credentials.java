package xyz.crediblepulse.crediblepulsebackend.config.keycloak.config;

import org.keycloak.representations.idm.CredentialRepresentation;

public interface Credentials {
    static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(true);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
