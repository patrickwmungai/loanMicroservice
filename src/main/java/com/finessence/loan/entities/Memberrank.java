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
@Table(name = "MEMBERRANK")
@NamedQueries({
    @NamedQuery(name = "Memberrank.findAll", query = "SELECT m FROM Memberrank m")
    , @NamedQuery(name = "Memberrank.findByRankid", query = "SELECT m FROM Memberrank m WHERE m.rankid = :rankid")
    , @NamedQuery(name = "Memberrank.findByCanmakegrouptransaction", query = "SELECT m FROM Memberrank m WHERE m.canmakegrouptransaction = :canmakegrouptransaction")
    , @NamedQuery(name = "Memberrank.findByCanviewmember", query = "SELECT m FROM Memberrank m WHERE m.canviewmember = :canviewmember")
    , @NamedQuery(name = "Memberrank.findByCanaddinvestments", query = "SELECT m FROM Memberrank m WHERE m.canaddinvestments = :canaddinvestments")
    , @NamedQuery(name = "Memberrank.findByCanapplyloan", query = "SELECT m FROM Memberrank m WHERE m.canapplyloan = :canapplyloan")
    , @NamedQuery(name = "Memberrank.findByCanapproveloan", query = "SELECT m FROM Memberrank m WHERE m.canapproveloan = :canapproveloan")
    , @NamedQuery(name = "Memberrank.findByCangenerategrpstmt", query = "SELECT m FROM Memberrank m WHERE m.cangenerategrpstmt = :cangenerategrpstmt")
    , @NamedQuery(name = "Memberrank.findByCanmanagemembers", query = "SELECT m FROM Memberrank m WHERE m.canmanagemembers = :canmanagemembers")
    , @NamedQuery(name = "Memberrank.findByGroupid", query = "SELECT m FROM Memberrank m WHERE m.groupid = :groupid")
    , @NamedQuery(name = "Memberrank.findByIsgroupadmin", query = "SELECT m FROM Memberrank m WHERE m.isgroupadmin = :isgroupadmin")
    , @NamedQuery(name = "Memberrank.findByRankname", query = "SELECT m FROM Memberrank m WHERE m.rankname = :rankname")})
public class Memberrank implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RANKID")
    private Long rankid;
    @Size(max = 255)
    @Column(name = "CANMAKEGROUPTRANSACTION")
    private String canmakegrouptransaction;
    @Size(max = 255)
    @Column(name = "CANVIEWMEMBER")
    private String canviewmember;
    @Size(max = 255)
    @Column(name = "CANADDINVESTMENTS")
    private String canaddinvestments;
    @Size(max = 255)
    @Column(name = "CANAPPLYLOAN")
    private String canapplyloan;
    @Size(max = 255)
    @Column(name = "CANAPPROVELOAN")
    private String canapproveloan;
    @Size(max = 255)
    @Column(name = "CANGENERATEGRPSTMT")
    private String cangenerategrpstmt;
    @Size(max = 255)
    @Column(name = "CANMANAGEMEMBERS")
    private String canmanagemembers;
    @Size(max = 255)
    @Column(name = "GROUPID")
    private String groupid;
    @Size(max = 255)
    @Column(name = "ISGROUPADMIN")
    private String isgroupadmin;
    @Size(max = 255)
    @Column(name = "RANKNAME")
    private String rankname;

    public Memberrank() {
    }

    public Memberrank(Long rankid) {
        this.rankid = rankid;
    }

    public Long getRankid() {
        return rankid;
    }

    public void setRankid(Long rankid) {
        this.rankid = rankid;
    }

    public String getCanmakegrouptransaction() {
        return canmakegrouptransaction;
    }

    public void setCanmakegrouptransaction(String canmakegrouptransaction) {
        this.canmakegrouptransaction = canmakegrouptransaction;
    }

    public String getCanviewmember() {
        return canviewmember;
    }

    public void setCanviewmember(String canviewmember) {
        this.canviewmember = canviewmember;
    }

    public String getCanaddinvestments() {
        return canaddinvestments;
    }

    public void setCanaddinvestments(String canaddinvestments) {
        this.canaddinvestments = canaddinvestments;
    }

    public String getCanapplyloan() {
        return canapplyloan;
    }

    public void setCanapplyloan(String canapplyloan) {
        this.canapplyloan = canapplyloan;
    }

    public String getCanapproveloan() {
        return canapproveloan;
    }

    public void setCanapproveloan(String canapproveloan) {
        this.canapproveloan = canapproveloan;
    }

    public String getCangenerategrpstmt() {
        return cangenerategrpstmt;
    }

    public void setCangenerategrpstmt(String cangenerategrpstmt) {
        this.cangenerategrpstmt = cangenerategrpstmt;
    }

    public String getCanmanagemembers() {
        return canmanagemembers;
    }

    public void setCanmanagemembers(String canmanagemembers) {
        this.canmanagemembers = canmanagemembers;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getIsgroupadmin() {
        return isgroupadmin;
    }

    public void setIsgroupadmin(String isgroupadmin) {
        this.isgroupadmin = isgroupadmin;
    }

    public String getRankname() {
        return rankname;
    }

    public void setRankname(String rankname) {
        this.rankname = rankname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rankid != null ? rankid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memberrank)) {
            return false;
        }
        Memberrank other = (Memberrank) object;
        if ((this.rankid == null && other.rankid != null) || (this.rankid != null && !this.rankid.equals(other.rankid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Memberrank[ rankid=" + rankid + " ]";
    }
    
}
