package com.finessence.loan.services;

import com.finessence.loan.entities.Accounttype;
import com.finessence.loan.entities.ApprovalsDone;
import com.finessence.loan.entities.GroupApprovalLevel;
import com.finessence.loan.entities.GroupApprovalLevelsConfig;
import com.finessence.loan.entities.Groupmember;
import com.finessence.loan.entities.InsuranceFeeConfigs;
import com.finessence.loan.entities.Invgroup;
import com.finessence.loan.entities.LoanDisbursements;
import com.finessence.loan.entities.LoanRepayment;
import com.finessence.loan.entities.Loanapplication;
import com.finessence.loan.entities.Loandetails;
import com.finessence.loan.entities.Loanguarantor;
import com.finessence.loan.entities.Loantype;
import com.finessence.loan.entities.LonSchedule;
import com.finessence.loan.entities.Memberaccount;
import com.finessence.loan.entities.Permission;
import com.finessence.loan.entities.Role;
import com.finessence.loan.entities.Transactiondetails;
import com.finessence.loan.entities.Users;
import com.finessence.loan.model.ApiResponse;
import com.finessence.loan.model.LoanScheduleEnvelope;
import com.finessence.loan.model.ResponseCodes;
import com.finessence.loan.model.Token;
import com.finessence.loan.repository.CrudService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.stream.DoubleStream;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.formula.functions.FinanceLib;
import org.jboss.logging.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author patrick
 */
@Service("functions")
public class GlobalFunctions {

    private final Logger LOG = Logger.getLogger(GlobalFunctions.class);

    @Autowired
    CrudService crudService;

    @Autowired
    ResponseCodes responseCodes;

