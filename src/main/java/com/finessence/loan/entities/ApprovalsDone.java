/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "APPROVALS_DONE")
@NamedQueries({
    @NamedQuery(name = "ApprovalsDone.findAll", query = "SELECT a FROM ApprovalsDone a")
    , @NamedQuery(name = "ApprovalsDone.findById", query = "SELECT a FROM ApprovalsDone a WHERE a.id = :id")
    , @NamedQuery(name = "ApprovalsDone.findByGroupId", query = "SELECT a FROM ApprovalsDone a WHERE a.groupId = :groupId")
    , @NamedQuery(name = "ApprovalsDone.findByType", query = "SELECT a FROM ApprovalsDone a WHERE a.type = :type")
    , @NamedQuery(name = "ApprovalsDone.findByRecordId", query = "SELECT a FROM ApprovalsDone a WHERE a.recordId = :recordId")
    , @NamedQuery(name = "ApprovalsDone.findByApproverId", query = "SELECT a FROM ApprovalsDone a WHERE a.approverId = :approverId")
    , @NamedQuery(name = "ApprovalsDone.findByApprovalLevel", query = "SELECT a FROM ApprovalsDone a WHERE a.approvalLevel = :approvalLevel")
    , @NamedQuery(name = "ApprovalsDone.findByApprovalLevelName", query = "SELECT a FROM ApprovalsDone a WHERE a.approvalLevelName = :approvalLevelName")
    , @NamedQuery(name = "ApprovalsDone.findByApprovedAmount", query = "SELECT a FROM ApprovalsDone a WHERE a.approvedAmount = :approvedAmount")
    , @NamedQuery(name = "ApprovalsDone.findByOriginalAmount", query = "SELECT a FROM ApprovalsDone a WHERE a.originalAmount = :originalAmount")
    , @NamedQuery(name = "ApprovalsDone.findByApprovalTime", query = "SELECT a FROM ApprovalsDone a WHERE a.approvalTime = :approvalTime")
    , @NamedQuery(name = "ApprovalsDone.findByApprovalStatus", query = "SELECT a FROM ApprovalsDone a WHERE a.approvalStatus = :approvalStatus")})
public class ApprovalsDone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 200)
    @Column(name = "GROUP_ID")
    private String groupId;
    @Size(max = 100)
    @Column(name = "TYPE")
    private String type;
    @Column(name = "RECORD_ID")
    private Integer recordId;
    @Column(name = "APPROVER_ID")
    private Integer approverId;
    @Column(name = "APPROVAL_LEVEL")
    private Integer approvalLevel;
    @Size(max = 100)
    @Column(name = "APPROVAL_LEVEL_NAME")
    private String approvalLevelName;
    @Lob
    @Size(max = 65535)
    @Column(name = "APPROVAL_NOTES")
    private String approvalNotes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "APPROVED_AMOUNT")
    private BigDecimal approvedAmount;
    @Column(name = "ORIGINAL_AMOUNT")
    private BigDecimal originalAmount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "APPROVAL_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalTime;
    @Size(max = 18)
    @Column(name = "APPROVAL_STATUS")
    private String approvalStatus;

    public ApprovalsDone() {
    }

    public ApprovalsDone(Integer id) {
        this.id = id;
    }

    public ApprovalsDone(Integer id, Date approvalTime) {
        this.id = id;
        this.approvalTime = approvalTime;
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

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getApproverId() {
        return approverId;
    }

    public void setApproverId(Integer approverId) {
        this.approverId = approverId;
    }

    public Integer getApprovalLevel() {
        return approvalLevel;
    }

    public void setApprovalLevel(Integer approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    public String getApprovalLevelName() {
        return approvalLevelName;
    }

    public void setApprovalLevelName(String approvalLevelName) {
        this.approvalLevelName = approvalLevelName;
    }

    public String getApprovalNotes() {
        return approvalNotes;
    }

    public void setApprovalNotes(String approvalNotes) {
        this.approvalNotes = approvalNotes;
    }

    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
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
        if (!(object instanceof ApprovalsDone)) {
            return false;
        }
        ApprovalsDone other = (ApprovalsDone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.ApprovalsDone[ id=" + id + " ]";
    }
    
}
