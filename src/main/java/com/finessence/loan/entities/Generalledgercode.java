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
@Table(name = "GENERALLEDGERCODE")
@NamedQueries({
    @NamedQuery(name = "Generalledgercode.findAll", query = "SELECT g FROM Generalledgercode g")
    , @NamedQuery(name = "Generalledgercode.findById", query = "SELECT g FROM Generalledgercode g WHERE g.id = :id")
    , @NamedQuery(name = "Generalledgercode.findByCodetype", query = "SELECT g FROM Generalledgercode g WHERE g.codetype = :codetype")
    , @NamedQuery(name = "Generalledgercode.findByDescription", query = "SELECT g FROM Generalledgercode g WHERE g.description = :description")
    , @NamedQuery(name = "Generalledgercode.findByGlcode", query = "SELECT g FROM Generalledgercode g WHERE g.glcode = :glcode")})
public class Generalledgercode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "CODETYPE")
    private String codetype;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "GLCODE")
    private String glcode;

    public Generalledgercode() {
    }

    public Generalledgercode(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
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
        if (!(object instanceof Generalledgercode)) {
            return false;
        }
        Generalledgercode other = (Generalledgercode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Generalledgercode[ id=" + id + " ]";
    }
    
}
