/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities.views;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "LOAN_DETAILS_SCHEDULE")
@NamedQueries({
    @NamedQuery(name = "LoanDetailsSchedule.findAll", query = "SELECT l FROM LoanDetailsSchedule l")
    , @NamedQuery(name = "LoanDetailsSchedule.findById", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.id = :id")
    , @NamedQuery(name = "LoanDetailsSchedule.findByAddedby", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.addedby = :addedby")
    , @NamedQuery(name = "LoanDetailsSchedule.findByApplicationdate", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.applicationdate = :applicationdate")
    , @NamedQuery(name = "LoanDetailsSchedule.findByApplicationid", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.applicationid = :applicationid")
    , @NamedQuery(name = "LoanDetailsSchedule.findByDisbursementamount", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.disbursementamount = :disbursementamount")
    , @NamedQuery(name = "LoanDetailsSchedule.findByDisbursementdate", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.disbursementdate = :disbursementdate")
    , @NamedQuery(name = "LoanDetailsSchedule.findByGroupid", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.groupid = :groupid")
    , @NamedQuery(name = "LoanDetailsSchedule.findByInstallmentamount", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.installmentamount = :installmentamount")
    , @NamedQuery(name = "LoanDetailsSchedule.findByInteresttotal", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.interesttotal = :interesttotal")
    , @NamedQuery(name = "LoanDetailsSchedule.findByLastpaymentamount", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.lastpaymentamount = :lastpaymentamount")
    , @NamedQuery(name = "LoanDetailsSchedule.findByLastpaymentdate", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.lastpaymentdate = :lastpaymentdate")
    , @NamedQuery(name = "LoanDetailsSchedule.findByLoanaccountid", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.loanaccountid = :loanaccountid")
    , @NamedQuery(name = "LoanDetailsSchedule.findByLoanbalance", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.loanbalance = :loanbalance")
    , @NamedQuery(name = "LoanDetailsSchedule.findByMembercode", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.membercode = :membercode")
    , @NamedQuery(name = "LoanDetailsSchedule.findByNextpaymentamount", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.nextpaymentamount = :nextpaymentamount")
    , @NamedQuery(name = "LoanDetailsSchedule.findByNextpaymentdate", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.nextpaymentdate = :nextpaymentdate")
    , @NamedQuery(name = "LoanDetailsSchedule.findByPrincipletotal", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.principletotal = :principletotal")
    , @NamedQuery(name = "LoanDetailsSchedule.findByTotalinterestpaid", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.totalinterestpaid = :totalinterestpaid")
    , @NamedQuery(name = "LoanDetailsSchedule.findByTotalpriciplepaid", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.totalpriciplepaid = :totalpriciplepaid")
    , @NamedQuery(name = "LoanDetailsSchedule.findByMembername", query = "SELECT l FROM LoanDetailsSchedule l WHERE l.membername = :membername")})
public class LoanDetailsSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @Id
    private long id;
    @Size(max = 255)
    @Column(name = "ADDEDBY")
    private String addedby;
    @Column(name = "APPLICATIONDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applicationdate;
    @Column(name = "APPLICATIONID")
    private BigInteger applicationid;
    @Column(name = "DISBURSEMENTAMOUNT")
    private BigInteger disbursementamount;
    @Column(name = "DISBURSEMENTDATE")
    @Temporal(TemporalType.DATE)
    private Date disbursementdate;
    @Size(max = 100)
    @Column(name = "GROUPID")
    private String groupid;
    @Column(name = "INSTALLMENTAMOUNT")
    private BigInteger installmentamount;
    @Column(name = "INTERESTTOTAL")
    private BigInteger interesttotal;
    @Column(name = "LASTPAYMENTAMOUNT")
    private BigInteger lastpaymentamount;
    @Column(name = "LASTPAYMENTDATE")
    @Temporal(TemporalType.DATE)
    private Date lastpaymentdate;
    @Size(max = 255)
    @Column(name = "LOANACCOUNTID")
    private String loanaccountid;
    @Column(name = "LOANBALANCE")
    private BigInteger loanbalance;
    @Size(max = 255)
    @Column(name = "MEMBERCODE")
    private String membercode;
    @Column(name = "NEXTPAYMENTAMOUNT")
    private BigInteger nextpaymentamount;
    @Column(name = "NEXTPAYMENTDATE")
    @Temporal(TemporalType.DATE)
    private Date nextpaymentdate;
    @Column(name = "PRINCIPLETOTAL")
    private BigInteger principletotal;
    @Column(name = "TOTALINTERESTPAID")
    private BigInteger totalinterestpaid;
    @Column(name = "TOTALPRICIPLEPAID")
    private BigInteger totalpriciplepaid;
    @Size(max = 255)
    @Column(name = "MEMBERNAME")
    private String membername;

    public LoanDetailsSchedule() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public Date getApplicationdate() {
        return applicationdate;
    }

    public void setApplicationdate(Date applicationdate) {
        this.applicationdate = applicationdate;
    }

    public BigInteger getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(BigInteger applicationid) {
        this.applicationid = applicationid;
    }

    public BigInteger getDisbursementamount() {
        return disbursementamount;
    }

    public void setDisbursementamount(BigInteger disbursementamount) {
        this.disbursementamount = disbursementamount;
    }

    public Date getDisbursementdate() {
        return disbursementdate;
    }

    public void setDisbursementdate(Date disbursementdate) {
        this.disbursementdate = disbursementdate;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public BigInteger getInstallmentamount() {
        return installmentamount;
    }

    public void setInstallmentamount(BigInteger installmentamount) {
        this.installmentamount = installmentamount;
    }

    public BigInteger getInteresttotal() {
        return interesttotal;
    }

    public void setInteresttotal(BigInteger interesttotal) {
        this.interesttotal = interesttotal;
    }

    public BigInteger getLastpaymentamount() {
        return lastpaymentamount;
    }

    public void setLastpaymentamount(BigInteger lastpaymentamount) {
        this.lastpaymentamount = lastpaymentamount;
    }

    public Date getLastpaymentdate() {
        return lastpaymentdate;
    }

    public void setLastpaymentdate(Date lastpaymentdate) {
        this.lastpaymentdate = lastpaymentdate;
    }

    public String getLoanaccountid() {
        return loanaccountid;
    }

    public void setLoanaccountid(String loanaccountid) {
        this.loanaccountid = loanaccountid;
    }

    public BigInteger getLoanbalance() {
        return loanbalance;
    }

    public void setLoanbalance(BigInteger loanbalance) {
        this.loanbalance = loanbalance;
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
    }

    public BigInteger getNextpaymentamount() {
        return nextpaymentamount;
    }

    public void setNextpaymentamount(BigInteger nextpaymentamount) {
        this.nextpaymentamount = nextpaymentamount;
    }

    public Date getNextpaymentdate() {
        return nextpaymentdate;
    }

    public void setNextpaymentdate(Date nextpaymentdate) {
        this.nextpaymentdate = nextpaymentdate;
    }

    public BigInteger getPrincipletotal() {
        return principletotal;
    }

    public void setPrincipletotal(BigInteger principletotal) {
        this.principletotal = principletotal;
    }

    public BigInteger getTotalinterestpaid() {
        return totalinterestpaid;
    }

    public void setTotalinterestpaid(BigInteger totalinterestpaid) {
        this.totalinterestpaid = totalinterestpaid;
    }

    public BigInteger getTotalpriciplepaid() {
        return totalpriciplepaid;
    }

    public void setTotalpriciplepaid(BigInteger totalpriciplepaid) {
        this.totalpriciplepaid = totalpriciplepaid;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }
    
}
