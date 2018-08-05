package com.finessence.loan.controller;

import com.finessence.loan.entities.InsuranceFeeConfigs;
import com.finessence.loan.model.ApiResponse;
import com.finessence.loan.model.ResponseCodes;
import com.finessence.loan.model.Token;
import java.util.List;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import com.finessence.loan.repository.CrudService;
import com.finessence.loan.services.GlobalFunctions;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author patrick
 */
@RestController
@RequestMapping("/loanInsuranceFeeConfig")
public class LoanInsuranceFeeConfigController {

    private final Logger LOG = Logger.getLogger(LoanInsuranceFeeConfigController.class);

    @Autowired
    Environment env;

    @Autowired
    GlobalFunctions globalFunctions;

    @Autowired
    CrudService crudService;

    @Autowired
    ResponseCodes responseCodes;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String authKey, @RequestBody InsuranceFeeConfigs insuranceFeeConfigs) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            insuranceFeeConfigs.setCreatedBy(Integer.parseInt(token.getUserid()));
            insuranceFeeConfigs.setUpdatedBy(Integer.parseInt(token.getUserid()));
            insuranceFeeConfigs.setDateCreated(new Date());
            insuranceFeeConfigs.setTimeCreated(new Date());
            
            Object test = crudService.save(insuranceFeeConfigs);

            if (test != null) {
                ApiResponse SUCCESS = responseCodes.SUCCESS;
                SUCCESS.setEntity(test);
                res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            } else {
                LOG.error("Creation failed");
                res = new ResponseEntity<>(responseCodes.CREATION_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findById(@RequestHeader(value = "Authorization") String authKey, @RequestParam("id") String id) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            InsuranceFeeConfigs entity = crudService.findEntity(Integer.parseInt(id), InsuranceFeeConfigs.class);

            if (entity != null) {
                ApiResponse SUCCESS = responseCodes.SUCCESS;
                SUCCESS.setEntity(entity);
                res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            } else {
                LOG.error("No such record");
                res = new ResponseEntity<>(responseCodes.NO_RECORDS_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> update(@RequestHeader(value = "Authorization") String authKey, @RequestBody InsuranceFeeConfigs insuranceFeeConfigs) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            insuranceFeeConfigs.setUpdatedBy(Integer.parseInt(token.getUserid()));

            crudService.saveOrUpdate(insuranceFeeConfigs);

            ApiResponse SUCCESS = responseCodes.SUCCESS;
            SUCCESS.setEntity(insuranceFeeConfigs);
            res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/cleanGroupInsuranceFees", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> cleanGroupInsuranceFees(@RequestHeader(value = "Authorization") String authKey, @RequestParam("groupId") String groupId) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "delete from InsuranceFeeConfigs r  where groupId='" + groupId + "'";

            crudService.executeHibernateQuery(q, Collections.EMPTY_MAP);

            ApiResponse SUCCESS = responseCodes.SUCCESS;
            SUCCESS.setEntity(null);
            res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String authKey, @RequestBody InsuranceFeeConfigs insuranceFeeConfigs) {
        ResponseEntity<?> res = null;

        return res;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> read(@RequestHeader(value = "Authorization") String authKey, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);
            String addedQueryfilter = "";
            Map<String, Object> params = new HashMap<>();
            List<InsuranceFeeConfigs> entity = null;
            if (token.getIsadminagent().equals("NO")) {
                addedQueryfilter = " WHERE r.groupId =:id ";
                params = Collections.singletonMap("id", token.getGroupID());
            }

            String q = "select r from InsuranceFeeConfigs r  " + addedQueryfilter;
            if (token.getIsadminagent().equals("NO")) {
                entity = crudService.fetchWithHibernateQuery(q, params, start, end);
            } else {
                entity = crudService.fetchWithHibernateQuery(q, Collections.EMPTY_MAP, start, end);
            }

            if (entity != null) {
                ApiResponse SUCCESS = responseCodes.SUCCESS;
                SUCCESS.setEntity(entity);
                res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            } else {
                LOG.error("No such record");
                res = new ResponseEntity<>(responseCodes.NO_RECORDS_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/findByGroupId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findByGroupId(@RequestHeader(value = "Authorization") String authKey, @RequestParam("groupId") String groupId, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "select r from InsuranceFeeConfigs r where groupId=:groupId";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("groupId", groupId);
            List<InsuranceFeeConfigs> entity = crudService.fetchWithHibernateQuery(q, params, start, end);

            if (entity != null) {
                ApiResponse SUCCESS = responseCodes.SUCCESS;
                SUCCESS.setEntity(entity);
                res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            } else {
                LOG.error("No such record");
                res = new ResponseEntity<>(responseCodes.NO_RECORDS_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }
}
