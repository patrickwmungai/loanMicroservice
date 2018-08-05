package com.finessence.loan.controller;

import com.finessence.loan.entities.Accounttype;
import com.finessence.loan.entities.ApprovalsDone;
import com.finessence.loan.entities.GroupApprovalLevelsConfig;
import com.finessence.loan.entities.Groupmember;
import com.finessence.loan.entities.Invgroup;
import com.finessence.loan.entities.LoanSecurity;
import com.finessence.loan.entities.Loanapplication;
import com.finessence.loan.entities.Loandetails;
import com.finessence.loan.entities.Loanguarantor;
import com.finessence.loan.entities.Loantype;
import com.finessence.loan.entities.LonSchedule;
import com.finessence.loan.entities.Memberaccount;
import com.finessence.loan.entities.Users;
import com.finessence.loan.model.ApiResponse;
import com.finessence.loan.model.ApprovalPostRequest;
import com.finessence.loan.model.LoanScheduleEnvelope;
import com.finessence.loan.model.MessagePayload;
import com.finessence.loan.model.ResponseCodes;
import com.finessence.loan.model.Token;
import com.finessence.loan.processes.SendNotification;
import java.util.List;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author patrick
 */
@RestController
@RequestMapping("/loan")
public class LoanApplicationController {

    private final Logger LOG = LoggerFactory.getLogger(LoanApplicationController.class);

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

