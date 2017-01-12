/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.PersistenceUtil.getEntityManager;
import exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Palestrante;

/**
 *
 * @author luisr
 */
public class PalestranteDao implements Serializable {

    private PalestranteDao() {
        
    }
    private static PalestranteDao instance = new PalestranteDao();

    public static PalestranteDao getInstance() {
        return instance;
    }

    public void create(Palestrante palestrante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(palestrante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Palestrante palestrante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            palestrante = em.merge(palestrante);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = palestrante.getId();
                if (findPalestrante(id) == null) {
                    throw new NonexistentEntityException("The palestrante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Palestrante palestrante;
            try {
                palestrante = em.getReference(Palestrante.class, id);
                palestrante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The palestrante with id " + id + " no longer exists.", enfe);
            }
            em.remove(palestrante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Palestrante> findPalestranteEntities() {
        return findPalestranteEntities(true, -1, -1);
    }

    public List<Palestrante> findPalestranteEntities(int maxResults, int firstResult) {
        return findPalestranteEntities(false, maxResults, firstResult);
    }

    private List<Palestrante> findPalestranteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Palestrante.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Palestrante findPalestrante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Palestrante.class, id);
        } finally {
            em.close();
        }
    }

    public int getPalestranteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Palestrante> rt = cq.from(Palestrante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
