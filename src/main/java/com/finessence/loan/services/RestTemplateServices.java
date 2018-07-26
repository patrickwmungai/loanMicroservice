package com.finessence.loan.services;

import java.util.Map;

/**
 *
 * @author patrick
 */
public interface RestTemplateServices {

    String GETrequest(String URL, String authorizationKey);

    String POSTrequest(String URL, String authorizationKey, Object requestBody);

    String POSTrequestNoAuth(String URL, Object requestBody);

    String GETrequestNoAuth(String URL);
    
    String POSTrequestForParams(String URL, String authorizationKey, Map<String,String> requestBody);
}
