package com.finessence.loan.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "LOANGUARANTOR")
@NamedQueries({
    @NamedQuery(name = "Loanguarantor.findAll", query = "SELECT l FROM Loanguarantor l")
    , @NamedQuery(name = "Loanguarantor.findById", query = "SELECT l FROM Loanguarantor l WHERE l.id = :id")
    , @NamedQuery(name = "Loanguarantor.findByAmount", query = "SELECT l FROM Loanguarantor l WHERE l.amount = :amount")
    , @NamedQuery(name = "Loanguarantor.findByDateadded", query = "SELECT l FROM Loanguarantor l WHERE l.dateadded = :dateadded")
    , @NamedQuery(name = "Loanguarantor.findByMembercode", query = "SELECT l FROM Loanguarantor l WHERE l.membercode = :membercode")
    , @NamedQuery(name = "Loanguarantor.findByStatus", query = "SELECT l FROM Loanguarantor l WHERE l.status = :status")})
public class Loanguarantor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Column(name = "AMOUNT")
    private BigInteger amount;
    @Column(name = "DATEADDED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateadded;
    @Size(max = 255)
    @Column(name = "MEMBERCODE")
    private String membercode;
    @Size(max = 255)
    @Column(name = "STATUS")
    private String status;
    @Column(name = "LOAN_APPLICTION_ID")
    private Long lonApplicationId;

    public Loanguarantor() {
    }

    public Long getLonApplicationId() {
        return lonApplicationId;
    }

    public void setLonApplicationId(Long lonApplicationId) {
        this.lonApplicationId = lonApplicationId;
    }

    public Loanguarantor(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

    public String getMembercode() {
        return membercode;
    }

    public void setMembercode(String membercode) {
        this.membercode = membercode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof Loanguarantor)) {
            return false;
        }
        Loanguarantor other = (Loanguarantor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.finessence.loan.entities.Loanguarantor[ id=" + id + " ]";
    }

}
