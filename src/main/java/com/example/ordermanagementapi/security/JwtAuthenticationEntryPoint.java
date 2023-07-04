/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/26/2023
 * Time: 11:48 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * This method is called when an unauthenticated user tries to access a protected resource.
     * It handles the entry point for JWT authentication by sending an HTTP 401 Unauthorized error.
     *
     * @param httpServletRequest  The HTTP servlet request.
     * @param httpServletResponse The HTTP servlet response.
     * @param e                   The authentication exception (not used in this implementation).
     * @throws IOException Signals an error while writing to the response.
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
