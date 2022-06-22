/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package Main;

import bd.DatabaseController;
import bd.Leitor;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author allanneves
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        DatabaseController bd = new DatabaseController();
        
        DatabaseController.createDB();
        DatabaseController.createTables();
        
        Parent root = FXMLLoader.load(getClass().getResource("/Views/FluxoCaixa.fxml"));
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Fluxo de Caixa");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
