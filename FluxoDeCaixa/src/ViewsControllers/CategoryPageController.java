/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewsControllers;

import DAO.CategoriaContaDAO;
import DAO.SubCategoriaDAO;
import Exceptions.ValidationException;
import Entidades.CategoriasContas;
import Entidades.SubCategorias;
import controllers.exceptions.NonexistentEntityException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    ObservableList<SubCategorias> listaSubCategorias;
    CategoriaContaDAO banco;
    SubCategoriaDAO subCategoryService;
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
    
    private SubCategorias selectedSubCategory;
    @FXML
    private HBox editPane;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField txtSubCat;
    @FXML
    private Button btnAddSubCat;
    @FXML
    private ListView<SubCategorias> listSubCat;
    @FXML
    private VBox editSubPane;
    @FXML
    private Button btnDeleteSubCat;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            banco = new CategoriaContaDAO();
            listaCategorias = FXCollections.observableArrayList(banco.consultar());
            System.out.println(listaCategorias);
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
            
            this.listSubCat.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                setSubValues(newSelection);
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
            this.togglePositive.setText("N??o");
        }
    }

    @FXML
    private void save(ActionEvent event) {
        
        try {
            banco = new CategoriaContaDAO();
            
            this.selectedCategory.setDescricao(this.txtDescription.getText());
            this.selectedCategory.setPositiva(this.toogleChosen);            
            if (this.selectedCategory.getCodigo() != null) {
                System.out.println("Editando");
                banco.editar(selectedCategory);
            } else {
                System.out.println("Criando");
                banco.inserir(this.selectedCategory);
            }
            
//            JOptionPane.showMessageDialog(null, "Categoria salva com sucesso.", "Salvo com sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            this.selectedCategory = new CategoriasContas();
            
            listaCategorias = FXCollections.observableArrayList(banco.consultar());
            
            tabela.getItems().clear();
            tabela.setItems(listaCategorias);
            
            this.cleanInputs();
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(null, ex.getErrorMessage(), "Informa????es inv??lidas", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
//            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    @FXML
    private void newCategoryAdd(ActionEvent event) {
        this.cleanInputs();
        this.selectedCategory = new CategoriasContas();
        this.editPane.setVisible(true);
        this.deleteButton.setVisible(false);
        this.listaSubCategorias = FXCollections.observableArrayList(new ArrayList<SubCategorias>());
        this.listSubCat.setCellFactory(param -> new ListCell<SubCategorias> () {
            @Override
            protected void updateItem(SubCategorias item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getDescricao() == null) {
                    setText(null);
                } else {
                    setText(item.getDescricao());
                }
            }
        });
        listSubCat.setItems(listaSubCategorias);
    }

    @FXML
    private void delete(ActionEvent event) {
        if (this.selectedCategory.getCodigo() == null) {
            JOptionPane.showMessageDialog(null, "N??o ?? poss??vel deletar.");
        } else {
            
            UIManager.put("OptionPane.noButtonText", "N??o");
            UIManager.put("OptionPane.yesButtonText", "Sim");
            
            int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Cuidado", JOptionPane.YES_NO_OPTION);
            
            if (result == 0) {
                try {
                    banco = new CategoriaContaDAO();
                
                    banco.excluir(selectedCategory);
                
                    JOptionPane.showMessageDialog(null, "Categoria exclu??da com sucesso.", "Categoria exclu??da",JOptionPane.INFORMATION_MESSAGE);
                    
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
            this.listaSubCategorias = FXCollections.observableArrayList(this.selectedCategory.getSubCategoriasCollection());
            this.listSubCat.setCellFactory(param -> new ListCell<SubCategorias> () {
                @Override
                protected void updateItem(SubCategorias item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getDescricao() == null) {
                        setText(null);
                    } else {
                        setText(item.getDescricao());
                    }
                }
            });
            listSubCat.setItems(listaSubCategorias);
            this.editPane.setVisible(true);
            this.txtDescription.setText(this.selectedCategory.getDescricao());
            this.toogleChosen = this.selectedCategory.getPositiva();
            this.setToogleText();
            this.deleteButton.setVisible(true);
        }
    }
    
    private void setSubValues(SubCategorias newSelection) {
        if (newSelection != null) {
            this.editSubPane.setVisible(true);
            this.selectedSubCategory = newSelection;
            this.txtSubCat.setText(this.selectedSubCategory.getDescricao());
        }
    }

    @FXML
    private void addSubCat(ActionEvent event) {
        if (this.txtSubCat.getText() == null || this.txtSubCat.getText() == "") {
            JOptionPane.showMessageDialog(null, "Digite a descri????o da subcategoria para continuar.");
        } else {
            String desc = this.txtSubCat.getText();
            
            this.selectedSubCategory.setDescricao(desc);
            
            this.subCategoryService = new SubCategoriaDAO();
            
            try {
                this.subCategoryService.editar(this.selectedSubCategory);
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
//                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } finally {
                this.editPane.setVisible(false);
                this.selectedCategory = null;
                this.selectedSubCategory = null;
                this.txtSubCat.setText("");
                this.editSubPane.setVisible(false);
            }
        }
    }

    @FXML
    private void deleteSubCat(ActionEvent event) {
        UIManager.put("OptionPane.noButtonText", "N??o");
        UIManager.put("OptionPane.yesButtonText", "Sim");
            
        int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Cuidado", JOptionPane.YES_NO_OPTION);
            
        if (result == 0) {
            try {
                this.subCategoryService = new SubCategoriaDAO();
                
                subCategoryService.excluir(this.selectedSubCategory);
                
                JOptionPane.showMessageDialog(null, "SubCategoria exclu??da com sucesso.", "Categoria exclu??da",JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } finally {
                this.editPane.setVisible(false);
                this.selectedCategory = null;
                this.selectedSubCategory = null;
                this.txtSubCat.setText("");
                this.editSubPane.setVisible(false);
            }
        }
    }
    
}
