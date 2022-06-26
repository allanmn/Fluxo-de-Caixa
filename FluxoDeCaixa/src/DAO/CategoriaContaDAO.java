/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Controllers.CategoriasContasJpaController;
import Exceptions.ValidationException;
import controllers.exceptions.NonexistentEntityException;
import Entidades.CategoriasContas;
import Entidades.SubCategorias;
import java.util.List;

/**
 *
 * @author allanneves
 */
public class CategoriaContaDAO extends ModeloDAO<CategoriasContas, CategoriasContasJpaController> {
    public CategoriaContaDAO () {
        this.objetoJPA = new CategoriasContasJpaController(getEmf());
    }

    @Override
    public void inserir(CategoriasContas objeto) throws Exception, ValidationException {
        if (objeto.getDescricao() == null || objeto.getDescricao().equals("")) {
            throw new ValidationException("Descricao");
        }
        
        if (objeto.getSubCategoriasCollection() != null) {
            for(SubCategorias subCategoria : objeto.getSubCategoriasCollection()) {
                
            }
        }
        
        objetoJPA.create(objeto);
    }

    @Override
    public void editar(CategoriasContas objeto) throws Exception {
        try {
            objetoJPA.edit(objeto);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe este vendedor no banco " + objeto);
        }
    }

    @Override
    public void excluir(Integer id) throws Exception {
        try {
            objetoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe este vendedor no banco: " + id);
        }
    }

    @Override
    public void excluir(CategoriasContas objeto) throws Exception {
        try {
            objetoJPA.destroy(objeto.getCodigo());
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe este vendedor no banco: " + objeto);
        }
    }

    @Override
    public CategoriasContas consultar(Integer id) throws Exception {
        CategoriasContas categoria = null;
        try {
            categoria = objetoJPA.findCategoriasContas(id);
            if (categoria == null) {
                throw new NonexistentEntityException
                    ("Não existe este vendedor no banco: " + id);
            }
        } catch (NonexistentEntityException ex) {
            throw new Exception(ex.getMessage());
        } finally {
            return categoria;
        }
    }
    
    public List<CategoriasContas> consultar() throws NonexistentEntityException{
        List lista = objetoJPA.findCategoriasContasEntities();
        if(lista == null){
            throw new NonexistentEntityException("Não há produtos cadastrados");
        }
        return lista;
    }
}
