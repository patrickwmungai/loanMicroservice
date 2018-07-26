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
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "LOAN_SECURITY")
@NamedQueries({
    @NamedQuery(name = "LoanSecurity.findAll", query = "SELECT l FROM LoanSecurity l")
    , @NamedQuery(name = "LoanSecurity.findById", query = "SELECT l FROM LoanSecurity l WHERE l.id = :id")
    , @NamedQuery(name = "LoanSecurity.findByLoanApplicationId", query = "SELECT l FROM LoanSecurity l WHERE l.loanApplicationId = :loanApplicationId")
    , @NamedQuery(name = "LoanSecurity.findByName", query = "SELECT l FROM LoanSecurity l WHERE l.name = :name")
    , @NamedQuery(name = "LoanSecurity.findByValue", query = "SELECT l FROM LoanSecurity l WHERE l.value = :value")
    , @NamedQuery(name = "LoanSecurity.findByDescription", query = "SELECT l FROM LoanSecurity l WHERE l.description = :description")
    , @NamedQuery(name = "LoanSecurity.findByGroupId", query = "SELECT l FROM LoanSecurity l WHERE l.groupId = :groupId")
    , @NamedQuery(name = "LoanSecurity.findByDateCreated", query = "SELECT l FROM LoanSecurity l WHERE l.dateCreated = :dateCreated")})
public class LoanSecurity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "LOAN_APPLICATION_ID")
    private Integer loanApplicationId;
    @Size(max = 70)
    @Column(name = "NAME")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALUE")
    private Double value;
    @Size(max = 350)
    @Column(name = "DESCRIPTION")
    private String description;
    @Lob
    @Column(name = "DOCUMENT")
    private byte[] document;
    @Size(max = 100)
    @Column(name = "GROUP_ID")
    private String groupId;
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    public LoanSecurity() {
    }

    public LoanSecurity(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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
        if (!(object instanceof LoanSecurity)) {
            return false;
        }
        LoanSecurity other = (LoanSecurity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.LoanSecurity[ id=" + id + " ]";
    }
    
}
