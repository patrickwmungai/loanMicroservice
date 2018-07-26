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
@Table(name = "INVESTMENT")
@NamedQueries({
    @NamedQuery(name = "Investment.findAll", query = "SELECT i FROM Investment i")
    , @NamedQuery(name = "Investment.findById", query = "SELECT i FROM Investment i WHERE i.id = :id")
    , @NamedQuery(name = "Investment.findByAccountid", query = "SELECT i FROM Investment i WHERE i.accountid = :accountid")
    , @NamedQuery(name = "Investment.findByAddedby", query = "SELECT i FROM Investment i WHERE i.addedby = :addedby")
    , @NamedQuery(name = "Investment.findByAmountvalue", query = "SELECT i FROM Investment i WHERE i.amountvalue = :amountvalue")
    , @NamedQuery(name = "Investment.findByGroupid", query = "SELECT i FROM Investment i WHERE i.groupid = :groupid")
    , @NamedQuery(name = "Investment.findByInvname", query = "SELECT i FROM Investment i WHERE i.invname = :invname")})
public class Investment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "ACCOUNTID")
    private String accountid;
    @Size(max = 255)
    @Column(name = "ADDEDBY")
    private String addedby;
    @Column(name = "AMOUNTVALUE")
    private BigInteger amountvalue;
    @Size(max = 255)
    @Column(name = "GROUPID")
    private String groupid;
    @Size(max = 255)
    @Column(name = "INVNAME")
    private String invname;

    public Investment() {
    }

    public Investment(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public BigInteger getAmountvalue() {
        return amountvalue;
    }

    public void setAmountvalue(BigInteger amountvalue) {
        this.amountvalue = amountvalue;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getInvname() {
        return invname;
    }

    public void setInvname(String invname) {
        this.invname = invname;
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
        if (!(object instanceof Investment)) {
            return false;
        }
        Investment other = (Investment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Investment[ id=" + id + " ]";
    }
    
}
