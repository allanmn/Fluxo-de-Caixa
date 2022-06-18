/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import entidades.CategoriasContas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.SubCategorias;
import java.util.ArrayList;
import java.util.Collection;
import entidades.FluxoCaixa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author allanneves
 */
public class CategoriasContasJpaController implements Serializable {

    public CategoriasContasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriasContas categoriasContas) throws PreexistingEntityException, Exception {
        if (categoriasContas.getSubCategoriasCollection() == null) {
            categoriasContas.setSubCategoriasCollection(new ArrayList<SubCategorias>());
        }
        if (categoriasContas.getFluxoCaixaCollection() == null) {
            categoriasContas.setFluxoCaixaCollection(new ArrayList<FluxoCaixa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<SubCategorias> attachedSubCategoriasCollection = new ArrayList<SubCategorias>();
            for (SubCategorias subCategoriasCollectionSubCategoriasToAttach : categoriasContas.getSubCategoriasCollection()) {
                subCategoriasCollectionSubCategoriasToAttach = em.getReference(subCategoriasCollectionSubCategoriasToAttach.getClass(), subCategoriasCollectionSubCategoriasToAttach.getCodSub());
                attachedSubCategoriasCollection.add(subCategoriasCollectionSubCategoriasToAttach);
            }
            categoriasContas.setSubCategoriasCollection(attachedSubCategoriasCollection);
            Collection<FluxoCaixa> attachedFluxoCaixaCollection = new ArrayList<FluxoCaixa>();
            for (FluxoCaixa fluxoCaixaCollectionFluxoCaixaToAttach : categoriasContas.getFluxoCaixaCollection()) {
                fluxoCaixaCollectionFluxoCaixaToAttach = em.getReference(fluxoCaixaCollectionFluxoCaixaToAttach.getClass(), fluxoCaixaCollectionFluxoCaixaToAttach.getId());
                attachedFluxoCaixaCollection.add(fluxoCaixaCollectionFluxoCaixaToAttach);
            }
            categoriasContas.setFluxoCaixaCollection(attachedFluxoCaixaCollection);
            em.persist(categoriasContas);
            for (SubCategorias subCategoriasCollectionSubCategorias : categoriasContas.getSubCategoriasCollection()) {
                CategoriasContas oldCodCatOfSubCategoriasCollectionSubCategorias = subCategoriasCollectionSubCategorias.getCodCat();
                subCategoriasCollectionSubCategorias.setCodCat(categoriasContas);
                subCategoriasCollectionSubCategorias = em.merge(subCategoriasCollectionSubCategorias);
                if (oldCodCatOfSubCategoriasCollectionSubCategorias != null) {
                    oldCodCatOfSubCategoriasCollectionSubCategorias.getSubCategoriasCollection().remove(subCategoriasCollectionSubCategorias);
                    oldCodCatOfSubCategoriasCollectionSubCategorias = em.merge(oldCodCatOfSubCategoriasCollectionSubCategorias);
                }
            }
            for (FluxoCaixa fluxoCaixaCollectionFluxoCaixa : categoriasContas.getFluxoCaixaCollection()) {
                CategoriasContas oldCodCatOfFluxoCaixaCollectionFluxoCaixa = fluxoCaixaCollectionFluxoCaixa.getCodCat();
                fluxoCaixaCollectionFluxoCaixa.setCodCat(categoriasContas);
                fluxoCaixaCollectionFluxoCaixa = em.merge(fluxoCaixaCollectionFluxoCaixa);
                if (oldCodCatOfFluxoCaixaCollectionFluxoCaixa != null) {
                    oldCodCatOfFluxoCaixaCollectionFluxoCaixa.getFluxoCaixaCollection().remove(fluxoCaixaCollectionFluxoCaixa);
                    oldCodCatOfFluxoCaixaCollectionFluxoCaixa = em.merge(oldCodCatOfFluxoCaixaCollectionFluxoCaixa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategoriasContas(categoriasContas.getCodigo()) != null) {
                throw new PreexistingEntityException("CategoriasContas " + categoriasContas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriasContas categoriasContas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriasContas persistentCategoriasContas = em.find(CategoriasContas.class, categoriasContas.getCodigo());
            Collection<SubCategorias> subCategoriasCollectionOld = persistentCategoriasContas.getSubCategoriasCollection();
            Collection<SubCategorias> subCategoriasCollectionNew = categoriasContas.getSubCategoriasCollection();
            Collection<FluxoCaixa> fluxoCaixaCollectionOld = persistentCategoriasContas.getFluxoCaixaCollection();
            Collection<FluxoCaixa> fluxoCaixaCollectionNew = categoriasContas.getFluxoCaixaCollection();
            List<String> illegalOrphanMessages = null;
            for (SubCategorias subCategoriasCollectionOldSubCategorias : subCategoriasCollectionOld) {
                if (!subCategoriasCollectionNew.contains(subCategoriasCollectionOldSubCategorias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SubCategorias " + subCategoriasCollectionOldSubCategorias + " since its codCat field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<SubCategorias> attachedSubCategoriasCollectionNew = new ArrayList<SubCategorias>();
            for (SubCategorias subCategoriasCollectionNewSubCategoriasToAttach : subCategoriasCollectionNew) {
                subCategoriasCollectionNewSubCategoriasToAttach = em.getReference(subCategoriasCollectionNewSubCategoriasToAttach.getClass(), subCategoriasCollectionNewSubCategoriasToAttach.getCodSub());
                attachedSubCategoriasCollectionNew.add(subCategoriasCollectionNewSubCategoriasToAttach);
            }
            subCategoriasCollectionNew = attachedSubCategoriasCollectionNew;
            categoriasContas.setSubCategoriasCollection(subCategoriasCollectionNew);
            Collection<FluxoCaixa> attachedFluxoCaixaCollectionNew = new ArrayList<FluxoCaixa>();
            for (FluxoCaixa fluxoCaixaCollectionNewFluxoCaixaToAttach : fluxoCaixaCollectionNew) {
                fluxoCaixaCollectionNewFluxoCaixaToAttach = em.getReference(fluxoCaixaCollectionNewFluxoCaixaToAttach.getClass(), fluxoCaixaCollectionNewFluxoCaixaToAttach.getId());
                attachedFluxoCaixaCollectionNew.add(fluxoCaixaCollectionNewFluxoCaixaToAttach);
            }
            fluxoCaixaCollectionNew = attachedFluxoCaixaCollectionNew;
            categoriasContas.setFluxoCaixaCollection(fluxoCaixaCollectionNew);
            categoriasContas = em.merge(categoriasContas);
            for (SubCategorias subCategoriasCollectionNewSubCategorias : subCategoriasCollectionNew) {
                if (!subCategoriasCollectionOld.contains(subCategoriasCollectionNewSubCategorias)) {
                    CategoriasContas oldCodCatOfSubCategoriasCollectionNewSubCategorias = subCategoriasCollectionNewSubCategorias.getCodCat();
                    subCategoriasCollectionNewSubCategorias.setCodCat(categoriasContas);
                    subCategoriasCollectionNewSubCategorias = em.merge(subCategoriasCollectionNewSubCategorias);
                    if (oldCodCatOfSubCategoriasCollectionNewSubCategorias != null && !oldCodCatOfSubCategoriasCollectionNewSubCategorias.equals(categoriasContas)) {
                        oldCodCatOfSubCategoriasCollectionNewSubCategorias.getSubCategoriasCollection().remove(subCategoriasCollectionNewSubCategorias);
                        oldCodCatOfSubCategoriasCollectionNewSubCategorias = em.merge(oldCodCatOfSubCategoriasCollectionNewSubCategorias);
                    }
                }
            }
            for (FluxoCaixa fluxoCaixaCollectionOldFluxoCaixa : fluxoCaixaCollectionOld) {
                if (!fluxoCaixaCollectionNew.contains(fluxoCaixaCollectionOldFluxoCaixa)) {
                    fluxoCaixaCollectionOldFluxoCaixa.setCodCat(null);
                    fluxoCaixaCollectionOldFluxoCaixa = em.merge(fluxoCaixaCollectionOldFluxoCaixa);
                }
            }
            for (FluxoCaixa fluxoCaixaCollectionNewFluxoCaixa : fluxoCaixaCollectionNew) {
                if (!fluxoCaixaCollectionOld.contains(fluxoCaixaCollectionNewFluxoCaixa)) {
                    CategoriasContas oldCodCatOfFluxoCaixaCollectionNewFluxoCaixa = fluxoCaixaCollectionNewFluxoCaixa.getCodCat();
                    fluxoCaixaCollectionNewFluxoCaixa.setCodCat(categoriasContas);
                    fluxoCaixaCollectionNewFluxoCaixa = em.merge(fluxoCaixaCollectionNewFluxoCaixa);
                    if (oldCodCatOfFluxoCaixaCollectionNewFluxoCaixa != null && !oldCodCatOfFluxoCaixaCollectionNewFluxoCaixa.equals(categoriasContas)) {
                        oldCodCatOfFluxoCaixaCollectionNewFluxoCaixa.getFluxoCaixaCollection().remove(fluxoCaixaCollectionNewFluxoCaixa);
                        oldCodCatOfFluxoCaixaCollectionNewFluxoCaixa = em.merge(oldCodCatOfFluxoCaixaCollectionNewFluxoCaixa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoriasContas.getCodigo();
                if (findCategoriasContas(id) == null) {
                    throw new NonexistentEntityException("The categoriasContas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriasContas categoriasContas;
            try {
                categoriasContas = em.getReference(CategoriasContas.class, id);
                categoriasContas.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriasContas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SubCategorias> subCategoriasCollectionOrphanCheck = categoriasContas.getSubCategoriasCollection();
            for (SubCategorias subCategoriasCollectionOrphanCheckSubCategorias : subCategoriasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CategoriasContas (" + categoriasContas + ") cannot be destroyed since the SubCategorias " + subCategoriasCollectionOrphanCheckSubCategorias + " in its subCategoriasCollection field has a non-nullable codCat field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<FluxoCaixa> fluxoCaixaCollection = categoriasContas.getFluxoCaixaCollection();
            for (FluxoCaixa fluxoCaixaCollectionFluxoCaixa : fluxoCaixaCollection) {
                fluxoCaixaCollectionFluxoCaixa.setCodCat(null);
                fluxoCaixaCollectionFluxoCaixa = em.merge(fluxoCaixaCollectionFluxoCaixa);
            }
            em.remove(categoriasContas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CategoriasContas> findCategoriasContasEntities() {
        return findCategoriasContasEntities(true, -1, -1);
    }

    public List<CategoriasContas> findCategoriasContasEntities(int maxResults, int firstResult) {
        return findCategoriasContasEntities(false, maxResults, firstResult);
    }

    private List<CategoriasContas> findCategoriasContasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriasContas.class));
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

    public CategoriasContas findCategoriasContas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriasContas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriasContasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriasContas> rt = cq.from(CategoriasContas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
