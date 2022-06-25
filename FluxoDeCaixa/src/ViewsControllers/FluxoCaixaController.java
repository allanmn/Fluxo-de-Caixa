/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewsControllers;

import DAO.CategoriaContaDAO;
import DAO.FluxoCaixaDAO;
import Entidades.CategoriasContas;
import Entidades.FluxoCaixa;
import Entidades.Pagamento;
import controllers.exceptions.NonexistentEntityException;
import helpers.Helper;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox<Pagamento> payment_method;
    @FXML
    private ComboBox<CategoriasContas> category;
    @FXML
    private TableView<FluxoCaixa> table;
    @FXML
    private Button destroy;
    @FXML
    private Button edit;

    private FluxoCaixaDAO banco;
    private CategoriaContaDAO categoria_service;

    private FluxoCaixa selectedModel;

    private ObservableList<FluxoCaixa> listaFluxoCaixa;

    private ObservableList<CategoriasContas> listaCategoria;

    private ObservableList<Pagamento> listaPagamento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.banco = new FluxoCaixaDAO();
            this.categoria_service = new CategoriaContaDAO();
            this.selectedModel = new FluxoCaixa();

            listaFluxoCaixa = FXCollections.observableArrayList(banco.consultar());
            listaCategoria = FXCollections.observableArrayList(categoria_service.consultar());
            listaPagamento = FXCollections.observableArrayList(Pagamento.values());

            TableColumn idColumn = new TableColumn("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn descriptionColumn = new TableColumn("Descrição");
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

            TableColumn valueColumn = new TableColumn("valor");
            valueColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

            TableColumn dateColumn = new TableColumn("Data de Ocorrencia");
            dateColumn.setCellFactory(column -> {
                TableCell<FluxoCaixa, Date> cell = new TableCell<FluxoCaixa, Date>() {
                    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(format.format(item));
                        }
                    }
                };

                return cell;
            });
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("dataOcorrencia"));

            TableColumn catColumn = new TableColumn("Categoria");
            catColumn.setCellFactory(column -> {
                TableCell<FluxoCaixa, CategoriasContas> cell = new TableCell<FluxoCaixa, CategoriasContas>() {

                    @Override
                    protected void updateItem(CategoriasContas item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                        } else {
                            setText(item.getDescricao());
                        }
                    }
                };

                return cell;
            });
            catColumn.setCellValueFactory(new PropertyValueFactory<>("codCat"));

            TableColumn paymentColumn = new TableColumn("Pagamento");
            paymentColumn.setCellFactory(column -> {
                TableCell<FluxoCaixa, Integer> cell = new TableCell<FluxoCaixa, Integer>() {

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            Pagamento pgto = Pagamento.values()[item];
                            setText(pgto.getNome());
                        }
                    }
                };

                return cell;
            });
            paymentColumn.setCellValueFactory(new PropertyValueFactory<>("formaPagto"));

            this.table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                setValues(newSelection);
            });

            this.table.getColumns().addAll(idColumn, dateColumn, descriptionColumn,valueColumn, catColumn, paymentColumn);

            this.table.setItems(listaFluxoCaixa);

            this.category.setItems(listaCategoria);

            this.payment_method.setItems(listaPagamento);
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    @FXML
    public void save() {
        Date converted_date;
        Double value_converted;
        try {
            converted_date = Date.from(this.date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            value_converted = Double.parseDouble(this.value.getText());
        } catch (NullPointerException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Valores inválidos informados");
            return;
        }

        this.selectedModel = new FluxoCaixa();
        this.selectedModel.setDataOcorrencia(converted_date);
        this.selectedModel.setDescricao(this.description.getText());
        this.selectedModel.setFormaPagto(1);
        this.selectedModel.setValor(value_converted);
        this.selectedModel.setCodCat(this.category.getValue());

        try {
            this.banco.inserir(this.selectedModel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        this.flushItems();

    }

    @FXML
    public void update() {
        Date converted_date;
        Double value_converted;
        try {
            converted_date = Date.from(this.date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            value_converted = Double.parseDouble(this.value.getText());
        } catch (NullPointerException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Valores Não foram informados");
            return;
        }

        this.selectedModel.setDataOcorrencia(converted_date);
        this.selectedModel.setDescricao(this.description.getText());
        this.selectedModel.setFormaPagto(this.payment_method.getValue().getValor());
        this.selectedModel.setValor(value_converted);
        this.selectedModel.setCodCat(this.category.getValue());

        this.selectedModel.setValor(value_converted);
        this.selectedModel.setCodCat(this.category.getValue());
        try {
            banco.editar(this.selectedModel);
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
        this.category.setValue(this.selectedModel.getCodCat());
        this.payment_method.setValue(Pagamento.values()[this.selectedModel.getFormaPagto()]);
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
}
