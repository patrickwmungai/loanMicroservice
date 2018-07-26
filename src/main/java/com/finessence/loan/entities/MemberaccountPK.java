/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Embeddable
public class MemberaccountPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ACCOUNTID")
    private String accountid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private long id;

    public MemberaccountPK() {
    }

    public MemberaccountPK(String accountid, long id) {
        this.accountid = accountid;
        this.id = id;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountid != null ? accountid.hashCode() : 0);
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemberaccountPK)) {
            return false;
        }
        MemberaccountPK other = (MemberaccountPK) object;
        if ((this.accountid == null && other.accountid != null) || (this.accountid != null && !this.accountid.equals(other.accountid))) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.MemberaccountPK[ accountid=" + accountid + ", id=" + id + " ]";
    }
    
}
