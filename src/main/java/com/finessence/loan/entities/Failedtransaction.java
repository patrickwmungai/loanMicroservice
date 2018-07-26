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
@Table(name = "FAILEDTRANSACTION")
@NamedQueries({
    @NamedQuery(name = "Failedtransaction.findAll", query = "SELECT f FROM Failedtransaction f")
    , @NamedQuery(name = "Failedtransaction.findById", query = "SELECT f FROM Failedtransaction f WHERE f.id = :id")
    , @NamedQuery(name = "Failedtransaction.findByAmount", query = "SELECT f FROM Failedtransaction f WHERE f.amount = :amount")
    , @NamedQuery(name = "Failedtransaction.findByInitiatedby", query = "SELECT f FROM Failedtransaction f WHERE f.initiatedby = :initiatedby")
    , @NamedQuery(name = "Failedtransaction.findByPostingdate", query = "SELECT f FROM Failedtransaction f WHERE f.postingdate = :postingdate")
    , @NamedQuery(name = "Failedtransaction.findByReason", query = "SELECT f FROM Failedtransaction f WHERE f.reason = :reason")
    , @NamedQuery(name = "Failedtransaction.findByTransactionreference", query = "SELECT f FROM Failedtransaction f WHERE f.transactionreference = :transactionreference")})
public class Failedtransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Column(name = "AMOUNT")
    private BigInteger amount;
    @Size(max = 255)
    @Column(name = "INITIATEDBY")
    private String initiatedby;
    @Column(name = "POSTINGDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postingdate;
    @Size(max = 255)
    @Column(name = "REASON")
    private String reason;
    @Size(max = 255)
    @Column(name = "TRANSACTIONREFERENCE")
    private String transactionreference;

    public Failedtransaction() {
    }

    public Failedtransaction(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getInitiatedby() {
        return initiatedby;
    }

    public void setInitiatedby(String initiatedby) {
        this.initiatedby = initiatedby;
    }

    public Date getPostingdate() {
        return postingdate;
    }

    public void setPostingdate(Date postingdate) {
        this.postingdate = postingdate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTransactionreference() {
        return transactionreference;
    }

    public void setTransactionreference(String transactionreference) {
        this.transactionreference = transactionreference;
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
        if (!(object instanceof Failedtransaction)) {
            return false;
        }
        Failedtransaction other = (Failedtransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Failedtransaction[ id=" + id + " ]";
    }
    
}
