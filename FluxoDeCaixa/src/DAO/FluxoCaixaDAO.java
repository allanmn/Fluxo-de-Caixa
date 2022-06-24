/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entidades.CategoriasContas;
import controllers.FluxoCaixaJpaController;
import controllers.exceptions.NonexistentEntityException;
import Entidades.FluxoCaixa;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author allanneves
 */
public class FluxoCaixaDAO extends ModeloDAO<FluxoCaixa, FluxoCaixaJpaController> {
    public FluxoCaixaDAO () {
        this.objetoJPA = new FluxoCaixaJpaController(getEmf());
    }

    @Override
    public void inserir(FluxoCaixa objeto) throws Exception {
        objetoJPA.create(objeto);
    }

    @Override
    public void editar(FluxoCaixa objeto) throws Exception {
        try {
            objetoJPA.edit(objeto);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe este fluxo de caixa no banco " + objeto);
        }
    }

    @Override
    public void excluir(Integer id) throws Exception {
        try {
            objetoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe este fluxo de caixa no banco: " + id);
        }
    }

    @Override
    public void excluir(FluxoCaixa objeto) throws Exception {
        try {
            objetoJPA.destroy(objeto.getId());
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe este fluxo de caixa no banco: " + objeto);
        }
    }

    @Override
    public FluxoCaixa consultar(Integer id) throws Exception {
        FluxoCaixa fluxo_caixa = null;
        try {
            fluxo_caixa = objetoJPA.findFluxoCaixa(id);
            if (fluxo_caixa == null) {
                throw new NonexistentEntityException
                    ("Não existe este fluxo de caixa no banco: " + id);
            }
        } catch (NonexistentEntityException ex) {
            throw new Exception(ex.getMessage());
        } finally {
            return fluxo_caixa;
        }
    }
    
    public List<FluxoCaixa> findByDate(Date data) throws Exception {
        CategoriaContaDAO category_service = new CategoriaContaDAO();
        
        CategoriasContas pagar = category_service.consultar(1);
        
        EntityManager em = objetoJPA.getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Query q = em.createNamedQuery("FluxoCaixa.findByDataOcorrenciaAndAPagar", FluxoCaixa.class);
            q.setParameter("dataOcorrencia", data);
            q.setParameter("codCat", pagar);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<FluxoCaixa> consultar() throws NonexistentEntityException{
        List lista = objetoJPA.findFluxoCaixaEntities();
        if(lista == null){
            throw new NonexistentEntityException("Não há produtos cadastrados");
        }
        return lista;
    }
}
