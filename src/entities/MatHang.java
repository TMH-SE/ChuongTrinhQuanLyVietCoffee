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
@Table(name = "MatHang")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatHang.findAll", query = "SELECT m FROM MatHang m")
    , @NamedQuery(name = "MatHang.findByMaHang", query = "SELECT m FROM MatHang m WHERE m.maHang = :maHang")
    , @NamedQuery(name = "MatHang.findByDonGia", query = "SELECT m FROM MatHang m WHERE m.donGia = :donGia")
    , @NamedQuery(name = "MatHang.findByDonViTinh", query = "SELECT m FROM MatHang m WHERE m.donViTinh = :donViTinh")
    , @NamedQuery(name = "MatHang.findByLoaiMatHang", query = "SELECT m FROM MatHang m WHERE m.loaiMatHang = :loaiMatHang")
    , @NamedQuery(name = "MatHang.findByTenMatHang", query = "SELECT m FROM MatHang m WHERE m.tenMatHang = :tenMatHang")})
public class MatHang implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "maHang")
    private Integer maHang;
    
    @Basic(optional = false)
    @Column(name = "tenMatHang", columnDefinition = "NVARCHAR(255)")
    private String tenMatHang;
    
    @Basic(optional = false)
    @Column(name = "loaiMatHang", columnDefinition = "NVARCHAR(255)")
    private String loaiMatHang;  
    
    @Basic(optional = false)
    @Column(name = "donViTinh", columnDefinition = "NVARCHAR(255)")
    private String donViTinh;
    
    @Basic(optional = false)
    @Column(name = "donGia")
    private double donGia;
    
    @OneToMany(mappedBy = "maHang")
    private List<ChiTietHoaDon> chiTietHoaDonList;

    public MatHang() {
    }

    public MatHang(Integer maHang) {
        this.maHang = maHang;
    }

    public MatHang(Integer maHang, String tenMatHang, String loaiMatHang, String donViTinh, double donGia) {
        this.maHang = maHang;
        this.tenMatHang = tenMatHang;
        this.loaiMatHang = loaiMatHang;
        this.donViTinh = donViTinh;
        this.donGia = donGia;
    }

    public MatHang(String tenMatHang, String loaiMatHang, String donViTinh, double donGia) {
        this.tenMatHang = tenMatHang;
        this.loaiMatHang = loaiMatHang;
        this.donViTinh = donViTinh;
        this.donGia = donGia;
    }
    
    public Integer getMaHang() {
        return maHang;
    }

    public void setMaHang(Integer maHang) {
        this.maHang = maHang;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getLoaiMatHang() {
        return loaiMatHang;
    }

    public void setLoaiMatHang(String loaiMatHang) {
        this.loaiMatHang = loaiMatHang;
    }

    public String getTenMatHang() {
        return tenMatHang;
    }

    public void setTenMatHang(String tenMatHang) {
        this.tenMatHang = tenMatHang;
    }

    @XmlTransient
    public List<ChiTietHoaDon> getChiTietHoaDonList() {
        return chiTietHoaDonList;
    }

    public void setChiTietHoaDonList(List<ChiTietHoaDon> chiTietHoaDonList) {
        this.chiTietHoaDonList = chiTietHoaDonList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maHang != null ? maHang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatHang)) {
            return false;
        }
        MatHang other = (MatHang) object;
        if ((this.maHang == null && other.maHang != null) || (this.maHang != null && !this.maHang.equals(other.maHang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MatHang{" + "maHang=" + maHang + ", tenMatHang=" + tenMatHang + ", loaiMatHang=" + loaiMatHang + ", donViTinh=" + donViTinh + ", donGia=" + donGia + '}';
    }
}
