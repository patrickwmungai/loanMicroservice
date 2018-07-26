package com.finessence.loan.services;

import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author patrick
 */
@Service
public class RestTemplateServicesImpl implements RestTemplateServices {

    private final Logger LOG = Logger.getLogger(RestTemplateServicesImpl.class);

    @Override
    public String GETrequest(String URL, String authorizationKey) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = createHttpHeaders(authorizationKey);
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (Exception ex) {
            LOG.error("** Exception: " + ex.getMessage());
            return null;
        }

    }

    @Override
    public String POSTrequest(String URL, String authorizationKey, Object requestBody) {
        try {            
            HttpHeaders headers = createHttpHeaders(authorizationKey);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);
            String response = restTemplate.postForObject(URL, request, String.class);
            return response;
        } catch (Exception ex) {
            LOG.error("Error Occured:" + ex.getMessage());
            return null;
        }
    }

    private HttpHeaders createHttpHeaders(String encodedAuth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", encodedAuth);
        return headers;
    }

    @Override
    public String POSTrequestNoAuth(String URL, Object requestBody) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HttpEntity<Object> request = new HttpEntity<>(requestBody);
            String response = restTemplate.postForObject(URL, request, String.class);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            return null;
        }
    }

    @Override
    public String GETrequestNoAuth(String URL) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpEntity<String> entity = new HttpEntity<>("parameters");
            ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (Exception ex) {
            LOG.error("** Exception: " + ex.getMessage());
            return null;
        }

    }
    
      @Override
    public String POSTrequestForParams(String URL, String authorizationKey, Map<String,String> requestBody) {
        try {            
            HttpHeaders headers = createHttpHeaders(authorizationKey);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);
            String response = restTemplate.postForObject(URL, request, String.class);
            return response;
        } catch (Exception ex) {
            LOG.error("Error Occured:" + ex.getMessage());
            return null;
        }
    }
}
