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
@Table(name = "TRANSACTIONDETAILS")
@NamedQueries({
    @NamedQuery(name = "Transactiondetails.findAll", query = "SELECT t FROM Transactiondetails t")
    , @NamedQuery(name = "Transactiondetails.findByTransactionid", query = "SELECT t FROM Transactiondetails t WHERE t.transactionid = :transactionid")
    , @NamedQuery(name = "Transactiondetails.findByAccountid", query = "SELECT t FROM Transactiondetails t WHERE t.accountid = :accountid")
    , @NamedQuery(name = "Transactiondetails.findByAmount", query = "SELECT t FROM Transactiondetails t WHERE t.amount = :amount")
    , @NamedQuery(name = "Transactiondetails.findByGroupid", query = "SELECT t FROM Transactiondetails t WHERE t.groupid = :groupid")
    , @NamedQuery(name = "Transactiondetails.findByMembercode", query = "SELECT t FROM Transactiondetails t WHERE t.membercode = :membercode")
    , @NamedQuery(name = "Transactiondetails.findByNarration", query = "SELECT t FROM Transactiondetails t WHERE t.narration = :narration")
    , @NamedQuery(name = "Transactiondetails.findByPostingdate", query = "SELECT t FROM Transactiondetails t WHERE t.postingdate = :postingdate")
    , @NamedQuery(name = "Transactiondetails.findByReference", query = "SELECT t FROM Transactiondetails t WHERE t.reference = :reference")
    , @NamedQuery(name = "Transactiondetails.findByStatus", query = "SELECT t FROM Transactiondetails t WHERE t.status = :status")
    , @NamedQuery(name = "Transactiondetails.findByTransname", query = "SELECT t FROM Transactiondetails t WHERE t.transname = :transname")
    , @NamedQuery(name = "Transactiondetails.findByTransactor", query = "SELECT t FROM Transactiondetails t WHERE t.transactor = :transactor")
    , @NamedQuery(name = "Transactiondetails.findByTranstype", query = "SELECT t FROM Transactiondetails t WHERE t.transtype = :transtype")})
public class Transactiondetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANSACTIONID")
    private Long transactionid;
    @Size(max = 255)
    @Column(name = "ACCOUNTID")
    private String accountid;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Size(max = 255)
    @Column(name = "GROUPID")
    private String groupid;
    @Size(max = 255)
    @Column(name = "MEMBERCODE")
    private String membercode;
    @Size(max = 255)
    @Column(name = "NARRATION")
    private String narration;
    @Column(name = "POSTINGDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postingdate;
    @Size(max = 255)
    @Column(name = "REFERENCE")
    private String reference;
    @Size(max = 255)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 255)
    @Column(name = "TRANSNAME")
    private String transname;
    @Size(max = 255)
    @Column(name = "TRANSACTOR")
    private String transactor;
    @Size(max = 255)
    @Column(name = "TRANSTYPE")
    private String transtype;
    @Column(name = "RUNNING_BALANCE")
    private BigDecimal runningBalance;

    public Transactiondetails() {
    }

    public BigDecimal getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(BigDecimal runningBalance) {
        this.runningBalance = runningBalance;
    }

    public Transactiondetails(Long transactionid) {
        this.transactionid = transactionid;
    }

    public Long getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(Long transactionid) {
        this.transactionid = transactionid;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public Date getPostingdate() {
        return postingdate;
    }

    public void setPostingdate(Date postingdate) {
        this.postingdate = postingdate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransname() {
        return transname;
    }

    public void setTransname(String transname) {
        this.transname = transname;
    }

    public String getTransactor() {
        return transactor;
    }

    public void setTransactor(String transactor) {
        this.transactor = transactor;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionid != null ? transactionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactiondetails)) {
            return false;
        }
        Transactiondetails other = (Transactiondetails) object;
        if ((this.transactionid == null && other.transactionid != null) || (this.transactionid != null && !this.transactionid.equals(other.transactionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Transactiondetails[ transactionid=" + transactionid + " ]";
    }

}
