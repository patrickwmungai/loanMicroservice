/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.controller;

import com.finessence.loan.entities.Permission;
import com.finessence.loan.model.Token;
import com.finessence.loan.repository.CrudService;
import com.finessence.loan.services.GlobalFunctions;
import java.math.BigDecimal;
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
import java.math.BigInteger;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author patrick
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final Logger LOG = Logger.getLogger(PermissionController.class);

    @Autowired
    Environment env;

    @Autowired
    GlobalFunctions globalFunctions;

    @Autowired
    CrudService crudService;

    /**
     * Function creates new permission
     *
     * @param authKey
     * @param permission
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String authKey, @RequestBody Permission permission) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            permission.setCreatedBy(Integer.parseInt(token.getUserid()));

            Object entity = crudService.save(permission);

            if (entity != null) {
                res = new ResponseEntity<>(entity, HttpStatus.OK);
            } else {
                LOG.error("Creation failed");
                res = new ResponseEntity<>("Creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>("Error Occured:" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    /**
     * Function finds permission by id passed
     *
     * @param authKey
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findById(@RequestHeader(value = "Authorization") String authKey, @RequestParam("id") Integer id) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            Permission entity = crudService.findEntity(id, Permission.class);

            if (entity != null) {
                LOG.info(entity.getName());
                res = new ResponseEntity<>(entity, HttpStatus.OK);
            } else {
                LOG.error("No such record");
                res = new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>("Error Occured:" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    /**
     * Function updates the permission posted
     *
     * @param authKey
     * @param permission
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> update(@RequestHeader(value = "Authorization") String authKey, @RequestBody Permission permission) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            permission.setUpdatedBy(Integer.parseInt(token.getUserid()));
            crudService.saveOrUpdate(permission);

            res = new ResponseEntity<>(permission, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>("Error Occured:" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String authKey, @RequestBody Permission permission) {
        ResponseEntity<?> res = null;

        return res;
    }

    /**
     * Function only fetches permissions for only the logged in user
     *
     * @param authKey
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> read(@RequestHeader(value = "Authorization") String authKey, @RequestParam("start") String start, @RequestParam("end") String end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            List<Permission> entity = globalFunctions.findPermissionByUserId(Integer.parseInt(token.getUserid()));

            if (entity != null) {
                LOG.info("Fetched List of:" + entity.size());
                res = new ResponseEntity<>(entity, HttpStatus.OK);
            } else {
                LOG.error("No records found");
                res = new ResponseEntity<>("No records found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>("Error Occured:" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    /**
     * Function to Get menus for a given looged in user using the authentication
     * token details Function will be mainly called by portal to list the menus
     * customer is to see
     *
     * @param authKey
     * @return
     */
    @RequestMapping(value = "/getMenus", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getMenus(@RequestHeader(value = "Authorization") String authKey) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {

            Token token = globalFunctions.parseJWT(authKey);
            List<Permission> entity = globalFunctions.findAllMenuPermissions(Integer.parseInt(token.getUserid()));

            if (entity != null) {
                LOG.info("Fetched List of:" + entity.size());
                res = new ResponseEntity<>(entity, HttpStatus.OK);
            } else {
                LOG.error("No records found");
                res = new ResponseEntity<>("No records found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>("Error Occured:" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    /**
     * Function to get permissions for a given sub module
     *
     * @param authKey
     * @param subModule
     * @return
     */
    @RequestMapping(value = "/getChildrenPermissions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getChildrenPermissions(@RequestHeader(value = "Authorization") String authKey, @RequestParam("subModule") String subModule) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {

            Token token = globalFunctions.parseJWT(authKey);
            List<Permission> entity = globalFunctions.findMenuChildPermissions(subModule, Integer.parseInt(token.getUserid()));

            if (entity != null) {
                LOG.info("Fetched List of:" + entity.size());
                res = new ResponseEntity<>(entity, HttpStatus.OK);
            } else {
                LOG.error("No records found");
                res = new ResponseEntity<>("No records found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>("Error Occured:" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

}
