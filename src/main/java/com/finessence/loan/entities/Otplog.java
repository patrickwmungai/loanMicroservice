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
@Table(name = "OTPLOG")
@NamedQueries({
    @NamedQuery(name = "Otplog.findAll", query = "SELECT o FROM Otplog o")
    , @NamedQuery(name = "Otplog.findById", query = "SELECT o FROM Otplog o WHERE o.id = :id")
    , @NamedQuery(name = "Otplog.findByOtp", query = "SELECT o FROM Otplog o WHERE o.otp = :otp")
    , @NamedQuery(name = "Otplog.findByGendate", query = "SELECT o FROM Otplog o WHERE o.gendate = :gendate")
    , @NamedQuery(name = "Otplog.findByMembercode", query = "SELECT o FROM Otplog o WHERE o.membercode = :membercode")})
public class Otplog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "OTP")
    private String otp;
    @Column(name = "GENDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gendate;
    @Size(max = 255)
    @Column(name = "MEMBERCODE")
    private String membercode;

    public Otplog() {
    }

    public Otplog(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getGendate() {
        return gendate;
    }

    public void setGendate(Date gendate) {
        this.gendate = gendate;
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
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
        if (!(object instanceof Otplog)) {
            return false;
        }
        Otplog other = (Otplog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Otplog[ id=" + id + " ]";
    }
    
}
