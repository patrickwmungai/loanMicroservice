/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "GROUPMEMBER")
@NamedQueries({
    @NamedQuery(name = "Groupmember.findAll", query = "SELECT g FROM Groupmember g")
    , @NamedQuery(name = "Groupmember.findByAddedby", query = "SELECT g FROM Groupmember g WHERE g.addedby = :addedby")
    , @NamedQuery(name = "Groupmember.findByAllowedimei", query = "SELECT g FROM Groupmember g WHERE g.allowedimei = :allowedimei")
    , @NamedQuery(name = "Groupmember.findByApprovedby", query = "SELECT g FROM Groupmember g WHERE g.approvedby = :approvedby")
    , @NamedQuery(name = "Groupmember.findByDateadded", query = "SELECT g FROM Groupmember g WHERE g.dateadded = :dateadded")
    , @NamedQuery(name = "Groupmember.findByEmail", query = "SELECT g FROM Groupmember g WHERE g.email = :email")
    , @NamedQuery(name = "Groupmember.findByMemberstatus", query = "SELECT g FROM Groupmember g WHERE g.memberstatus = :memberstatus")
    , @NamedQuery(name = "Groupmember.findByMembertype", query = "SELECT g FROM Groupmember g WHERE g.membertype = :membertype")
    , @NamedQuery(name = "Groupmember.findByMembername", query = "SELECT g FROM Groupmember g WHERE g.membername = :membername")
    , @NamedQuery(name = "Groupmember.findByNextofkin", query = "SELECT g FROM Groupmember g WHERE g.nextofkin = :nextofkin")
    , @NamedQuery(name = "Groupmember.findByNextofkintel", query = "SELECT g FROM Groupmember g WHERE g.nextofkintel = :nextofkintel")
    , @NamedQuery(name = "Groupmember.findByOccupation", query = "SELECT g FROM Groupmember g WHERE g.occupation = :occupation")
    , @NamedQuery(name = "Groupmember.findByPassword", query = "SELECT g FROM Groupmember g WHERE g.password = :password")
    , @NamedQuery(name = "Groupmember.findByRankid", query = "SELECT g FROM Groupmember g WHERE g.rankid = :rankid")
    , @NamedQuery(name = "Groupmember.findByResidence", query = "SELECT g FROM Groupmember g WHERE g.residence = :residence")
    , @NamedQuery(name = "Groupmember.findBySurname", query = "SELECT g FROM Groupmember g WHERE g.surname = :surname")
    , @NamedQuery(name = "Groupmember.findByTelephone", query = "SELECT g FROM Groupmember g WHERE g.telephone = :telephone")
    , @NamedQuery(name = "Groupmember.findByTitle", query = "SELECT g FROM Groupmember g WHERE g.title = :title")})
public class Groupmember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @Id
    private long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "MEMBERCODE")
    private String membercode;
    @Size(max = 255)
    @Column(name = "ADDEDBY")
    private String addedby;
    @Size(max = 255)
    @Column(name = "ALLOWEDIMEI")
    private String allowedimei;
    @Size(max = 255)
    @Column(name = "APPROVEDBY")
    private String approvedby;
    @Column(name = "DATEADDED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateadded;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Lob
    @Column(name = "MEMBERIMAGE")
    private byte[] memberimage;
    @Size(max = 255)
    @Column(name = "MEMBERSTATUS")
    private String memberstatus;
    @Size(max = 255)
    @Column(name = "MEMBERTYPE")
    private String membertype;
    @Size(max = 255)
    @Column(name = "MEMBERNAME")
    private String membername;
    @Size(max = 255)
    @Column(name = "NEXTOFKIN")
    private String nextofkin;
    @Size(max = 255)
    @Column(name = "NEXTOFKINTEL")
    private String nextofkintel;
    @Size(max = 255)
    @Column(name = "OCCUPATION")
    private String occupation;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "RANKID")
    private BigInteger rankid;
    @Size(max = 255)
    @Column(name = "RESIDENCE")
    private String residence;
    @Size(max = 255)
    @Column(name = "SURNAME")
    private String surname;
    @Size(max = 255)
    @Column(name = "TELEPHONE")
    private String telephone;
    @Size(max = 255)
    @Column(name = "TITLE")
    private String title;
    @Column(name = "MONTHLY_SALARY")
    private double monthlySalary;

    public Groupmember() {
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public String getAllowedimei() {
        return allowedimei;
    }

    public void setAllowedimei(String allowedimei) {
        this.allowedimei = allowedimei;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getMemberimage() {
        return memberimage;
    }

    public void setMemberimage(byte[] memberimage) {
        this.memberimage = memberimage;
    }

    public String getMemberstatus() {
        return memberstatus;
    }

    public void setMemberstatus(String memberstatus) {
        this.memberstatus = memberstatus;
    }

    public String getMembertype() {
        return membertype;
    }

    public void setMembertype(String membertype) {
        this.membertype = membertype;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getNextofkin() {
        return nextofkin;
    }

    public void setNextofkin(String nextofkin) {
        this.nextofkin = nextofkin;
    }

    public String getNextofkintel() {
        return nextofkintel;
    }

    public void setNextofkintel(String nextofkintel) {
        this.nextofkintel = nextofkintel;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigInteger getRankid() {
        return rankid;
    }

    public void setRankid(BigInteger rankid) {
        this.rankid = rankid;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public Collection<Invgroup> getInvgroupCollection() {
//        return invgroupCollection;
//    }
//
//    public void setInvgroupCollection(Collection<Invgroup> invgroupCollection) {
//        this.invgroupCollection = invgroupCollection;
//    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }
    
}
