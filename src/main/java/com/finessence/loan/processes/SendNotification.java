/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.processes;

import com.finessence.loan.controller.LoanApplicationController;
import com.finessence.loan.model.MessagePayload;
import com.finessence.loan.services.RestTemplateServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 *
 * @author patrick
 */
public class SendNotification implements Runnable {

    private final Logger LOG = LoggerFactory.getLogger(SendNotification.class);
    private RestTemplateServices resttemplateService;
    private Environment env;
    private String message;
    private String messageType;
    private MessagePayload messagePayload;

    public SendNotification(RestTemplateServices resttemplateService, Environment env, MessagePayload messagePayload) {
        this.resttemplateService = resttemplateService;
        this.env = env;
        this.messagePayload = messagePayload;
    }

    @Override
    public void run() {
        try {
            LOG.info("Sending message: "+messagePayload.getMessage()+" to: "+messagePayload.getToPhone());
            String response3 = resttemplateService.POSTrequestNoAuth(env.getRequiredProperty("datasource.crudeapp.notificationServiceUrl"), messagePayload);
            LOG.info("Notification sucess response:" + response3);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error Occured:" + e.getMessage());
        }

    }

}
