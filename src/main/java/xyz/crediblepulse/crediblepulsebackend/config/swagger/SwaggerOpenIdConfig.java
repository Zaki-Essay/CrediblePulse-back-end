package xyz.crediblepulse.crediblepulsebackend.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnProperty(name = "springdoc.swagger-ui.enabled", havingValue = "true")
public class SwaggerOpenIdConfig {

    public static final String OPEN_ID_SCHEME_NAME = "openId";
    public static final String TOKEN = "/token";
    private static final String OPENID_CONFIG = "/.well-known/openid-configuration";

    @Value("${springdoc.swagger-ui.oauth.endpoint}")
    private String url;

    @Value("${springdoc.swagger-ui.oauth.issuer}")
    private String issuer;

    @Value("${springdoc.title}")
    private String title;

    @Value("${springdoc.description}")
    private String description;

    @Value("${springdoc.contact.name}")
    private String contactName;

    @Value("${springdoc.contact.url}")
    private String contactUrl;

    @Value("${springdoc.contact.email}")
    private String contactEmail;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .components(new Components().addSecuritySchemes(OPEN_ID_SCHEME_NAME, createOpenIdScheme()))
                .addSecurityItem(new SecurityRequirement().addList(OPEN_ID_SCHEME_NAME))
                .security(List.of(new SecurityRequirement().addList(OPEN_ID_SCHEME_NAME)))
                .info(info());
    }

    private SecurityScheme createOpenIdScheme() {
        String connectUrl = issuer + OPENID_CONFIG;

        return new SecurityScheme()
                .flows(oAuth())
                .type(SecurityScheme.Type.OPENIDCONNECT)
                .openIdConnectUrl(connectUrl);
    }

    public OAuthFlows oAuth() {
        return new OAuthFlows()
                .password(new OAuthFlow()
                        .authorizationUrl(url + "/auth")
                        .tokenUrl(url + TOKEN)
                        .refreshUrl(url + TOKEN))
                .authorizationCode(new OAuthFlow()
                        .authorizationUrl(url + "/auth")
                        .tokenUrl(url + TOKEN)
                        .refreshUrl(url + TOKEN));
    }

    @Bean
    public Info info() {
        return new Info().title(title).description(description).version("V1").contact(contact());
    }

    private Contact contact() {
        return new Contact().name(contactName).url(contactUrl).email(contactEmail);
    }
}
