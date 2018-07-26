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
@Table(name = "PERMISSION_MAP")
@NamedQueries({
    @NamedQuery(name = "PermissionMap.findAll", query = "SELECT p FROM PermissionMap p")
    , @NamedQuery(name = "PermissionMap.findById", query = "SELECT p FROM PermissionMap p WHERE p.id = :id")
    , @NamedQuery(name = "PermissionMap.findByRoleId", query = "SELECT p FROM PermissionMap p WHERE p.roleId = :roleId")
    , @NamedQuery(name = "PermissionMap.findByPermissionId", query = "SELECT p FROM PermissionMap p WHERE p.permissionId = :permissionId")
    , @NamedQuery(name = "PermissionMap.findByTimeCreated", query = "SELECT p FROM PermissionMap p WHERE p.timeCreated = :timeCreated")
    , @NamedQuery(name = "PermissionMap.findByTimeUpdated", query = "SELECT p FROM PermissionMap p WHERE p.timeUpdated = :timeUpdated")
    , @NamedQuery(name = "PermissionMap.findByCreatedBy", query = "SELECT p FROM PermissionMap p WHERE p.createdBy = :createdBy")
    , @NamedQuery(name = "PermissionMap.findByUpdatedBy", query = "SELECT p FROM PermissionMap p WHERE p.updatedBy = :updatedBy")
    , @NamedQuery(name = "PermissionMap.findByStatus", query = "SELECT p FROM PermissionMap p WHERE p.status = :status")
    , @NamedQuery(name = "PermissionMap.findByApprovalStatus", query = "SELECT p FROM PermissionMap p WHERE p.approvalStatus = :approvalStatus")
    , @NamedQuery(name = "PermissionMap.findByMaxApprovals", query = "SELECT p FROM PermissionMap p WHERE p.maxApprovals = :maxApprovals")
    , @NamedQuery(name = "PermissionMap.findByCurrentApprovalLevel", query = "SELECT p FROM PermissionMap p WHERE p.currentApprovalLevel = :currentApprovalLevel")
    , @NamedQuery(name = "PermissionMap.findByIntrash", query = "SELECT p FROM PermissionMap p WHERE p.intrash = :intrash")
    , @NamedQuery(name = "PermissionMap.findByModule", query = "SELECT p FROM PermissionMap p WHERE p.module = :module")})
public class PermissionMap implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ROLE_ID")
    private Integer roleId;
    @Column(name = "PERMISSION_ID")
    private Integer permissionId;
    @Column(name = "TIME_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @Column(name = "TIME_UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeUpdated;
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
    @Size(max = 45)
    @Column(name = "MODULE")
    private String module;

    public PermissionMap() {
    }

    public PermissionMap(Integer id) {
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

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(Date timeUpdated) {
        this.timeUpdated = timeUpdated;
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
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
        if (!(object instanceof PermissionMap)) {
            return false;
        }
        PermissionMap other = (PermissionMap) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.PermissionMap[ id=" + id + " ]";
    }
    
}
