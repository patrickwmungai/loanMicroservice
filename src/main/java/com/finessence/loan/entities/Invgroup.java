/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "INVGROUP")
@NamedQueries({
    @NamedQuery(name = "Invgroup.findAll", query = "SELECT i FROM Invgroup i")
    , @NamedQuery(name = "Invgroup.findByGroupid", query = "SELECT i FROM Invgroup i WHERE i.groupid = :groupid")
    , @NamedQuery(name = "Invgroup.findByCreatedby", query = "SELECT i FROM Invgroup i WHERE i.createdby = :createdby")
    , @NamedQuery(name = "Invgroup.findByDatecreated", query = "SELECT i FROM Invgroup i WHERE i.datecreated = :datecreated")
    , @NamedQuery(name = "Invgroup.findByGroupcode", query = "SELECT i FROM Invgroup i WHERE i.groupcode = :groupcode")
    , @NamedQuery(name = "Invgroup.findByGroupname", query = "SELECT i FROM Invgroup i WHERE i.groupname = :groupname")
    , @NamedQuery(name = "Invgroup.findByPhoneimei", query = "SELECT i FROM Invgroup i WHERE i.phoneimei = :phoneimei")})
public class Invgroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "GROUPID")
    private String groupid;
    @Size(max = 255)
    @Column(name = "CREATEDBY")
    private String createdby;
    @Column(name = "DATECREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreated;
    @Size(max = 255)
    @Column(name = "GROUPCODE")
    private String groupcode;
    @Size(max = 255)
    @Column(name = "GROUPNAME")
    private String groupname;
    @Size(max = 255)
    @Column(name = "PHONEIMEI")
    private String phoneimei;
    @Column(name = "IS_ADMIN_GROUP")
    private int isAdminGroup;
    @Column(name = "REPAYMENT_DAY")
    private int repaymentDay;
    @Column(name = "MESSAGINGAPIKEY")
    private String messagingKey;
    @Column(name = "MESSAGINGUSERNAME")
    private String messagingUsername;

    public Invgroup() {
    }

    public int getIsAdminGroup() {
        return isAdminGroup;
    }

    public void setIsAdminGroup(int isAdminGroup) {
        this.isAdminGroup = isAdminGroup;
    }

    public Invgroup(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getPhoneimei() {
        return phoneimei;
    }

    public void setPhoneimei(String phoneimei) {
        this.phoneimei = phoneimei;
    }

    
  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupid != null ? groupid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invgroup)) {
            return false;
        }
        Invgroup other = (Invgroup) object;
        if ((this.groupid == null && other.groupid != null) || (this.groupid != null && !this.groupid.equals(other.groupid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Invgroup[ groupid=" + groupid + " ]";
    }

    public int getRepaymentDay() {
        return repaymentDay;
    }

    public void setRepaymentDay(int repaymentDay) {
        this.repaymentDay = repaymentDay;
    }

    public String getMessagingKey() {
        return messagingKey;
    }

    public void setMessagingKey(String messagingKey) {
        this.messagingKey = messagingKey;
    }

    public String getMessagingUsername() {
        return messagingUsername;
    }

    public void setMessagingUsername(String messagingUsername) {
        this.messagingUsername = messagingUsername;
    }
        
}
