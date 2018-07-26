package com.finessence.loan.controller;

import com.finessence.loan.entities.Param;
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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author patrick
 */
@RestController
@RequestMapping("/param")
public class ParamController {

    private final Logger LOG = Logger.getLogger(ParamController.class);

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
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String authKey, @RequestBody Param param) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            Object test = crudService.save(param);

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

            Param entity = crudService.findEntity(Integer.parseInt(id), Param.class);

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
    public ResponseEntity<?> update(@RequestHeader(value = "Authorization") String authKey, @RequestBody Param param) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            crudService.saveOrUpdate(param);

            ApiResponse SUCCESS = responseCodes.SUCCESS;
            SUCCESS.setEntity(param);
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
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String authKey, @RequestBody Param param) {
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

            String q = "select r from Param r ";
            List<Param> entity = crudService.fetchWithHibernateQuery(q, Collections.EMPTY_MAP, start, end);

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
    
    @RequestMapping(value = "/findByParameter", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findByParameter(@RequestHeader(value = "Authorization") String authKey, @RequestParam("parameter") String parameter, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "select r from Param r where parameter=:parameter";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("parameter", parameter);
            List<Param> entity = crudService.fetchWithHibernateQuery(q, params, start, end);

            if (entity != null) {
                ApiResponse SUCCESS = responseCodes.SUCCESS;
                SUCCESS.setEntity(entity.get(0));
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
