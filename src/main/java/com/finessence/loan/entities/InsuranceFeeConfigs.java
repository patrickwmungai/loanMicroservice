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
@Table(name = "INSURANCE_FEE_CONFIGS")
@NamedQueries({
    @NamedQuery(name = "InsuranceFeeConfigs.findAll", query = "SELECT i FROM InsuranceFeeConfigs i")
    , @NamedQuery(name = "InsuranceFeeConfigs.findById", query = "SELECT i FROM InsuranceFeeConfigs i WHERE i.id = :id")
    , @NamedQuery(name = "InsuranceFeeConfigs.findByUpToAmount", query = "SELECT i FROM InsuranceFeeConfigs i WHERE i.upToAmount = :upToAmount")
    , @NamedQuery(name = "InsuranceFeeConfigs.findByFee", query = "SELECT i FROM InsuranceFeeConfigs i WHERE i.fee = :fee")
    , @NamedQuery(name = "InsuranceFeeConfigs.findByCreatedBy", query = "SELECT i FROM InsuranceFeeConfigs i WHERE i.createdBy = :createdBy")
    , @NamedQuery(name = "InsuranceFeeConfigs.findByUpdatedBy", query = "SELECT i FROM InsuranceFeeConfigs i WHERE i.updatedBy = :updatedBy")
    , @NamedQuery(name = "InsuranceFeeConfigs.findByDateCreated", query = "SELECT i FROM InsuranceFeeConfigs i WHERE i.dateCreated = :dateCreated")
    , @NamedQuery(name = "InsuranceFeeConfigs.findByTimeCreated", query = "SELECT i FROM InsuranceFeeConfigs i WHERE i.timeCreated = :timeCreated")
    , @NamedQuery(name = "InsuranceFeeConfigs.findByGroupId", query = "SELECT i FROM InsuranceFeeConfigs i WHERE i.groupId = :groupId")})
public class InsuranceFeeConfigs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "UP_TO_AMOUNT")
    private BigDecimal upToAmount;
    @Column(name = "FEE")
    private BigDecimal fee;
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Column(name = "UPDATED_BY")
    private Integer updatedBy;
    @Column(name = "REPAYMENT_PERIOD")
    private Integer repaymentPeriod;
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "TIME_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @Size(max = 80)
    @Column(name = "GROUP_ID")
    private String groupId;

    public InsuranceFeeConfigs() {
    }

    public InsuranceFeeConfigs(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getUpToAmount() {
        return upToAmount;
    }

    public void setUpToAmount(BigDecimal upToAmount) {
        this.upToAmount = upToAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(Integer repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
        if (!(object instanceof InsuranceFeeConfigs)) {
            return false;
        }
        InsuranceFeeConfigs other = (InsuranceFeeConfigs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.InsuranceFeeConfigs[ id=" + id + " ]";
    }
    
}
