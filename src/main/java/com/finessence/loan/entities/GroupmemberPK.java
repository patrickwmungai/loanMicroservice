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
public class GroupmemberPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "MEMBERCODE")
    private String membercode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private long id;

    public GroupmemberPK() {
    }

    public GroupmemberPK(String membercode, long id) {
        this.membercode = membercode;
        this.id = id;
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
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
        hash += (membercode != null ? membercode.hashCode() : 0);
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupmemberPK)) {
            return false;
        }
        GroupmemberPK other = (GroupmemberPK) object;
        if ((this.membercode == null && other.membercode != null) || (this.membercode != null && !this.membercode.equals(other.membercode))) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.GroupmemberPK[ membercode=" + membercode + ", id=" + id + " ]";
    }
    
}
