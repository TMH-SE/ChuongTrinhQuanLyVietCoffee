package daos;

import entities.Ban;

/**
 *
 * @author minhh
 */
public class BanDAO extends GeneralCRUD<Ban>{
    public Ban getBanTheoMa(int maban){
        Ban ban=null;
        try{
            em.getTransaction().begin();
            ban=(Ban) em.createNamedQuery("Ban.findByMaBan").setParameter("maBan", maban).getSingleResult();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return ban;
    }
    
}
