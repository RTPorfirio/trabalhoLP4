/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Palestra;
import model.Palestrante;
import exceptions.NonexistentEntityException;

/**
 *
 * @author Gabriel
 */
public class PalestraDao implements Serializable {

    private PalestraDao() {
    }

    private static PalestraDao instance = new PalestraDao();
    private EntityManagerFactory emf = null;

    public static PalestraDao getInstance() {
        return instance;
    }

    public EntityManager getEntityManager() {
        return PersistenceUtil.getEntityManager();
    }

    public void create(Palestra palestra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Palestrante idPalestrante = palestra.getIdpalestrante();
            if (idPalestrante != null) {
                idPalestrante = em.getReference(idPalestrante.getClass(), idPalestrante.getId());
                palestra.setIdpalestrante(idPalestrante);
            }
            em.persist(palestra);
            if (idPalestrante != null) {
                idPalestrante.getPalestraCollection().add(palestra);
                idPalestrante = em.merge(idPalestrante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Palestra palestra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Palestra persistentPalestra = em.find(Palestra.class, palestra.getId());
            Palestrante idPalestranteOld = persistentPalestra.getIdpalestrante();
            Palestrante idPalestranteNew = palestra.getIdpalestrante();
            if (idPalestranteNew != null) {
                idPalestranteNew = em.getReference(idPalestranteNew.getClass(), idPalestranteNew.getId());
                palestra.setIdpalestrante(idPalestranteNew);
            }
            palestra = em.merge(palestra);
            if (idPalestranteOld != null && !idPalestranteOld.equals(idPalestranteNew)) {
                idPalestranteOld.getPalestraCollection().remove(palestra);
                idPalestranteOld = em.merge(idPalestranteOld);
            }
            if (idPalestranteNew != null && !idPalestranteNew.equals(idPalestranteOld)) {
                idPalestranteNew.getPalestraCollection().add(palestra);
                idPalestranteNew = em.merge(idPalestranteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = palestra.getId();
                if (findPalestra(id) == null) {
                    throw new NonexistentEntityException("The palestra with id " + id + " no longer exists.");
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
            Palestra palestra;
            try {
                palestra = em.getReference(Palestra.class, id);
                palestra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The palestra with id " + id + " no longer exists.", enfe);
            }
            Palestrante idPalestrante = palestra.getIdpalestrante();
            if (idPalestrante != null) {
                idPalestrante.getPalestraCollection().remove(palestra);
                idPalestrante = em.merge(idPalestrante);
            }
            em.remove(palestra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Palestra> findPalestraEntities() {
        return findPalestraEntities(true, -1, -1);
    }

    public List<Palestra> findPalestraEntities(int maxResults, int firstResult) {
        return findPalestraEntities(false, maxResults, firstResult);
    }

    private List<Palestra> findPalestraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Palestra.class));
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

    public Palestra findPalestra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Palestra.class, id);
        } finally {
            em.close();
        }
    }

    public int getPalestraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Palestra> rt = cq.from(Palestra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
