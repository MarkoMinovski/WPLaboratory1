package mk.ukim.finki.wp.lab.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login")
                        .permitAll()
                        .requestMatchers("/songs", "/artists/songDetails")
                        .hasRole("USER")
                        .requestMatchers("/songs/*", "/artists/*").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .exceptionHandling((ex) -> ex
                        .accessDeniedPage("/access_denied")
                );

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("admin123"))
                .roles("ADMIN").build();

        UserDetails user = User.withUsername("marko")
                .password(passwordEncoder.encode("marko123"))
                .roles("USER").build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