    @Transactional
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String authKey, @RequestBody Loanapplication loan) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            //Validations before creations of loan
            String loanId = loan.getLoantypeid().toString().trim();
            LOG.info("LOAN TYPE ID IS " + loanId);
            Loantype loantype = crudService.findEntity(Long.parseLong(loanId), Loantype.class);
            Groupmember groupmember = globalFunctions.getGroupmember(loan.getMembercode());
            Accounttype savingAccounttype = null;
            Memberaccount memberSavingsAccount = null;
            loan.setInterestRate(loantype.getInterestrate());
            loan.setUserId(Integer.parseInt(token.getUserid()));
            Integer appliedamount = Integer.parseInt(loan.getAppliedamount().toString());
            if (groupmember == null) {
                res = new ResponseEntity<>(responseCodes.MEMBER_NOT_FOUND, HttpStatus.OK);
            } else if (groupmember.getMonthlySalary().doubleValue() <= 0) {
                res = new ResponseEntity<>(responseCodes.MEMBER_SALARY_NOT_SET_OR_ZERO, HttpStatus.OK);
            } else if (!globalFunctions.validLoanIncomeRatio(groupmember, loantype, loan)) {
                res = new ResponseEntity<>(responseCodes.LOAN_AMOUNT_WOULD_EXCEED_INSTALLMENT_SALARY_RATIO, HttpStatus.OK);
            } else if (loantype == null) {
                res = new ResponseEntity<>(responseCodes.LOAN_TYPE_NOT_FOUND, HttpStatus.OK);
            } else if (Integer.parseInt(loantype.getMaximumloanamount().toString()) != 0 && Integer.parseInt(loantype.getMaximumloanamount().toString()) < appliedamount) {
                res = new ResponseEntity<>(responseCodes.LOAN_AMOUNT_EXCEEDS_MAX_AMOUNT, HttpStatus.OK);
            } else if (loantype.getMaximumrepaymentperiod() < loan.getRepaymentPeriod()) {
                ApiResponse VALIDATION_FAIL = responseCodes.LOAN_PERIOD_EXCEEDS_MAX_REPAY_PERIOD;
                String errorMsg = VALIDATION_FAIL.getResponseDescription().replace("<PERIOD>", loan.getRepaymentPeriod().toString()).replace("<LIMIT>", loantype.getMaximumrepaymentperiod().toString());
                VALIDATION_FAIL.setResponseDescription(errorMsg);
                res = new ResponseEntity<>(VALIDATION_FAIL, HttpStatus.OK);
            } else {
                savingAccounttype = globalFunctions.getAccountByName("SAVING");
                memberSavingsAccount = globalFunctions.getMemberaccountByAccountTypeAndGroupIdAndMemberCode(savingAccounttype.getTypecode() + "KES", loan.getGroupid(), loan.getMembercode());

                BigDecimal savingsAccountBalance = memberSavingsAccount.getAccountbalance();

                Integer savingsfactor = loantype.getSavingsfactor();
                BigDecimal savingsLimit = savingsAccountBalance.multiply(new BigDecimal(savingsfactor));

                LOG.info("Applied Amount {} savings amount {} loantype factor {}", appliedamount, savingsAccountBalance, savingsfactor);
                //ensure loan has gurantors
                if (loantype.getIscheckoff().equals("N") && loan.getLoansecuritys().size() <= 0) {
                    res = new ResponseEntity<>(responseCodes.LOAN_REQUIRES_SECURITY, HttpStatus.OK);
                } else if (loantype.getMinimumguarantors() > loan.getLoanguarantors().size()) {
                    ApiResponse VALIDATION_FAIL = responseCodes.LOAN_REQUIRES_GURANTORS;
                    String errorMsg = VALIDATION_FAIL.getResponseDescription().replace("<COUNT>", loantype.getMinimumguarantors().toString());
                    VALIDATION_FAIL.setResponseDescription(errorMsg);
                    res = new ResponseEntity<>(VALIDATION_FAIL, HttpStatus.OK);
                } else if (globalFunctions.checkifAnyOfGurantorsIsTheGuyApplyingLoan(loan.getLoanguarantors(), loan.getMembercode())) {
                    res = new ResponseEntity<>(responseCodes.APPLICANT_NOT_ALLOWED_TO_GURANTEE_HIS_LOAN, HttpStatus.OK);
                } else if (globalFunctions.getLoanByGroupIdMemberNoAndStatus(loan.getLoantypeid(), loan.getGroupid(), loan.getMembercode(), "Pending") != null) {
                    res = new ResponseEntity<>(responseCodes.DUPLICATE_LOAN, HttpStatus.OK);
                } else if (!gurantorSavingsSufficientToLoan(savingAccounttype, loan)) {
                    res = new ResponseEntity<>(responseCodes.GURANTOR_SAVINGS_AMOUNT_NOT_SUFFICIENT_TO_LOAN, HttpStatus.OK);
                } else if (savingsLimit.longValue() < appliedamount.longValue()) {//ensure the amounts are within savings limit
                    ApiResponse VALIDATION_FAIL = responseCodes.LOAN_AMOUNT_EXEEDS_FACTOR_LIMIT;
                    String errorMsg = VALIDATION_FAIL.getResponseDescription().replace("<AMOUNT>", appliedamount.toString()).replace("<FACTOR>", savingsfactor.toString()).replace("<SAVINGS>", savingsAccountBalance.toString()).replace("<FACTORSAVINGS>", savingsLimit.toString());
                    VALIDATION_FAIL.setResponseDescription(errorMsg);
                    res = new ResponseEntity<>(VALIDATION_FAIL, HttpStatus.OK);
                } else { //ensure the rates are within the type limits
                    boolean loanHasNoApprovalLevels = false;
                    //Get approval levels if any then set
                    GroupApprovalLevelsConfig approvalLevelsConfig = globalFunctions.getGroupsApprovalLevelsConfigByType("LOAN_APPLICATION", loan.getGroupid());
                    if (approvalLevelsConfig != null) {
                        String[] levels = approvalLevelsConfig.getTypeApprovals().split(",");
                        if (levels.length > 0) {
                            loan.setCurrentApprovalLevel(Integer.parseInt(levels[0]));
                            loan.setApprovalStatus("Pending");
                            loan.setApplicationstatus("Active");
                        } else {
                            loan.setApprovalStatus("Approved");
                            loan.setApplicationstatus("Active");
                            loan.setCurrentApprovalLevel(0);
                            loanHasNoApprovalLevels = true;
                        }
                    } else {
                        loanHasNoApprovalLevels = true;
                    }
                    loan.setId(Long.parseLong("0"));
                    loan.setInterestRate(loantype.getInterestrate());
                    loan.setApplicationdate(new Date());

                    Object test = crudService.save(loan);

                    //Get the loan id Saved
                    Loanapplication savedLoan = globalFunctions.getLoanByGroupIdMemberNoAndStatus(loan.getLoantypeid(), loan.getGroupid(), loan.getMembercode(), loan.getApprovalStatus());
                    LOG.info("The loan id saved is " + savedLoan.getId());
                    //Saving gurantors
                    for (Loanguarantor loanguarantor : loan.getLoanguarantors()) {
                        loanguarantor.setLonApplicationId(savedLoan.getId());
                        loanguarantor.setId(Long.parseLong("0"));
                        loanguarantor.setDateadded(new Date());
                        crudService.save(loanguarantor);
                    }

                    for (LoanSecurity loanSecurity : loan.getLoansecuritys()) {
                        loanSecurity.setLoanApplicationId(Integer.parseInt(savedLoan.getId().toString()));
                        loanSecurity.setId(0);
                        loanSecurity.setGroupId(loan.getGroupid());
                        loanSecurity.setDateCreated(new Date());
                        loanSecurity.setDescription("Loan gurantors.");
                        crudService.save(loanSecurity);
                    }

                    loan.setId(Long.parseLong(test.toString()));

                    if (loanHasNoApprovalLevels) {
                        processLoanAfterApprovals(loan, token);
                    }

                    //Cretion of loan account
                    ApiResponse SUCCESS = responseCodes.SUCCESS;
                    SUCCESS.setEntity(test);
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

    @Transactional
    @RequestMapping(value = "/createDataEntry", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createDataEntry(@RequestHeader(value = "Authorization") String authKey, @RequestBody Loanapplication loan) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            //Validations before creations of loan
            String loanId = loan.getLoantypeid().toString().trim();
            LOG.info("LOAN TYPE ID IS " + loanId);
            Loantype loantype = crudService.findEntity(Long.parseLong(loanId), Loantype.class);
            Groupmember groupmember = globalFunctions.getGroupmember(loan.getMembercode());
            Accounttype savingAccounttype = null;
            Memberaccount memberSavingsAccount = null;
            loan.setInterestRate(loantype.getInterestrate());
            loan.setUserId(Integer.parseInt(token.getUserid()));
            Integer appliedamount = Integer.parseInt(loan.getAppliedamount().toString());
            if (groupmember == null) {
                res = new ResponseEntity<>(responseCodes.MEMBER_NOT_FOUND, HttpStatus.OK);
            } else if (loantype == null) {
                res = new ResponseEntity<>(responseCodes.LOAN_TYPE_NOT_FOUND, HttpStatus.OK);
            } else {

                loan.setApplicationstatus("Active");
                loan.setCurrentApprovalLevel(0);
                loan.setId(Long.parseLong("0"));
                loan.setInterestRate(loantype.getInterestrate());
                //set on excel
                //loan.setApplicationdate(new Date());

                Object test = crudService.save(loan);

                //Get the loan id Saved
                //To be reviewed
                Loanapplication savedLoan = globalFunctions.getLoanByGroupIdMemberNoAndStatus(loan.getLoantypeid(), loan.getGroupid(), loan.getMembercode(), loan.getApprovalStatus());
                LOG.info("The loan id saved is " + savedLoan.getId());
                //Saving gurantors
                for (Loanguarantor loanguarantor : loan.getLoanguarantors()) {
                    loanguarantor.setLonApplicationId(savedLoan.getId());
                    loanguarantor.setId(Long.parseLong("0"));
                    loanguarantor.setDateadded(new Date());
                    loanguarantor.setStatus("Approved");
                    crudService.save(loanguarantor);
                }

                for (LoanSecurity loanSecurity : loan.getLoansecuritys()) {
                    loanSecurity.setLoanApplicationId(Integer.parseInt(savedLoan.getId().toString()));
                    loanSecurity.setId(0);
                    loanSecurity.setGroupId(loan.getGroupid());
                    loanSecurity.setDateCreated(new Date());
                    loanSecurity.setDescription("Loan Securitys.");
                    crudService.save(loanSecurity);
                }

                loan.setId(Long.parseLong(test.toString()));
                processLoanAfterApprovalsDataEntry(loan, token);

                //Creation of loan account
                ApiResponse SUCCESS = responseCodes.SUCCESS;
                SUCCESS.setEntity(test);
                res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    private boolean gurantorSavingsSufficientToLoan(Accounttype savingAccounttype, Loanapplication loan) {
        BigDecimal totalGurantorSavings = BigDecimal.ZERO;
        for (Loanguarantor loanguarantor : loan.getLoanguarantors()) {
            Memberaccount memberSavingsAccount = globalFunctions.getMemberaccountByAccountTypeAndGroupIdAndMemberCode(savingAccounttype.getTypecode() + "KES", loan.getGroupid(), loanguarantor.getMembercode());

            BigDecimal savingsAccountBalance = memberSavingsAccount.getAccountbalance();
            totalGurantorSavings = totalGurantorSavings.add(savingsAccountBalance);
        }
        if (totalGurantorSavings.longValue() >= loan.getAppliedamount().longValue()) {
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findById(@RequestHeader(value = "Authorization") String authKey, @RequestParam("id") String id) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);

            Loanapplication entity = crudService.findEntity(Long.parseLong(id), Loanapplication.class
            );

            if (entity != null) {
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

    @RequestMapping(value = "/approveLoan", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> approveLoan(@RequestHeader(value = "Authorization") String authKey, @RequestBody ApprovalPostRequest approvalPostRequest) {
        ResponseEntity<?> res = null;
        try {
            List<Long> loanIds = approvalPostRequest.getLoanIds();
            String approvalComments = approvalPostRequest.getApprovalComments();
            String approvalStatus = approvalPostRequest.getApprovalStatus();
            Token token = globalFunctions.parseJWT(authKey);
            //Get Approval Levels for loan application
            //Insert into the approvals table
            //push the loan to the next approval level
            //if last approval level then mark the status to be approved
            //Insert into Loan disbursements table

            for (Long loanid : loanIds) {
                Loanapplication loan = crudService.findEntity(loanid, Loanapplication.class
                );

                if (approvalStatus == null || !(approvalStatus.equals("Approved") || approvalStatus.equals("Rejected"))) {
                    res = new ResponseEntity<>(responseCodes.APPROVAL_NOT_SET, HttpStatus.OK);
                } else if (loan.getApprovalStatus().equals("Approved") || loan.getApprovalStatus().equals("Rejected")) {
                    res = new ResponseEntity<>(responseCodes.LOAN_ALREADY_UNDEGONE_APPROVAL_PROCESS, HttpStatus.OK);
                } else if (!globalFunctions.usersApprovalLevelPerRecord(loan.getId().intValue(), "LOAN_APPLICATION", token.getUserid()).isEmpty()) {
                    LOG.error("Loan had previously approved");
                    res = new ResponseEntity<>(responseCodes.USER_HAD_PREVIOUSLY_APPROVED, HttpStatus.OK);
                } else {
                    GroupApprovalLevelsConfig approvalLevelsConfig = globalFunctions.getGroupsApprovalLevelsConfigByType("LOAN_APPLICATION", loan.getGroupid());
                    if (approvalLevelsConfig != null) {
                        String[] levels = approvalLevelsConfig.getTypeApprovals().split(",");
                        if (levels.length > 0) {
                            if (approvalStatus.equals("Reject")) {
                                //flag loan as rejected
                                loan.setApprovalStatus("Rejected");
                                crudService.saveOrUpdate(loan);
                                //send notification to the member
                                //enter rejection to approvals table
                                globalFunctions.logApprovals(loan.getCurrentApprovalLevel(), loan.getGroupid(), approvalComments, loan.getApprovalStatus(), loan.getAppliedamount(), loan.getAppliedamount(), loan.getId(), "LOAN_APPLICATION", Integer.parseInt(token.getUserid()));
                            } else {//Action Approve
                                Integer currentApprovalLevel = loan.getCurrentApprovalLevel();
                                //Get position of current approval level
                                int indexOfCurrentApproval = globalFunctions.postionOfItemInArray(levels, currentApprovalLevel);
                                Integer lastApprovalLevel = Integer.parseInt(levels[levels.length - 1]);
                                boolean islastApproval = indexOfCurrentApproval + 1 == levels.length;
                                Integer nextApprovalLevel = islastApproval ? Integer.parseInt(levels[indexOfCurrentApproval]) : Integer.parseInt(levels[indexOfCurrentApproval + 1]);
                                LOG.info("Current Approval Level:" + currentApprovalLevel + " Next Approval level " + nextApprovalLevel + " Last approval Level: " + lastApprovalLevel);
                                //Mark the record as approved if its the last record else mark it pending
                                if (islastApproval) {
                                    loan.setApprovalStatus("Approved");
                                } else {
                                    loan.setApprovalStatus("Pending");
                                }

                                loan.setCurrentApprovalLevel(nextApprovalLevel);
                                loan.setUpdatedBy(Integer.parseInt(token.getUserid()));
                                crudService.saveOrUpdate(loan);
                                globalFunctions.logApprovals(currentApprovalLevel, loan.getGroupid(), approvalComments, "Approved", loan.getAppliedamount(), loan.getAppliedamount(), loan.getId(), "LOAN_APPLICATION", Integer.parseInt(token.getUserid()));
                            }

                            //if loan is approved initiated disbursement
                            if (loan.getApprovalStatus().equalsIgnoreCase("Approved")) {
                                processLoanAfterApprovals(loan, token);
                            }

                            //Send notification to next apprvers
                            if (loan.getApprovalStatus().equals("Pending")) {
                                String nextApprovalPermissionName = globalFunctions.resolveApprovalPermissionByLevel(loan.getCurrentApprovalLevel(), "LOAN_APPLICATION");
                                List<Users> nextApproversList = globalFunctions.getUsersWithParticularPermissionAndHaveNotApproved(loan.getGroupid(), nextApprovalPermissionName,"LOAN_APPLICATION",loan.getId().intValue());
                                Invgroup group = globalFunctions.getGroupById(token.getGroupID());
                                for (Users user : nextApproversList) {
                                    MessagePayload messagePayload = new MessagePayload();
                                    String message = "Dear " + user.getUserName() + ", you have some loan records pending your action.";
                                    messagePayload.setMessage(message);
                                    messagePayload.setToPhone(user.getPhoneNumber());
                                    messagePayload.setToEmail(user.getEmail());
                                    messagePayload.setSubject("Loan Approval Notification");
                                    messagePayload.setUserName(group.getMessagingUsername());
                                    messagePayload.setApiKey(group.getMessagingKey());
                                    messagePayload.setMessageMode("S");
                                    exec_service.execute(new SendNotification(resttemplateService, env, messagePayload));

                                }
                            } else if (loan.getApprovalStatus().equals("Approved")) {
                                //Send notification to disburment approval team
                                String nextApprovalPermissionName = globalFunctions.resolveApprovalPermissionByLevel(1, "LOAN_DISBURSEMENT");
                                List<Users> nextApproversList = globalFunctions.getUsersWithParticularPermission(loan.getGroupid(), nextApprovalPermissionName);
                                Invgroup group = globalFunctions.getGroupById(token.getGroupID());
                                for (Users user : nextApproversList) {
                                    MessagePayload messagePayload = new MessagePayload();
                                    String message = "Dear " + user.getUserName() + ", you have some disbursement records pending your action.";
                                    messagePayload.setMessage(message);
                                    messagePayload.setToPhone(user.getPhoneNumber());
                                    messagePayload.setToEmail(user.getEmail());
                                    messagePayload.setSubject("Disburmsent Approval Notification");
                                    messagePayload.setUserName(group.getMessagingUsername());
                                    messagePayload.setApiKey(group.getMessagingKey());
                                    messagePayload.setMessageMode("S");
                                    exec_service.execute(new SendNotification(resttemplateService, env, messagePayload));

                                }
                            }
                        }

                    }
                    ApiResponse SUCCESS = responseCodes.SUCCESS;
                    SUCCESS.setEntity(loanIds);
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

    @RequestMapping(value = "/testApprovalNotificaiton", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> testApprovalNotificaiton(@RequestHeader(value = "Authorization") String authKey, @RequestBody Loanapplication loan) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            String nextApprovalPermissionName = globalFunctions.resolveApprovalPermissionByLevel(loan.getCurrentApprovalLevel(), "LOAN_APPLICATION");
            List<Users> nextApproversList = globalFunctions.getUsersWithParticularPermission(loan.getGroupid(), nextApprovalPermissionName);
            Invgroup group = globalFunctions.getGroupById(token.getGroupID());
            for (Users user : nextApproversList) {
                MessagePayload messagePayload = new MessagePayload();
                String message = "Dear " + user.getUserName() + ", you have some loan records pending your action.";
                messagePayload.setMessage(message);
                messagePayload.setToPhone(user.getPhoneNumber());
                messagePayload.setToEmail(user.getEmail());
                messagePayload.setSubject("Loan Approval Notification");
                messagePayload.setUserName(group.getMessagingUsername());
                messagePayload.setApiKey(group.getMessagingKey());
                messagePayload.setMessageMode("S");
                exec_service.execute(new SendNotification(resttemplateService, env, messagePayload));

            }

            ApiResponse SUCCESS = responseCodes.SUCCESS;
            SUCCESS.setEntity(loan);
            res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> update(@RequestHeader(value = "Authorization") String authKey, @RequestBody Loanapplication loan) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            loan.setUpdatedBy(Integer.parseInt(token.getUserid()));

            crudService.saveOrUpdate(loan);

            ApiResponse SUCCESS = responseCodes.SUCCESS;
            SUCCESS.setEntity(loan);
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
            @RequestBody Loanapplication loan
    ) {
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

//            String q = "select r from Loanapplication r ";
//            List<Loanapplication> entity = crudService.fetchWithHibernateQuery(q, Collections.EMPTY_MAP, start, end);
            String addedQueryfilter = "";
            Map<String, Object> params = new HashMap<>();
            List<Loanapplication> entity = null;
            if (token.getIsadminagent().equals("NO")) {
                addedQueryfilter = " WHERE r.groupid =:id ";
                params = Collections.singletonMap("id", token.getGroupID());
            }

            String q = "select r from Loanapplication r  " + addedQueryfilter;
            if (token.getIsadminagent().equals("NO")) {
                entity = crudService.fetchWithHibernateQuery(q, params, start, end);
            } else {
                entity = crudService.fetchWithHibernateQuery(q, Collections.EMPTY_MAP, start, end);
            }

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

    @RequestMapping(value = "/findByApprovalLevel", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findByApprovalLevel(@RequestHeader(value = "Authorization") String authKey, @RequestParam("currentApprovalLevel") Integer currentApprovalLevel, @RequestParam("approvalStatus") String approvalStatus, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "select r from Loanapplication r where currentApprovalLevel=:currentApprovalLevel and approvalStatus=:approvalStatus ";
            Map<String, Object> map = new HashMap<>();
            map.put("currentApprovalLevel", currentApprovalLevel);
            map.put("approvalStatus", approvalStatus);
            List<Loanapplication> entity = crudService.fetchWithHibernateQuery(q, map, start, end);

            //List<LoanReport> entity = loanRepository.findCounties();
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

    @RequestMapping(value = "/findByMemberCodeOrPhoneOrIdNumber", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findByMemberCodeOrPhoneOrIdNumber(@RequestHeader(value = "Authorization") String authKey,
            @RequestParam("searchParam") String searchParam, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "select r from Loanapplication r where membercode=:searchParam or"
                    + " membercode in (select membercode from Groupmember p where telephone=:searchParam or idnumber=:searchParam)";
            Map<String, Object> map = new HashMap<>();
            map.put("searchParam", searchParam);
            List<Loanapplication> entity = crudService.fetchWithHibernateQuery(q, map, start, end);

            //List<LoanReport> entity = loanRepository.findCounties();
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

    @RequestMapping(value = "/checkIfuserHadAlreadyApproved", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> checkIfuserHadAlreadyApproved(@RequestHeader(value = "Authorization") String authKey, @RequestParam("loanApplicationId") int loanApplicationId) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);
            List<ApprovalsDone> entity = globalFunctions.usersApprovalLevelPerRecord(loanApplicationId, "LOAN_APPLICATION", token.getUserid());
            if (entity.isEmpty()) {
                LOG.info("Fetched List of:" + entity.size());
                ApiResponse SUCCESS = responseCodes.SUCCESS;
                SUCCESS.setEntity(entity);
                res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);

            } else {
                LOG.error("Loan had previously approved");
                res = new ResponseEntity<>(responseCodes.USER_HAD_PREVIOUSLY_APPROVED, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/findByLoantypeId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findByLoantypeId(@RequestHeader(value = "Authorization") String authKey, @RequestParam("loanTypeId") int loantypeid) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "select r from Loanapplication r where loantypeid = :loantypeid";

            Map<String, Object> params = new HashMap<>();
            params.put("loantypeid", loantypeid);
            List<Loanapplication> entity = crudService.fetchWithHibernateQuery(q, params);

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

    @RequestMapping(value = "/findLoanDetailsByLoanApplicaitonId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findByLoanApplicaitonId(@RequestHeader(value = "Authorization") String authKey, @RequestParam("lonApplicationId") Long lonApplicationId, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String q = "select r from Loandetails r where applicationid=:applicationid";
            Map<String, Object> params = new HashMap<>();
            params.put("applicationid", lonApplicationId);
            List<Loandetails> entity = crudService.fetchWithHibernateQuery(q, params, start, end);

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

    @RequestMapping(value = "/generateLoanSchedule", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> generateLoanSchedule(@RequestHeader(value = "Authorization") String authKey, @RequestParam("intestRatePerYear") BigDecimal intestRatePerYear, @RequestParam("repaymentDurationInMonths") double repaymentDurationInMonths, @RequestParam("principal") BigDecimal principal, @RequestParam("startDate") String dateString) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        //date format 01/02/2018
        try {
            Token token = globalFunctions.parseJWT(authKey);
            LoanScheduleEnvelope amortizationSchedule = globalFunctions.amortizationSchedule(dateString, principal, intestRatePerYear, repaymentDurationInMonths, Integer.parseInt(token.getUserid()));

            ApiResponse SUCCESS = responseCodes.SUCCESS;
            SUCCESS.setEntity(amortizationSchedule);
            res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Error Occured:" + ex.getMessage());
            res = new ResponseEntity<>(responseCodes.EXCEPTION_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    public void processLoanAfterApprovals(Loanapplication loan, Token token) {
        // loan.setCurrentApprovalLevel(loan.getC);
        if (loan.getCurrentApprovalLevel() == 0) {
            loan.setApplicationstatus("Approved");
            loan.setApprovedamount(loan.getAppliedamount());
            loan.setApprovedby(new BigInteger(token.getUserid()));
            String approvalComments = "Loan Automaticaly approved as it has no approval levels";
            globalFunctions.logApprovals(loan.getCurrentApprovalLevel(), loan.getGroupid(), approvalComments, loan.getApprovalStatus(), loan.getAppliedamount(), loan.getAppliedamount(), loan.getId(), "LOAN_APPLICATION", Integer.parseInt(token.getUserid()));
            globalFunctions.createDisbursement(loan, token);
        } else {
            loan.setApprovedamount(loan.getAppliedamount());
            loan.setApprovedby(new BigInteger(token.getUserid()));
            globalFunctions.createDisbursement(loan, token);
        }
    }

    public void processLoanAfterApprovalsDataEntry(Loanapplication loan, Token token) {
        loan.setApplicationstatus("Active");
        loan.setApprovedamount(loan.getAppliedamount());
        loan.setApprovedby(new BigInteger(token.getUserid()));
//            String approvalComments = "Loan Automaticaly approved during data entry of exising loans.";
//            globalFunctions.logApprovals(loan.getCurrentApprovalLevel(), loan.getGroupid(), approvalComments, loan.getApprovalStatus(), loan.getAppliedamount(), loan.getAppliedamount(), loan.getId(), "LOAN_APPLICATION", Integer.parseInt(token.getUserid()));
        globalFunctions.createDisbursementDuringDataEntry(loan, token);

    }

    @RequestMapping(value = "/loanMonthlyScheduleByLoanApplicationId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> loanMonthlyScheduleByLoanApplicationId(@RequestHeader(value = "Authorization") String authKey, @RequestParam("loanApplicationId") Integer loanApplicationId) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);
            LOG.info("Getting loan application loan schedule for:" + loanApplicationId);

            String addedQueryfilter = "";
            Map<String, Object> params = new HashMap<>();
            List<LonSchedule> entity = null;

            String q = "select r from LonSchedule r WHERE loanApplicationId=:loanApplicationId ";
            params = Collections.singletonMap("loanApplicationId", loanApplicationId);

            entity = crudService.fetchWithHibernateQuery(q, params, 0, 1000);

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
