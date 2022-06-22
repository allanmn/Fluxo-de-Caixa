/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewsControllers.CategoryPageControllerViewsControllers;

import DAO.CategoriaContaDAO;
import Exceptions.ValidationException;
import entidades.CategoriasContas;
import controllers.exceptions.NonexistentEntityException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author allanneves
 */
public class CategoryPageController implements Initializable {

    @FXML
    private TextArea txtDescription;
    @FXML
    private ToggleButton togglePositive;
    @FXML
    private Button saveButton;
    
    private boolean toogleChosen = false;
    
    ObservableList<CategoriasContas> listaCategorias;
    CategoriaContaDAO banco;
    @FXML
    private TableView<CategoriasContas> tabela;
    @FXML
    private TableColumn<CategoriasContas, Integer> colCod;
    @FXML
    private TableColumn<CategoriasContas, String> colDesc;
    @FXML
    private TableColumn<CategoriasContas, String> colPos;
    @FXML
    private Button btnNew;
    
    private CategoriasContas selectedCategory;
    @FXML
    private AnchorPane editPane;
    @FXML
    private Button deleteButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            banco = new CategoriaContaDAO();
            listaCategorias = FXCollections.observableArrayList(banco.consultar());
            tabela.setItems(listaCategorias);
            colCod.setCellValueFactory(
                    new PropertyValueFactory<>("codigo")
            );
            colDesc.setCellValueFactory(
                    new PropertyValueFactory<>("descricao")
            );
            colPos.setCellValueFactory(
                    new PropertyValueFactory<>("positiva")
            );
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }    

    @FXML
    private void tooglePositive(ActionEvent event) {
        this.toogleChosen = !this.toogleChosen;
        
        if (this.toogleChosen == true) {
            this.togglePositive.setText("Sim");
        } else {
            this.togglePositive.setText("Nâo");
        }
    }

    @FXML
    private void save(ActionEvent event) {
        try {
            banco = new CategoriaContaDAO();
            
            this.selectedCategory.setDescricao(this.txtDescription.getText());
            this.selectedCategory.setPositiva(this.toogleChosen);
            
            banco.inserir(this.selectedCategory);
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Informações inválidas", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    @FXML
    private void newCategoryAdd(ActionEvent event) {
        this.selectedCategory = new CategoriasContas();
        this.editPane.setVisible(true);
    }

    @FXML
    private void delete(ActionEvent event) {
        if (this.selectedCategory.getCodigo() == null) {
            JOptionPane.showMessageDialog(null, "Não é possível deletar.");
        } else {
            
        }
    }
    
}
