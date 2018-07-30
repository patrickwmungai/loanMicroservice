/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "LOANTYPE")
@NamedQueries({
    @NamedQuery(name = "Loantype.findAll", query = "SELECT l FROM Loantype l")
    , @NamedQuery(name = "Loantype.findById", query = "SELECT l FROM Loantype l WHERE l.id = :id")
    , @NamedQuery(name = "Loantype.findByAddedby", query = "SELECT l FROM Loantype l WHERE l.addedby = :addedby")
    , @NamedQuery(name = "Loantype.findByApplicationfee", query = "SELECT l FROM Loantype l WHERE l.applicationfee = :applicationfee")
    , @NamedQuery(name = "Loantype.findByDateadded", query = "SELECT l FROM Loantype l WHERE l.dateadded = :dateadded")
    , @NamedQuery(name = "Loantype.findByGroupid", query = "SELECT l FROM Loantype l WHERE l.groupid = :groupid")
    , @NamedQuery(name = "Loantype.findByInsurancefee", query = "SELECT l FROM Loantype l WHERE l.insurancefee = :insurancefee")
    , @NamedQuery(name = "Loantype.findByInterestrate", query = "SELECT l FROM Loantype l WHERE l.interestrate = :interestrate")
    , @NamedQuery(name = "Loantype.findByInteresttype", query = "SELECT l FROM Loantype l WHERE l.interesttype = :interesttype")
    , @NamedQuery(name = "Loantype.findByIscheckoff", query = "SELECT l FROM Loantype l WHERE l.ischeckoff = :ischeckoff")
    , @NamedQuery(name = "Loantype.findByMaximumloanamount", query = "SELECT l FROM Loantype l WHERE l.maximumloanamount = :maximumloanamount")
    , @NamedQuery(name = "Loantype.findByMaximumloancount", query = "SELECT l FROM Loantype l WHERE l.maximumloancount = :maximumloancount")
    , @NamedQuery(name = "Loantype.findByMaximumrepaymentperiod", query = "SELECT l FROM Loantype l WHERE l.maximumrepaymentperiod = :maximumrepaymentperiod")
    , @NamedQuery(name = "Loantype.findByMinimumguarantors", query = "SELECT l FROM Loantype l WHERE l.minimumguarantors = :minimumguarantors")
    , @NamedQuery(name = "Loantype.findByRequiressecurity", query = "SELECT l FROM Loantype l WHERE l.requiressecurity = :requiressecurity")
    , @NamedQuery(name = "Loantype.findBySavingsfactor", query = "SELECT l FROM Loantype l WHERE l.savingsfactor = :savingsfactor")
    , @NamedQuery(name = "Loantype.findByTypename", query = "SELECT l FROM Loantype l WHERE l.typename = :typename")})
