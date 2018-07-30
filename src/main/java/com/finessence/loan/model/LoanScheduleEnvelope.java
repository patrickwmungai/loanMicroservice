/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.model;

import com.finessence.loan.entities.LonSchedule;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patrick
 */
public class LoanScheduleEnvelope {

    private BigDecimal monthlyPayment;
    private BigDecimal principal;
    private BigDecimal totalInterest;
    private BigDecimal totalPayment;
    private double repaymentMonths;
    private Date startDate;
    private Date endDate;
    private List<LonSchedule> loanSchedules;

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(BigDecimal totalInterest) {
        this.totalInterest = totalInterest;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<LonSchedule> getLoanSchedules() {
        return loanSchedules;
    }

    public void setLoanSchedules(List<LonSchedule> loanSchedules) {
        this.loanSchedules = loanSchedules;
    }

    public double getRepaymentMonths() {
        return repaymentMonths;
    }

    public void setRepaymentMonths(double repaymentMonths) {
        this.repaymentMonths = repaymentMonths;
    }

    
}
