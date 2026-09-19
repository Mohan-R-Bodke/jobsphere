package JobSphere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import JobSphere.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // Public authentication endpoints
                .requestMatchers(
                    "/api/auth/register",
                    "/api/auth/login"
                ).permitAll()

                // Admin
                .requestMatchers("/api/admin/**")
                    .hasRole("ADMIN")

                // User management
                .requestMatchers("/api/users/**")
                    .hasRole("ADMIN")

                // Jobs
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/jobs",
                    "/api/jobs/**"
                ).hasAnyRole(
                    "CANDIDATE",
                    "RECRUITER",
                    "ADMIN"
                )

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/jobs"
                ).hasRole("RECRUITER")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/jobs/**"
                ).hasRole("RECRUITER")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/jobs/**"
                ).hasRole("RECRUITER")

                // Applications
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/applications/apply"
                ).hasRole("CANDIDATE")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/applications/candidate/**"
                ).hasRole("CANDIDATE")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/applications/job/**"
                ).hasRole("RECRUITER")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/applications/*/status"
                ).hasRole("RECRUITER")

                // Candidate profile
                .requestMatchers("/api/candidate/profile")
                    .hasRole("CANDIDATE")

                // Recruiter profile
                .requestMatchers("/api/recruiter/profile")
                    .hasRole("RECRUITER")

                // Everything else requires authentication
                .anyRequest().authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}