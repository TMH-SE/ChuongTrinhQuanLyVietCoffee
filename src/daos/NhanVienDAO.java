package daos;

import entities.NhanVien;
import java.math.BigDecimal;
/**
 *
 * @author minhh
 */
public class NhanVienDAO extends GeneralCRUD<NhanVien> {

    public int getIdentityCurrent() {
        String sql = "Select Ident_Current('NhanVien')";
        int idCurrent = 0;
        try {
            em.getTransaction().begin();
            idCurrent = ((BigDecimal) em.createNativeQuery(sql).getSingleResult()).intValueExact();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return idCurrent;
    }
    
    public NhanVien getNhanVienTheoTenDangNhap(String tenDN){
        NhanVien nv=null;
        try{
            em.getTransaction().begin();
            nv=(NhanVien) em.createNamedQuery("NhanVien.findByTenDangNhap").setParameter("tenDangNhap", tenDN).getSingleResult();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return nv;
    }
}
