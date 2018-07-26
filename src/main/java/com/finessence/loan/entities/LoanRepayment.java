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
@Table(name = "LOAN_REPAYMENT")
@NamedQueries({
    @NamedQuery(name = "LoanRepayment.findAll", query = "SELECT l FROM LoanRepayment l")
    , @NamedQuery(name = "LoanRepayment.findById", query = "SELECT l FROM LoanRepayment l WHERE l.id = :id")
    , @NamedQuery(name = "LoanRepayment.findByGroupId", query = "SELECT l FROM LoanRepayment l WHERE l.groupId = :groupId")
    , @NamedQuery(name = "LoanRepayment.findByLoanApplicationId", query = "SELECT l FROM LoanRepayment l WHERE l.loanApplicationId = :loanApplicationId")
    , @NamedQuery(name = "LoanRepayment.findByPaymentType", query = "SELECT l FROM LoanRepayment l WHERE l.paymentType = :paymentType")
    , @NamedQuery(name = "LoanRepayment.findByReceiptNo", query = "SELECT l FROM LoanRepayment l WHERE l.receiptNo = :receiptNo")
    , @NamedQuery(name = "LoanRepayment.findByDateCreated", query = "SELECT l FROM LoanRepayment l WHERE l.dateCreated = :dateCreated")
    , @NamedQuery(name = "LoanRepayment.findByCreatedBy", query = "SELECT l FROM LoanRepayment l WHERE l.createdBy = :createdBy")
    , @NamedQuery(name = "LoanRepayment.findByRunningBalance", query = "SELECT l FROM LoanRepayment l WHERE l.runningBalance = :runningBalance")})
public class LoanRepayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 155)
    @Column(name = "GROUP_ID")
    private String groupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOAN_APPLICATION_ID")
    private int loanApplicationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PAYMENT_TYPE")
    private String paymentType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "RECEIPT_NO")
    private String receiptNo;
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED_BY")
    private int createdBy;
    @Column(name = "UPDATED_BY")
    private int updatedBy;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT")
    private Float amount;
    @Column(name = "RUNNING_BALANCE")
    private Float runningBalance;

    public LoanRepayment() {
    }

    public LoanRepayment(Integer id) {
        this.id = id;
    }

    public LoanRepayment(Integer id, String groupId, int loanApplicationId, String paymentType, String receiptNo, int createdBy) {
        this.id = id;
        this.groupId = groupId;
        this.loanApplicationId = loanApplicationId;
        this.paymentType = paymentType;
        this.receiptNo = receiptNo;
        this.createdBy = createdBy;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(int loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Float getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(Float runningBalance) {
        this.runningBalance = runningBalance;
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
        if (!(object instanceof LoanRepayment)) {
            return false;
        }
        LoanRepayment other = (LoanRepayment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.LoanRepayment[ id=" + id + " ]";
    }

}