public class Loantype implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "ADDEDBY")
    private String addedby;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "APPLICATIONFEETYPE")
    private String applicationfeetype;
    @Column(name = "APPLICATIONFEE")
    private BigDecimal applicationfee;
    @Column(name = "DATEADDED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateadded;
    @Size(max = 255)
    @Column(name = "GROUPID")
    private String groupid;
    @Column(name = "INSURANCEFEETYPE")
    private String insurancefeetype;
    @Column(name = "INSURANCEFEE")
    private BigDecimal insurancefee;
    @Column(name = "INTERESTRATE")
    private BigDecimal interestrate;
    @Size(max = 255)
    @Column(name = "INTERESTTYPE")
    private String interesttype;
    @Size(max = 255)
    @Column(name = "ISCHECKOFF")
    private String ischeckoff;
    @Column(name = "MAXIMUMLOANAMOUNT")
    private BigInteger maximumloanamount;
    @Column(name = "MAXIMUMLOANCOUNT")
    private Integer maximumloancount;
    @Column(name = "MAXIMUMREPAYMENTPERIOD")
    private Integer maximumrepaymentperiod;
    @Column(name = "MINIMUMGUARANTORS")
    private Integer minimumguarantors;
    @Size(max = 255)
    @Column(name = "REQUIRESSECURITY")
    private String requiressecurity;
    @Column(name = "SAVINGSFACTOR")
    private Integer savingsfactor;
    @Size(max = 255)
    @Column(name = "TYPENAME")
    private String typename;
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Column(name = "UPDATED_BY")
    private Integer updatedBy;
    @Column(name = "LOAN_INCOME_RATIO")
    private double loanIncomeRatio;
    @Column(name = "APPROVAL_STATUS")
    private String approvalStatus;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "MAX_APPROVAL_LEVEL")
    private Integer maxApprovalLevel;
    @Column(name = "CURRENT_APPROVAL_LEVEL")
    private Integer currentApprovalLevel;

    public Loantype() {
    }

    public String getApplicationfeetype() {
        return applicationfeetype;
    }

    public void setApplicationfeetype(String applicationfeetype) {
        this.applicationfeetype = applicationfeetype;
    }

    public String getInsurancefeetype() {
        return insurancefeetype;
    }

    public void setInsurancefeetype(String insurancefeetype) {
        this.insurancefeetype = insurancefeetype;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Loantype(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public BigDecimal getApplicationfee() {
        return applicationfee;
    }

    public void setApplicationfee(BigDecimal applicationfee) {
        this.applicationfee = applicationfee;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public BigDecimal getInsurancefee() {
        return insurancefee;
    }

    public void setInsurancefee(BigDecimal insurancefee) {
        this.insurancefee = insurancefee;
    }

    public BigDecimal getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(BigDecimal interestrate) {
        this.interestrate = interestrate;
    }

    public String getInteresttype() {
        return interesttype;
    }

    public void setInteresttype(String interesttype) {
        this.interesttype = interesttype;
    }

    public String getIscheckoff() {
        return ischeckoff;
    }

    public void setIscheckoff(String ischeckoff) {
        this.ischeckoff = ischeckoff;
    }

    public BigInteger getMaximumloanamount() {
        return maximumloanamount;
    }

    public void setMaximumloanamount(BigInteger maximumloanamount) {
        this.maximumloanamount = maximumloanamount;
    }

    public Integer getMaximumloancount() {
        return maximumloancount;
    }

    public void setMaximumloancount(Integer maximumloancount) {
        this.maximumloancount = maximumloancount;
    }

    public Integer getMaximumrepaymentperiod() {
        return maximumrepaymentperiod;
    }

    public void setMaximumrepaymentperiod(Integer maximumrepaymentperiod) {
        this.maximumrepaymentperiod = maximumrepaymentperiod;
    }

    public Integer getMinimumguarantors() {
        return minimumguarantors;
    }

    public void setMinimumguarantors(Integer minimumguarantors) {
        this.minimumguarantors = minimumguarantors;
    }

    public String getRequiressecurity() {
        return requiressecurity;
    }

    public void setRequiressecurity(String requiressecurity) {
        this.requiressecurity = requiressecurity;
    }

    public Integer getSavingsfactor() {
        return savingsfactor;
    }

    public void setSavingsfactor(Integer savingsfactor) {
        this.savingsfactor = savingsfactor;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public double getLoanIncomeRatio() {
        return loanIncomeRatio;
    }

    public void setLoanIncomeRatio(double loanIncomeRatio) {
        this.loanIncomeRatio = loanIncomeRatio;
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
        if (!(object instanceof Loantype)) {
            return false;
        }
        Loantype other = (Loantype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Loantype[ id=" + id + " ]";
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getMaxApprovalLevel() {
        return maxApprovalLevel;
    }

    public void setMaxApprovalLevel(Integer maxApprovalLevel) {
        this.maxApprovalLevel = maxApprovalLevel;
    }

    public Integer getCurrentApprovalLevel() {
        return currentApprovalLevel;
    }

    public void setCurrentApprovalLevel(Integer currentApprovalLevel) {
        this.currentApprovalLevel = currentApprovalLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
