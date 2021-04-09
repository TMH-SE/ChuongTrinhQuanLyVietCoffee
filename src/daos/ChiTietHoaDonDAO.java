package daos;

import entities.ChiTietHoaDon;
import entities.HoaDon;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author minhh
 */
public class ChiTietHoaDonDAO extends GeneralCRUD<ChiTietHoaDon>{
    public List<ChiTietHoaDon> getCTHoaDonTheoBan(HoaDon hd){
        List<ChiTietHoaDon> listhd=null;
        //String sql = "SELECT c FROM ChiTietHoaDon c WHERE c.maHoaDon = :maHoaDon";
        try{
            em.getTransaction().begin();
            CriteriaBuilder cb=em.getCriteriaBuilder();
            CriteriaQuery<ChiTietHoaDon> cq=cb.createQuery(ChiTietHoaDon.class);
            Root<ChiTietHoaDon> r=cq.from(ChiTietHoaDon.class);
            cq.select(r);
            cq.where(cb.equal(r.get("maHoaDon"), hd));
            TypedQuery<ChiTietHoaDon> query=em.createQuery(cq);
            listhd=query.getResultList();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return listhd;
    }
    
    public ChiTietHoaDon getCTHoaDonTheoMa(int mact){
        ChiTietHoaDon ct=null;
        try{
            em.getTransaction().begin();
            ct=(ChiTietHoaDon) em.createNamedQuery("ChiTietHoaDon.findByMaChiTietHoaDon").setParameter("maChiTietHoaDon", mact).getSingleResult();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return ct;
    }
}
