   /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Monica
 */
public abstract class ModeloDAO<To, Tj> {

    private EntityManagerFactory emf;
    protected Tj objetoJPA;

    public ModeloDAO() {
        emf = Persistence.createEntityManagerFactory("FluxoDeCaixaPU");
    }

    public abstract void inserir(To objeto)throws Exception;
//        objetoJPA.create(objeto);

    public abstract void editar(To objeto) throws Exception;
//        try {
//            objetoJPA.edit(objeto);
//        } catch (NonexistentEntityException ex) {
//            throw new Exception("Não existe esta venda no banco: " + objeto);
//        }

    public abstract void excluir(Integer id) throws Exception;

    public abstract void excluir(To objeto) throws Exception;

    public abstract To consultar(Integer id) throws Exception;

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

}
