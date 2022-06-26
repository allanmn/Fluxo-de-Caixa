/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewsControllers;

import DAO.CategoriaContaDAO;
import DAO.SubCategoriaDAO;
import Entidades.CategoriasContas;
import Entidades.SubCategorias;
import Exceptions.ValidationException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author allanneves
 */
public class SubCategoryPageController implements Initializable {

    @FXML
    private ComboBox<CategoriasContas> category;
    @FXML
    private TextArea txtDesc;
    @FXML
    private Button btnSalvar;
    
    private CategoriaContaDAO categoryService;
    
    private SubCategoriaDAO subCategoryService;
    
    private ObservableList<CategoriasContas> categorias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            categoryService = new CategoriaContaDAO();
            
            categorias = FXCollections.observableArrayList(categoryService.consultar());
            this.category.setItems(categorias);
            
            this.category.setCellFactory(param -> new ListCell<CategoriasContas> () {
                @Override
                protected void updateItem(CategoriasContas item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getDescricao() == null) {
                        setText(null);
                    } else {
                        setText(item.getDescricao());
                    }
                }
            });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
//            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }    

    @FXML
    private void save(ActionEvent event) {
        SubCategorias subCategoria = new SubCategorias();
        
        subCategoria.setDescricao(this.txtDesc.getText());
        subCategoria.setCodCat(this.category.getValue());
        
        try {
            
            subCategoryService = new SubCategoriaDAO();
            
            subCategoryService.inserir(subCategoria);
            
            JOptionPane.showMessageDialog(null, "Subcategoria cadastrada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Informações inválidas", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            this.txtDesc.setText("");
            this.category.setValue(null);
        }
    }
    
}
