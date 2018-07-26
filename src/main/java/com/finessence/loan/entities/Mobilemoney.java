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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "MOBILEMONEY")
@NamedQueries({
    @NamedQuery(name = "Mobilemoney.findAll", query = "SELECT m FROM Mobilemoney m")
    , @NamedQuery(name = "Mobilemoney.findById", query = "SELECT m FROM Mobilemoney m WHERE m.id = :id")
    , @NamedQuery(name = "Mobilemoney.findByAddedby", query = "SELECT m FROM Mobilemoney m WHERE m.addedby = :addedby")
    , @NamedQuery(name = "Mobilemoney.findByDateadded", query = "SELECT m FROM Mobilemoney m WHERE m.dateadded = :dateadded")
    , @NamedQuery(name = "Mobilemoney.findByGroupid", query = "SELECT m FROM Mobilemoney m WHERE m.groupid = :groupid")
    , @NamedQuery(name = "Mobilemoney.findByProvider", query = "SELECT m FROM Mobilemoney m WHERE m.provider = :provider")
    , @NamedQuery(name = "Mobilemoney.findByStatus", query = "SELECT m FROM Mobilemoney m WHERE m.status = :status")})
public class Mobilemoney implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "ADDEDBY")
    private String addedby;
    @Column(name = "DATEADDED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateadded;
    @Size(max = 255)
    @Column(name = "GROUPID")
    private String groupid;
    @Size(max = 255)
    @Column(name = "PROVIDER")
    private String provider;
    @Size(max = 255)
    @Column(name = "STATUS")
    private String status;

    public Mobilemoney() {
    }

    public Mobilemoney(Long id) {
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
        if (!(object instanceof Mobilemoney)) {
            return false;
        }
        Mobilemoney other = (Mobilemoney) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Mobilemoney[ id=" + id + " ]";
    }
    
}
