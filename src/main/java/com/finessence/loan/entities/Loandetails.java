/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

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
@Table(name = "LOANDETAILS")
@NamedQueries({
    @NamedQuery(name = "Loandetails.findAll", query = "SELECT l FROM Loandetails l")
    , @NamedQuery(name = "Loandetails.findById", query = "SELECT l FROM Loandetails l WHERE l.id = :id")
    , @NamedQuery(name = "Loandetails.findByAddedby", query = "SELECT l FROM Loandetails l WHERE l.addedby = :addedby")
    , @NamedQuery(name = "Loandetails.findByApplicationdate", query = "SELECT l FROM Loandetails l WHERE l.applicationdate = :applicationdate")
    , @NamedQuery(name = "Loandetails.findByApplicationid", query = "SELECT l FROM Loandetails l WHERE l.applicationid = :applicationid")
    , @NamedQuery(name = "Loandetails.findByDisbursementamount", query = "SELECT l FROM Loandetails l WHERE l.disbursementamount = :disbursementamount")
    , @NamedQuery(name = "Loandetails.findByDisbursementdate", query = "SELECT l FROM Loandetails l WHERE l.disbursementdate = :disbursementdate")
    , @NamedQuery(name = "Loandetails.findByGroupid", query = "SELECT l FROM Loandetails l WHERE l.groupid = :groupid")
    , @NamedQuery(name = "Loandetails.findByInstallmentamount", query = "SELECT l FROM Loandetails l WHERE l.installmentamount = :installmentamount")
    , @NamedQuery(name = "Loandetails.findByInteresttotal", query = "SELECT l FROM Loandetails l WHERE l.interesttotal = :interesttotal")
    , @NamedQuery(name = "Loandetails.findByLastpaymentamount", query = "SELECT l FROM Loandetails l WHERE l.lastpaymentamount = :lastpaymentamount")
    , @NamedQuery(name = "Loandetails.findByLastpaymentdate", query = "SELECT l FROM Loandetails l WHERE l.lastpaymentdate = :lastpaymentdate")
    , @NamedQuery(name = "Loandetails.findByLoanaccountid", query = "SELECT l FROM Loandetails l WHERE l.loanaccountid = :loanaccountid")
    , @NamedQuery(name = "Loandetails.findByLoanbalance", query = "SELECT l FROM Loandetails l WHERE l.loanbalance = :loanbalance")
    , @NamedQuery(name = "Loandetails.findByMembercode", query = "SELECT l FROM Loandetails l WHERE l.membercode = :membercode")
    , @NamedQuery(name = "Loandetails.findByNextpaymentamount", query = "SELECT l FROM Loandetails l WHERE l.nextpaymentamount = :nextpaymentamount")
    , @NamedQuery(name = "Loandetails.findByNextpaymentdate", query = "SELECT l FROM Loandetails l WHERE l.nextpaymentdate = :nextpaymentdate")
    , @NamedQuery(name = "Loandetails.findByPrincipletotal", query = "SELECT l FROM Loandetails l WHERE l.principletotal = :principletotal")
    , @NamedQuery(name = "Loandetails.findByTotalinterestpaid", query = "SELECT l FROM Loandetails l WHERE l.totalinterestpaid = :totalinterestpaid")
    , @NamedQuery(name = "Loandetails.findByTotalpriciplepaid", query = "SELECT l FROM Loandetails l WHERE l.totalpriciplepaid = :totalpriciplepaid")})
public class Loandetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
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

    public Loandetails() {
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public Loandetails(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loandetails)) {
            return false;
        }
        Loandetails other = (Loandetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Loandetails[ id=" + id + " ]";
    }
    
}
