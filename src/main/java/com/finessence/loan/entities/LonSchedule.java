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
@Table(name = "LON_SCHEDULE")
@NamedQueries({
    @NamedQuery(name = "LonSchedule.findAll", query = "SELECT l FROM LonSchedule l")
    , @NamedQuery(name = "LonSchedule.findById", query = "SELECT l FROM LonSchedule l WHERE l.id = :id")
    , @NamedQuery(name = "LonSchedule.findByLoanApplicationId", query = "SELECT l FROM LonSchedule l WHERE l.loanApplicationId = :loanApplicationId")
    , @NamedQuery(name = "LonSchedule.findByPaymentDate", query = "SELECT l FROM LonSchedule l WHERE l.paymentDate = :paymentDate")
    , @NamedQuery(name = "LonSchedule.findByBeginningBalance", query = "SELECT l FROM LonSchedule l WHERE l.beginningBalance = :beginningBalance")
    , @NamedQuery(name = "LonSchedule.findByInstalmentAmount", query = "SELECT l FROM LonSchedule l WHERE l.instalmentAmount = :instalmentAmount")
    , @NamedQuery(name = "LonSchedule.findByPrincipal", query = "SELECT l FROM LonSchedule l WHERE l.principal = :principal")
    , @NamedQuery(name = "LonSchedule.findByInterest", query = "SELECT l FROM LonSchedule l WHERE l.interest = :interest")
    , @NamedQuery(name = "LonSchedule.findByEndingBalance", query = "SELECT l FROM LonSchedule l WHERE l.endingBalance = :endingBalance")
    , @NamedQuery(name = "LonSchedule.findByCumulativeInterest", query = "SELECT l FROM LonSchedule l WHERE l.cumulativeInterest = :cumulativeInterest")
    , @NamedQuery(name = "LonSchedule.findByPaidStatus", query = "SELECT l FROM LonSchedule l WHERE l.paidStatus = :paidStatus")
    , @NamedQuery(name = "LonSchedule.findByPenalty", query = "SELECT l FROM LonSchedule l WHERE l.penalty = :penalty")
    , @NamedQuery(name = "LonSchedule.findByPaymentNo", query = "SELECT l FROM LonSchedule l WHERE l.paymentNo = :paymentNo")
    , @NamedQuery(name = "LonSchedule.findByNoOfDueDate", query = "SELECT l FROM LonSchedule l WHERE l.noOfDueDate = :noOfDueDate")
    , @NamedQuery(name = "LonSchedule.findByDateCreated", query = "SELECT l FROM LonSchedule l WHERE l.dateCreated = :dateCreated")
    , @NamedQuery(name = "LonSchedule.findByCreatedBy", query = "SELECT l FROM LonSchedule l WHERE l.createdBy = :createdBy")
    , @NamedQuery(name = "LonSchedule.findByUpdatedBy", query = "SELECT l FROM LonSchedule l WHERE l.updatedBy = :updatedBy")})
public class LonSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "LOAN_APPLICATION_ID")
    private Integer loanApplicationId;
    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BEGINNING_BALANCE")
    private Double beginningBalance;
    @Column(name = "INSTALMENT_AMOUNT")
    private Double instalmentAmount;
    @Column(name = "PRINCIPAL")
    private Double principal;
    @Column(name = "INTEREST")
    private Double interest;
    @Column(name = "ENDING_BALANCE")
    private Double endingBalance;
    @Column(name = "CUMULATIVE_INTEREST")
    private Double cumulativeInterest;
    @Size(max = 45)
    @Column(name = "PAID_STATUS")
    private String paidStatus;
    @Column(name = "PENALTY")
    private Double penalty;
    @Column(name = "PAYMENT_NO")
    private Integer paymentNo;
    @Column(name = "NO_OF_DUE_DATE")
    private Integer noOfDueDate;
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Column(name = "UPDATED_BY")
    private Integer updatedBy;
    @Column(name = "GROUP_ID")
    private String groupId;
    @Column(name = "REPAYMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repaymentDate;

    public LonSchedule() {
    }

    public LonSchedule(Integer id) {
        this.id = id;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(Integer loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getBeginningBalance() {
        return beginningBalance;
    }

    public void setBeginningBalance(Double beginningBalance) {
        this.beginningBalance = beginningBalance;
    }

    public Double getInstalmentAmount() {
        return instalmentAmount;
    }

    public void setInstalmentAmount(Double instalmentAmount) {
        this.instalmentAmount = instalmentAmount;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getEndingBalance() {
        return endingBalance;
    }

    public void setEndingBalance(Double endingBalance) {
        this.endingBalance = endingBalance;
    }

    public Double getCumulativeInterest() {
        return cumulativeInterest;
    }

    public void setCumulativeInterest(Double cumulativeInterest) {
        this.cumulativeInterest = cumulativeInterest;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(Double penalty) {
        this.penalty = penalty;
    }

    public Integer getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(Integer paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Integer getNoOfDueDate() {
        return noOfDueDate;
    }

    public void setNoOfDueDate(Integer noOfDueDate) {
        this.noOfDueDate = noOfDueDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LonSchedule)) {
            return false;
        }
        LonSchedule other = (LonSchedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.LonSchedule[ id=" + id + " ]";
    }

}
