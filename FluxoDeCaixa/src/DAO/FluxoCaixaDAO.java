/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import controllers.FluxoCaixaJpaController;
import controllers.exceptions.NonexistentEntityException;
import entidades.FluxoCaixa;
import java.util.List;

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
        FluxoCaixa categoria = null;
        try {
            categoria = objetoJPA.findFluxoCaixa(id);
            if (categoria == null) {
                throw new NonexistentEntityException
                    ("Não existe este fluxo de caixa no banco: " + id);
            }
        } catch (NonexistentEntityException ex) {
            throw new Exception(ex.getMessage());
        } finally {
            return categoria;
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
