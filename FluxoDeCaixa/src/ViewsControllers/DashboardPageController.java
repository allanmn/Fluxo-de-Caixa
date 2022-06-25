/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewsControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private Menu fluxo_caixa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void openFluxoPage(ActionEvent event) {
        try {
            openView("FluxoCaixa.fxml");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

