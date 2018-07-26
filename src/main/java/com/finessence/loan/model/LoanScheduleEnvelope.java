/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.model;

import com.finessence.loan.entities.LonSchedule;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patrick
 */
public class LoanScheduleEnvelope {

    private double monthlyPayment;
    private double principal;
    private double totalInterest;
    private double totalPayment;
    private double repaymentMonths;
    private Date startDate;
    private Date endDate;
    private List<LonSchedule> loanSchedules;

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
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
