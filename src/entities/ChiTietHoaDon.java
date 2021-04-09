/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author minhh
 */
@Entity
@Table(name = "ChiTietHoaDon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChiTietHoaDon.findAll", query = "SELECT c FROM ChiTietHoaDon c")
    , @NamedQuery(name = "ChiTietHoaDon.findByMaChiTietHoaDon", query = "SELECT c FROM ChiTietHoaDon c WHERE c.maChiTietHoaDon = :maChiTietHoaDon")
    , @NamedQuery(name = "ChiTietHoaDon.findBySoLuong", query = "SELECT c FROM ChiTietHoaDon c WHERE c.soLuong = :soLuong")
    , @NamedQuery(name = "ChiTietHoaDon.findByThanhTien", query = "SELECT c FROM ChiTietHoaDon c WHERE c.thanhTien = :thanhTien")})
public class ChiTietHoaDon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "maChiTietHoaDon")
    private Integer maChiTietHoaDon;
    
    @Basic(optional = false)
    @Column(name = "soLuong")
    private int soLuong;
    
    @Basic(optional = false)
    @Column(name = "thanhTien")
    private double thanhTien;
    
    @JoinColumn(name = "maHoaDon", referencedColumnName = "maHoaDon")
    @ManyToOne
    private HoaDon maHoaDon;
    
    @JoinColumn(name = "maHang", referencedColumnName = "maHang")
    @ManyToOne
    private MatHang maHang;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(Integer maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public ChiTietHoaDon(Integer maChiTietHoaDon, int soLuong, int thanhTien) {
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public Integer getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }

    public void setMaChiTietHoaDon(Integer maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public HoaDon getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(HoaDon maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public MatHang getMaHang() {
        return maHang;
    }

    public void setMaHang(MatHang maHang) {
        this.maHang = maHang;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maChiTietHoaDon != null ? maChiTietHoaDon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChiTietHoaDon)) {
            return false;
        }
        ChiTietHoaDon other = (ChiTietHoaDon) object;
        if ((this.maChiTietHoaDon == null && other.maChiTietHoaDon != null) || (this.maChiTietHoaDon != null && !this.maChiTietHoaDon.equals(other.maChiTietHoaDon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ChiTietHoaDon[ maChiTietHoaDon=" + maChiTietHoaDon + " ]";
    }
    
}
