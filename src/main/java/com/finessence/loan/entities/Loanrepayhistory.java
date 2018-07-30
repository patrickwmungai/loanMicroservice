/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigDecimal;
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

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "LOANREPAYHISTORY")
@NamedQueries({
    @NamedQuery(name = "Loanrepayhistory.findAll", query = "SELECT l FROM Loanrepayhistory l")
    , @NamedQuery(name = "Loanrepayhistory.findById", query = "SELECT l FROM Loanrepayhistory l WHERE l.id = :id")
    , @NamedQuery(name = "Loanrepayhistory.findByInterestamountpaid", query = "SELECT l FROM Loanrepayhistory l WHERE l.interestamountpaid = :interestamountpaid")
    , @NamedQuery(name = "Loanrepayhistory.findByLoandetailsid", query = "SELECT l FROM Loanrepayhistory l WHERE l.loandetailsid = :loandetailsid")
    , @NamedQuery(name = "Loanrepayhistory.findByPrincipleamountpaid", query = "SELECT l FROM Loanrepayhistory l WHERE l.principleamountpaid = :principleamountpaid")
    , @NamedQuery(name = "Loanrepayhistory.findByRepaydate", query = "SELECT l FROM Loanrepayhistory l WHERE l.repaydate = :repaydate")})
public class Loanrepayhistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Column(name = "INTERESTAMOUNTPAID")
    private BigDecimal interestamountpaid;
    @Column(name = "LOANDETAILSID")
    private BigDecimal loandetailsid;
    @Column(name = "PRINCIPLEAMOUNTPAID")
    private BigDecimal principleamountpaid;
    @Column(name = "REPAYDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repaydate;

    public Loanrepayhistory() {
    }

    public Loanrepayhistory(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getInterestamountpaid() {
        return interestamountpaid;
    }

    public void setInterestamountpaid(BigDecimal interestamountpaid) {
        this.interestamountpaid = interestamountpaid;
    }

    public BigDecimal getLoandetailsid() {
        return loandetailsid;
    }

    public void setLoandetailsid(BigDecimal loandetailsid) {
        this.loandetailsid = loandetailsid;
    }

    public BigDecimal getPrincipleamountpaid() {
        return principleamountpaid;
    }

    public void setPrincipleamountpaid(BigDecimal principleamountpaid) {
        this.principleamountpaid = principleamountpaid;
    }

    public Date getRepaydate() {
        return repaydate;
    }

    public void setRepaydate(Date repaydate) {
        this.repaydate = repaydate;
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
        if (!(object instanceof Loanrepayhistory)) {
            return false;
        }
        Loanrepayhistory other = (Loanrepayhistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Loanrepayhistory[ id=" + id + " ]";
    }
    
}
