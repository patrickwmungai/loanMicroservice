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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "LOAN_DISBURSEMENTS")
@NamedQueries({
    @NamedQuery(name = "LoanDisbursements.findAll", query = "SELECT l FROM LoanDisbursements l")
    , @NamedQuery(name = "LoanDisbursements.findById", query = "SELECT l FROM LoanDisbursements l WHERE l.id = :id")
    , @NamedQuery(name = "LoanDisbursements.findByLoanApplicationId", query = "SELECT l FROM LoanDisbursements l WHERE l.loanApplicationId = :loanApplicationId")
    , @NamedQuery(name = "LoanDisbursements.findByGroupId", query = "SELECT l FROM LoanDisbursements l WHERE l.groupId = :groupId")
    , @NamedQuery(name = "LoanDisbursements.findByAmount", query = "SELECT l FROM LoanDisbursements l WHERE l.amount = :amount")
    , @NamedQuery(name = "LoanDisbursements.findByDisbursementChannel", query = "SELECT l FROM LoanDisbursements l WHERE l.disbursementChannel = :disbursementChannel")
    , @NamedQuery(name = "LoanDisbursements.findByReceiptNo", query = "SELECT l FROM LoanDisbursements l WHERE l.receiptNo = :receiptNo")
    , @NamedQuery(name = "LoanDisbursements.findByApprovalStatus", query = "SELECT l FROM LoanDisbursements l WHERE l.approvalStatus = :approvalStatus")
    , @NamedQuery(name = "LoanDisbursements.findByCurrentApprovalLevel", query = "SELECT l FROM LoanDisbursements l WHERE l.currentApprovalLevel = :currentApprovalLevel")
    , @NamedQuery(name = "LoanDisbursements.findByApprovedBy", query = "SELECT l FROM LoanDisbursements l WHERE l.approvedBy = :approvedBy")
    , @NamedQuery(name = "LoanDisbursements.findByDateCreated", query = "SELECT l FROM LoanDisbursements l WHERE l.dateCreated = :dateCreated")
    , @NamedQuery(name = "LoanDisbursements.findByDateApproved", query = "SELECT l FROM LoanDisbursements l WHERE l.dateApproved = :dateApproved")})
public class LoanDisbursements implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOAN_APPLICATION_ID")
    private int loanApplicationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "GROUP_ID")
    private String groupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private long amount;
    @Size(max = 45)
    @Column(name = "DISBURSEMENT_CHANNEL")
    private String disbursementChannel;
    @Size(max = 45)
    @Column(name = "RECEIPT_NO")
    private String receiptNo;
    @Size(max = 45)
    @Column(name = "APPROVAL_STATUS")
    private String approvalStatus;
    @Column(name = "CURRENT_APPROVAL_LEVEL")
    private Integer currentApprovalLevel;
    @Size(max = 45)
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "DATE_APPROVED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateApproved;
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Column(name = "UPDATED_BY")
    private Integer updatedBy;

    public LoanDisbursements() {
    }

    public LoanDisbursements(Integer id) {
        this.id = id;
    }

    public LoanDisbursements(Integer id, int loanApplicationId, String groupId, long amount) {
        this.id = id;
        this.loanApplicationId = loanApplicationId;
        this.groupId = groupId;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(int loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDisbursementChannel() {
        return disbursementChannel;
    }

    public void setDisbursementChannel(String disbursementChannel) {
        this.disbursementChannel = disbursementChannel;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getCurrentApprovalLevel() {
        return currentApprovalLevel;
    }

    public void setCurrentApprovalLevel(Integer currentApprovalLevel) {
        this.currentApprovalLevel = currentApprovalLevel;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
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
        if (!(object instanceof LoanDisbursements)) {
            return false;
        }
        LoanDisbursements other = (LoanDisbursements) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.LoanDisbursements[ id=" + id + " ]";
    }
    
}
