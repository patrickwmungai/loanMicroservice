/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
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
@Table(name = "MPESAVALIDATIONLOG")
@NamedQueries({
    @NamedQuery(name = "Mpesavalidationlog.findAll", query = "SELECT m FROM Mpesavalidationlog m")
    , @NamedQuery(name = "Mpesavalidationlog.findById", query = "SELECT m FROM Mpesavalidationlog m WHERE m.id = :id")
    , @NamedQuery(name = "Mpesavalidationlog.findByBusinessshortcode", query = "SELECT m FROM Mpesavalidationlog m WHERE m.businessshortcode = :businessshortcode")
    , @NamedQuery(name = "Mpesavalidationlog.findByFintranstype", query = "SELECT m FROM Mpesavalidationlog m WHERE m.fintranstype = :fintranstype")
    , @NamedQuery(name = "Mpesavalidationlog.findByGroupcode", query = "SELECT m FROM Mpesavalidationlog m WHERE m.groupcode = :groupcode")
    , @NamedQuery(name = "Mpesavalidationlog.findByMembercode", query = "SELECT m FROM Mpesavalidationlog m WHERE m.membercode = :membercode")
    , @NamedQuery(name = "Mpesavalidationlog.findByMpesatranstype", query = "SELECT m FROM Mpesavalidationlog m WHERE m.mpesatranstype = :mpesatranstype")
    , @NamedQuery(name = "Mpesavalidationlog.findByMsisdn", query = "SELECT m FROM Mpesavalidationlog m WHERE m.msisdn = :msisdn")
    , @NamedQuery(name = "Mpesavalidationlog.findByPosted", query = "SELECT m FROM Mpesavalidationlog m WHERE m.posted = :posted")
    , @NamedQuery(name = "Mpesavalidationlog.findByPostingdate", query = "SELECT m FROM Mpesavalidationlog m WHERE m.postingdate = :postingdate")
    , @NamedQuery(name = "Mpesavalidationlog.findByPostingerror", query = "SELECT m FROM Mpesavalidationlog m WHERE m.postingerror = :postingerror")
    , @NamedQuery(name = "Mpesavalidationlog.findByPostingretries", query = "SELECT m FROM Mpesavalidationlog m WHERE m.postingretries = :postingretries")
    , @NamedQuery(name = "Mpesavalidationlog.findByTransid", query = "SELECT m FROM Mpesavalidationlog m WHERE m.transid = :transid")
    , @NamedQuery(name = "Mpesavalidationlog.findByTransactionamount", query = "SELECT m FROM Mpesavalidationlog m WHERE m.transactionamount = :transactionamount")
    , @NamedQuery(name = "Mpesavalidationlog.findByTransactionreference", query = "SELECT m FROM Mpesavalidationlog m WHERE m.transactionreference = :transactionreference")
    , @NamedQuery(name = "Mpesavalidationlog.findByValidationstatus", query = "SELECT m FROM Mpesavalidationlog m WHERE m.validationstatus = :validationstatus")
    , @NamedQuery(name = "Mpesavalidationlog.findByValidationdate", query = "SELECT m FROM Mpesavalidationlog m WHERE m.validationdate = :validationdate")})
public class Mpesavalidationlog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "BUSINESSSHORTCODE")
    private String businessshortcode;
    @Size(max = 255)
    @Column(name = "FINTRANSTYPE")
    private String fintranstype;
    @Size(max = 255)
    @Column(name = "GROUPCODE")
    private String groupcode;
    @Size(max = 255)
    @Column(name = "MEMBERCODE")
    private String membercode;
    @Size(max = 255)
    @Column(name = "MPESATRANSTYPE")
    private String mpesatranstype;
    @Size(max = 255)
    @Column(name = "MSISDN")
    private String msisdn;
    @Size(max = 255)
    @Column(name = "POSTED")
    private String posted;
    @Column(name = "POSTINGDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postingdate;
    @Size(max = 255)
    @Column(name = "POSTINGERROR")
    private String postingerror;
    @Column(name = "POSTINGRETRIES")
    private Integer postingretries;
    @Size(max = 255)
    @Column(name = "TRANSID")
    private String transid;
    @Column(name = "TRANSACTIONAMOUNT")
    private BigInteger transactionamount;
    @Size(max = 255)
    @Column(name = "TRANSACTIONREFERENCE")
    private String transactionreference;
    @Size(max = 255)
    @Column(name = "VALIDATIONSTATUS")
    private String validationstatus;
    @Column(name = "VALIDATIONDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validationdate;

    public Mpesavalidationlog() {
    }

    public Mpesavalidationlog(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessshortcode() {
        return businessshortcode;
    }

    public void setBusinessshortcode(String businessshortcode) {
        this.businessshortcode = businessshortcode;
    }

    public String getFintranstype() {
        return fintranstype;
    }

    public void setFintranstype(String fintranstype) {
        this.fintranstype = fintranstype;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
    }

    public String getMpesatranstype() {
        return mpesatranstype;
    }

    public void setMpesatranstype(String mpesatranstype) {
        this.mpesatranstype = mpesatranstype;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public Date getPostingdate() {
        return postingdate;
    }

    public void setPostingdate(Date postingdate) {
        this.postingdate = postingdate;
    }

    public String getPostingerror() {
        return postingerror;
    }

    public void setPostingerror(String postingerror) {
        this.postingerror = postingerror;
    }

    public Integer getPostingretries() {
        return postingretries;
    }

    public void setPostingretries(Integer postingretries) {
        this.postingretries = postingretries;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public BigInteger getTransactionamount() {
        return transactionamount;
    }

    public void setTransactionamount(BigInteger transactionamount) {
        this.transactionamount = transactionamount;
    }

    public String getTransactionreference() {
        return transactionreference;
    }

    public void setTransactionreference(String transactionreference) {
        this.transactionreference = transactionreference;
    }

    public String getValidationstatus() {
        return validationstatus;
    }

    public void setValidationstatus(String validationstatus) {
        this.validationstatus = validationstatus;
    }

    public Date getValidationdate() {
        return validationdate;
    }

    public void setValidationdate(Date validationdate) {
        this.validationdate = validationdate;
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
        if (!(object instanceof Mpesavalidationlog)) {
            return false;
        }
        Mpesavalidationlog other = (Mpesavalidationlog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Mpesavalidationlog[ id=" + id + " ]";
    }
    
}
