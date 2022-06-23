/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewsControllers;

import DAO.FluxoCaixaDAO;
import entidades.FluxoCaixa;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author beraldo
 */
public class FluxoCaixaController implements Initializable {

    @FXML
    private Button create;
    @FXML
    private TextField description;
    @FXML
    private TextField value;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<?> payment_method;
    @FXML
    private ComboBox<?> category;
    @FXML
    private TableView<FluxoCaixa> table;
    @FXML
    private Button destroy;
    @FXML
    private Button edit;

    private FluxoCaixaDAO banco;

    private FluxoCaixa selectedModel;
    
    private FluxoCaixa model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.banco = new FluxoCaixaDAO();
        this.selectedModel = new FluxoCaixa();

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn descriptionColumn = new TableColumn("Descrição");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn valueColumn = new TableColumn("valor");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

        TableColumn dateColumn = new TableColumn("Data de Ocorrencia");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dataOcorrencia"));

        TableColumn paymentColumn = new TableColumn("Pagamento");
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("formaPagto"));

        this.table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            setValues(newSelection);
        });

        this.table.getColumns().addAll(idColumn, dateColumn, descriptionColumn, paymentColumn);

    }

    @FXML
    public void save() {
        Date converted_date = Date.from(this.date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Double value_converted = Double.parseDouble(this.value.getText());
        
        this.selectedModel.setDataOcorrencia(converted_date);
        this.selectedModel.setDescricao(this.description.getText());
        this.selectedModel.setFormaPagto(1);
        this.selectedModel.setValor(value_converted);

        try {
            this.banco.inserir(this.selectedModel);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        this.table.getItems().add(this.selectedModel);

    }

    @FXML
    public void update() {
        int index = findModelIndex(this.selectedModel);
        Date converted_date = Date.from(this.date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Double value_converted = Double.parseDouble(this.value.getText());
        FluxoCaixa new_model = new FluxoCaixa(
                this.selectedModel.getId(),
                converted_date,
                this.description.getText(),
                value_converted,
                1
        );
        this.table.getItems().set(index, new_model);
    }

    @FXML
    private void destroy(ActionEvent event) {
        System.out.println("teste");
        int index = findModelIndex(this.selectedModel);
        this.table.getItems().remove(index);
    }

    public int findModelIndex(FluxoCaixa model) {
        int index = 0;
        for (FluxoCaixa item : this.table.getItems()) {
            if (item.getId() == model.getId()) {
                index = this.table.getItems().indexOf(item);
                break;
            }
        }
        return index;
    }

    public void clearSelection() {
        this.destroy.setDisable(true);
        this.edit.setDisable(true);
        this.table.getSelectionModel().select(0);
        this.selectedModel = null;
    }

    public void setValues(FluxoCaixa model) {
        this.destroy.setDisable(false);
        this.edit.setDisable(false);
        this.selectedModel = model;
    }

}
