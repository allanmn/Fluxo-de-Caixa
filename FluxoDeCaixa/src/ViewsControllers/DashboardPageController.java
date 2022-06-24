/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewsControllers;

import DAO.CategoriaContaDAO;
import DAO.FluxoCaixaDAO;
import Entidades.CategoriasContas;
import Entidades.FluxoCaixa;
import controllers.exceptions.NonexistentEntityException;
import helpers.Helper;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author allanneves
 */
public class DashboardPageController implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private Menu categories;
    @FXML
    private Menu payments;
    @FXML
    private DatePicker dateFilter;
    
    private CategoriaContaDAO category_service;
    
    private ObservableList<CategoriasContas> categorias;
    
    private FluxoCaixaDAO fluxo_service;
    
    private ObservableList<FluxoCaixa> contas;
    
    private ComboBox<CategoriasContas> cbCategories;
    @FXML
    private TableView<FluxoCaixa> tablecontas;
    
    private CategoriasContas selectedCategoria;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.dateFilter.setValue(LocalDate.now());
        
        TableColumn colDatePagar = new TableColumn("Data de ocorrência");
        colDatePagar.setCellFactory(col -> {
            TableCell<FluxoCaixa, Date> cell = new TableCell<FluxoCaixa, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };
            
            return cell;
        });
        
        TableColumn descriptionColumn = new TableColumn("Descrição");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn valueColumn = new TableColumn("valor");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        
        this.getCategories();
        
        this.getContasPagar();
    }   
    
    private void getCategories() {
        this.category_service = new CategoriaContaDAO();
        try {
            this.categorias = FXCollections.observableArrayList(category_service.consultar());
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        cbCategories.setItems(categorias);
    }
    
    private void openView(String name) throws IOException {
        Parent newView = (Pane) new FXMLLoader().load(getClass().getClassLoader().getResource("./Views/" + name));
        Scene scene = new Scene(newView);
        Stage secondStage = new Stage();
        secondStage.initOwner((Stage) root.getScene().getWindow());
        secondStage.initModality(Modality.WINDOW_MODAL);
        secondStage.setScene(scene);
        secondStage.show();
        secondStage.setOnCloseRequest(e -> secondStage.close());
    }

    @FXML
    private void openCategoryPage(ActionEvent event) {
        try {
            openView("CategoryPage.fxml");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void getContasPagar() {
        LocalDate date = dateFilter.getValue();
        
        this.fluxo_service = new FluxoCaixaDAO();
        
        try {
            this.contas = FXCollections.observableArrayList(
                this.fluxo_service.findByDate(Helper.convertToDate(date))
            );        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void openPaymentsPage(ActionEvent event) {
    }


    @FXML
    private void filterByDate(ActionEvent event) {
        this.getContasPagar();
    }
    
}