    //function to decode Jwt
    public Token parseJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("KEY123456"))
                .parseClaimsJws(jwt).getBody();

        Token token = new Token();
        token.setGroupID(claims.get("groupid", String.class));
        token.setUserid(claims.get("userid", String.class));
        token.setIsadminagent(claims.get("isadmingroup", String.class));

        return token;

    }

    public boolean checkifAnyOfGurantorsIsTheGuyApplyingLoan(List<Loanguarantor> gurantors, String currentMemberCode) {
        boolean isApplicant = false;
        for (Loanguarantor loanguarantor : gurantors) {
            if (loanguarantor.getMembercode().equals(currentMemberCode)) {
                isApplicant = true;
                break;
            }
        }
        return isApplicant;
    }

    public void logApprovals(Integer approvalLevel, String groupId, String approvalComments, String approvalStatus, BigDecimal approvedAmount, BigDecimal originalAmount, Long recordId, String recordType, Integer approverId) {
        GroupApprovalLevel groupApprovalLevel = getGroupApprovalLevel(groupId, approvalLevel);

        ApprovalsDone approvalsDone = new ApprovalsDone();
        approvalsDone.setId(0);
        approvalsDone.setApprovalLevel(approvalLevel);
        approvalsDone.setGroupId(groupId);
        approvalsDone.setApprovalLevelName(groupApprovalLevel.getName());
        approvalsDone.setApprovalNotes(approvalComments);
        approvalsDone.setApprovalStatus(approvalStatus);
        approvalsDone.setApprovalTime(new Date());
        approvalsDone.setApprovedAmount(approvedAmount);
        approvalsDone.setOriginalAmount(originalAmount);
        approvalsDone.setRecordId(Integer.parseInt(recordId.toString()));
        approvalsDone.setType(recordType);
        approvalsDone.setApproverId(approverId);

        crudService.save(approvalsDone);
    }

    public void createDisbursement(Loanapplication loanapplication, Token token) {
        LoanDisbursements loanDisbursements = new LoanDisbursements();
        loanDisbursements.setId(0);
        loanDisbursements.setLoanApplicationId(Integer.parseInt(loanapplication.getId().toString()));
        loanDisbursements.setCreatedBy(Integer.parseInt(token.getUserid()));
        loanDisbursements.setDateCreated(new Date());
        loanDisbursements.setGroupId(loanapplication.getGroupid());
        loanDisbursements.setAmount(loanapplication.getApprovedamount());

        boolean disbursementHasNoApprovalLevels = false;
        //Get approval levels if any then set
        GroupApprovalLevelsConfig approvalLevelsConfig = getGroupsApprovalLevelsConfigByType("LOAN_DISBURSEMENT", loanapplication.getGroupid());
        if (approvalLevelsConfig != null) {
            String[] levels = approvalLevelsConfig.getTypeApprovals().split(",");
            if (levels.length > 0) {
                loanDisbursements.setCurrentApprovalLevel(Integer.parseInt(levels[0]));
                loanDisbursements.setApprovalStatus("Pending");
            } else {
                disbursementHasNoApprovalLevels = true;
            }
        } else {
            disbursementHasNoApprovalLevels = true;
        }
        if (disbursementHasNoApprovalLevels) {
            //Automatically approve disbursement
            loanDisbursements.setCurrentApprovalLevel(0);
            loanDisbursements.setApprovalStatus("Approved");
            loanDisbursements.setApprovedBy(token.getUserid());
            loanDisbursements.setDateApproved(new Date());
            crudService.save(loanDisbursements);

            processApprovedDisbursement(loanDisbursements, loanapplication, token);

        } else {
            crudService.save(loanDisbursements);
        }

    }

    public void createDisbursementDuringDataEntry(Loanapplication loanapplication, Token token) {
        LoanDisbursements loanDisbursements = new LoanDisbursements();
        loanDisbursements.setId(0);
        loanDisbursements.setLoanApplicationId(Integer.parseInt(loanapplication.getId().toString()));
        loanDisbursements.setCreatedBy(Integer.parseInt(token.getUserid()));
        loanDisbursements.setDateCreated(new Date());
        loanDisbursements.setGroupId(loanapplication.getGroupid());
        loanDisbursements.setAmount(loanapplication.getApprovedamount());

        //Automatically approve disbursement
        loanDisbursements.setCurrentApprovalLevel(0);
        loanDisbursements.setApprovalStatus("Approved");
        loanDisbursements.setApprovedBy(token.getUserid());
        loanDisbursements.setDateApproved(new Date());

        crudService.save(loanDisbursements);
        LoanDisbursements loanDisbursements1 = getDisbursementByLoanId(loanapplication.getId());
        loanDisbursements.setId(loanDisbursements1.getId());
        processApprovedDisbursementDuringDataEntry(loanDisbursements, loanapplication, token);

    }

    public void processApprovedDisbursement(LoanDisbursements loanDisbursements, Loanapplication loanapplication, Token token) {
        //create loan details
        Memberaccount loanAccount = createLoanAccount(loanapplication);
        //create loan details account
        createLoanDetails(loanapplication, loanDisbursements, token);

        //Call accounts function for accounting entries
        //Debit loan account
        debitAccount(loanAccount, loanapplication.getAppliedamount(), "Loan Disbursment Debit");
        //credit current 
        Accounttype accountTypeCurrent = getAccountByName("CURRENT");
        Memberaccount memberCurrentAccount = getMemberaccountByAccountTypeAndAccountGroupId(accountTypeCurrent.getTypecode() + "KES", loanapplication.getGroupid());
        creditAccount(memberCurrentAccount, loanapplication.getAppliedamount(), "Credit customer loan account from loan account " + loanAccount.getAccountid());
        //Get charges
        //Get accounts
        //Get loan type to check the fees to be charged
        Loantype loantype = getLoantype(loanapplication.getLoantypeid().toString());
        if (loantype != null) {
            String applicaitonfeeType = loantype.getApplicationfeetype() == null ? "Amount" : loantype.getApplicationfeetype();
            String insurancefeeType = loantype.getInsurancefeetype() == null ? "Amount" : loantype.getApplicationfeetype();
            BigDecimal applicationfee = loantype.getApplicationfee() == null ? BigDecimal.ZERO : applicaitonfeeType.equals("Amount") ? loantype.getApplicationfee() : ((loantype.getApplicationfee().divide(new BigDecimal("100"))).multiply(loanapplication.getAppliedamount()));
            BigDecimal insurancefee = getInsuranceFeeByGroupIdAndAmount(loanapplication.getGroupid(), loanapplication.getAppliedamount(), loanapplication.getRepaymentPeriod());// loantype.getInsurancefee() == null ? BigDecimal.ZERO : insurancefeeType.equals("Amount") ? loantype.getInsurancefee() : ((loantype.getInsurancefee().divide(new BigDecimal("100"))).multiply(loanapplication.getAppliedamount()));
            if (applicationfee.compareTo(BigDecimal.ZERO) == 1) {
                //get application  fee account
                Accounttype accounttype = getAccountByName("APPLICATION_FEE");
                Memberaccount applicationFeeAccount = getMemberaccountByAccountTypeAndAccountGroupId(accounttype.getTypecode() + "KES", loanapplication.getGroupid());

                //Debit wallet Account applicationFeeAccount
                debitAccount(memberCurrentAccount, applicationfee, "Loan Application fee payment to account " + applicationFeeAccount.getAccountid());
                //credit applicationFeeAccount
                creditAccount(applicationFeeAccount, applicationfee, "Loan Application fee from member account " + memberCurrentAccount.getAccountid());

            }

            if (insurancefee.compareTo(BigDecimal.ZERO) == 1) {
                //get Insurance  fee account
                Accounttype accounttype = getAccountByName("INSURANCE_FEE");
                Memberaccount insuranceFeeAccount = getMemberaccountByAccountTypeAndAccountGroupId(accounttype.getTypecode() + "KES", loanapplication.getGroupid());

                //Debit wallet Account applicationFeeAccount
                debitAccount(memberCurrentAccount, insurancefee, "Loan Insurance fee payment to account " + insuranceFeeAccount.getAccountid());
                //credit insuranceFeeAccount
                creditAccount(insuranceFeeAccount, insurancefee, "Loan Insurance fee from member account " + memberCurrentAccount.getAccountid());

            }

            //Insert loan Schedule
            DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("dd/MM/YYYY");
            String dateCreated = DATE_TIME_FORMATTER.print(new Date().getTime());
            LoanScheduleEnvelope amortizationSchedule = amortizationSchedule(dateCreated, loanapplication.getAppliedamount(), loanapplication.getInterestRate(), loanapplication.getRepaymentPeriod(), Integer.parseInt(token.getUserid()));
            for (LonSchedule schedule : amortizationSchedule.getLoanSchedules()) {
                schedule.setCreatedBy(Integer.parseInt(token.getUserid()));
                schedule.setDateCreated(new Date());
                schedule.setId(0);
                schedule.setLoanApplicationId(Integer.parseInt(loanapplication.getId().toString()));
                schedule.setPaidStatus("Pending");
                schedule.setPenalty(BigDecimal.ZERO);
                schedule.setGroupId(token.getGroupID());
                crudService.save(schedule);
            }
            LoanDisbursements loanDisbursements1 = getDisbursementByLoanId(loanapplication.getId());
            //Update status to Disbursed
            loanDisbursements.setId(loanDisbursements1.getId());
            loanDisbursements.setApprovalStatus("Disbursed");
            crudService.saveOrUpdate(loanDisbursements);

        } else {
            LOG.info("loan type is null");
        }

    }

    public void processApprovedDisbursementDuringDataEntry(LoanDisbursements loanDisbursements, Loanapplication loanapplication, Token token) {
        Accounttype accountTypeCurrent = getAccountByName("CURRENT");
        Memberaccount memberCurrentAccount = getMemberaccountByAccountTypeAndAccountGroupId(accountTypeCurrent.getTypecode() + "KES", loanapplication.getGroupid());

        //create loan details
        Memberaccount loanAccount = createLoanAccount(loanapplication);
//        //create loan details account
//        createLoanDetailsNew(loanapplication, loanDisbursements, token);

        //Call accounts function for accounting entries
        //Debit loan account NB with the loan balance as this is during data entry
        debitAccount(loanAccount, loanapplication.getAppliedamount(), "Loan Disbursement Debit");
        //Get charges
        //Get accounts
        //Get loan type to check the fees to be charged
        Loantype loantype = getLoantype(loanapplication.getLoantypeid().toString());
        if (loantype != null) {

            BigDecimal applicationfee = loanapplication.getApplicationFee();// loantype.getApplicationfee() == null ? 0.0 : applicaitonfeeType.equals("Amount") ? loantype.getApplicationfee() : ((loantype.getApplicationfee() / 100) * (Double.parseDouble(loanapplication.getAppliedamount().toString())));
            BigDecimal insurancefee = loanapplication.getInsuranceFee();// loantype.getInsurancefee() == null ? 0.0 : insurancefeeType.equals("Amount") ? loantype.getInsurancefee() : ((loantype.getInsurancefee() / 100) * (Double.parseDouble(loanapplication.getAppliedamount().toString())));
            if (applicationfee.compareTo(BigDecimal.ZERO) == 1) {//fee is greator than 0
                //get application  fee account
                Accounttype accounttype = getAccountByName("APPLICATION_FEE");
                Memberaccount applicationFeeAccount = getMemberaccountByAccountTypeAndAccountGroupId(accounttype.getTypecode() + "KES", loanapplication.getGroupid());

                //credit applicationFeeAccount
                creditAccount(applicationFeeAccount, applicationfee, "Loan Application fee from member account (data entry) " + memberCurrentAccount.getAccountid());

            }

            if (insurancefee.compareTo(BigDecimal.ZERO) == 1) {//fee is greator than 0
                //get Insurance  fee account
                Accounttype accounttype = getAccountByName("INSURANCE_FEE");
                Memberaccount insuranceFeeAccount = getMemberaccountByAccountTypeAndAccountGroupId(accounttype.getTypecode() + "KES", loanapplication.getGroupid());

                //credit insuranceFeeAccount
                creditAccount(insuranceFeeAccount, insurancefee, "Loan Insurance fee from member account (data entry)" + memberCurrentAccount.getAccountid());

            }

            //Insert loan Schedule
            DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("dd/MM/YYYY");

            String dateCreated = DATE_TIME_FORMATTER.print(loanapplication.getApplicationdate().getTime());
            //get inv group date and replace ith the first digits
            Invgroup group = getGroupById(loanapplication.getGroupid());
            String paymentday = String.valueOf(group.getRepaymentDay()).equals("") || String.valueOf(group.getRepaymentDay()).equals("null") ? "24" : String.valueOf(group.getRepaymentDay());
            System.out.println("Payment day is:" + paymentday);
            dateCreated = paymentday + "/" + dateCreated.substring(dateCreated.indexOf("/") + 1);
            System.out.println("Payment day is2:" + paymentday);

            LoanScheduleEnvelope amortizationSchedule = amortizationSchedule(dateCreated, loanapplication.getAppliedamount(), loanapplication.getInterestRate(), loanapplication.getRepaymentPeriod(), Integer.parseInt(token.getUserid()));
            BigDecimal paidAmount = BigDecimal.ZERO;
            BigDecimal paidInterestAmount = BigDecimal.ZERO;
            for (LonSchedule schedule : amortizationSchedule.getLoanSchedules()) {
                schedule.setCreatedBy(Integer.parseInt(token.getUserid()));
                schedule.setDateCreated(new Date());
                schedule.setId(0);
                schedule.setGroupId(token.getGroupID());
                schedule.setLoanApplicationId(Integer.parseInt(loanapplication.getId().toString()));
                if ((schedule.getPaymentDate().getYear() < new Date().getYear()) || ((schedule.getPaymentDate().getYear() == new Date().getYear()) && (schedule.getPaymentDate().getMonth()) < (new Date().getMonth()))) {
                    schedule.setPaidStatus("Paid");
                    paidAmount = paidAmount.add(schedule.getBeginningBalance());//Principal Paid
                    paidInterestAmount = paidInterestAmount.add(schedule.getInterest());
                    loanapplication.setLoanBalance(schedule.getEndingBalance());
                    repayLoanDataEntry(schedule, loanapplication.getMembercode());
                } else {
                    schedule.setPaidStatus("Pending");
                }
                schedule.setPenalty(BigDecimal.ZERO);
                crudService.save(schedule);
            }

            //Update status to Disbursed
            loanDisbursements.setApprovalStatus("Disbursed");
            crudService.saveOrUpdate(loanDisbursements);

            //create loan details account
            createLoanDetailsNew(loanapplication, loanDisbursements, amortizationSchedule, paidAmount, paidInterestAmount, token);

        } else {
            LOG.info("loan type is null");
        }

    }

    public Memberaccount createLoanAccount(Loanapplication loanapplication) {
        Loantype loantype = getLoantype(loanapplication.getLoantypeid().toString());
        Groupmember groupmember = getGroupmember(loanapplication.getMembercode());
        Invgroup invgroup = getGroupById(loanapplication.getGroupid());
        Memberaccount loanaccount = new Memberaccount();
        loanaccount.setId(0);
        loanaccount.setAccountbalance(BigDecimal.ZERO);
        loanaccount.setAccountname(groupmember.getMembername() + " " + loantype.getTypename());
        loanaccount.setAvailablebalance(BigInteger.ZERO);
        loanaccount.setBlockedbalance(BigInteger.ZERO);
        loanaccount.setCurrency("KES");
        loanaccount.setDescription("Loan Account for member");
        loanaccount.setDormantstatus("0");
        loanaccount.setGroupid(loanapplication.getGroupid());
        loanaccount.setMembercode(groupmember.getMembercode());
        loanaccount.setDateopened(new Date());
        Accounttype accounttype = getAccountByName("LOAN");
        String accountid = accounttype.getTypecode() + groupmember.getMembercode() + invgroup.getGroupcode() + loanapplication.getId();
        loanaccount.setAccountid(accountid);
        loanaccount.setAccounttype(accounttype.getTypecode() + "KES");
        loanaccount.setGlcode("01A");

        crudService.save(loanaccount);
        Memberaccount memberaccount = getMemberaccountByAccountNo(accountid);
        loanaccount.setId(memberaccount.getId());
        //update loan with the created loan account
        loanapplication.setLoanAccountNo(accountid);
        crudService.saveOrUpdate(loanapplication);

        //Create Settlement account
        Memberaccount settlementAccount = new Memberaccount();
        settlementAccount.setId(0);
        settlementAccount.setAccountbalance(BigDecimal.ZERO);
        settlementAccount.setAccountname(groupmember.getMembername() + " " + loantype.getTypename() + "Settlement Account");
        settlementAccount.setAvailablebalance(BigInteger.ZERO);
        settlementAccount.setBlockedbalance(BigInteger.ZERO);
        settlementAccount.setCurrency("KES");
        settlementAccount.setDescription("Loan Settlement for member");
        settlementAccount.setDormantstatus("0");
        settlementAccount.setGroupid(loanapplication.getGroupid());
        settlementAccount.setMembercode(groupmember.getMembercode());
        settlementAccount.setDateopened(new Date());
        Accounttype setttlementAccounttype = getAccountByName("SETTLEMENT_ACCOUNT");
        String settleemntAccountid = setttlementAccounttype.getTypecode() + groupmember.getMembercode() + invgroup.getGroupcode() + loanapplication.getId();
        settlementAccount.setAccountid(settleemntAccountid);
        settlementAccount.setAccounttype(setttlementAccounttype.getTypecode() + "KES");
        settlementAccount.setGlcode("01A");
        crudService.save(settlementAccount);

        //update loan with the created settlement account
        loanapplication.setLoanSettlementAccountNo(settleemntAccountid);
        crudService.saveOrUpdate(loanapplication);

        //return loan account to be debited
        return loanaccount;
    }

    public void creditAccount(Memberaccount memberaccount, BigDecimal amountToCredit, String description) {
        memberaccount.setAccountbalance(memberaccount.getAccountbalance().add(amountToCredit));
        crudService.saveOrUpdate(memberaccount);

        //Accounting entris
        //Credit  leg to new Investment account
        int randomNo = new Random().nextInt(10000);
        String transactionReference = randomNo + new SimpleDateFormat("ddMMhhmmss").format(new Date());

        Transactiondetails accountCR = new Transactiondetails();
        accountCR.setTransactionid(Long.parseLong("0"));
        accountCR.setTransname(description);
        accountCR.setMembercode(memberaccount.getMembercode());
        accountCR.setTransactor(memberaccount.getMembercode());
        accountCR.setAccountid(memberaccount.getAccountid());
        accountCR.setAmount(amountToCredit);
        accountCR.setGroupid(memberaccount.getGroupid());
        accountCR.setNarration(memberaccount.getAccountname());
        accountCR.setReference(transactionReference);
        accountCR.setTranstype("C");
        accountCR.setPostingdate(new Date());
        accountCR.setRunningBalance(memberaccount.getAccountbalance());

        crudService.save(accountCR);
    }

    public void debitAccount(Memberaccount memberaccount, BigDecimal amountToDebit, String description) {
        memberaccount.setAccountbalance(memberaccount.getAccountbalance().subtract(amountToDebit));
        crudService.saveOrUpdate(memberaccount);

        //Accounting entris
        //Credit  leg to new Investment account
        int randomNo = new Random().nextInt(10000);
        String transactionReference = randomNo + new SimpleDateFormat("ddMMhhmmss").format(new Date());

        Transactiondetails accountDR = new Transactiondetails();
        accountDR.setTransactionid(Long.parseLong("0"));
        accountDR.setTransname(description);
        accountDR.setMembercode(memberaccount.getMembercode());
        accountDR.setTransactor(memberaccount.getMembercode());
        accountDR.setAccountid(memberaccount.getAccountid());
        accountDR.setAmount(amountToDebit);
        accountDR.setGroupid(memberaccount.getGroupid());
        accountDR.setNarration(memberaccount.getAccountname());
        accountDR.setReference(transactionReference);
        accountDR.setTranstype("D");
        accountDR.setPostingdate(new Date());
        accountDR.setRunningBalance(memberaccount.getAccountbalance());

        crudService.save(accountDR);
    }
