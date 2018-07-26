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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "GROUP_APPROVAL_LEVELS_CONFIG")
@NamedQueries({
    @NamedQuery(name = "GroupApprovalLevelsConfig.findAll", query = "SELECT g FROM GroupApprovalLevelsConfig g")
    , @NamedQuery(name = "GroupApprovalLevelsConfig.findById", query = "SELECT g FROM GroupApprovalLevelsConfig g WHERE g.id = :id")
    , @NamedQuery(name = "GroupApprovalLevelsConfig.findByGroupId", query = "SELECT g FROM GroupApprovalLevelsConfig g WHERE g.groupId = :groupId")
    , @NamedQuery(name = "GroupApprovalLevelsConfig.findByType", query = "SELECT g FROM GroupApprovalLevelsConfig g WHERE g.type = :type")
    , @NamedQuery(name = "GroupApprovalLevelsConfig.findByDateCreated", query = "SELECT g FROM GroupApprovalLevelsConfig g WHERE g.dateCreated = :dateCreated")
    , @NamedQuery(name = "GroupApprovalLevelsConfig.findByCreatedBy", query = "SELECT g FROM GroupApprovalLevelsConfig g WHERE g.createdBy = :createdBy")
    , @NamedQuery(name = "GroupApprovalLevelsConfig.findByUpdatedBy", query = "SELECT g FROM GroupApprovalLevelsConfig g WHERE g.updatedBy = :updatedBy")
    , @NamedQuery(name = "GroupApprovalLevelsConfig.findByStatus", query = "SELECT g FROM GroupApprovalLevelsConfig g WHERE g.status = :status")})
public class GroupApprovalLevelsConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 200)
    @Column(name = "GROUP_ID")
    private String groupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "TYPE")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TYPE_APPROVALS")
    private String typeApprovals;
    @Size(max = 45)
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED_BY")
    private int createdBy;
    @Column(name = "UPDATED_BY")
    private Integer updatedBy;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "TYPE_APPROVAL_IDS")
    private String typeApprovalIds;

    public GroupApprovalLevelsConfig() {
    }

    public GroupApprovalLevelsConfig(Integer id) {
        this.id = id;
    }

    public GroupApprovalLevelsConfig(Integer id, String type, String typeApprovals, int createdBy) {
        this.id = id;
        this.type = type;
        this.typeApprovals = typeApprovals;
        this.createdBy = createdBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTypeApprovalIds() {
        return typeApprovalIds;
    }

    public void setTypeApprovalIds(String typeApprovalIds) {
        this.typeApprovalIds = typeApprovalIds;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeApprovals() {
        return typeApprovals;
    }

    public void setTypeApprovals(String typeApprovals) {
        this.typeApprovals = typeApprovals;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
        if (!(object instanceof GroupApprovalLevelsConfig)) {
            return false;
        }
        GroupApprovalLevelsConfig other = (GroupApprovalLevelsConfig) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.GroupApprovalLevelsConfig[ id=" + id + " ]";
    }

}
