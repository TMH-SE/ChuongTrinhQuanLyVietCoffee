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
@Table(name = "NhanVien")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NhanVien.findAll", query = "SELECT n FROM NhanVien n")
    , @NamedQuery(name = "NhanVien.findByMaNhanVien", query = "SELECT n FROM NhanVien n WHERE n.maNhanVien = :maNhanVien")
    , @NamedQuery(name = "NhanVien.findByDiaChi", query = "SELECT n FROM NhanVien n WHERE n.diaChi = :diaChi")
    , @NamedQuery(name = "NhanVien.findByEmail", query = "SELECT n FROM NhanVien n WHERE n.email = :email")
    , @NamedQuery(name = "NhanVien.findByHoten", query = "SELECT n FROM NhanVien n WHERE n.hoten = :hoten")
    , @NamedQuery(name = "NhanVien.findByLoaiNhanVien", query = "SELECT n FROM NhanVien n WHERE n.loaiNhanVien = :loaiNhanVien")
    , @NamedQuery(name = "NhanVien.findByMatKhau", query = "SELECT n FROM NhanVien n WHERE n.matKhau = :matKhau")
    , @NamedQuery(name = "NhanVien.findByNgaySinh", query = "SELECT n FROM NhanVien n WHERE n.ngaySinh = :ngaySinh")
    , @NamedQuery(name = "NhanVien.findBySoCMND", query = "SELECT n FROM NhanVien n WHERE n.soCMND = :soCMND")
    , @NamedQuery(name = "NhanVien.findBySoDienThoai", query = "SELECT n FROM NhanVien n WHERE n.soDienThoai = :soDienThoai")
    , @NamedQuery(name = "NhanVien.findByTenDangNhap", query = "SELECT n FROM NhanVien n WHERE n.tenDangNhap = :tenDangNhap")})
public class NhanVien implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "maNhanVien")
    private Integer maNhanVien;
    
    @Basic(optional = false)
    @Column(name = "hoten", columnDefinition = "NVARCHAR(255)")
    private String hoten;
    
    @Basic(optional = false)
    @Column(name = "soCMND")
    private String soCMND;
    
    @Basic(optional = false)
    @Column(name = "ngaySinh")
    private LocalDate ngaySinh;
    
    @Basic(optional = false)
    @Column(name = "diaChi", columnDefinition = "NVARCHAR(255)")
    private String diaChi;
    
    @Basic(optional = false)
    @Column(name = "soDienThoai")
    private String soDienThoai;
    
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    
    @Basic(optional = false)
    @Column(name = "loaiNhanVien")
    private String loaiNhanVien;
    
    @Basic(optional = false)
    @Column(name = "tenDangNhap", unique = true)
    private String tenDangNhap;
    
    @Basic(optional = false)
    @Column(name = "matKhau")
    private String matKhau;
    
    @OneToMany(mappedBy = "maNhanVien")
    private List<HoaDon> hoaDonList;

    public NhanVien() {
    }

    public NhanVien(Integer maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public NhanVien(Integer maNhanVien, String hoten, String soCMND, LocalDate ngaySinh, String diaChi, String soDienThoai, String email, String loaiNhanVien, String tenDangNhap, String matKhau) {
        this.maNhanVien = maNhanVien;
        this.hoten = hoten;
        this.soCMND = soCMND;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.loaiNhanVien = loaiNhanVien;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public NhanVien(String hoten, String soCMND, LocalDate ngaySinh, String diaChi, String soDienThoai, String email, String loaiNhanVien, String tenDangNhap, String matKhau) {
        this.hoten = hoten;
        this.soCMND = soCMND;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.loaiNhanVien = loaiNhanVien;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public Integer getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(Integer maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getLoaiNhanVien() {
        return loaiNhanVien;
    }

    public void setLoaiNhanVien(String loaiNhanVien) {
        this.loaiNhanVien = loaiNhanVien;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
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
        hash += (maNhanVien != null ? maNhanVien.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NhanVien)) {
            return false;
        }
        NhanVien other = (NhanVien) object;
        if ((this.maNhanVien == null && other.maNhanVien != null) || (this.maNhanVien != null && !this.maNhanVien.equals(other.maNhanVien))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NhanVien{" + "maNhanVien=" + maNhanVien + ", hoten=" + hoten + '}';
    }
    
}
