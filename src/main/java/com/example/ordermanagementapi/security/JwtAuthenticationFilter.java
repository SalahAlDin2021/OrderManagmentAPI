/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/26/2023
 * Time: 11:32 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    /**
     * This method filters incoming requests and performs JWT authentication.
     * If a valid JWT is present in the request, it loads the associated user details
     * and sets the authentication in the security context.
     *
     * @param request     The HTTP servlet request.
     * @param response    The HTTP servlet response.
     * @param filterChain The filter chain.
     * @throws ServletException Signals a servlet exception.
     * @throws IOException      Signals an error while filtering the request.
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // get JWT (token) from http request
        String token = getJWTfromRequest(request);
        // validate token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // get username from token
            String username = jwtTokenProvider.getUsernameFromJWT(token);

            //check if the user is authenticated or not
            if(username!= null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // load user associated with token
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // create AuthenticationToken
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println(authenticationToken.toString());
                // set spring security, Store Authentication object in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    /**
     * Extracts the JWT token from the request.
     *
     * @param request The HTTP request.
     * @return The extracted JWT token or null if not found.
     */
    // Bearer <accessToken>
    private String getJWTfromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("bearerToken:"+bearerToken);
        if (StringUtils.hasText(bearerToken)
                && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}