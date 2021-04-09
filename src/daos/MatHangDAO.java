package daos;

import entities.MatHang;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.type.StringNVarcharType;

/**
 *
 * @author minhh
 */
public class MatHangDAO extends GeneralCRUD<MatHang>{
    public MatHang getHangTheoMa(int maHang){
        MatHang mh=null;
        try{
            em.getTransaction().begin();
            mh=(MatHang) em.createNamedQuery("MatHang.findByMaHang").setParameter("maHang", maHang).getSingleResult();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return mh;
    }
    
    public int getIdentityCurrent() {
        String sql = "Select Ident_Current('MatHang')";
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
    public int getIdentitySeed() {
        String sql = "Select Ident_Seed('MatHang')";
        int idSeed = 0;
        try {
            em.getTransaction().begin();
            idSeed = ((BigDecimal) em.createNativeQuery(sql).getSingleResult()).intValueExact();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return idSeed;
    }
    
    public List<MatHang> getMatHangTheoLoaiHang(String loaiHang){
        List<MatHang> list=null;
        try{
            em.getTransaction().begin();
            list=em.createNamedQuery("MatHang.findByLoaiMatHang").setParameter("loaiMatHang", loaiHang).getResultList();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return list;
    }
}
