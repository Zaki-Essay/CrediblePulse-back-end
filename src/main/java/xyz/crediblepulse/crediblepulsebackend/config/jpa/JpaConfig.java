package xyz.crediblepulse.crediblepulsebackend.config.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import xyz.crediblepulse.crediblepulsebackend.config.security.CurrentUserProvider;

import java.util.Objects;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@RequiredArgsConstructor
public class JpaConfig {

    public final CurrentUserProvider currentUserProvider;


    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            String username = currentUserProvider.getUsername();

            return Objects.requireNonNullElse(username, "UNKNOWN").describeConstable();
        };
    }
}
