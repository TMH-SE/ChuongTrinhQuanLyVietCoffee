/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author minhh
 */
@Entity
@Table(name = "HoaDon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HoaDon.findAll", query = "SELECT h FROM HoaDon h")
    , @NamedQuery(name = "HoaDon.findByMaHoaDon", query = "SELECT h FROM HoaDon h WHERE h.maHoaDon = :maHoaDon")
    , @NamedQuery(name = "HoaDon.findByNgayLap", query = "SELECT h FROM HoaDon h WHERE h.ngayLap = :ngayLap")
    , @NamedQuery(name = "HoaDon.findByTrangThaiThanhToan", query = "SELECT h FROM HoaDon h WHERE h.trangThaiThanhToan = :trangThaiThanhToan")})
public class HoaDon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "maHoaDon")
    private Integer maHoaDon;
    
    @Basic(optional = false)
    @Column(name = "ngayLap")
    private LocalDate ngayLap;
    
    @Basic(optional = false)
    @Column(name = "trangThaiThanhToan")
    private boolean trangThaiThanhToan;
    
    @Basic(optional = false)
    @Column(name = "tongTien")
    private double tongTien;
    
    @OneToMany(mappedBy = "maHoaDon")
    private List<ChiTietHoaDon> chiTietHoaDonList;
    
    @JoinColumn(name = "maBan", referencedColumnName = "maBan")
    @ManyToOne
    private Ban maBan;
    
    @JoinColumn(name = "maNhanVien", referencedColumnName = "maNhanVien")
    @ManyToOne
    private NhanVien maNhanVien;

    public HoaDon() {
    }

    public HoaDon(Integer maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public HoaDon(Integer maHoaDon, LocalDate ngayLap, boolean trangThaiThanhToan) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public HoaDon(Integer maHoaDon, LocalDate ngayLap, boolean trangThaiThanhToan, double tongTien) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.tongTien = tongTien;
    }
    
    
    public Integer getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(Integer maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public boolean getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(boolean trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
    @XmlTransient
    public List<ChiTietHoaDon> getChiTietHoaDonList() {
        return chiTietHoaDonList;
    }

    public void setChiTietHoaDonList(List<ChiTietHoaDon> chiTietHoaDonList) {
        this.chiTietHoaDonList = chiTietHoaDonList;
    }

    public Ban getMaBan() {
        return maBan;
    }

    public void setMaBan(Ban maBan) {
        this.maBan = maBan;
    }

    public NhanVien getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(NhanVien maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maHoaDon != null ? maHoaDon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HoaDon)) {
            return false;
        }
        HoaDon other = (HoaDon) object;
        if ((this.maHoaDon == null && other.maHoaDon != null) || (this.maHoaDon != null && !this.maHoaDon.equals(other.maHoaDon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.HoaDon[ maHoaDon=" + maHoaDon + " ]";
    }
    
}
