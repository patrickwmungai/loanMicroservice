package com.finessence.loan.controller;

import com.finessence.loan.entities.GroupApprovalLevel;
import com.finessence.loan.entities.GroupApprovalLevelsConfig;
import com.finessence.loan.entities.Invgroup;
import com.finessence.loan.entities.LoanDisbursements;
import com.finessence.loan.entities.Loanapplication;
import com.finessence.loan.entities.Users;
import com.finessence.loan.model.ApiResponse;
import com.finessence.loan.model.ApprovalPostRequest;
import com.finessence.loan.model.MessagePayload;
import com.finessence.loan.model.ResponseCodes;
import com.finessence.loan.model.Token;
import com.finessence.loan.processes.SendNotification;
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
import com.finessence.loan.services.RestTemplateServices;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author patrick
 */
@RestController
@RequestMapping("/loanDisbursements")
public class LoanDisbursementController {

    private final Logger LOG = Logger.getLogger(LoanDisbursementController.class);

    @Autowired
    Environment env;

    @Autowired
    GlobalFunctions globalFunctions;

    @Autowired
    CrudService crudService;

    @Autowired
    ResponseCodes responseCodes;

    @Autowired
    RestTemplateServices resttemplateService;

    private static ThreadPoolExecutor exec_service = new ThreadPoolExecutor(10, 10, 5000, TimeUnit.SECONDS, new LinkedBlockingQueue());

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String authKey, @RequestBody LoanDisbursements loanDisbursements) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            loanDisbursements.setCreatedBy(Integer.parseInt(token.getUserid()));

            Object test = crudService.save(loanDisbursements);

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

            LoanDisbursements entity = crudService.findEntity(Integer.parseInt(id), LoanDisbursements.class);

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
    public ResponseEntity<?> update(@RequestHeader(value = "Authorization") String authKey, @RequestBody LoanDisbursements loanDisbursements) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            loanDisbursements.setUpdatedBy(Integer.parseInt(token.getUserid()));

            crudService.saveOrUpdate(loanDisbursements);

            ApiResponse SUCCESS = responseCodes.SUCCESS;
            SUCCESS.setEntity(loanDisbursements);
            res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/disburseLoan", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> disburseLoan(@RequestHeader(value = "Authorization") String authKey, @RequestParam("disbursementId") Integer disbursementId) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            LoanDisbursements disbursement = crudService.findEntity(disbursementId, LoanDisbursements.class);
            Loanapplication loanApplication = globalFunctions.getLoanApplicationById(String.valueOf(disbursement.getLoanApplicationId()));
            if (disbursement == null) {
                res = new ResponseEntity<>(responseCodes.DISBURSEMENT_NOT_FOUND, HttpStatus.OK);
            } else if (loanApplication == null) {
                res = new ResponseEntity<>(responseCodes.DISBURSEMENT_LOAN_RECORD_NOT_FOUND, HttpStatus.OK);
            } else if (disbursement.getApprovalStatus().equalsIgnoreCase("Pending") || disbursement.getApprovalStatus().equalsIgnoreCase("Rejected")) {
                res = new ResponseEntity<>(responseCodes.DISBURSE_MONEY_ONLY_ALLOWED_FOR_APPROVED, HttpStatus.OK);
            } else if (disbursement.getApprovalStatus().equalsIgnoreCase("Approved")) {
                //if loan is approved initiated disbursement
                globalFunctions.processApprovedDisbursement(disbursement, loanApplication, token);

                ApiResponse SUCCESS = responseCodes.SUCCESS;
                SUCCESS.setEntity(disbursement.getId());
                res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/approveDisbursement", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> approveDisbursement(@RequestHeader(value = "Authorization") String authKey, @RequestBody ApprovalPostRequest approvalPostRequest) {
        ResponseEntity<?> res = null;
        try {
            List<Integer> disbursementIds = approvalPostRequest.getDisbursementIds();
            String approvalComments = approvalPostRequest.getApprovalComments();
            String approvalStatus = approvalPostRequest.getApprovalStatus();
            Token token = globalFunctions.parseJWT(authKey);
            //Get Approval Levels for Disbursement
            //Insert into the approvals table
            //push the Disbursement to the next approval level
            //if last approval level then mark the status to be approved
            for (Integer disbursementId : disbursementIds) {
                LoanDisbursements disbursement = crudService.findEntity(disbursementId, LoanDisbursements.class);
                if (disbursement.getApprovalStatus().equals("Approved") || disbursement.getApprovalStatus().equals("Rejected")) {
                    res = new ResponseEntity<>(responseCodes.DISUBURSEMENT_ALREADY_UNDEGONE_APPROVAL_PROCESS, HttpStatus.OK);
                } else if (!globalFunctions.usersApprovalLevelPerRecord(disbursement.getId(), "LOAN_DISBURSEMENT", token.getUserid()).isEmpty()) {
                    LOG.error("User had previously approved");
                    res = new ResponseEntity<>(responseCodes.USER_HAD_PREVIOUSLY_APPROVED, HttpStatus.OK);
                } else {
                    GroupApprovalLevelsConfig approvalLevelsConfig = globalFunctions.getGroupsApprovalLevelsConfigByType("LOAN_DISBURSEMENT", disbursement.getGroupId());
                    if (approvalLevelsConfig != null) {
                        String[] levels = approvalLevelsConfig.getTypeApprovals().split(",");
                        if (levels.length > 0) {
                            Loanapplication loanApplication = globalFunctions.getLoanApplicationById(String.valueOf(disbursement.getLoanApplicationId()));
                            if (approvalStatus.equals("Reject")) {
                                //flag disbursemnt as rejected
                                disbursement.setApprovalStatus("Rejected");
                                crudService.saveOrUpdate(disbursement);
                                //send notification to the member
                                //enter rejection to approvals table
                                globalFunctions.logApprovals(disbursement.getCurrentApprovalLevel(), disbursement.getGroupId(), approvalComments, disbursement.getApprovalStatus(), loanApplication.getAppliedamount(), loanApplication.getAppliedamount(), Long.parseLong(disbursement.getId().toString()), "LOAN_DISBURSEMENT", Integer.parseInt(token.getUserid()));
                            } else {//Action Approve
                                Integer currentApprovalLevel = disbursement.getCurrentApprovalLevel();
                                //Get position of current approval level
                                int indexOfCurrentApproval = globalFunctions.postionOfItemInArray(levels, currentApprovalLevel);
                                Integer lastApprovalLevel = Integer.parseInt(levels[levels.length - 1]);
                                boolean islastApproval = indexOfCurrentApproval + 1 == levels.length;
                                Integer nextApprovalLevel = islastApproval ? Integer.parseInt(levels[indexOfCurrentApproval]) : Integer.parseInt(levels[indexOfCurrentApproval + 1]);
                                LOG.info("Current Approval Level:" + currentApprovalLevel + " Next Approval level " + nextApprovalLevel + " Last approval Level: " + lastApprovalLevel);
                                //Mark the record as approved if its the last record else mark it pending
                                if (islastApproval) {
                                    disbursement.setApprovalStatus("Approved");
                                    disbursement.setApprovedBy(token.getUserid());
                                    disbursement.setDateApproved(new Date());
                                } else {
                                    disbursement.setApprovalStatus("Pending");
                                }

                                disbursement.setCurrentApprovalLevel(nextApprovalLevel);
                                disbursement.setUpdatedBy(Integer.parseInt(token.getUserid()));
                                crudService.saveOrUpdate(disbursement);
                                globalFunctions.logApprovals(disbursement.getCurrentApprovalLevel(), disbursement.getGroupId(), approvalComments, "Approved", loanApplication.getAppliedamount(), loanApplication.getAppliedamount(), Long.parseLong(disbursement.getId().toString()), "LOAN_DISBURSEMENT", Integer.parseInt(token.getUserid()));
                            }

//                            //if loan is approved initiated disbursement
//                            if (disbursement.getApprovalStatus().equalsIgnoreCase("Approved")) {
//                                globalFunctions.processApprovedDisbursement(disbursement, loanApplication, token);
//                            }
                            //Send notification to next apprvers
                            if (disbursement.getApprovalStatus().equals("Pending")) {
                                String nextApprovalPermissionName = globalFunctions.resolveApprovalPermissionByLevel(disbursement.getCurrentApprovalLevel(), "LOAN_DISBURSEMENT");
                                List<Users> nextApproversList = globalFunctions.getUsersWithParticularPermissionAndHaveNotApproved(disbursement.getGroupId(), nextApprovalPermissionName, "LOAN_DISBURSEMENT",disbursement.getId());
                                Invgroup group = globalFunctions.getGroupById(token.getGroupID());
                                for (Users user : nextApproversList) {
                                    MessagePayload messagePayload = new MessagePayload();
                                    String message = "Dear " + user.getUserName() + ", you have some disbursements records pending your action.";
                                    messagePayload.setMessage(message);
                                    messagePayload.setToPhone(user.getPhoneNumber());
                                    messagePayload.setToEmail(user.getEmail());
                                    messagePayload.setSubject("Disbursement Approval Notification");
                                    messagePayload.setUserName(group.getMessagingUsername());
                                    messagePayload.setApiKey(group.getMessagingKey());
                                    messagePayload.setMessageMode("S");
                                    exec_service.execute(new SendNotification(resttemplateService, env, messagePayload));

                                }
                            }
                        }

                    }
                    ApiResponse SUCCESS = responseCodes.SUCCESS;
                    SUCCESS.setEntity(disbursementIds);
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

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String authKey, @RequestBody LoanDisbursements loanDisbursements) {
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

//            String q = "select r from LoanDisbursements r ";
//            List<LoanDisbursements> entity = crudService.fetchWithHibernateQuery(q, Collections.EMPTY_MAP, start, end);
            String addedQueryfilter = "";
            Map<String, Object> params = new HashMap<>();
            List<LoanDisbursements> entity = null;
            if (token.getIsadminagent().equals("NO")) {
                addedQueryfilter = " WHERE r.groupId =:id ";
                params = Collections.singletonMap("id", token.getGroupID());
            }

            String q = "select r from LoanDisbursements r  " + addedQueryfilter;
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

    @RequestMapping(value = "/findByApprovalLevel", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findByApprovalLevel(@RequestHeader(value = "Authorization") String authKey, @RequestParam("currentApprovalLevel") Integer currentApprovalLevel, @RequestParam("approvalStatus") String approvalStatus, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "select r from LoanDisbursements r where currentApprovalLevel=:currentApprovalLevel and approvalStatus=:approvalStatus";
            Map<String, Object> map = new HashMap<>();
            map.put("currentApprovalLevel", currentApprovalLevel);
            map.put("approvalStatus", approvalStatus);
            List<LoanDisbursements> entity = crudService.fetchWithHibernateQuery(q, map, start, end);

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

    @RequestMapping(value = "/findApprovedDisbursements", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findApprovedDisbursements(@RequestHeader(value = "Authorization") String authKey, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "select r from LoanDisbursements r where approvalStatus=:approvalStatus";
            Map<String, Object> map = new HashMap<>();
            map.put("approvalStatus", "Approved");
            List<LoanDisbursements> entity = crudService.fetchWithHibernateQuery(q, map, start, end);

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

            String q = "select r from LoanDisbursements r where groupId=:groupId";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("groupId", groupId);
            List<LoanDisbursements> entity = crudService.fetchWithHibernateQuery(q, params, start, end);

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

    @RequestMapping(value = "/findByLoanApplicationId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findByLoanApplicationId(@RequestHeader(value = "Authorization") String authKey, @RequestParam("loanApplicationId") Integer loanApplicationId, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "select r from LoanDisbursements r where loanApplicationId=:loanApplicationId";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("loanApplicationId", loanApplicationId);
            List<LoanDisbursements> entity = crudService.fetchWithHibernateQuery(q, params, start, end);

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
