package divar.config;

import divar.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return web -> web.ignoring()
                .requestMatchers("/uploads/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/uploads/**"
                        )
                        .permitAll()
                        .requestMatchers("/api/users/login",
                                "/api/users/register")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/ads/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/ads/**")
                        .authenticated()

                        .requestMatchers(HttpMethod.PUT, "/api/ads/**")
                        .authenticated()

                        .requestMatchers(HttpMethod.DELETE, "/api/ads/**")
                        .authenticated()

                        .requestMatchers("/api/cities/**",
                                "/api/categories/**")
                        .permitAll()

                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .exceptionHandling(handling ->
                        handling.accessDeniedHandler(accessDeniedHandler()))

                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {

        return (request, response, ex) -> {

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    "{\"message\":\"You are not allowed to access this resource.\",\"status\":403}"
            );
        };
    }
}