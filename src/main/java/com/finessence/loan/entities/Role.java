/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import com.finessence.loan.model.PermssionHolder;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "ROLE")
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
    , @NamedQuery(name = "Role.findById", query = "SELECT r FROM Role r WHERE r.id = :id")
    , @NamedQuery(name = "Role.findByName", query = "SELECT r FROM Role r WHERE r.name = :name")
    , @NamedQuery(name = "Role.findByGroupId", query = "SELECT r FROM Role r WHERE r.groupId = :groupId")
    , @NamedQuery(name = "Role.findByTimeCreated", query = "SELECT r FROM Role r WHERE r.timeCreated = :timeCreated")
    , @NamedQuery(name = "Role.findByTimeUpdated", query = "SELECT r FROM Role r WHERE r.timeUpdated = :timeUpdated")
    , @NamedQuery(name = "Role.findByCreatedBy", query = "SELECT r FROM Role r WHERE r.createdBy = :createdBy")
    , @NamedQuery(name = "Role.findByUpdatedBy", query = "SELECT r FROM Role r WHERE r.updatedBy = :updatedBy")
    , @NamedQuery(name = "Role.findByStatus", query = "SELECT r FROM Role r WHERE r.status = :status")
    , @NamedQuery(name = "Role.findByApprovalStatus", query = "SELECT r FROM Role r WHERE r.approvalStatus = :approvalStatus")
    , @NamedQuery(name = "Role.findByMaxApprovals", query = "SELECT r FROM Role r WHERE r.maxApprovals = :maxApprovals")
    , @NamedQuery(name = "Role.findByCurrentApprovalLevel", query = "SELECT r FROM Role r WHERE r.currentApprovalLevel = :currentApprovalLevel")
    , @NamedQuery(name = "Role.findByIntrash", query = "SELECT r FROM Role r WHERE r.intrash = :intrash")})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 70)
    @Column(name = "NAME")
    private String name;
    @Size(max = 255)
    @Column(name = "GROUP_ID")
    private String groupId;
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
    @Transient
    private List<Permission> permissions;
    @Transient
    private boolean has_role;
     @Transient
    private List<PermssionHolder> permissionsMap;

    public Role() {
    }

    public List<PermssionHolder> getPermissionsMap() {
        return permissionsMap;
    }

    public void setPermissionsMap(List<PermssionHolder> permissionsMap) {
        this.permissionsMap = permissionsMap;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public boolean isHas_role() {
        return has_role;
    }

    public void setHas_role(boolean has_role) {
        this.has_role = has_role;
    }

    public Role(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Role[ id=" + id + " ]";
    }
    
}
