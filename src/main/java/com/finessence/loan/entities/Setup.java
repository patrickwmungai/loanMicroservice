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
@Table(name = "SETUP")
@NamedQueries({
    @NamedQuery(name = "Setup.findAll", query = "SELECT s FROM Setup s")
    , @NamedQuery(name = "Setup.findById", query = "SELECT s FROM Setup s WHERE s.id = :id")
    , @NamedQuery(name = "Setup.findByEmailpassword", query = "SELECT s FROM Setup s WHERE s.emailpassword = :emailpassword")
    , @NamedQuery(name = "Setup.findByEmailport", query = "SELECT s FROM Setup s WHERE s.emailport = :emailport")
    , @NamedQuery(name = "Setup.findByEmailserver", query = "SELECT s FROM Setup s WHERE s.emailserver = :emailserver")
    , @NamedQuery(name = "Setup.findByFromemail", query = "SELECT s FROM Setup s WHERE s.fromemail = :fromemail")})
public class Setup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "EMAILPASSWORD")
    private String emailpassword;
    @Size(max = 255)
    @Column(name = "EMAILPORT")
    private String emailport;
    @Size(max = 255)
    @Column(name = "EMAILSERVER")
    private String emailserver;
    @Size(max = 255)
    @Column(name = "FROMEMAIL")
    private String fromemail;

    public Setup() {
    }

    public Setup(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailpassword() {
        return emailpassword;
    }

    public void setEmailpassword(String emailpassword) {
        this.emailpassword = emailpassword;
    }

    public String getEmailport() {
        return emailport;
    }

    public void setEmailport(String emailport) {
        this.emailport = emailport;
    }

    public String getEmailserver() {
        return emailserver;
    }

    public void setEmailserver(String emailserver) {
        this.emailserver = emailserver;
    }

    public String getFromemail() {
        return fromemail;
    }

    public void setFromemail(String fromemail) {
        this.fromemail = fromemail;
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
        if (!(object instanceof Setup)) {
            return false;
        }
        Setup other = (Setup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Setup[ id=" + id + " ]";
    }
    
}
