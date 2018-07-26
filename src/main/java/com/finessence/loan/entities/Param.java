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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "PARAM")
@NamedQueries({
    @NamedQuery(name = "Param.findAll", query = "SELECT p FROM Param p")
    , @NamedQuery(name = "Param.findByParameter", query = "SELECT p FROM Param p WHERE p.parameter = :parameter")
    , @NamedQuery(name = "Param.findByValue", query = "SELECT p FROM Param p WHERE p.value = :value")
    , @NamedQuery(name = "Param.findByDescription", query = "SELECT p FROM Param p WHERE p.description = :description")
    , @NamedQuery(name = "Param.findByPosition", query = "SELECT p FROM Param p WHERE p.position = :position")
    , @NamedQuery(name = "Param.findById", query = "SELECT p FROM Param p WHERE p.id = :id")})
public class Param implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 100)
    @Column(name = "PARAMETER")
    private String parameter;
    @Size(max = 200)
    @Column(name = "VALUE")
    private String value;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "POSITION")
    private Integer position;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    public Param() {
    }

    public Param(Integer id) {
        this.id = id;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Param)) {
            return false;
        }
        Param other = (Param) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Param[ id=" + id + " ]";
    }
    
}
