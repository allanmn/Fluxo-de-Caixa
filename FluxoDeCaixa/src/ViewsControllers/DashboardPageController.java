/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewsControllers;

import DAO.CategoriaContaDAO;
import controllers.exceptions.NonexistentEntityException;
import entidades.CategoriasContas;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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
    private Button noFilterButton;
    @FXML
    private DatePicker dateFilter;
    
    private CategoriaContaDAO category_service;
    private ObservableList<CategoriasContas> categorias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.dateFilter.setValue(LocalDate.now());
        
        this.category_service = new CategoriaContaDAO();
        try {
            categorias = FXCollections.observableArrayList(category_service.consultar());
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
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

    @FXML
    private void openPaymentsPage(ActionEvent event) {
    }

    @FXML
    private void deselectCategory(ActionEvent event) {
    }
    
}
