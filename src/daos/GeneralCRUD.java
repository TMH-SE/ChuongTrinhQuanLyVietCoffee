/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author minhh
 * @param <T>
 */
public abstract class GeneralCRUD<T> {

    protected EntityManager em;

    public GeneralCRUD() {
        em = MyEntityManagerFactory.getInstance().getEntityManager();
    }

    //crud
    public boolean save(T t) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(t);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
        }
        return false;
    }

    public boolean update(T t) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(t);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
        }
        return false;
    }

    public boolean delete(T t) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.remove(t);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
        }
        return false;
    }

    public List<T> getAll(Class<T> t) {
        String sql="select t from "+ t.getName()+" t";
        Query q=em.createQuery(sql);
        return q.getResultList();
    }

}
