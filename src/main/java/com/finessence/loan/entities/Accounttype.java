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
@Table(name = "ACCOUNTTYPE")
@NamedQueries({
    @NamedQuery(name = "Accounttype.findAll", query = "SELECT a FROM Accounttype a")
    , @NamedQuery(name = "Accounttype.findById", query = "SELECT a FROM Accounttype a WHERE a.id = :id")
    , @NamedQuery(name = "Accounttype.findByTypecode", query = "SELECT a FROM Accounttype a WHERE a.typecode = :typecode")
    , @NamedQuery(name = "Accounttype.findByTypename", query = "SELECT a FROM Accounttype a WHERE a.typename = :typename")})
public class Accounttype implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "TYPECODE")
    private String typecode;
    @Size(max = 255)
    @Column(name = "TYPENAME")
    private String typename;

    public Accounttype() {
    }

    public Accounttype(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
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
        if (!(object instanceof Accounttype)) {
            return false;
        }
        Accounttype other = (Accounttype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Accounttype[ id=" + id + " ]";
    }
    
}
