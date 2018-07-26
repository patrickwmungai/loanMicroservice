package com.finessence.loan.controller;

import com.finessence.loan.entities.Invgroup;
import com.finessence.loan.entities.Role;
import com.finessence.loan.entities.RoleMap;
import com.finessence.loan.entities.Users;
import com.finessence.loan.model.ApiResponse;
import com.finessence.loan.model.LogIn;
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
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Key;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collections;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author patrick
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger LOG = Logger.getLogger(UserController.class);

    @Autowired
    Environment env;

    @Autowired
    GlobalFunctions globalFunctions;

    @Autowired
    CrudService crudService;

    @Autowired
    ResponseCodes responseCodes;
    private Calendar currenttime = Calendar.getInstance();

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String authKey, @RequestBody Users user) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            user.setCreatedBy(Integer.parseInt(token.getUserid()));

            Object test = crudService.save(user);
            user.setId(Integer.parseInt(test.toString()));
            //save role map
            for (Role role : user.getRoles()) {
                RoleMap roleMap = new RoleMap();
                roleMap.setId(0);
                roleMap.setApprovalStatus(0);
                roleMap.setCurrentApprovalLevel(0);
                roleMap.setIntrash("NO");
                roleMap.setStatus(1);
                roleMap.setRoleId(role.getId());
                roleMap.setTimeCreated(new Date(currenttime.getTime().getTime()));
                roleMap.setMaxApprovals(0);
                roleMap.setUserId(user.getId());
                crudService.save(roleMap);
            }

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

    /**
     *
     * Function to Loan roles during loading of new user Form
     */
    @RequestMapping(value = "/preCreate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> preCreate(@RequestHeader(value = "Authorization") String authKey) {
        ResponseEntity<?> res = null;
        try {

            Token token = globalFunctions.parseJWT(authKey);
            List<Role> loggedinUserRoles = globalFunctions.findRolesByUserId(Integer.parseInt(token.getUserid()));

            res = new ResponseEntity<>(loggedinUserRoles, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>("Error Occured:" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findById(@RequestHeader(value = "Authorization") String authKey, @RequestParam("id") String id) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            Users selectedUser = crudService.findEntity(Integer.parseInt(id), Users.class);

            if (selectedUser != null) {
                /*          Description
                    Get selected user details
                    Get selected user roles
                    Get logged in user details
                    Get logged in user roles
                    Loop though logged in user roles
                    for each role loop through selected user roles comparing if he has the role
                    if he has mark has role to true
                 */
                List<Role> selecteduserRoles = globalFunctions.findRolesByUserId(selectedUser.getId());

                List<Role> loogedinUserRoles = globalFunctions.findRolesByUserId(Integer.parseInt(token.getUserid()));
                //selectedUser.setRoleMap(loogedinUserRoles);
                for (Role loggedInUserRole : loogedinUserRoles) {
                    for (Role selectedUserrole : selecteduserRoles) {
                        if (loggedInUserRole.getId().equals(selectedUserrole.getId())) {
                            loggedInUserRole.setHas_role(true);
                        }
                    }
                }

                selectedUser.setRoles(loogedinUserRoles);

                ApiResponse SUCCESS = responseCodes.SUCCESS;
                SUCCESS.setEntity(selectedUser);
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
    public ResponseEntity<?> update(@RequestHeader(value = "Authorization") String authKey,
            @RequestBody Users user
    ) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            user.setUpdatedBy(Integer.parseInt(token.getUserid()));

            crudService.saveOrUpdate(user);
            
            //delete all roles assigned to the user
            globalFunctions.deleteRolesByUserId(user.getId());

            //insert new roles to the new user            
            //save role map
            for (Role role : user.getRoles()) {
                RoleMap roleMap = new RoleMap();
                roleMap.setId(0);
                roleMap.setApprovalStatus(0);
                roleMap.setCurrentApprovalLevel(0);
                roleMap.setIntrash("NO");
                roleMap.setStatus(1);
                roleMap.setRoleId(role.getId());
                roleMap.setTimeCreated(new Date(currenttime.getTime().getTime()));
                roleMap.setUserId(user.getId());
                roleMap.setMaxApprovals(0);
                crudService.save(roleMap);
            }

            ApiResponse SUCCESS = responseCodes.SUCCESS;
            SUCCESS.setEntity(user);
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
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String authKey,
            @RequestBody Users user
    ) {
        ResponseEntity<?> res = null;

        return res;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> read(@RequestHeader(value = "Authorization") String authKey,
            @RequestParam("start") int start,
            @RequestParam("end") int end
    ) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "select r from Users r ";
            List<Users> entity = crudService.fetchWithHibernateQuery(q, Collections.EMPTY_MAP, start, end);

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

    @RequestMapping(value = "/serviceLogIn", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> simulatedLogIn(@RequestBody LogIn logIn
    ) {
        ResponseEntity<?> res = null;

        try {
            String p = "";
            Users users = globalFunctions.findUserByuserNameAndPassordAndGroupCode(logIn.getUsername(), logIn.getPassword(), logIn.getGroupCode());

            Invgroup usersGroup = users == null ? null : crudService.findEntity(users.getGroupId(), Invgroup.class);
            if (users != null && usersGroup != null) {
                String isadminGroup = "NO";

                if (usersGroup.getIsAdminGroup() == 1) {
                    isadminGroup = "YES";
                }
                String token = createJWT(users.getId().toString(), usersGroup.getGroupid(), isadminGroup, 800000000);

                if (token != null) {
                    LOG.info("Generated Token:" + token);
                    users.setToken(token);
                    res = new ResponseEntity<>(users, HttpStatus.OK);
                } else {
                    LOG.error("No token generated");
                    res = new ResponseEntity<>("No token generated", HttpStatus.NOT_FOUND);
                }
            } else {
                LOG.error("No token generated");
                res = new ResponseEntity<>("Invalid Credentials", HttpStatus.NOT_FOUND);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>("Error Occured:" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/simulatedLogIn", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> simulatedLogIn(@RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("groupCode") String groupCode
    ) {
        ResponseEntity<?> res = null;

        try {
            String p = "";
            Users users = globalFunctions.findUserByuserNameAndPassordAndGroupCode(username, password, groupCode);

            Invgroup usersGroup = users == null ? null : crudService.findEntity(users.getGroupId(), Invgroup.class);
            if (users != null && usersGroup != null) {
                String isadminGroup = "NO";

                if (usersGroup.getIsAdminGroup() == 1) {
                    isadminGroup = "YES";
                }
                String token = createJWT(users.getId().toString(), usersGroup.getGroupid(), isadminGroup, 800000000);

                if (token != null) {
                    LOG.info("Generated Token:" + token);
                    users.setToken(token);
                    res = new ResponseEntity<>(users, HttpStatus.OK);
                } else {
                    LOG.error("No token generated");
                    res = new ResponseEntity<>("No token generated", HttpStatus.NOT_FOUND);
                }
            } else {
                LOG.error("No token generated");
                res = new ResponseEntity<>("Invalid Credentials", HttpStatus.NOT_FOUND);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>("Error Occured:" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/decodeJWTToken", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> decodeJWTToken(@RequestParam("token") String jwt
    ) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {

            Token entity = globalFunctions.parseJWT(jwt);

            if (entity != null) {
                LOG.info("Generated item:" + entity);
                res = new ResponseEntity<>(entity, HttpStatus.OK);
            } else {
                LOG.error("Unable to decode");
                res = new ResponseEntity<>("Unable to decode", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>("Error Occured:" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }
    //Sample method to construct a JWT

    private String createJWT(String userid, String agentId, String isadmingroup, long ttlMillis) {

        Token token = null;
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("KEY123456");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Calendar now_expiry = Calendar.getInstance();
        now_expiry.add(Calendar.MINUTE, 10);
        java.util.Date teenMinutesFromNow = now_expiry.getTime();

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setId(userid)
                .setIssuedAt(now)
                .setExpiration(teenMinutesFromNow)
                .claim("groupid", agentId)
                .claim("userid", userid)
                .claim("isadmingroup", isadmingroup)
                .setSubject("registration")
                .setIssuer("synapse")
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}
