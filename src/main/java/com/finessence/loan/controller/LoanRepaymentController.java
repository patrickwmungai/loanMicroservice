package com.finessence.loan.controller;

import com.finessence.loan.entities.Accounttype;
import com.finessence.loan.entities.Invgroup;
import com.finessence.loan.entities.LoanRepayment;
import com.finessence.loan.entities.Loanapplication;
import com.finessence.loan.entities.Loandetails;
import com.finessence.loan.entities.LonSchedule;
import com.finessence.loan.entities.Memberaccount;
import com.finessence.loan.entities.views.LoanDetailsSchedule;
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
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author patrick
 */
@RestController
@RequestMapping("/loanRepayment")
public class LoanRepaymentController {

    private final Logger LOG = Logger.getLogger(LoanRepaymentController.class);

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
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String authKey, @RequestBody LoanRepayment loanRepayment) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            //Get Loan Details
            Loandetails loandetails = globalFunctions.getLoanDetailsByLoanApplicationById(new BigInteger(String.valueOf(loanRepayment.getLoanApplicationId())));
            List<LonSchedule> schedulesDue = globalFunctions.getDueLoanScheduleByLoanApplicationId(loanRepayment.getLoanApplicationId());

            if (schedulesDue.isEmpty()) {
                if (loandetails == null || schedulesDue.isEmpty()) {
                    res = new ResponseEntity<>(responseCodes.LOAN_DETAILS_NOT_FOUND, HttpStatus.OK);
                } else {
                    //get Wallet account balance
                    Accounttype accountTypeSettlement = globalFunctions.getAccountByName("SETTLEMENT_ACCOUNT");
                    Memberaccount memberLoanSettlementAccount = globalFunctions.getMemberaccountByAccountTypeAndAccountGroupId(accountTypeSettlement.getTypecode() + "KES", loandetails.getGroupid());

                    if (memberLoanSettlementAccount == null) {
                        res = new ResponseEntity<>(responseCodes.SETTLEMENT_ACCOUNT_NOT_FOUND, HttpStatus.OK);
                    } else {
                        Double amountDue = globalFunctions.amountToPay(schedulesDue);
                        //Check If Payment amount is sufficient in settlement account
                        if (Integer.parseInt(memberLoanSettlementAccount.getAccountbalance().toString()) < amountDue) {
                            ApiResponse VALIDATION_FAIL = responseCodes.INSUFFICIENT_BALANCE_FOR_LOAN_REPAYMENT;
                            String errorMsg = VALIDATION_FAIL.getResponseDescription().replace("<RAMOUNT>", memberLoanSettlementAccount.getAccountbalance().toString()).replace("<IAMOUNT>", amountDue.toString());
                            VALIDATION_FAIL.setResponseDescription(errorMsg);
                            res = new ResponseEntity<>(VALIDATION_FAIL, HttpStatus.OK);

                        } else {
                            loanRepayment.setCreatedBy(Integer.parseInt(token.getUserid()));
                            loanRepayment.setDateCreated(new Date());
                            loanRepayment.setId(0);

                            Object test = crudService.save(loanRepayment);
                            //Loan Schedule
                            //Credit loan account
                            Invgroup invgroup = globalFunctions.getGroupById(loandetails.getGroupid());
                            Accounttype accountTypeLoan = globalFunctions.getAccountByName("LOAN");
                            String accountid = accountTypeLoan.getTypecode() + loandetails.getMembercode() + invgroup.getGroupcode() + loandetails.getApplicationid();
                            Memberaccount memberLoanAccount = globalFunctions.getMemberaccountByAccountNo(accountid);
                            globalFunctions.creditAccount(memberLoanAccount, loandetails.getInstallmentamount(), "Loan Repayment Credit");
                            //Debit current 
                            globalFunctions.debitAccount(memberLoanSettlementAccount, loandetails.getInstallmentamount(), "Debit customer settlement account to repay loan account " + memberLoanAccount.getAccountid());
                            //Update loan details
                            Loanapplication loanapplication = crudService.findEntity(Long.parseLong(loandetails.getApplicationid().toString()), Loanapplication.class);

                            loandetails.setLastpaymentamount(loandetails.getInstallmentamount());
                            loandetails.setLastpaymentdate(new Date());
                            loandetails.setLoanbalance(loandetails.getLoanbalance().subtract(loandetails.getInstallmentamount()));
                            Calendar c = new GregorianCalendar();
                            c.add(Calendar.DATE, 30);
                            Date dPlus30Days = c.getTime();
                            loandetails.setNextpaymentdate(dPlus30Days);
                            BigInteger monthlyInterest = loandetails.getInteresttotal().divide(new BigInteger(loanapplication.getRepaymentPeriod().toString()));
                            loandetails.setTotalinterestpaid(loandetails.getTotalinterestpaid().add(monthlyInterest));
                            loandetails.setTotalpriciplepaid(loandetails.getTotalpriciplepaid().add(loandetails.getInstallmentamount()));
                            crudService.saveOrUpdate(loandetails);
                            //to add interst paid and 
                            //Update the loan schedule
                            globalFunctions.updateLoanMonthlySchedule(schedulesDue);
                            
                            if (test != null) {
                                ApiResponse SUCCESS = responseCodes.SUCCESS;
                                SUCCESS.setEntity(test);
                                res = new ResponseEntity<>(SUCCESS, HttpStatus.OK);
                            } else {
                                LOG.error("Creation failed");
                                res = new ResponseEntity<>(responseCodes.CREATION_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                        }
                    }
                }
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

            LoanRepayment entity = crudService.findEntity(Integer.parseInt(id), LoanRepayment.class
            );

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
    public ResponseEntity<?> update(@RequestHeader(value = "Authorization") String authKey, @RequestBody LoanRepayment loanRepayment) {
        ResponseEntity<?> res = null;
        try {
            Token token = globalFunctions.parseJWT(authKey);
            loanRepayment.setUpdatedBy(Integer.parseInt(token.getUserid()));

            crudService.saveOrUpdate(loanRepayment);

            ApiResponse SUCCESS = responseCodes.SUCCESS;
            SUCCESS.setEntity(loanRepayment);
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
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String authKey, @RequestBody LoanRepayment loanRepayment) {
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

//            String q = "select r from LoanRepayment r ";
//            List<LoanRepayment> entity = crudService.fetchWithHibernateQuery(q, Collections.EMPTY_MAP, start, end);
            String addedQueryfilter = "";
            Map<String, Object> params = new HashMap<>();
            List<LoanRepayment> entity = null;
            if (token.getIsadminagent().equals("NO")) {
                addedQueryfilter = " WHERE r.groupId =:id ";
                params = Collections.singletonMap("id", token.getGroupID());
            }

            String q = "select r from LoanRepayment r  " + addedQueryfilter;
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

    @RequestMapping(value = "/loanRepaymentSchedule", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> loanRepaymentSchedule(@RequestHeader(value = "Authorization") String authKey, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);

            String addedQueryfilter = "";
            Map<String, Object> params = new HashMap<>();
            List<LoanDetailsSchedule> entity = null;
            if (token.getIsadminagent().equals("NO")) {
                addedQueryfilter = " AND r.groupid =:id ";
                LOG.info("Group id is:" + token.getGroupID());
                params = Collections.singletonMap("id", token.getGroupID());
            }

            String q = "select r from LoanDetailsSchedule r WHERE nextpaymentamount > 0" + addedQueryfilter;
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

            String q = "select r from LoanRepayment r where groupId=:groupId";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("groupId", groupId);
            List<LoanRepayment> entity = crudService.fetchWithHibernateQuery(q, params, start, end);

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

            String q = "select r from LoanRepayment r where loanApplicationId=:loanApplicationId";
            Map<String, Object> params = new HashMap<>();
            params.put("loanApplicationId", loanApplicationId);
            List<LoanRepayment> entity = crudService.fetchWithHibernateQuery(q, params, start, end);

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

    @RequestMapping(value = "/loanMonthlySchedule", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> loanMonthlySchedule(@RequestHeader(value = "Authorization") String authKey, @RequestParam("month") String month, @RequestParam("year") String year, @RequestParam("start") int start, @RequestParam("end") int end) {
        ResponseEntity<?> res = null;
        //return record set and total count of rows on the entity
        try {
            Token token = globalFunctions.parseJWT(authKey);
            LOG.info("TESTING HERE GROUP ID:" + token.getGroupID());

            String addedQueryfilter = "";
            Map<String, Object> params = new HashMap<>();
            List<LonSchedule> entity = null;
            if (token.getIsadminagent().equals("NO")) {
                addedQueryfilter = " AND r.groupId =:id ";
                LOG.info("Group id is:" + token.getGroupID());
                params = Collections.singletonMap("id", token.getGroupID());
            }

            String q = "select r from LonSchedule r WHERE MONTH(paymentDate)='" + month + "' and YEAR(paymentDate)='" + year + "' " + addedQueryfilter;
//            params = Collections.singletonMap("month", month);
//            params = Collections.singletonMap("year", year);

            entity = crudService.fetchWithHibernateQuery(q, params, start, end);

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
