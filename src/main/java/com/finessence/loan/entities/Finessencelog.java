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
@Table(name = "FINESSENCELOG")
@NamedQueries({
    @NamedQuery(name = "Finessencelog.findAll", query = "SELECT f FROM Finessencelog f")
    , @NamedQuery(name = "Finessencelog.findById", query = "SELECT f FROM Finessencelog f WHERE f.id = :id")
    , @NamedQuery(name = "Finessencelog.findByDescription", query = "SELECT f FROM Finessencelog f WHERE f.description = :description")
    , @NamedQuery(name = "Finessencelog.findByIpaddress", query = "SELECT f FROM Finessencelog f WHERE f.ipaddress = :ipaddress")
    , @NamedQuery(name = "Finessencelog.findByLogtype", query = "SELECT f FROM Finessencelog f WHERE f.logtype = :logtype")})
public class Finessencelog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "IPADDRESS")
    private String ipaddress;
    @Size(max = 255)
    @Column(name = "LOGTYPE")
    private String logtype;

    public Finessencelog() {
    }

    public Finessencelog(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype;
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
        if (!(object instanceof Finessencelog)) {
            return false;
        }
        Finessencelog other = (Finessencelog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Finessencelog[ id=" + id + " ]";
    }
    
}