//100000 applied amount
//Conpute loan intrest 10 % 100000 =10000
//charges 5000
    //principal 100000
    //interest amount 10000
//total amount applied amount + intrest=balance 1100000+10000=110000    
    //during disbursment
    //Disbursment is application amount 100000
    //INSTALLMENTAMOUNT loan bal 110k /period PMT formular Google to calculate
    //INTERESTTOTAL 10000
    //NEXTPAYMENTDATE 30 days from disburment date
    //disbursement amount appliend amount

    public void createLoanDetails(Loanapplication loanapplication, LoanDisbursements loanDisbursements, Token token) {
        Loandetails loandetails = new Loandetails();
        loandetails.setId(Long.parseLong("0"));
        loandetails.setAddedby(token.getUserid());
        loandetails.setApplicationdate(loanapplication.getApplicationdate());
        loandetails.setApplicationid(new BigInteger(loanapplication.getId().toString()));
        loandetails.setGroupid(loanapplication.getGroupid());
        loandetails.setDisbursementamount(loanapplication.getAppliedamount());
        loandetails.setDisbursementdate(loanDisbursements.getDateApproved());
        loandetails.setGroupid(loanapplication.getGroupid());
        BigDecimal interestRate = loanapplication.getInterestRate().divide(new BigDecimal("100"));
        BigDecimal interestAmountTotal = loanapplication.getAppliedamount().multiply(interestRate);
        BigDecimal totalAmount = loanapplication.getAppliedamount().add(interestAmountTotal);
        BigDecimal installementAmount = totalAmount.divide(new BigDecimal(loanapplication.getRepaymentPeriod().longValue()), 2, RoundingMode.HALF_UP);
        loandetails.setInstallmentamount(installementAmount);
        loandetails.setInteresttotal(interestAmountTotal);
        loandetails.setLastpaymentamount(BigDecimal.ZERO);
        loandetails.setLoanaccountid(loanapplication.getLoanAccountNo());
        loandetails.setLoanbalance(totalAmount);
        loandetails.setMembercode(loanapplication.getMembercode());
        loandetails.setNextpaymentamount(installementAmount);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, 30);
        Date dPlus30Days = c.getTime();
        loandetails.setNextpaymentdate(dPlus30Days);
        loandetails.setPrincipletotal(totalAmount);
        loandetails.setTotalinterestpaid(BigDecimal.ZERO);
        loandetails.setTotalpriciplepaid(BigDecimal.ZERO);
        crudService.save(loandetails);
    }

    public void createLoanDetailsNew(Loanapplication loanapplication, LoanDisbursements loanDisbursements, LoanScheduleEnvelope amortizationSchedule, BigDecimal paidAmount, BigDecimal paidInterestAmount, Token token) {
        Loandetails loandetails = new Loandetails();
        loandetails.setId(Long.parseLong("0"));
        loandetails.setAddedby(token.getUserid());
        loandetails.setApplicationdate(loanapplication.getApplicationdate());
        loandetails.setApplicationid(new BigInteger(loanapplication.getId().toString()));
        loandetails.setGroupid(loanapplication.getGroupid());
        loandetails.setDisbursementamount(loanapplication.getAppliedamount());
        loandetails.setDisbursementdate(loanapplication.getApplicationdate());//for data entry only
//        loandetails.setDisbursementdate(loanDisbursements.getDateApproved());//Normal application
        loandetails.setGroupid(loanapplication.getGroupid());
        BigDecimal interestRate = loanapplication.getInterestRate().divide(new BigDecimal("100"));
        BigDecimal interestAmountTotal = amortizationSchedule.getTotalInterest();//Double.parseDouble(loanapplication.getAppliedamount().toString()) * interestRate;
        BigDecimal totalAmount = amortizationSchedule.getTotalPayment();//loanapplication.getAppliedamount().add(new BigInteger(interestAmountTotal.toString().replace(".0", "")));
        BigDecimal installementAmount = amortizationSchedule.getMonthlyPayment();//totalAmount.divide(new BigInteger(loanapplication.getRepaymentPeriod().toString()));
        loandetails.setInstallmentamount(installementAmount);
        loandetails.setInteresttotal(interestAmountTotal);
        loandetails.setLastpaymentamount(installementAmount);
        loandetails.setLoanaccountid(loanapplication.getLoanAccountNo());
        loandetails.setLoanbalance(loanapplication.getLoanBalance());
        loandetails.setMembercode(loanapplication.getMembercode());
        loandetails.setNextpaymentamount(installementAmount);
        //read from configuration
        Invgroup group = getGroupById(loanapplication.getGroupid());

        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_MONTH, group.getRepaymentDay());
        //c.add(Calendar.DATE, 30);
        Date dPlus30Days = c.getTime();
        loandetails.setNextpaymentdate(dPlus30Days);
        ////////////
        loandetails.setPrincipletotal(totalAmount);
        loandetails.setTotalinterestpaid(paidInterestAmount);
        loandetails.setTotalpriciplepaid(paidAmount);
        crudService.save(loandetails);
    }

    public Loantype getLoantype(String loanTypeId) {
        //get approva level name
        String q = "select r from Loantype r where id = :id ";

        Map<String, Object> params = new HashMap<>();
        params.put("id", Long.parseLong(loanTypeId));
        List<Loantype> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public Loanapplication getLoanApplicationById(String loanApplicationId) {
        //get approva level name
        String q = "select r from Loanapplication r where id = :id ";

        Map<String, Object> params = new HashMap<>();
        params.put("id", Long.parseLong(loanApplicationId));
        List<Loanapplication> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public Loandetails getLoanDetailsByLoanApplicationById(BigInteger loanApplicationId) {
        //get approva level name
        String q = "select r from Loandetails r where applicationid = :applicationid ";

        Map<String, Object> params = new HashMap<>();
        params.put("applicationid", loanApplicationId);
        List<Loandetails> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public Loanapplication getLoanApplicationById(Long loanApplicationId) {
        //get approva level name
        String q = "select r from Loanapplication r where id = :id ";

        Map<String, Object> params = new HashMap<>();
        params.put("id", loanApplicationId);
        List<Loanapplication> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public Memberaccount getMemberaccountByAccountNo(String accountId) {
        //get approva level name
        String q = "select r from Memberaccount r where accountid = :accountid ";

        Map<String, Object> params = new HashMap<>();
        params.put("accountid", accountId);
        List<Memberaccount> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public Memberaccount getMemberaccountByAccountTypeAndAccountGroupId(String accountType, String groupId) {
        //get approva level name
        String q = "select r from Memberaccount r where accounttype = :accounttype and groupid=:groupid";

        Map<String, Object> params = new HashMap<>();
        params.put("accounttype", accountType);
        params.put("groupid", groupId);
        List<Memberaccount> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public Memberaccount getMemberaccountByAccountId(String accountId) {
        //get approva level name
        String q = "select r from Memberaccount r where accountid = :accountid ";

        Map<String, Object> params = new HashMap<>();
        params.put("accountid", accountId);
        List<Memberaccount> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public Memberaccount getMemberaccountByAccountTypeAndGroupIdAndMemberCode(String accountType, String groupId, String memberCode) {
        //get approva level name
        String q = "select r from Memberaccount r where accounttype = :accounttype and groupid=:groupid and membercode=:membercode";

        Map<String, Object> params = new HashMap<>();
        params.put("accounttype", accountType);
        params.put("groupid", groupId);
        params.put("membercode", memberCode);
        List<Memberaccount> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null || entity.isEmpty() ? null : entity.get(0);
    }

    public Groupmember getGroupmember(String memberNo) {

        String q = "select r from Groupmember r where membercode = :membercode ";

        Map<String, Object> params = new HashMap<>();
        params.put("membercode", memberNo);
        List<Groupmember> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null || entity.isEmpty() ? null : entity.get(0);
    }

    public Accounttype getAccountByName(String typename) {
        String q = "select r from Accounttype r where typename = :typename ";

        Map<String, Object> params = new HashMap<>();
        params.put("typename", typename);
        List<Accounttype> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public Invgroup getGroupById(String groupid) {
        String q = "select r from Invgroup r where groupid = :groupid ";

        Map<String, Object> params = new HashMap<>();
        params.put("groupid", groupid);
        List<Invgroup> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public GroupApprovalLevelsConfig getGroupsApprovalLevelsConfigByType(String type, String groupId) {
        String q = "select r from GroupApprovalLevelsConfig r where groupId = :groupId and type = :type";

        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("type", type);
        List<GroupApprovalLevelsConfig> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public Loanapplication getLoanByGroupIdMemberNoAndStatus(BigInteger loantypeid, String groupId, String membercode, String approvalStatus) {
        //get approva level name
        Loanapplication loanapplication = null;
        String q = "select r from Loanapplication r where loantypeid=:loantypeid and groupId = :groupId and membercode = :membercode and approvalStatus=:approvalStatus order by id desc";

        Map<String, Object> params = new HashMap<>();
        params.put("loantypeid", loantypeid);
        params.put("groupId", groupId);
        params.put("membercode", membercode);
        params.put("approvalStatus", approvalStatus);
        List<Loanapplication> entity = crudService.fetchWithHibernateQuery(q, params);

        if (!entity.isEmpty()) {
            loanapplication = entity.get(0);
        }
        return loanapplication;
    }

    public GroupApprovalLevel getGroupApprovalLevel(String groupId, Integer position) {
        //get approva level name
        String q = "select r from GroupApprovalLevel r where groupId = :groupId and position = :position";

        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("position", position);
        List<GroupApprovalLevel> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public Users findByUserName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Users findUserByuserNameAndPassordAndGroupCode(String username, String password, String groupCode) {
        String q = "select r from Users r where userName = :username and password = :password and groupId in (select p.groupid from Invgroup p where groupcode=:groupcode)";

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("groupcode", groupCode);
        List<Users> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public List<Role> findRolesByUserId(Integer userId) {

        String q = "select r from Role r where id = (select p.roleId from RoleMap p where userId=:userId)";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<Role> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity;
    }

    public int postionOfItemInArray(String[] array, int needle) {
        int positon = 0;
        String needleString = String.valueOf(needle);
        for (int i = 0; i < array.length; i++) {
            if (needleString.equals(array[i])) {
                positon = i;
                break;
            }
        }

        return positon;
    }

    public void deleteRolesByUserId(Integer userId) {
        String q = "delete from RoleMap c where c.userId =:userId";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        crudService.executeHibernateQuery(q, params);
    }

    public List<Permission> findPermissionByUserId(Integer userId) {

        String q = "select r from Permission r where id = (select pm.permissionId from PermissionMap where roleId in "
                + "(select rm.roleId from roleMap where userId=:userId)) order by module";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<Permission> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity;
    }

    public List<Permission> findAllMenuPermissions(Integer userId) {
        String q = "select r from Permission r where isMenu='1' and id = (select pm.permissionId from PermissionMap where roleId in "
                + "(select rm.roleId from roleMap where userId=:userId)) ";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<Permission> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity;
    }

    public List<Permission> findMenuChildPermissions(String subModule, Integer userId) {

        String q = "select r from Permission r where isMenu='0' and subModule=:subModule and id = (select pm.permissionId from PermissionMap where roleId in "
                + "(select rm.roleId from roleMap where userId=:userId)) ";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("subModule", subModule);
        List<Permission> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity;
    }

    public Role findRoleByNameAndGroupIdAndIntrash(String name, String groupId, String intrash) {
        String q = "select r from Role r where name =:name and groupId=:groupId and intrash=:intrash";

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("groupId", groupId);
        params.put("intrash", intrash);
        List<Role> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    public List<Permission> findPermissionByRoleId(Integer roleId) {

        String q = "select r from Permission r where id in ( select pm.permissionId from PermissionMap where roleId =:roleId ) ";

        Map<String, Object> params = new HashMap<>();
        params.put("roleId", roleId);
        List<Permission> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity;
    }

    public void deleteByRoleId(Integer roleId) {

        String q = "delete from PermissionMap c where c.roleId =:roleId";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", roleId);
        crudService.executeHibernateQuery(q, params);
    }

    public List<ApprovalsDone> usersApprovalLevelPerRecord(Integer recordId, String type, String userId) {
        String q = "select r from ApprovalsDone r where r.recordId = :recordId and type='" + type + "' and approverId='" + userId + "'";

        Map<String, Object> params = new HashMap<>();
        params.put("recordId", recordId);
        List<ApprovalsDone> entity = crudService.fetchWithHibernateQuery(q, params);
        return entity;
    }

    public LoanScheduleEnvelope amortizationSchedule(String dateString, BigDecimal principal1, BigDecimal annualInterestRate1,
            double numMonths, int createdBy) {
        double principal = principal1.doubleValue();
        double annualInterestRate = annualInterestRate1.doubleValue();
        LoanScheduleEnvelope loanScheduleEnvelope = new LoanScheduleEnvelope();
        double interestPaid, principalPaid, newBalance;
        double monthlyInterestRate, monthlyPayment;
        int month;
        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateAsString = dateString;
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = sourceFormat.parse(dateAsString);
            endDate = addMonths(startDate, (int) numMonths);
        } catch (ParseException ex) {
            ex.printStackTrace();
            java.util.logging.Logger.getLogger(GlobalFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Output monthly payment and total payment
        monthlyInterestRate = annualInterestRate / 12;
        //monthlyPayment = monthlyPayment(principal, monthlyInterestRate, numYears);
        double rate = (annualInterestRate / 100) / 12;
        monthlyPayment = FinanceLib.pmt(rate, numMonths, principal, 0, false) * -1;

        // monthlyPayment = FinanceLib.pmt(annualInterestRate, numMonths, principal, 0, false);
        double totalPayment = monthlyPayment * numMonths;
        double totalIntest = totalPayment - principal;
        System.out.format("Monthly Payment: %8.2f%n", monthlyPayment);
        System.out.format("Principal:   %8.2f%n", principal);
        System.out.format("Total interest:   %8.2f%n", totalIntest);
        System.out.format("Total Payment:   %8.2f%n", totalPayment);
        System.out.println("Start Date:  " + startDate);
        System.out.println("End Date:  " + endDate);
        loanScheduleEnvelope.setEndDate(endDate);
        loanScheduleEnvelope.setStartDate(startDate);
        loanScheduleEnvelope.setPrincipal(new BigDecimal(principal).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        loanScheduleEnvelope.setTotalInterest(new BigDecimal(totalIntest).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        loanScheduleEnvelope.setTotalPayment(new BigDecimal(totalPayment).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        loanScheduleEnvelope.setRepaymentMonths(numMonths);
        loanScheduleEnvelope.setMonthlyPayment(new BigDecimal(monthlyPayment).setScale(2, BigDecimal.ROUND_HALF_EVEN));

        // Print the table header
        double cumulativeInterest = 0;
        Date nextInstallmentDate = startDate;// new Date();
        // Print the table header
        // printTableHeader();
        List<LonSchedule> loanDetailsSchedules = new ArrayList<>();
        for (month = 1; month <= numMonths; month++) {
            // Compute amount paid and new balance for each payment period
            interestPaid = principal * (monthlyInterestRate / 100);
            principalPaid = monthlyPayment - interestPaid;
            newBalance = principal - principalPaid;
            cumulativeInterest += interestPaid;
            nextInstallmentDate = addMonths(startDate, month);

            // Output the data item
            // printScheduleItem(month, interestPaid, principalPaid, newBalance, monthlyPayment, cumulativeInterest, nextInstallmentDate);
//            System.out.format("\n%8s%10s%10s%10s%10s%10s%12s\n",
//                    "Payment#", "Interest", "Principal", "instalment", "Balance", "Cumulative Interest", "Installment Due Date");
            LonSchedule schedule = new LonSchedule();
            schedule.setPaymentNo(month);
            schedule.setInterest(new BigDecimal(interestPaid).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            schedule.setPrincipal(new BigDecimal(principal).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            schedule.setInstalmentAmount(new BigDecimal(monthlyPayment).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            schedule.setCumulativeInterest(new BigDecimal(cumulativeInterest).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            schedule.setPaymentDate(nextInstallmentDate);

            schedule.setCreatedBy(createdBy);
            schedule.setDateCreated(new Date());
            schedule.setEndingBalance(new BigDecimal(newBalance).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            schedule.setBeginningBalance(new BigDecimal(principalPaid).setScale(2, BigDecimal.ROUND_HALF_EVEN));

            loanDetailsSchedules.add(schedule);
            // Update the balance
            principal = newBalance;
        }
        loanScheduleEnvelope.setLoanSchedules(loanDetailsSchedules);
        return loanScheduleEnvelope;
    }

//    public LoanScheduleEnvelope amortizationSchedule(String dateString, BigDecimal principal, BigDecimal annualInterestRate,
//            double numMonths, int createdBy) {
//        LoanScheduleEnvelope loanScheduleEnvelope = new LoanScheduleEnvelope();
//        BigDecimal interestPaid, principalPaid, newBalance;
//        BigDecimal monthlyInterestRate;
//        double monthlyPayment;
//        int month;
//        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String dateAsString = dateString;
//        Date startDate = new Date();
//        Date endDate = new Date();
//        try {
//            startDate = sourceFormat.parse(dateAsString);
//            endDate = addMonths(startDate, (int) numMonths);
//        } catch (ParseException ex) {
//            ex.printStackTrace();
//            java.util.logging.Logger.getLogger(GlobalFunctions.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        // Output monthly payment and total payment
//        monthlyInterestRate = new BigDecimal(annualInterestRate.doubleValue() / 12);//annualInterestRate.divide(new BigDecimal("12"));
//        //monthlyPayment = monthlyPayment(principal, monthlyInterestRate, numYears);
//        BigDecimal rate = new BigDecimal((annualInterestRate.doubleValue() / 100) / 12);
//        monthlyPayment = FinanceLib.pmt(rate.doubleValue(), numMonths, principal.doubleValue(), 0, false) * -1;
//
//        // monthlyPayment = FinanceLib.pmt(annualInterestRate, numMonths, principal, 0, false);
//        double totalPayment = monthlyPayment * numMonths;
//        double totalIntest = totalPayment - principal.doubleValue();
//        System.out.format("Monthly Payment: %8.2f%n", monthlyPayment);
//        System.out.format("Principal:   %8.2f%n", principal);
//        System.out.format("Total interest:   %8.2f%n", totalIntest);
//        System.out.format("Total Payment:   %8.2f%n", totalPayment);
//        System.out.println("Start Date:  " + startDate);
//        System.out.println("End Date:  " + endDate);
//        loanScheduleEnvelope.setEndDate(endDate);
//        loanScheduleEnvelope.setStartDate(startDate);
//        loanScheduleEnvelope.setPrincipal(principal.setScale(2, BigDecimal.ROUND_HALF_UP));//Math.round(principal * 100) / 100);
//        loanScheduleEnvelope.setTotalInterest(new BigDecimal(totalIntest).setScale(2, BigDecimal.ROUND_HALF_UP));//(Math.round(totalIntest * 100) / 100);
//        loanScheduleEnvelope.setTotalPayment(new BigDecimal(totalPayment).setScale(2, BigDecimal.ROUND_HALF_UP));//(Math.round(totalPayment * 100) / 100);
//        loanScheduleEnvelope.setRepaymentMonths(numMonths);
//        loanScheduleEnvelope.setMonthlyPayment(new BigDecimal(monthlyPayment).setScale(2, BigDecimal.ROUND_HALF_UP));//(Math.round(monthlyPayment * 100) / 100);
//
//        // Print the table header
//        double cumulativeInterest = 0;
//        Date nextInstallmentDate = startDate;// new Date();
//        // Print the table header
//        // printTableHeader();
//        List<LonSchedule> loanDetailsSchedules = new ArrayList<>();
//        for (month = 1; month <= numMonths; month++) {
//            // Compute amount paid and new balance for each payment period
//            interestPaid = principal.multiply(monthlyInterestRate.divide(new BigDecimal("100")));
//            principalPaid = new BigDecimal(monthlyPayment - interestPaid.doubleValue());
//            newBalance = principal.subtract(principalPaid);
//            cumulativeInterest += interestPaid.doubleValue();
//            nextInstallmentDate = addMonths(startDate, month);
//
//            // Output the data item
//            // printScheduleItem(month, interestPaid, principalPaid, newBalance, monthlyPayment, cumulativeInterest, nextInstallmentDate);
////            System.out.format("\n%8s%10s%10s%10s%10s%10s%12s\n",
////                    "Payment#", "Interest", "Principal", "instalment", "Balance", "Cumulative Interest", "Installment Due Date");
//            LonSchedule schedule = new LonSchedule();
//            schedule.setPaymentNo(month);
//            schedule.setInterest(interestPaid.setScale(2, RoundingMode.HALF_UP));
//            schedule.setPrincipal(principal.setScale(2, RoundingMode.HALF_UP));;//(Double.parseDouble(String.valueOf(Math.round(principal * 100) / 100)));
//            schedule.setInstalmentAmount(new BigDecimal(monthlyPayment).setScale(2, RoundingMode.HALF_UP));;//(Double.parseDouble(String.valueOf(Math.round(monthlyPayment * 100) / 100)));
//            schedule.setCumulativeInterest(new BigDecimal(cumulativeInterest).setScale(2, RoundingMode.HALF_UP));;//(Double.parseDouble(String.valueOf(Math.round(cumulativeInterest * 100) / 100)));
//            schedule.setPaymentDate(nextInstallmentDate);
//
//            schedule.setCreatedBy(createdBy);
//            schedule.setDateCreated(new Date());
//            schedule.setEndingBalance(newBalance.setScale(2, RoundingMode.HALF_UP));//(Double.parseDouble(String.valueOf(Math.round(newBalance * 100) / 100)));
//            schedule.setBeginningBalance(principalPaid.setScale(2, RoundingMode.HALF_UP));//(Double.parseDouble(String.valueOf(Math.round(principalPaid * 100) / 100)));
//
//            loanDetailsSchedules.add(schedule);
//            // Update the balance
//            principal = newBalance;
//        }
//        loanScheduleEnvelope.setLoanSchedules(loanDetailsSchedules);
//        return loanScheduleEnvelope;
//    }
//    private void printScheduleItem(int month, double interestPaid,
//            double principalPaid, double newBalance, double monthlyPayment, double cumulativeInterest, Date nextInstallmentDate) {
//        System.out.format("%8d%10.2f%10.2f%12.2f%12.2f%12.2f%12\n",
//                month, interestPaid, principalPaid, monthlyPayment, newBalance, cumulativeInterest);
//    }
//    private void printTableHeader() {
//        System.out.println("\nAmortization schedule");
//        for (int i = 0; i < 40; i++) {  // Draw a line
//            System.out.print("-");
//        }
//        System.out.format("\n%8s%10s%10s%10s%10s%10s%12s\n",
//                "Payment#", "Interest", "Principal", "instalment", "Balance", "Cumulative Interest", "Installment Due Date");
//
//    }
    public Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public Date addMonths(Date date, int months) {
        Date newDate = DateUtils.addMonths(date, months);
        return newDate;
    }

    public boolean validLoanIncomeRatio(Groupmember member, Loantype loantype, Loanapplication loanapplication) {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("dd/MM/YYYY");
        String dateCreated = DATE_TIME_FORMATTER.print(new Date().getTime());
        LOG.info("Date Created+" + dateCreated);
        LOG.info("Applied Amount+" + loanapplication.getAppliedamount());
        LOG.info("Interest Rate:+" + loantype.getInterestrate());
        LOG.info("Repayment Period:" + loanapplication.getRepaymentPeriod());
        LOG.info("User Id:" + loanapplication.getUserId());
        LoanScheduleEnvelope amortizationSchedule = amortizationSchedule(dateCreated, loanapplication.getAppliedamount(), loantype.getInterestrate(), loanapplication.getRepaymentPeriod(), loanapplication.getUserId());
        if (member.getMonthlySalary().multiply(new BigDecimal(loantype.getLoanIncomeRatio())).compareTo(amortizationSchedule.getMonthlyPayment()) == 1) {
            return true;
        }
        return false;
    }

    public List<LonSchedule> getDueLoanScheduleByLoanApplicationId(Integer loanApplicationId) {
        //get approva level name
        String q = "select r from LonSchedule r where loanApplicationId = :loanApplicationId and paidStatus!='Paid'"
                + " and MONTH(paymentDate)<=MONTH(CURRENT_TIMESTAMP) and YEAR(paymentDate)<=YEAR(CURRENT_TIMESTAMP) order by id ";

        Map<String, Object> params = new HashMap<>();
        params.put("loanApplicationId", loanApplicationId);
        List<List<LonSchedule>> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    //Gets any loans due i.e when the repayment date attained
    public List<LonSchedule> getDueLoansForRepayment() {

        String q = "select r from LonSchedule r where paidStatus!='Paid' "
                + " and MONTH(paymentDate)<=MONTH(CURRENT_TIMESTAMP) and YEAR(paymentDate)<=YEAR(CURRENT_TIMESTAMP) order by id ";

        List<LonSchedule> entity = crudService.fetchWithHibernateQuery(q, Collections.EMPTY_MAP);

        return entity.isEmpty() ? null : entity;
    }

    public void processRepayment() {
        List<LonSchedule> dueLoansForRepayment = getDueLoansForRepayment();
        for (LonSchedule loanSchedule : dueLoansForRepayment) {
            LOG.info("START REPAYMENT OF LOAN ID:" + loanSchedule.getLoanApplicationId() + " SCHEDULE ID:" + loanSchedule.getId());
            //get loan application
            Loanapplication loanapplication = getLoanApplicationById(Long.parseLong(loanSchedule.getLoanApplicationId().toString()));

            Loandetails loandetails = getLoanDetailsByLoanApplicationById(new BigInteger(String.valueOf(loanapplication.getId())));
            //get settlement account balance
            Accounttype accountTypeSettlement = getAccountByName("SETTLEMENT_ACCOUNT");
            Memberaccount memberLoanSettlementAccount = getMemberaccountByAccountTypeAndAccountGroupId(accountTypeSettlement.getTypecode() + "KES", loandetails.getGroupid());

            List<LonSchedule> lonSchedulesInit = new ArrayList<>();
            lonSchedulesInit.add(loanSchedule);

            if (memberLoanSettlementAccount == null) {
                LOG.error(responseCodes.SETTLEMENT_ACCOUNT_NOT_FOUND);
            } else {

                BigDecimal amountDue = amountToPay(lonSchedulesInit);
                //Check If Payment amount is sufficient in settlement account
                if (memberLoanSettlementAccount.getAccountbalance().doubleValue() < amountDue.doubleValue()) {
                    ApiResponse VALIDATION_FAIL = responseCodes.INSUFFICIENT_BALANCE_FOR_LOAN_REPAYMENT;
                    String errorMsg = VALIDATION_FAIL.getResponseDescription().replace("<RAMOUNT>", memberLoanSettlementAccount.getAccountbalance().toString()).replace("<IAMOUNT>", amountDue.toString());
                    VALIDATION_FAIL.setResponseDescription(errorMsg);
                    LOG.error(VALIDATION_FAIL);
                } else {
                    LoanRepayment loanRepayment = new LoanRepayment();
                    loanRepayment.setCreatedBy(Integer.parseInt("-1"));
                    loanRepayment.setDateCreated(new Date());
                    loanRepayment.setAmount(loanSchedule.getInstalmentAmount());
                    loanRepayment.setGroupId(loanapplication.getGroupid());
                    loanRepayment.setLoanApplicationId(Integer.parseInt(loanapplication.getId().toString()));
                    loanRepayment.setPaymentType("CHECK OFF");
                    DoubleStream receiptNo = ThreadLocalRandom.current().doubles();
                    loanRepayment.setReceiptNo(loanapplication.getId() + "SYS" + receiptNo);
                    loanRepayment.setRunningBalance(loanSchedule.getEndingBalance());
                    loanRepayment.setId(0);

                    Object test = crudService.save(loanRepayment);
                    //Loan Schedule
                    //Credit loan account
                    Invgroup invgroup = getGroupById(loanapplication.getGroupid());
                    Accounttype accountTypeLoan = getAccountByName("LOAN");
                    String accountid = accountTypeLoan.getTypecode() + loanapplication.getMembercode() + invgroup.getGroupcode() + loanapplication.getId();
                    Memberaccount memberLoanAccount = getMemberaccountByAccountNo(accountid);
                    creditAccount(memberLoanAccount, loanRepayment.getAmount(), "Loan Repayment Credit");
                    //Debit settlement 
                    debitAccount(memberLoanSettlementAccount, loanRepayment.getAmount(), "Debit customer settlement account to repay loan account " + memberLoanAccount.getAccountid());
                    //Update loan details
                    // Loanapplication loanapplication = crudService.findEntity(Long.parseLong(loanApplication.getApplicationid().toString()), Loanapplication.class);

                    loandetails.setLastpaymentamount(loandetails.getInstallmentamount());
                    loandetails.setLastpaymentdate(new Date());
                    loandetails.setLoanbalance(loandetails.getLoanbalance().subtract(loandetails.getInstallmentamount()));
                    Calendar c = new GregorianCalendar();
                    c.add(Calendar.DATE, 30);
                    Date dPlus30Days = c.getTime();
                    loandetails.setNextpaymentdate(dPlus30Days);
                    BigDecimal monthlyInterest = loandetails.getInteresttotal().divide(new BigDecimal(loanapplication.getRepaymentPeriod().toString()));
                    loandetails.setTotalinterestpaid(loandetails.getTotalinterestpaid().add(monthlyInterest));
                    loandetails.setTotalpriciplepaid(loandetails.getTotalpriciplepaid().add(loandetails.getInstallmentamount()));
                    crudService.saveOrUpdate(loandetails);
                    //to add interst paid and 
                    //Update the loan schedule
                    updateLoanMonthlySchedule(lonSchedulesInit);

                    if (test != null) {
                        ApiResponse SUCCESS = responseCodes.SUCCESS;
                        SUCCESS.setEntity(test);
                        LOG.info(SUCCESS);
                    } else {
                        LOG.error("Creation failed");
                        LOG.error(responseCodes.CREATION_FAILED);
                    }
                }
            }
            LOG.info("END REPAYMENT OF LOAN ID:" + loanSchedule.getLoanApplicationId() + " SCHEDULE ID:" + loanSchedule.getId());
        }
    }

    public BigDecimal amountToPay(List<LonSchedule> lonSchedules) {
        BigDecimal amountDue = BigDecimal.ZERO;
        for (LonSchedule schedule : lonSchedules) {
            amountDue = amountDue.add(schedule.getInstalmentAmount().add(schedule.getPenalty()));
        }
        return amountDue;
    }

    public void updateLoanMonthlySchedule(List<LonSchedule> lonSchedules) {
        for (LonSchedule schedule : lonSchedules) {
            schedule.setPaidStatus("Paid");
            schedule.setRepaymentDate(new Date());
            crudService.saveOrUpdate(schedule);
        }
    }

    private LoanDisbursements getDisbursementByLoanId(Long id) {
        String q = "select r from LoanDisbursements r where loanApplicationId = :loanApplicationId order by id desc";

        Map<String, Object> params = new HashMap<>();
        params.put("loanApplicationId", Integer.parseInt(id.toString()));
        List<LoanDisbursements> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? null : entity.get(0);
    }

    private BigDecimal getInsuranceFeeByGroupIdAndAmount(String groupId, BigDecimal amount, Integer repaymentPeriod) {
        String q = "select r from InsuranceFeeConfigs r where groupId = :groupId and upToAmount<=:upToAmount and repaymentPeriod<=:repaymentPeriod order by upToAmount desc,repaymentPeriod desc";

        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("upToAmount", amount);
        params.put("repaymentPeriod", repaymentPeriod);
        List<InsuranceFeeConfigs> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity == null ? BigDecimal.ZERO : entity.get(0).getFee();
    }

    public List<Users> getUsersWithParticularPermission(String groupId, String permissionName) {
        String q = "select r from Users r where groupId = :groupId and id in (select userId from RoleMap where roleId in "
                + " (select roleId from PermissionMap where permissionId in "
                + " (select id from Permission where name=:name ) ))";

        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("name", permissionName);
        List<Users> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity;
    }

    public List<Users> getUsersWithParticularPermissionAndHaveNotApproved(String groupId, String permissionName,String type,Integer recordId) {
        String q = "select r from Users r where groupId = :groupId and id not in (select approverId from ApprovalsDone where type=:type and recordId=:recordId) and id in (select userId from RoleMap where roleId in "
                + " (select roleId from PermissionMap where permissionId in "
                + " (select id from Permission where name=:name ) ))";

        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("name", permissionName);
        params.put("recordId", recordId);
        params.put("type", type);
        List<Users> entity = crudService.fetchWithHibernateQuery(q, params);

        return entity;
    }
    
    public String resolveApprovalPermissionByLevel(Integer level, String approvalType) {
        String permissionName = "";

        if (approvalType.equals("LOAN_APPLICATION")) {
            switch (level) {
                case 1:
                    permissionName = "administration.loansapproval.level1";
                    break;
                case 2:
                    permissionName = "administration.loansapproval.level2";
                    break;
                case 3:
                    permissionName = "administration.loansapproval.level3";
                    break;
            }
        } else if (approvalType.equals("LOAN_DISBURSEMENT")) {
            switch (level) {
                case 1:
                    permissionName = "administration.disbursementapproval.level1";
                    break;
                case 2:
                    permissionName = "administration.disbursementapproval.level2";
                    break;
                case 3:
                    permissionName = "administration.disbursementapproval.level3";
                    break;
            }
        }
        return permissionName;
    }

    private void repayLoanDataEntry(LonSchedule lonSchedule, String membercode) {
        LoanRepayment loanRepayment = new LoanRepayment();
        loanRepayment.setCreatedBy(0);
        loanRepayment.setDateCreated(new Date());
        loanRepayment.setId(0);
        loanRepayment.setAmount(lonSchedule.getInstalmentAmount());
        loanRepayment.setGroupId(lonSchedule.getGroupId());
        loanRepayment.setPaymentType("Batch Check Off");
        loanRepayment.setReceiptNo("batch");
        loanRepayment.setLoanApplicationId(lonSchedule.getLoanApplicationId());
        crudService.save(loanRepayment);

        //Debit Asset account to source the memeber settlement account
        Accounttype accountTypeAsset = getAccountByName("ASSET");
        Memberaccount groupAssetAccount = getMemberaccountByAccountTypeAndAccountGroupId(accountTypeAsset.getTypecode() + "KES", lonSchedule.getGroupId());
        debitAccount(groupAssetAccount, lonSchedule.getInstalmentAmount(), "Debit Asset account to repay loan account " + groupAssetAccount.getAccountid());
        //Credit settlement account
        Accounttype accountTypeSettlement = getAccountByName("SETTLEMENT_ACCOUNT");
        Memberaccount memberLoanSettlementAccount = getMemberaccountByAccountTypeAndAccountGroupId(accountTypeSettlement.getTypecode() + "KES", lonSchedule.getGroupId());
        creditAccount(memberLoanSettlementAccount, lonSchedule.getInstalmentAmount(), "Loan Repayment Credit");

        Invgroup invgroup = getGroupById(lonSchedule.getGroupId());
        Accounttype accountTypeLoan = getAccountByName("LOAN");
        String loanAccountid = accountTypeLoan.getTypecode() + membercode + invgroup.getGroupcode() + lonSchedule.getLoanApplicationId();
        Memberaccount memberLoanAccount = getMemberaccountByAccountNo(loanAccountid);
        //Debit settlement account principal
        debitAccount(memberLoanSettlementAccount, lonSchedule.getBeginningBalance(), "Debit principal amt from customer settlement account to repay loan account " + memberLoanAccount.getAccountid());

        //Credit loan account
        creditAccount(memberLoanAccount, lonSchedule.getBeginningBalance(), "Loan Repayment Credit");

        //Debit settlement account interest
        debitAccount(memberLoanSettlementAccount, lonSchedule.getInterest(), "Debit interest amount from customer settlement account to repay loan account " + memberLoanAccount.getAccountid());

        //Credit loan interest account
        Accounttype accountTypeInterest = getAccountByName("INTEREST");
        Memberaccount groupInterestAccount = getMemberaccountByAccountTypeAndAccountGroupId(accountTypeInterest.getTypecode() + "KES", lonSchedule.getGroupId());
        creditAccount(groupInterestAccount, lonSchedule.getInterest(), "Interest Payment");

    }
}
