package com.jtownpizza.config;

import com.jtownpizza.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByEmail(username)
                .map(u -> {
                    String rawRole = u.getRole() == null ? "USER" : u.getRole().replaceFirst("^ROLE_", "");
                    UserDetails user = org.springframework.security.core.userdetails.User
                            .withUsername(u.getEmail())
                            .password(u.getPassword())
                            .roles(rawRole)
                            .build();
                    return user;
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AntPathRequestMatcher[] publicMatchers = new AntPathRequestMatcher[] {
                new AntPathRequestMatcher("/"),
                new AntPathRequestMatcher("/index"),
                new AntPathRequestMatcher("/css/**"),
                new AntPathRequestMatcher("/register"),
                new AntPathRequestMatcher("/menu"),
                new AntPathRequestMatcher("/h2-console/**")
        };

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers((org.springframework.security.web.util.matcher.RequestMatcher[]) publicMatchers).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        http.csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")));
        http.headers(headers -> headers.frameOptions().disable());

        return http.build();
    }
}
