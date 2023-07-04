/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/27/2023
 * Time: 8:42 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.config;

import com.example.ordermanagementapi.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            /*
            the client must delete the token upon receiving the response from this endpoint.
            This is usually done on the client-side (in JavaScript or whatever language your
            client is written in).
            */

            // Perform any necessary logout logic here
            // For example, clearing the security context
            SecurityContextHolder.clearContext();

            /* You can also perform additional actions, such as clearing cookies or invalidating sessions,but in our
             project we set SessionCreationPolicy.STATELESS, so the server doesn't create a session for each user,
             and the HttpSession object won't be used. So, trying to invalidate an HttpSession and removing the session
              cookie ("JSESSIONID") won't do anything here, as they are not used in stateless JWT authentication. */


            // You can send a response back to the client if needed
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try {
                response.getWriter().write("Logout successful");
                response.getWriter().flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
