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
import javax.persistence.EmbeddedId;
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
@Table(name = "MEMBERACCOUNT")
@NamedQueries({
    @NamedQuery(name = "Memberaccount.findAll", query = "SELECT m FROM Memberaccount m")
    , @NamedQuery(name = "Memberaccount.findByAccountbalance", query = "SELECT m FROM Memberaccount m WHERE m.accountbalance = :accountbalance")
    , @NamedQuery(name = "Memberaccount.findByAccountname", query = "SELECT m FROM Memberaccount m WHERE m.accountname = :accountname")
    , @NamedQuery(name = "Memberaccount.findByAccounttype", query = "SELECT m FROM Memberaccount m WHERE m.accounttype = :accounttype")
    , @NamedQuery(name = "Memberaccount.findByAvailablebalance", query = "SELECT m FROM Memberaccount m WHERE m.availablebalance = :availablebalance")
    , @NamedQuery(name = "Memberaccount.findByBlockedbalance", query = "SELECT m FROM Memberaccount m WHERE m.blockedbalance = :blockedbalance")
    , @NamedQuery(name = "Memberaccount.findByClosedstatus", query = "SELECT m FROM Memberaccount m WHERE m.closedstatus = :closedstatus")
    , @NamedQuery(name = "Memberaccount.findByCurrency", query = "SELECT m FROM Memberaccount m WHERE m.currency = :currency")
    , @NamedQuery(name = "Memberaccount.findByDateclosed", query = "SELECT m FROM Memberaccount m WHERE m.dateclosed = :dateclosed")
    , @NamedQuery(name = "Memberaccount.findByDateopened", query = "SELECT m FROM Memberaccount m WHERE m.dateopened = :dateopened")
    , @NamedQuery(name = "Memberaccount.findByDescription", query = "SELECT m FROM Memberaccount m WHERE m.description = :description")
    , @NamedQuery(name = "Memberaccount.findByDormantstatus", query = "SELECT m FROM Memberaccount m WHERE m.dormantstatus = :dormantstatus")
    , @NamedQuery(name = "Memberaccount.findByGlcode", query = "SELECT m FROM Memberaccount m WHERE m.glcode = :glcode")
    , @NamedQuery(name = "Memberaccount.findByGroupid", query = "SELECT m FROM Memberaccount m WHERE m.groupid = :groupid")
    , @NamedQuery(name = "Memberaccount.findByMembercode", query = "SELECT m FROM Memberaccount m WHERE m.membercode = :membercode")
    , @NamedQuery(name = "Memberaccount.findByDrawndownstatus", query = "SELECT m FROM Memberaccount m WHERE m.drawndownstatus = :drawndownstatus")})
public class Memberaccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @Id
    private long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ACCOUNTID")
    private String accountid;
    @Column(name = "ACCOUNTBALANCE")
    private BigInteger accountbalance;
    @Size(max = 255)
    @Column(name = "ACCOUNTNAME")
    private String accountname;
    @Size(max = 255)
    @Column(name = "ACCOUNTTYPE")
    private String accounttype;
    @Column(name = "AVAILABLEBALANCE")
    private BigInteger availablebalance;
    @Column(name = "BLOCKEDBALANCE")
    private BigInteger blockedbalance;
    @Size(max = 255)
    @Column(name = "CLOSEDSTATUS")
    private String closedstatus;
    @Size(max = 255)
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "DATECLOSED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateclosed;
    @Column(name = "DATEOPENED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateopened;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "DORMANTSTATUS")
    private String dormantstatus;
    @Size(max = 255)
    @Column(name = "GLCODE")
    private String glcode;
    @Size(max = 255)
    @Column(name = "GROUPID")
    private String groupid;
    @Size(max = 255)
    @Column(name = "MEMBERCODE")
    private String membercode;
    @Size(max = 45)
    @Column(name = "DRAWNDOWNSTATUS")
    private String drawndownstatus;

    public Memberaccount() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public BigInteger getAccountbalance() {
        return accountbalance;
    }

    public void setAccountbalance(BigInteger accountbalance) {
        this.accountbalance = accountbalance;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public BigInteger getAvailablebalance() {
        return availablebalance;
    }

    public void setAvailablebalance(BigInteger availablebalance) {
        this.availablebalance = availablebalance;
    }

    public BigInteger getBlockedbalance() {
        return blockedbalance;
    }

    public void setBlockedbalance(BigInteger blockedbalance) {
        this.blockedbalance = blockedbalance;
    }

    public String getClosedstatus() {
        return closedstatus;
    }

    public void setClosedstatus(String closedstatus) {
        this.closedstatus = closedstatus;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDateclosed() {
        return dateclosed;
    }

    public void setDateclosed(Date dateclosed) {
        this.dateclosed = dateclosed;
    }

    public Date getDateopened() {
        return dateopened;
    }

    public void setDateopened(Date dateopened) {
        this.dateopened = dateopened;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDormantstatus() {
        return dormantstatus;
    }

    public void setDormantstatus(String dormantstatus) {
        this.dormantstatus = dormantstatus;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
    }

    public String getDrawndownstatus() {
        return drawndownstatus;
    }

    public void setDrawndownstatus(String drawndownstatus) {
        this.drawndownstatus = drawndownstatus;
    }

}
