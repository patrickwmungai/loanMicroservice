/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "USERS")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
    , @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id")
    , @NamedQuery(name = "Users.findByFirstName", query = "SELECT u FROM Users u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "Users.findByLastName", query = "SELECT u FROM Users u WHERE u.lastName = :lastName")
    , @NamedQuery(name = "Users.findByUserName", query = "SELECT u FROM Users u WHERE u.userName = :userName")
    , @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email")
    , @NamedQuery(name = "Users.findByPhoneNumber", query = "SELECT u FROM Users u WHERE u.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password")
    , @NamedQuery(name = "Users.findByPin", query = "SELECT u FROM Users u WHERE u.pin = :pin")
    , @NamedQuery(name = "Users.findByGender", query = "SELECT u FROM Users u WHERE u.gender = :gender")
    , @NamedQuery(name = "Users.findByLastPasswordChange", query = "SELECT u FROM Users u WHERE u.lastPasswordChange = :lastPasswordChange")
    , @NamedQuery(name = "Users.findByLastLogin", query = "SELECT u FROM Users u WHERE u.lastLogin = :lastLogin")
    , @NamedQuery(name = "Users.findByLockedStatus", query = "SELECT u FROM Users u WHERE u.lockedStatus = :lockedStatus")
    , @NamedQuery(name = "Users.findByLanguage", query = "SELECT u FROM Users u WHERE u.language = :language")
    , @NamedQuery(name = "Users.findByStatus", query = "SELECT u FROM Users u WHERE u.status = :status")
    , @NamedQuery(name = "Users.findByApprovalStatus", query = "SELECT u FROM Users u WHERE u.approvalStatus = :approvalStatus")
    , @NamedQuery(name = "Users.findByTimeCreated", query = "SELECT u FROM Users u WHERE u.timeCreated = :timeCreated")
    , @NamedQuery(name = "Users.findByLastUpdate", query = "SELECT u FROM Users u WHERE u.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "Users.findByCreatedBy", query = "SELECT u FROM Users u WHERE u.createdBy = :createdBy")
    , @NamedQuery(name = "Users.findByUpdatedBy", query = "SELECT u FROM Users u WHERE u.updatedBy = :updatedBy")
    , @NamedQuery(name = "Users.findByMaxApprovals", query = "SELECT u FROM Users u WHERE u.maxApprovals = :maxApprovals")
    , @NamedQuery(name = "Users.findByCurrentApprovalLevel", query = "SELECT u FROM Users u WHERE u.currentApprovalLevel = :currentApprovalLevel")
    , @NamedQuery(name = "Users.findByIntrash", query = "SELECT u FROM Users u WHERE u.intrash = :intrash")
    , @NamedQuery(name = "Users.findByGroupId", query = "SELECT u FROM Users u WHERE u.groupId = :groupId")
    , @NamedQuery(name = "Users.findByOtp", query = "SELECT u FROM Users u WHERE u.otp = :otp")
    , @NamedQuery(name = "Users.findBySessionId", query = "SELECT u FROM Users u WHERE u.sessionId = :sessionId")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 50)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Size(max = 50)
    @Column(name = "USER_NAME")
    private String userName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Size(max = 100)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 100)
    @Column(name = "PIN")
    private String pin;
    @Size(max = 20)
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "LAST_PASSWORD_CHANGE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordChange;
    @Column(name = "LAST_LOGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @Column(name = "LOCKED_STATUS")
    private Integer lockedStatus;
    @Size(max = 20)
    @Column(name = "LANGUAGE")
    private String language;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "APPROVAL_STATUS")
    private Integer approvalStatus;
    @Column(name = "TIME_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @Column(name = "LAST_UPDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    @Column(name = "UPDATED_BY")
    private Integer updatedBy;
    @Column(name = "MAX_APPROVALS")
    private Integer maxApprovals;
    @Column(name = "CURRENT_APPROVAL_LEVEL")
    private Integer currentApprovalLevel;
    @Size(max = 5)
    @Column(name = "INTRASH")
    private String intrash;
    @Size(max = 200)
    @Column(name = "GROUP_ID")
    private String groupId;
    @Size(max = 30)
    @Column(name = "OTP")
    private String otp;
    @Size(max = 100)
    @Column(name = "SESSION_ID")
    private String sessionId;
    @Transient
    private List<Role> roles;
    @Transient
    private String token;

    public Users() {
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getLockedStatus() {
        return lockedStatus;
    }

    public void setLockedStatus(Integer lockedStatus) {
        this.lockedStatus = lockedStatus;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getMaxApprovals() {
        return maxApprovals;
    }

    public void setMaxApprovals(Integer maxApprovals) {
        this.maxApprovals = maxApprovals;
    }

    public Integer getCurrentApprovalLevel() {
        return currentApprovalLevel;
    }

    public void setCurrentApprovalLevel(Integer currentApprovalLevel) {
        this.currentApprovalLevel = currentApprovalLevel;
    }

    public String getIntrash() {
        return intrash;
    }

    public void setIntrash(String intrash) {
        this.intrash = intrash;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Users[ id=" + id + " ]";
    }

}
