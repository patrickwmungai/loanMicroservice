/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "ROLE_MAP")
@NamedQueries({
    @NamedQuery(name = "RoleMap.findAll", query = "SELECT r FROM RoleMap r")
    , @NamedQuery(name = "RoleMap.findById", query = "SELECT r FROM RoleMap r WHERE r.id = :id")
    , @NamedQuery(name = "RoleMap.findByRoleId", query = "SELECT r FROM RoleMap r WHERE r.roleId = :roleId")
    , @NamedQuery(name = "RoleMap.findByGroupId", query = "SELECT r FROM RoleMap r WHERE r.groupId = :groupId")
    , @NamedQuery(name = "RoleMap.findByUserId", query = "SELECT r FROM RoleMap r WHERE r.userId = :userId")
    , @NamedQuery(name = "RoleMap.findByTimeCreated", query = "SELECT r FROM RoleMap r WHERE r.timeCreated = :timeCreated")
    , @NamedQuery(name = "RoleMap.findByLastUpdate", query = "SELECT r FROM RoleMap r WHERE r.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "RoleMap.findByCreatedBy", query = "SELECT r FROM RoleMap r WHERE r.createdBy = :createdBy")
    , @NamedQuery(name = "RoleMap.findByUpdatedBy", query = "SELECT r FROM RoleMap r WHERE r.updatedBy = :updatedBy")
    , @NamedQuery(name = "RoleMap.findByStatus", query = "SELECT r FROM RoleMap r WHERE r.status = :status")
    , @NamedQuery(name = "RoleMap.findByApprovalStatus", query = "SELECT r FROM RoleMap r WHERE r.approvalStatus = :approvalStatus")
    , @NamedQuery(name = "RoleMap.findByMaxApprovals", query = "SELECT r FROM RoleMap r WHERE r.maxApprovals = :maxApprovals")
    , @NamedQuery(name = "RoleMap.findByCurrentApprovalLevel", query = "SELECT r FROM RoleMap r WHERE r.currentApprovalLevel = :currentApprovalLevel")
    , @NamedQuery(name = "RoleMap.findByIntrash", query = "SELECT r FROM RoleMap r WHERE r.intrash = :intrash")})
public class RoleMap implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ROLE_ID")
    private Integer roleId;
    @Size(max = 255)
    @Column(name = "GROUP_ID")
    private String groupId;
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "TIME_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @Column(name = "LAST_UPDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Column(name = "UPDATED_BY")
    private Integer updatedBy;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "APPROVAL_STATUS")
    private Integer approvalStatus;
    @Column(name = "MAX_APPROVALS")
    private Integer maxApprovals;
    @Column(name = "CURRENT_APPROVAL_LEVEL")
    private Integer currentApprovalLevel;
    @Size(max = 5)
    @Column(name = "INTRASH")
    private String intrash;

    public RoleMap() {
    }

    public RoleMap(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getMaxApprovals() {
        return maxApprovals;
    }

    public void setMaxApprovals(Integer maxApprovals) {
        this.maxApprovals = maxApprovals;
    }

    public Integer getCurrentApprovalLevel() {
        return currentApprovalLevel;
    }

    public void setCurrentApprovalLevel(Integer currentApprovalLevel) {
        this.currentApprovalLevel = currentApprovalLevel;
    }

    public String getIntrash() {
        return intrash;
    }

    public void setIntrash(String intrash) {
        this.intrash = intrash;
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
        if (!(object instanceof RoleMap)) {
            return false;
        }
        RoleMap other = (RoleMap) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.RoleMap[ id=" + id + " ]";
    }
    
}
