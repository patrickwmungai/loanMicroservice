/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private BigDecimal disbursementamount;
    @Column(name = "DISBURSEMENTDATE")
    @Temporal(TemporalType.DATE)
    private Date disbursementdate;
    @Column(name = "GROUPID")
    private String groupid;
    @Column(name = "INSTALLMENTAMOUNT")
    private BigDecimal installmentamount;
    @Column(name = "INTERESTTOTAL")
    private BigDecimal interesttotal;
    @Column(name = "LASTPAYMENTAMOUNT")
    private BigDecimal lastpaymentamount;
    @Column(name = "LASTPAYMENTDATE")
    @Temporal(TemporalType.DATE)
    private Date lastpaymentdate;
    @Size(max = 255)
    @Column(name = "LOANACCOUNTID")
    private String loanaccountid;
    @Column(name = "LOANBALANCE")
    private BigDecimal loanbalance;
    @Size(max = 255)
    @Column(name = "MEMBERCODE")
    private String membercode;
    @Column(name = "NEXTPAYMENTAMOUNT")
    private BigDecimal nextpaymentamount;
    @Column(name = "NEXTPAYMENTDATE")
    @Temporal(TemporalType.DATE)
    private Date nextpaymentdate;
    @Column(name = "PRINCIPLETOTAL")
    private BigDecimal principletotal;
    @Column(name = "TOTALINTERESTPAID")
    private BigDecimal totalinterestpaid;
    @Column(name = "TOTALPRICIPLEPAID")
    private BigDecimal totalpriciplepaid;

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

    public BigDecimal getDisbursementamount() {
        return disbursementamount;
    }

    public void setDisbursementamount(BigDecimal disbursementamount) {
        this.disbursementamount = disbursementamount;
    }

    public Date getDisbursementdate() {
        return disbursementdate;
    }

    public void setDisbursementdate(Date disbursementdate) {
        this.disbursementdate = disbursementdate;
    }

    public BigDecimal getInstallmentamount() {
        return installmentamount;
    }

    public void setInstallmentamount(BigDecimal installmentamount) {
        this.installmentamount = installmentamount;
    }

    public BigDecimal getInteresttotal() {
        return interesttotal;
    }

    public void setInteresttotal(BigDecimal interesttotal) {
        this.interesttotal = interesttotal;
    }

    public BigDecimal getLastpaymentamount() {
        return lastpaymentamount;
    }

    public void setLastpaymentamount(BigDecimal lastpaymentamount) {
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

    public BigDecimal getLoanbalance() {
        return loanbalance;
    }

    public void setLoanbalance(BigDecimal loanbalance) {
        this.loanbalance = loanbalance;
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
    }

    public BigDecimal getNextpaymentamount() {
        return nextpaymentamount;
    }

    public void setNextpaymentamount(BigDecimal nextpaymentamount) {
        this.nextpaymentamount = nextpaymentamount;
    }

    public Date getNextpaymentdate() {
        return nextpaymentdate;
    }

    public void setNextpaymentdate(Date nextpaymentdate) {
        this.nextpaymentdate = nextpaymentdate;
    }

    public BigDecimal getPrincipletotal() {
        return principletotal;
    }

    public void setPrincipletotal(BigDecimal principletotal) {
        this.principletotal = principletotal;
    }

    public BigDecimal getTotalinterestpaid() {
        return totalinterestpaid;
    }

    public void setTotalinterestpaid(BigDecimal totalinterestpaid) {
        this.totalinterestpaid = totalinterestpaid;
    }

    public BigDecimal getTotalpriciplepaid() {
        return totalpriciplepaid;
    }

    public void setTotalpriciplepaid(BigDecimal totalpriciplepaid) {
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
