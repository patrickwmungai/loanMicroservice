/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "TRANSACTIONAPPROVAL")
@NamedQueries({
    @NamedQuery(name = "Transactionapproval.findAll", query = "SELECT t FROM Transactionapproval t")
    , @NamedQuery(name = "Transactionapproval.findById", query = "SELECT t FROM Transactionapproval t WHERE t.id = :id")
    , @NamedQuery(name = "Transactionapproval.findByApprovedby", query = "SELECT t FROM Transactionapproval t WHERE t.approvedby = :approvedby")
    , @NamedQuery(name = "Transactionapproval.findByApprovedstatus", query = "SELECT t FROM Transactionapproval t WHERE t.approvedstatus = :approvedstatus")
    , @NamedQuery(name = "Transactionapproval.findByErrormessage", query = "SELECT t FROM Transactionapproval t WHERE t.errormessage = :errormessage")
    , @NamedQuery(name = "Transactionapproval.findByGroupid", query = "SELECT t FROM Transactionapproval t WHERE t.groupid = :groupid")
    , @NamedQuery(name = "Transactionapproval.findByInitiatedby", query = "SELECT t FROM Transactionapproval t WHERE t.initiatedby = :initiatedby")
    , @NamedQuery(name = "Transactionapproval.findByMembercode", query = "SELECT t FROM Transactionapproval t WHERE t.membercode = :membercode")
    , @NamedQuery(name = "Transactionapproval.findByMembernotified", query = "SELECT t FROM Transactionapproval t WHERE t.membernotified = :membernotified")
    , @NamedQuery(name = "Transactionapproval.findByMobilemoneymessage", query = "SELECT t FROM Transactionapproval t WHERE t.mobilemoneymessage = :mobilemoneymessage")
    , @NamedQuery(name = "Transactionapproval.findByNarration", query = "SELECT t FROM Transactionapproval t WHERE t.narration = :narration")
    , @NamedQuery(name = "Transactionapproval.findByOnetimepin", query = "SELECT t FROM Transactionapproval t WHERE t.onetimepin = :onetimepin")
    , @NamedQuery(name = "Transactionapproval.findByPaymentmode", query = "SELECT t FROM Transactionapproval t WHERE t.paymentmode = :paymentmode")
    , @NamedQuery(name = "Transactionapproval.findByTransactfrom", query = "SELECT t FROM Transactionapproval t WHERE t.transactfrom = :transactfrom")
    , @NamedQuery(name = "Transactionapproval.findByTransactionamount", query = "SELECT t FROM Transactionapproval t WHERE t.transactionamount = :transactionamount")
    , @NamedQuery(name = "Transactionapproval.findByTransactionref", query = "SELECT t FROM Transactionapproval t WHERE t.transactionref = :transactionref")
    , @NamedQuery(name = "Transactionapproval.findByTransactiontype", query = "SELECT t FROM Transactionapproval t WHERE t.transactiontype = :transactiontype")})
public class Transactionapproval implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "APPROVEDBY")
    private String approvedby;
    @Size(max = 255)
    @Column(name = "APPROVEDSTATUS")
    private String approvedstatus;
    @Size(max = 255)
    @Column(name = "ERRORMESSAGE")
    private String errormessage;
    @Size(max = 255)
    @Column(name = "GROUPID")
    private String groupid;
    @Size(max = 255)
    @Column(name = "INITIATEDBY")
    private String initiatedby;
    @Size(max = 255)
    @Column(name = "MEMBERCODE")
    private String membercode;
    @Size(max = 255)
    @Column(name = "MEMBERNOTIFIED")
    private String membernotified;
    @Size(max = 255)
    @Column(name = "MOBILEMONEYMESSAGE")
    private String mobilemoneymessage;
    @Size(max = 255)
    @Column(name = "NARRATION")
    private String narration;
    @Size(max = 255)
    @Column(name = "ONETIMEPIN")
    private String onetimepin;
    @Size(max = 255)
    @Column(name = "PAYMENTMODE")
    private String paymentmode;
    @Size(max = 255)
    @Column(name = "TRANSACTFROM")
    private String transactfrom;
    @Column(name = "TRANSACTIONAMOUNT")
    private BigInteger transactionamount;
    @Size(max = 255)
    @Column(name = "TRANSACTIONREF")
    private String transactionref;
    @Size(max = 255)
    @Column(name = "TRANSACTIONTYPE")
    private String transactiontype;

    public Transactionapproval() {
    }

    public Transactionapproval(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public String getApprovedstatus() {
        return approvedstatus;
    }

    public void setApprovedstatus(String approvedstatus) {
        this.approvedstatus = approvedstatus;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getInitiatedby() {
        return initiatedby;
    }

    public void setInitiatedby(String initiatedby) {
        this.initiatedby = initiatedby;
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
    }

    public String getMembernotified() {
        return membernotified;
    }

    public void setMembernotified(String membernotified) {
        this.membernotified = membernotified;
    }

    public String getMobilemoneymessage() {
        return mobilemoneymessage;
    }

    public void setMobilemoneymessage(String mobilemoneymessage) {
        this.mobilemoneymessage = mobilemoneymessage;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getOnetimepin() {
        return onetimepin;
    }

    public void setOnetimepin(String onetimepin) {
        this.onetimepin = onetimepin;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getTransactfrom() {
        return transactfrom;
    }

    public void setTransactfrom(String transactfrom) {
        this.transactfrom = transactfrom;
    }

    public BigInteger getTransactionamount() {
        return transactionamount;
    }

    public void setTransactionamount(BigInteger transactionamount) {
        this.transactionamount = transactionamount;
    }

    public String getTransactionref() {
        return transactionref;
    }

    public void setTransactionref(String transactionref) {
        this.transactionref = transactionref;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
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
        if (!(object instanceof Transactionapproval)) {
            return false;
        }
        Transactionapproval other = (Transactionapproval) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Transactionapproval[ id=" + id + " ]";
    }
    
}
