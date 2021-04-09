package daos;

import entities.Ban;
import entities.HoaDon;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author minhh
 */
public class HoaDonDAO extends GeneralCRUD<HoaDon>{
    public HoaDon getHoaDonTheoBan(int maBan){
        HoaDon hd=null;
        Ban ban= new BanDAO().getBanTheoMa(maBan);
        String sql = "SELECT h FROM HoaDon h WHERE h.maBan = :maBan and h.trangThaiThanhToan =0";
        try{
            em.getTransaction().begin();
            hd=(HoaDon) em.createQuery(sql).setParameter("maBan", ban).getSingleResult();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return hd;
    }
    
    public List<HoaDon> getHoaDonTheoNgay(LocalDate startDate, LocalDate endDate){
        List<HoaDon> list=new ArrayList<>();
        String sql = "SELECT h FROM HoaDon h WHERE h.ngayLap BETWEEN :startDate AND :endDate AND h.trangThaiThanhToan =1";
        try{
            em.getTransaction().begin();
            list=em.createQuery(sql).setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return list;
    }
    
}
