package xyz.crediblepulse.crediblepulsebackend.config.keycloak.config;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakApiConfig {

    @Value("${keycloakAdmin.realm}")
    private String realm;

    @Value("${keycloakAdmin.clientId}")
    private String clientId;

    @Value("${keycloakAdmin.clientSecret}")
    private String clientSecret;

    @Value("${keycloakAdmin.serverUrl}")
    private String serverUrl;

    @Value("${keycloakAdmin.poolSize:5}")
    private int connectionPoolSize;

    @Bean
    Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(connectionPoolSize)
                        .build())
                .build();
    }

    @Bean
    public RealmResource realmResource(Keycloak keycloak) {
        return keycloak.realm(realm);
    }

    @Bean
    public UsersResource usersResource(RealmResource realmResource) {
        return realmResource.users();
    }
}
