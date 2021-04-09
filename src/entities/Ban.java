/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author minhh
 */
@Entity
@Table(name = "Ban")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ban.findAll", query = "SELECT b FROM Ban b")
    , @NamedQuery(name = "Ban.findByMaBan", query = "SELECT b FROM Ban b WHERE b.maBan = :maBan")
    , @NamedQuery(name = "Ban.findByTenBan", query = "SELECT b FROM Ban b WHERE b.tenBan = :tenBan")
    , @NamedQuery(name = "Ban.findByTrangThai", query = "SELECT b FROM Ban b WHERE b.trangThai = :trangThai")})
public class Ban implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "maBan")
    private Integer maBan;
    
    @Basic(optional = false)
    @Column(name = "tenBan", columnDefinition = "NVARCHAR(255)")
    private String tenBan;
    
    @Basic(optional = false)
    @Column(name = "trangThai")
    private boolean trangThai;
    
    @OneToMany(mappedBy = "maBan")
    private List<HoaDon> hoaDonList;

    public Ban() {
    }

    public Ban(Integer maBan) {
        this.maBan = maBan;
    }

    public Ban(Integer maBan, String tenBan, boolean trangThai) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.trangThai = trangThai;
    }

    public Integer getMaBan() {
        return maBan;
    }

    public void setMaBan(Integer maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @XmlTransient
    public List<HoaDon> getHoaDonList() {
        return hoaDonList;
    }

    public void setHoaDonList(List<HoaDon> hoaDonList) {
        this.hoaDonList = hoaDonList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maBan != null ? maBan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ban)) {
            return false;
        }
        Ban other = (Ban) object;
        if ((this.maBan == null && other.maBan != null) || (this.maBan != null && !this.maBan.equals(other.maBan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ban[ maBan=" + maBan + " ]";
    }
    
}
