package xyz.crediblepulse.crediblepulsebackend.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static xyz.crediblepulse.crediblepulsebackend.config.constants.ApiPaths.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    protected static final String[] IGNORING_LIST = {
            "/v3/api-docs/**",
            "/v3/api-docs/swagger-config",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/actuator/**"
    };

    @Value("#{'${cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;

    @SuppressWarnings("unchecked")
    @Bean
    public Jwt2AuthoritiesConverter authoritiesConverter() {
        // This is a converter for roles as embedded in the JWT by a Keycloak server
        // Roles are taken from both realm_access.roles & resource_access.{client}.roles
        return jwt -> {
            final var realmAccess = (Map<String, Object>) jwt.getClaims().getOrDefault("realm_access", Map.of());

            String roles = "roles";

            final var realmRoles = (Collection<String>) realmAccess.getOrDefault(roles, List.of());

            final var resourceAccess = (Map<String, Object>) jwt.getClaims().getOrDefault("resource_access", Map.of());

            // We assume here you have "spring-addons-confidential" and "spring-addons-public" clients
            // configured with "client roles" mapper in Keycloak
            final var confidentialClientAccess =
                    (Map<String, Object>) resourceAccess.getOrDefault("spring-addons-confidential", Map.of());
            final var confidentialClientRoles =
                    (Collection<String>) confidentialClientAccess.getOrDefault(roles, List.of());
            final var publicClientAccess =
                    (Map<String, Object>) resourceAccess.getOrDefault("spring-addons-public", Map.of());
            final var publicClientRoles = (Collection<String>) publicClientAccess.getOrDefault(roles, List.of());

            // Merge the 3 sources of roles and map it to spring-security authorities
            return Stream.concat(
                            realmRoles.stream(),
                            Stream.concat(confidentialClientRoles.stream(), publicClientRoles.stream()))
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        };
    }

    @Bean
    public Jwt2AuthenticationConverter authenticationConverter(Jwt2AuthoritiesConverter authoritiesConverter) {
        return jwt -> new JwtAuthenticationToken(
                jwt, authoritiesConverter.convert(jwt), jwt.getClaimAsString(StandardClaimNames.PREFERRED_USERNAME));
    }

    @Bean
    public SecurityFilterChain apiFilterChain(
            HttpSecurity http,
            Converter<Jwt, ? extends AbstractAuthenticationToken> authenticationConverter,
            RequestMatcherBuilder mvc)
            throws Exception {

        // Authorize requests

        http.authorizeHttpRequests(authz -> authz.requestMatchers(mvc.matchers(IGNORING_LIST))
                .permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .requestMatchers(mvc.matchers(API + V1 + PUBLIC + "/**"))
                .permitAll()
                .anyRequest()
                .authenticated());

        // OAuth 2.0
        http.oauth2ResourceServer(
                oauth -> oauth.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(authenticationConverter)));

        // Session Management
        http.sessionManagement(session -> session.sessionAuthenticationStrategy(sessionAuthenticationStrategy())
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // CSRF
        http.csrf(AbstractHttpConfigurer::disable);

        // Cors
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // Exception handling
        http.exceptionHandling(handler -> handler.authenticationEntryPoint((request, response, authException) -> {
            response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Restricted Content\"");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(RequestMatcherBuilder mvc) {

        return web -> web.ignoring().requestMatchers(mvc.matchers(IGNORING_LIST));
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.setAllowedOrigins(allowedOrigins);
        corsConfiguration.setAllowedHeaders(Arrays.asList(
                "Origin",
                "Content-Type",
                "Accept",
                "Authorization",
                "Origin, Accept",
                "X-Requested-with",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
                "Access-Control-Allow-origin",
                "Access-Control-Allow-Credentials"));
        corsConfiguration.setExposedHeaders(Arrays.asList(
                "Origin",
                "Content-Type",
                "Accept",
                "Authorization",
                "Access-Control-Allow-origin",
                "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return configurationSource;
    }
}
