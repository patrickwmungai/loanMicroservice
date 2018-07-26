/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "GROUP_APPROVAL_LEVEL")
@NamedQueries({
    @NamedQuery(name = "GroupApprovalLevel.findAll", query = "SELECT g FROM GroupApprovalLevel g")
    , @NamedQuery(name = "GroupApprovalLevel.findById", query = "SELECT g FROM GroupApprovalLevel g WHERE g.id = :id")
    , @NamedQuery(name = "GroupApprovalLevel.findByGroupId", query = "SELECT g FROM GroupApprovalLevel g WHERE g.groupId = :groupId")
    , @NamedQuery(name = "GroupApprovalLevel.findByPosition", query = "SELECT g FROM GroupApprovalLevel g WHERE g.position = :position")
    , @NamedQuery(name = "GroupApprovalLevel.findByName", query = "SELECT g FROM GroupApprovalLevel g WHERE g.name = :name")
    , @NamedQuery(name = "GroupApprovalLevel.findByDescription", query = "SELECT g FROM GroupApprovalLevel g WHERE g.description = :description")
    , @NamedQuery(name = "GroupApprovalLevel.findByCreatedBy", query = "SELECT g FROM GroupApprovalLevel g WHERE g.createdBy = :createdBy")
    , @NamedQuery(name = "GroupApprovalLevel.findByUpdatedBy", query = "SELECT g FROM GroupApprovalLevel g WHERE g.updatedBy = :updatedBy")
    , @NamedQuery(name = "GroupApprovalLevel.findByDateCreated", query = "SELECT g FROM GroupApprovalLevel g WHERE g.dateCreated = :dateCreated")
    , @NamedQuery(name = "GroupApprovalLevel.findByStatus", query = "SELECT g FROM GroupApprovalLevel g WHERE g.status = :status")})
public class GroupApprovalLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "GROUP_ID")
    private String groupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSITION")
    private int position;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED_BY")
    private int createdBy;
    @Column(name = "UPDATED_BY")
    private Integer updatedBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "DATE_CREATED")
    private String dateCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private int status;

    public GroupApprovalLevel() {
    }

    public GroupApprovalLevel(Integer id) {
        this.id = id;
    }

    public GroupApprovalLevel(Integer id, String groupId, int position, String name, String description, int createdBy, String dateCreated, int status) {
        this.id = id;
        this.groupId = groupId;
        this.position = position;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        if (!(object instanceof GroupApprovalLevel)) {
            return false;
        }
        GroupApprovalLevel other = (GroupApprovalLevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.GroupApprovalLevel[ id=" + id + " ]";
    }
    
}
