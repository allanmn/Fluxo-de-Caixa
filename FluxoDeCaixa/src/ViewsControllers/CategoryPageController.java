/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewsControllers;

import DAO.CategoriaContaDAO;
import Exceptions.ValidationException;
import Entidades.CategoriasContas;
import controllers.exceptions.NonexistentEntityException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.layout.HBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

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
    private HBox editPane;
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
            colPos.setCellValueFactory(cellData -> {
                boolean pos = cellData.getValue().getPositiva();
                String posString;
                if (pos == true) {
                    posString = "Positiva";
                }
                else {
                    posString = "Negativa";
                }
                return new ReadOnlyStringWrapper(posString);
            });
            
            this.tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                setValues(newSelection);
            });
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }    
    
    @FXML
    private void tooglePositive(ActionEvent event) {
        this.toogleChosen = !this.toogleChosen;
        
        setToogleText();
    }
    
    private void setToogleText() {
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
            
            if (this.selectedCategory.getCodigo() != null) {
                banco.editar(selectedCategory);
            } else {
                banco.inserir(this.selectedCategory);
            }
            
            JOptionPane.showMessageDialog(null, "Categoria salva com sucesso.", "Salvo com sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            this.selectedCategory = new CategoriasContas();
            
            listaCategorias = FXCollections.observableArrayList(banco.consultar());
            
            tabela.getItems().clear();
            tabela.setItems(listaCategorias);
            
            this.cleanInputs();
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(null, ex.getErrorMessage(), "Informações inválidas", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    @FXML
    private void newCategoryAdd(ActionEvent event) {
        this.cleanInputs();
        this.selectedCategory = new CategoriasContas();
        this.editPane.setVisible(true);
        this.deleteButton.setVisible(false);
    }

    @FXML
    private void delete(ActionEvent event) {
        if (this.selectedCategory.getCodigo() == null) {
            JOptionPane.showMessageDialog(null, "Não é possível deletar.");
        } else {
            
            UIManager.put("OptionPane.noButtonText", "Não");
            UIManager.put("OptionPane.yesButtonText", "Sim");
            
            int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Cuidado", JOptionPane.YES_NO_OPTION);
            
            if (result == 0) {
                try {
                    banco = new CategoriaContaDAO();
                
                    banco.excluir(selectedCategory);
                
                    JOptionPane.showMessageDialog(null, "Categoria excluída com sucesso.", "Categoria excluída",JOptionPane.INFORMATION_MESSAGE);
                    
                    this.selectedCategory = new CategoriasContas();
            
                    listaCategorias = FXCollections.observableArrayList(banco.consultar());
                    
                    tabela.setItems(listaCategorias);
                    
                    cleanInputs();
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void cleanInputs() {
        this.txtDescription.setText("");
        this.toogleChosen = false;
        this.setToogleText();
        this.editPane.setVisible(false);
    }

    private void setValues(CategoriasContas newSelection) {
        if (newSelection != null) {
            this.selectedCategory = newSelection;
            this.editPane.setVisible(true);
            this.txtDescription.setText(this.selectedCategory.getDescricao());
            this.toogleChosen = this.selectedCategory.getPositiva();
            this.setToogleText();
            this.deleteButton.setVisible(true);
        }
    }
    
}
