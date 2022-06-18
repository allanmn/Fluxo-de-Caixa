/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.SubCategorias;
import entidades.CategoriasContas;
import entidades.FluxoCaixa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author allanneves
 */
public class FluxoCaixaJpaController implements Serializable {

    public FluxoCaixaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FluxoCaixa fluxoCaixa) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubCategorias codSubCat = fluxoCaixa.getCodSubCat();
            if (codSubCat != null) {
                codSubCat = em.getReference(codSubCat.getClass(), codSubCat.getCodSub());
                fluxoCaixa.setCodSubCat(codSubCat);
            }
            CategoriasContas codCat = fluxoCaixa.getCodCat();
            if (codCat != null) {
                codCat = em.getReference(codCat.getClass(), codCat.getCodigo());
                fluxoCaixa.setCodCat(codCat);
            }
            em.persist(fluxoCaixa);
            if (codSubCat != null) {
                codSubCat.getFluxoCaixaCollection().add(fluxoCaixa);
                codSubCat = em.merge(codSubCat);
            }
            if (codCat != null) {
                codCat.getFluxoCaixaCollection().add(fluxoCaixa);
                codCat = em.merge(codCat);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFluxoCaixa(fluxoCaixa.getId()) != null) {
                throw new PreexistingEntityException("FluxoCaixa " + fluxoCaixa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FluxoCaixa fluxoCaixa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FluxoCaixa persistentFluxoCaixa = em.find(FluxoCaixa.class, fluxoCaixa.getId());
            SubCategorias codSubCatOld = persistentFluxoCaixa.getCodSubCat();
            SubCategorias codSubCatNew = fluxoCaixa.getCodSubCat();
            CategoriasContas codCatOld = persistentFluxoCaixa.getCodCat();
            CategoriasContas codCatNew = fluxoCaixa.getCodCat();
            if (codSubCatNew != null) {
                codSubCatNew = em.getReference(codSubCatNew.getClass(), codSubCatNew.getCodSub());
                fluxoCaixa.setCodSubCat(codSubCatNew);
            }
            if (codCatNew != null) {
                codCatNew = em.getReference(codCatNew.getClass(), codCatNew.getCodigo());
                fluxoCaixa.setCodCat(codCatNew);
            }
            fluxoCaixa = em.merge(fluxoCaixa);
            if (codSubCatOld != null && !codSubCatOld.equals(codSubCatNew)) {
                codSubCatOld.getFluxoCaixaCollection().remove(fluxoCaixa);
                codSubCatOld = em.merge(codSubCatOld);
            }
            if (codSubCatNew != null && !codSubCatNew.equals(codSubCatOld)) {
                codSubCatNew.getFluxoCaixaCollection().add(fluxoCaixa);
                codSubCatNew = em.merge(codSubCatNew);
            }
            if (codCatOld != null && !codCatOld.equals(codCatNew)) {
                codCatOld.getFluxoCaixaCollection().remove(fluxoCaixa);
                codCatOld = em.merge(codCatOld);
            }
            if (codCatNew != null && !codCatNew.equals(codCatOld)) {
                codCatNew.getFluxoCaixaCollection().add(fluxoCaixa);
                codCatNew = em.merge(codCatNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fluxoCaixa.getId();
                if (findFluxoCaixa(id) == null) {
                    throw new NonexistentEntityException("The fluxoCaixa with id " + id + " no longer exists.");
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
            FluxoCaixa fluxoCaixa;
            try {
                fluxoCaixa = em.getReference(FluxoCaixa.class, id);
                fluxoCaixa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fluxoCaixa with id " + id + " no longer exists.", enfe);
            }
            SubCategorias codSubCat = fluxoCaixa.getCodSubCat();
            if (codSubCat != null) {
                codSubCat.getFluxoCaixaCollection().remove(fluxoCaixa);
                codSubCat = em.merge(codSubCat);
            }
            CategoriasContas codCat = fluxoCaixa.getCodCat();
            if (codCat != null) {
                codCat.getFluxoCaixaCollection().remove(fluxoCaixa);
                codCat = em.merge(codCat);
            }
            em.remove(fluxoCaixa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FluxoCaixa> findFluxoCaixaEntities() {
        return findFluxoCaixaEntities(true, -1, -1);
    }

    public List<FluxoCaixa> findFluxoCaixaEntities(int maxResults, int firstResult) {
        return findFluxoCaixaEntities(false, maxResults, firstResult);
    }

    private List<FluxoCaixa> findFluxoCaixaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FluxoCaixa.class));
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

    public FluxoCaixa findFluxoCaixa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FluxoCaixa.class, id);
        } finally {
            em.close();
        }
    }

    public int getFluxoCaixaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FluxoCaixa> rt = cq.from(FluxoCaixa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
