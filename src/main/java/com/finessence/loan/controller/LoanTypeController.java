package com.finessence.loan.controller;

import com.finessence.loan.entities.GroupApprovalLevelsConfig;
import com.finessence.loan.entities.Loantype;
import com.finessence.loan.model.ApiResponse;
import com.finessence.loan.model.ApprovalPostRequest;
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
import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author patrick
 */
@RestController
@RequestMapping("/loantype")
public class LoanTypeController {

    private final Logger LOG = Logger.getLogger(LoanTypeController.class);

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
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String authKey, @RequestBody Loantype loantype) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            loantype.setCreatedBy(Integer.parseInt(token.getUserid()));
            loantype.setUpdatedBy(Integer.parseInt(token.getUserid()));
            loantype.setAddedby(token.getUserid());
            loantype.setDateadded(new Date());

            GroupApprovalLevelsConfig approvalLevelsConfig = globalFunctions.getGroupsApprovalLevelsConfigByType("LOAN_TYPE", loantype.getGroupid());
            if (approvalLevelsConfig != null) {
                String[] levels = approvalLevelsConfig.getTypeApprovals().split(",");
                 LOG.info("Approval level set "+approvalLevelsConfig.getGroupId()+" levels "+approvalLevelsConfig.getType());
                if (levels.length > 0) {
                     LOG.info("Approval level length:"+levels.length+" setting status as pending status inactive level as "+levels[0]);
                    loantype.setCurrentApprovalLevel(Integer.parseInt(levels[0]));
                    loantype.setMaxApprovalLevel(levels.length);
                    loantype.setApprovalStatus("Pending");
                    loantype.setStatus("InActive");
                } else {
                    LOG.info("Approval level set length:"+0);
                    loantype.setApprovalStatus("Approved");
                    loantype.setMaxApprovalLevel(0);
                    loantype.setStatus("Active");
                    loantype.setCurrentApprovalLevel(0);
                }
            } else {
                LOG.info("No Approval Set");
                loantype.setMaxApprovalLevel(0);
                loantype.setApprovalStatus("Approved");
                loantype.setStatus("Active");
                loantype.setCurrentApprovalLevel(0);
            }

            Object test = crudService.save(loantype);

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
    public ResponseEntity<?> findById(@RequestHeader(value = "Authorization") String authKey, @RequestParam("id") Long id) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            Loantype entity = crudService.findEntity(id, Loantype.class);

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
    public ResponseEntity<?> update(@RequestHeader(value = "Authorization") String authKey, @RequestBody Loantype loantype) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            loantype.setUpdatedBy(Integer.parseInt(token.getUserid()));

            crudService.saveOrUpdate(loantype);

            ApiResponse SUCCESS = responseCodes.SUCCESS;
            SUCCESS.setEntity(loantype);
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
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String authKey, @RequestBody Loantype loantype) {
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

//            String q = "select r from Loantype r ";
//            List<Loantype> entity = crudService.fetchWithHibernateQuery(q, Collections.EMPTY_MAP, start, end);
            String addedQueryfilter = "";
            Map<String, Object> params = new HashMap<>();
            List<Loantype> entity = null;
            if (token.getIsadminagent().equals("NO")) {
                addedQueryfilter = " WHERE r.groupid =:id ";
                params = Collections.singletonMap("id", token.getGroupID());
            }

            String q = "select r from Loantype r  " + addedQueryfilter;
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

    @RequestMapping(value = "/approveLoanType", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> approveLoanType(@RequestHeader(value = "Authorization") String authKey, @RequestBody ApprovalPostRequest approvalPostRequest) {
        ResponseEntity<?> res = null;
        try {
            List<Long> loanTypes = approvalPostRequest.getLoanTypeIds();
            String approvalComments = approvalPostRequest.getApprovalComments();
            String approvalStatus = approvalPostRequest.getApprovalStatus();
            Token token = globalFunctions.parseJWT(authKey);
            //Get Approval Levels for loan types
            //Insert into the approvals table
            //push the loan type to the next approval level
            //if last approval level then mark the status to be approved
            for (Long loanid : loanTypes) {
                Loantype loanType = crudService.findEntity(loanid, Loantype.class);

                if (approvalStatus == null || !(approvalStatus.equals("Approved") || approvalStatus.equals("Rejected"))) {
                    res = new ResponseEntity<>(responseCodes.APPROVAL_NOT_SET, HttpStatus.OK);
                } else if (loanType.getApprovalStatus().equals("Approved") || loanType.getApprovalStatus().equals("Rejected")) {
                    res = new ResponseEntity<>(responseCodes.LOAN_TYPE_ALREADY_UNDEGONE_APPROVAL_PROCESS, HttpStatus.OK);
                } else if (!globalFunctions.usersApprovalLevelPerRecord(loanType.getId().intValue(), "LOAN_TYPE", token.getUserid()).isEmpty()) {
                    LOG.error("Loan type had previously approved by user.");
                    res = new ResponseEntity<>(responseCodes.USER_HAD_PREVIOUSLY_APPROVED, HttpStatus.OK);
                } else {
                    GroupApprovalLevelsConfig approvalLevelsConfig = globalFunctions.getGroupsApprovalLevelsConfigByType("LOAN_TYPE", loanType.getGroupid());
                    if (approvalLevelsConfig != null) {
                        String[] levels = approvalLevelsConfig.getTypeApprovals().split(",");
                        if (levels.length > 0) {
                            if (approvalStatus.equals("Reject")) {
                                //flag loan type as rejected
                                loanType.setApprovalStatus("Rejected");
                                crudService.saveOrUpdate(loanType);
                                //send notification to the member
                                //enter rejection to approvals table
                                globalFunctions.logApprovals(loanType.getCurrentApprovalLevel(), loanType.getGroupid(), approvalComments, loanType.getApprovalStatus(), BigInteger.ZERO, BigInteger.ZERO, loanType.getId(), "LOAN_TYPE", Integer.parseInt(token.getUserid()));
                            } else {//Action Approve
                                Integer currentApprovalLevel = loanType.getCurrentApprovalLevel();
                                //Get position of current approval level
                                int indexOfCurrentApproval = globalFunctions.postionOfItemInArray(levels, currentApprovalLevel);
                                Integer lastApprovalLevel = Integer.parseInt(levels[levels.length - 1]);
                                boolean islastApproval = indexOfCurrentApproval + 1 == levels.length;
                                Integer nextApprovalLevel = islastApproval ? Integer.parseInt(levels[indexOfCurrentApproval]) : Integer.parseInt(levels[indexOfCurrentApproval + 1]);
                                LOG.info("Current Approval Level:" + currentApprovalLevel + " Next Approval level " + nextApprovalLevel + " Last approval Level: " + lastApprovalLevel);
                                //Mark the record as approved if its the last record else mark it pending
                                if (islastApproval) {
                                    loanType.setApprovalStatus("Approved");
                                    loanType.setStatus("Active");
                                } else {
                                    loanType.setApprovalStatus("Pending");
                                }

                                loanType.setCurrentApprovalLevel(nextApprovalLevel);
                                loanType.setUpdatedBy(Integer.parseInt(token.getUserid()));
                                crudService.saveOrUpdate(loanType);
                                globalFunctions.logApprovals(currentApprovalLevel, loanType.getGroupid(), approvalComments, "Approved", BigInteger.ZERO, BigInteger.ZERO, loanType.getId(), "LOAN_APPLICATION", Integer.parseInt(token.getUserid()));
                            }

                        }

                    }
                    ApiResponse SUCCESS = responseCodes.SUCCESS;
                    SUCCESS.setEntity(loanTypes);
                    res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/findByApprovalLevel", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findByApprovalLevel(@RequestHeader(value = "Authorization") String authKey, @RequestParam("currentApprovalLevel") Integer currentApprovalLevel, @RequestParam("approvalStatus") String approvalStatus, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "select r from Loantype r where currentApprovalLevel=:currentApprovalLevel and approvalStatus=:approvalStatus ";
            Map<String, Object> map = new HashMap<>();
            map.put("currentApprovalLevel", currentApprovalLevel);
            map.put("approvalStatus", approvalStatus);
            List<Loantype> entity = crudService.fetchWithHibernateQuery(q, map, start, end);

            if (entity != null) {
                LOG.info("Fetched List of:" + entity.size());
                ApiResponse SUCCESS = responseCodes.SUCCESS;
                SUCCESS.setEntity(entity);
                res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);

            } else {
                LOG.error("No records found");
                res = new ResponseEntity<>(responseCodes.NO_RECORDS_FOUND, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }
}
