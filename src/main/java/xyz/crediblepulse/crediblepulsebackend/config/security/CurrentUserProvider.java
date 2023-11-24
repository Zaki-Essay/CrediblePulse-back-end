package xyz.crediblepulse.crediblepulsebackend.config.security;

import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import xyz.crediblepulse.crediblepulsebackend.config.holders.CurrentUser;

@Component
public class CurrentUserProvider implements CurrentUser {

    @Override
    public String getFirstName() {
        AccessToken token = getAccessToken();
        if (Objects.nonNull(token)) {
            return token.getGivenName();
        }
        return null;
    }

    @Override
    public String getLastName() {
        AccessToken token = getAccessToken();
        if (Objects.nonNull(token)) {
            return token.getFamilyName();
        }
        return null;
    }

    @Override
    public String getUsername() {

        String result = null;
        AccessToken token = getAccessToken();
        if (token != null) {
            result = token.getPreferredUsername();
        }
        return result;
    }

    @Override
    public String getFullName() {
        String result = null;
        AccessToken token = getAccessToken();
        if (token != null) {
            result = token.getGivenName() + " " + token.getFamilyName();
        }
        return result;
    }

    @Override
    public String getClientId() {
        AccessToken token = getAccessToken();
        if (token != null) {
            return token.getIssuedFor();
        }
        return null;
    }

    @Override
    public String getEmail() {

        String result = null;
        AccessToken token = getAccessToken();
        if (token != null) {
            result = token.getEmail();
        }
        return result;
    }

    @Override
    public Set<String> getRoles() {
        AccessToken token = getAccessToken();
        if (token != null && token.getRealmAccess() != null) {
            return token.getRealmAccess().getRoles().stream()
                    .map(s -> s.toUpperCase(Locale.ROOT))
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public AccessToken getAccessToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof JwtAuthenticationToken) {
            try {
                return extractAccessToken(auth);
            } catch (VerificationException e) {
                throw new RuntimeException();
            }
        }
        return null;
    }

    public AccessToken extractAccessToken(Authentication authentication) throws VerificationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;

        Jwt jwt = (Jwt) jwtAuthenticationToken.getCredentials();

        return TokenVerifier.create(jwt.getTokenValue(), AccessToken.class).getToken();
    }
}
