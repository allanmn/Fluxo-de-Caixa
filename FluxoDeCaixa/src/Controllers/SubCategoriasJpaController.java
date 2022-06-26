/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Entidades.CategoriasContas;
import Entidades.FluxoCaixa;
import Entidades.SubCategorias;
import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author allanneves
 */
public class SubCategoriasJpaController implements Serializable {
    
    public SubCategoriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void create(SubCategorias subCategorias) throws PreexistingEntityException, Exception {
//        if (categoriasContas.getSubCategoriasCollection() == null) {
//            categoriasContas.setSubCategoriasCollection(new ArrayList<SubCategorias>());
//        }
//        if (categoriasContas.getFluxoCaixaCollection() == null) {
//            categoriasContas.setFluxoCaixaCollection(new ArrayList<FluxoCaixa>());
//        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            Collection<SubCategorias> attachedSubCategoriasCollection = new ArrayList<SubCategorias>();
//            for (SubCategorias subCategoriasCollectionSubCategoriasToAttach : categoriasContas.getSubCategoriasCollection()) {
//                subCategoriasCollectionSubCategoriasToAttach = em.getReference(subCategoriasCollectionSubCategoriasToAttach.getClass(), subCategoriasCollectionSubCategoriasToAttach.getCodSub());
//                attachedSubCategoriasCollection.add(subCategoriasCollectionSubCategoriasToAttach);
//            }
//            categoriasContas.setSubCategoriasCollection(attachedSubCategoriasCollection);
//            Collection<FluxoCaixa> attachedFluxoCaixaCollection = new ArrayList<FluxoCaixa>();
//            for (FluxoCaixa fluxoCaixaCollectionFluxoCaixaToAttach : categoriasContas.getFluxoCaixaCollection()) {
//                fluxoCaixaCollectionFluxoCaixaToAttach = em.getReference(fluxoCaixaCollectionFluxoCaixaToAttach.getClass(), fluxoCaixaCollectionFluxoCaixaToAttach.getId());
//                attachedFluxoCaixaCollection.add(fluxoCaixaCollectionFluxoCaixaToAttach);
//            }
//            categoriasContas.setFluxoCaixaCollection(attachedFluxoCaixaCollection);
            em.persist(subCategorias);
//            for (SubCategorias subCategoriasCollectionSubCategorias : categoriasContas.getSubCategoriasCollection()) {
//                CategoriasContas oldCodCatOfSubCategoriasCollectionSubCategorias = subCategoriasCollectionSubCategorias.getCodCat();
//                subCategoriasCollectionSubCategorias.setCodCat(categoriasContas);
//                subCategoriasCollectionSubCategorias = em.merge(subCategoriasCollectionSubCategorias);
//                if (oldCodCatOfSubCategoriasCollectionSubCategorias != null) {
//                    oldCodCatOfSubCategoriasCollectionSubCategorias.getSubCategoriasCollection().remove(subCategoriasCollectionSubCategorias);
//                    oldCodCatOfSubCategoriasCollectionSubCategorias = em.merge(oldCodCatOfSubCategoriasCollectionSubCategorias);
//                }
//            }
//            for (FluxoCaixa fluxoCaixaCollectionFluxoCaixa : categoriasContas.getFluxoCaixaCollection()) {
//                CategoriasContas oldCodCatOfFluxoCaixaCollectionFluxoCaixa = fluxoCaixaCollectionFluxoCaixa.getCodCat();
//                fluxoCaixaCollectionFluxoCaixa.setCodCat(categoriasContas);
//                fluxoCaixaCollectionFluxoCaixa = em.merge(fluxoCaixaCollectionFluxoCaixa);
//                if (oldCodCatOfFluxoCaixaCollectionFluxoCaixa != null) {
//                    oldCodCatOfFluxoCaixaCollectionFluxoCaixa.getFluxoCaixaCollection().remove(fluxoCaixaCollectionFluxoCaixa);
//                    oldCodCatOfFluxoCaixaCollectionFluxoCaixa = em.merge(oldCodCatOfFluxoCaixaCollectionFluxoCaixa);
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SubCategorias subcategoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubCategorias persistentCSubCategoria = em.find(SubCategorias.class, subcategoria.getCodSub());
            List<String> illegalOrphanMessages = null;
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            subcategoria = em.merge(subcategoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
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
            SubCategorias subCategoria;
            try {
                subCategoria = em.getReference(SubCategorias.class, id);
                subCategoria.getCodSub();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriasContas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(subCategoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
