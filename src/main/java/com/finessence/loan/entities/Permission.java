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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "PERMISSION")
@NamedQueries({
    @NamedQuery(name = "Permission.findAll", query = "SELECT p FROM Permission p")
    , @NamedQuery(name = "Permission.findById", query = "SELECT p FROM Permission p WHERE p.id = :id")
    , @NamedQuery(name = "Permission.findByName", query = "SELECT p FROM Permission p WHERE p.name = :name")
    , @NamedQuery(name = "Permission.findByTimeCreated", query = "SELECT p FROM Permission p WHERE p.timeCreated = :timeCreated")
    , @NamedQuery(name = "Permission.findByTimeUpdated", query = "SELECT p FROM Permission p WHERE p.timeUpdated = :timeUpdated")
    , @NamedQuery(name = "Permission.findByCreatedBy", query = "SELECT p FROM Permission p WHERE p.createdBy = :createdBy")
    , @NamedQuery(name = "Permission.findByUpdatedBy", query = "SELECT p FROM Permission p WHERE p.updatedBy = :updatedBy")
    , @NamedQuery(name = "Permission.findByStatus", query = "SELECT p FROM Permission p WHERE p.status = :status")
    , @NamedQuery(name = "Permission.findByApprovalStatus", query = "SELECT p FROM Permission p WHERE p.approvalStatus = :approvalStatus")
    , @NamedQuery(name = "Permission.findByMaxApprovals", query = "SELECT p FROM Permission p WHERE p.maxApprovals = :maxApprovals")
    , @NamedQuery(name = "Permission.findByIntrash", query = "SELECT p FROM Permission p WHERE p.intrash = :intrash")
    , @NamedQuery(name = "Permission.findByCurrentApprovalLevel", query = "SELECT p FROM Permission p WHERE p.currentApprovalLevel = :currentApprovalLevel")
    , @NamedQuery(name = "Permission.findByUrl", query = "SELECT p FROM Permission p WHERE p.url = :url")
    , @NamedQuery(name = "Permission.findByParentId", query = "SELECT p FROM Permission p WHERE p.parentId = :parentId")
    , @NamedQuery(name = "Permission.findByIcon", query = "SELECT p FROM Permission p WHERE p.icon = :icon")
    , @NamedQuery(name = "Permission.findByIsMenu", query = "SELECT p FROM Permission p WHERE p.isMenu = :isMenu")
    , @NamedQuery(name = "Permission.findByMenuPosition", query = "SELECT p FROM Permission p WHERE p.menuPosition = :menuPosition")
    , @NamedQuery(name = "Permission.findByMenuName", query = "SELECT p FROM Permission p WHERE p.menuName = :menuName")
    , @NamedQuery(name = "Permission.findByDescription", query = "SELECT p FROM Permission p WHERE p.description = :description")
    , @NamedQuery(name = "Permission.findByModule", query = "SELECT p FROM Permission p WHERE p.module = :module")
    , @NamedQuery(name = "Permission.findBySubModule", query = "SELECT p FROM Permission p WHERE p.subModule = :subModule")})
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "NAME")
    private String name;
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
    @Size(max = 5)
    @Column(name = "INTRASH")
    private String intrash;
    @Column(name = "CURRENT_APPROVAL_LEVEL")
    private Integer currentApprovalLevel;
    @Size(max = 100)
    @Column(name = "URL")
    private String url;
    @Column(name = "PARENT_ID")
    private Integer parentId;
    @Size(max = 100)
    @Column(name = "ICON")
    private String icon;
    @Column(name = "IS_MENU")
    private Integer isMenu;
    @Column(name = "MENU_POSITION")
    private Integer menuPosition;
    @Size(max = 70)
    @Column(name = "MENU_NAME")
    private String menuName;
    @Size(max = 250)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 45)
    @Column(name = "MODULE")
    private String module;
    @Size(max = 45)
    @Column(name = "SUB_MODULE")
    private String subModule;
    @Transient
    private boolean has_permission;

    public Permission() {
    }

    public boolean isHas_permission() {
        return has_permission;
    }

    public void setHas_permission(boolean has_permission) {
        this.has_permission = has_permission;
    }

    public Permission(Integer id) {
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

    public String getIntrash() {
        return intrash;
    }

    public void setIntrash(String intrash) {
        this.intrash = intrash;
    }

    public Integer getCurrentApprovalLevel() {
        return currentApprovalLevel;
    }

    public void setCurrentApprovalLevel(Integer currentApprovalLevel) {
        this.currentApprovalLevel = currentApprovalLevel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public Integer getMenuPosition() {
        return menuPosition;
    }

    public void setMenuPosition(Integer menuPosition) {
        this.menuPosition = menuPosition;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSubModule() {
        return subModule;
    }

    public void setSubModule(String subModule) {
        this.subModule = subModule;
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
        if (!(object instanceof Permission)) {
            return false;
        }
        Permission other = (Permission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Permission[ id=" + id + " ]";
    }
    
}
