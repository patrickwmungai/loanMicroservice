package com.finessence.loan.config.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Authentication entry point for REST services
 */
public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "null");
        response.addHeader("WWW-Authenticate", "Basic realm=\"\"");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Auth-key");
        System.out.println("Request data >>>>>" + request);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        out.print("{\"message\":\"" + authException.getMessage() + "\", \"access-denied\":true,\"errorCode\":\"" + HttpServletResponse.SC_UNAUTHORIZED + "\"}");
        out.flush();
        out.close();
    }

}
