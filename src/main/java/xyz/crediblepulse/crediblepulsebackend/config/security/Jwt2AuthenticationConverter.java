package xyz.crediblepulse.crediblepulsebackend.config.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

// spring-boot looks for a Converter<Jwt, ? extends AbstractAuthenticationToken> bean
// that is a converter from Jwt to something extending AbstractAuthenticationToken (and not
// AbstractAuthenticationToken itself)
// In this conf, we use JwtAuthenticationToken as AbstractAuthenticationToken implementation
public interface Jwt2AuthenticationConverter extends Converter<Jwt, JwtAuthenticationToken> {}
