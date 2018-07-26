package com.finessence.loan.config.security;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finessence.loan.config.security.SecurityUtils;
import com.finessence.loan.entities.Users;
import com.finessence.loan.services.GlobalFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Spring Security success handler, specialized for Ajax requests.
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    GlobalFunctions globalFunctions;

    @Autowired
    private static final Logger LOGGER = Logger.getLogger(RestAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication)
            throws ServletException, IOException {
        System.out.println(" Success handler called #" + authentication.getName());

        Users user = globalFunctions.findByUserName(authentication.getName());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, user);
    }
}
