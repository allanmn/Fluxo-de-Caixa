/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Controllers.CategoriasContasJpaController;
import Controllers.SubCategoriasJpaController;
import Exceptions.ValidationException;
import controllers.exceptions.NonexistentEntityException;
import Entidades.CategoriasContas;
import Entidades.SubCategorias;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author allanneves
 */
public class SubCategoriaDAO extends ModeloDAO<SubCategorias, SubCategoriasJpaController> {
    public SubCategoriaDAO () {
        this.objetoJPA = new SubCategoriasJpaController(getEmf());
    }

    @Override
    public void inserir(SubCategorias objeto) throws Exception, ValidationException {
        if (objeto.getDescricao() == null || objeto.getDescricao().equals("")) {
            throw new ValidationException("Descricao");
        }
        
        objetoJPA.create(objeto);
    }

    @Override
    public void editar(SubCategorias objeto) throws Exception {
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
    public void excluir(SubCategorias objeto) throws Exception {
        try {
            objetoJPA.destroy(objeto.getCodSub());
        } catch (NonexistentEntityException ex) {
            throw new Exception("Não existe este vendedor no banco: " + objeto);
        }
    }

    @Override
    public SubCategorias consultar(Integer id) throws Exception {
        return new SubCategorias();
    }
    
    public List<SubCategorias> consultar() throws NonexistentEntityException{
        return new ArrayList<SubCategorias>();
    }
}
