/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewsControllers;

import DAO.FluxoCaixaDAO;
import Entidades.FluxoCaixa;
<<<<<<< Updated upstream
=======
import Entidades.Pagamento;
import Entidades.SubCategorias;
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import controllers.exceptions.NonexistentEntityException;
import helpers.Helper;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.JOptionPane;

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

    private ObservableList<FluxoCaixa> listaFluxoCaixa;

<<<<<<< Updated upstream
=======
    private ObservableList<CategoriasContas> listaCategoria;
    private ObservableList<SubCategorias> lista_sub_categoria;

    private ObservableList<Pagamento> listaPagamento;
    @FXML
    private ComboBox<SubCategorias> sub_category;

>>>>>>> Stashed changes
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.banco = new FluxoCaixaDAO();
            this.selectedModel = new FluxoCaixa();

            listaFluxoCaixa = FXCollections.observableArrayList(banco.consultar());
<<<<<<< Updated upstream
=======
            listaCategoria = FXCollections.observableArrayList(categoria_service.consultar());
            System.out.println(categoria_service.consultar(1).getSubCategoriasCollection());
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

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

            this.table.setItems(listaFluxoCaixa);
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(FluxoCaixaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void save() {
        Date converted_date ;
        Double value_converted;
        try {
            converted_date = Date.from(this.date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            value_converted = Double.parseDouble(this.value.getText());
        }catch(NullPointerException null_ex){
            JOptionPane.showMessageDialog(null, "Valores Não foram informados");
            return;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return;
        }

        this.selectedModel = new FluxoCaixa();
        this.selectedModel.setDataOcorrencia(converted_date);
        this.selectedModel.setDescricao(this.description.getText());
        this.selectedModel.setFormaPagto(1);
        this.selectedModel.setValor(value_converted);

        try {
            this.banco.inserir(this.selectedModel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        this.flushItems();

    }

    @FXML
    public void update() {
        Date converted_date = Helper.convertToDate(this.date.getValue());
        Double value_converted = Double.parseDouble(this.value.getText());
        FluxoCaixa updated_model = new FluxoCaixa(
                this.selectedModel.getId(),
                converted_date,
                this.description.getText(),
                value_converted,
                1
        );
        try {
            banco.editar(updated_model);
            this.flushItems();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @FXML
    private void destroy() {
        try {
            banco.excluir(this.selectedModel.getId());
            this.flushItems();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void clearSelection() {
        this.destroy.setDisable(true);
        this.edit.setDisable(true);
        this.table.getSelectionModel().select(0);
        this.selectedModel = new FluxoCaixa();
    }

    public void setValues(FluxoCaixa model) {
        if (model == null) {
            return;
        }
        this.destroy.setDisable(false);
        this.edit.setDisable(false);
        this.selectedModel = model;

        this.description.setText(this.selectedModel.getDescricao());
        this.value.setText(Double.toString(this.selectedModel.getValor()));
        this.date.setValue(Helper.convertToLocalDate(this.selectedModel.getDataOcorrencia()));
    }

    public void flushItems() {
        this.table.getItems().clear();
        try {
            listaFluxoCaixa = FXCollections.observableArrayList(banco.consultar());
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            this.table.setItems(listaFluxoCaixa);
        }
    }

    @FXML
    private void checkSubCategory(ActionEvent event) {
        System.out.println(this.category.getValue().getSubCategoriasCollection());
        this.sub_category.setDisable(false);
        this.lista_sub_categoria = FXCollections.observableArrayList(this.category.getValue().getSubCategoriasCollection());
    }
}
