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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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

    private ObservableList<FluxoCaixa> contasreceber;

    private ComboBox<CategoriasContas> cbCategories;
    @FXML
    private TableView<FluxoCaixa> tablecontas;

    private CategoriasContas selectedCategoria;
    @FXML
    private PieChart chartContasPorCat;

    private ObservableList<PieChart.Data> chartContasPerCatData;
    @FXML
    private DatePicker dateFilterReceber;
    @FXML
    private TableView<FluxoCaixa> tablecontasreceber;
    @FXML
    private Label totalRecebimentos;
    @FXML
    private Label totalPagar;
    @FXML
    private Label subtotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.dateFilter.setValue(LocalDate.now());
        this.dateFilterReceber.setValue(LocalDate.now());

        this.getCategories();

        this.getContasPagar();

        this.getContasReceber();

        this.getValues();

        setTableContasPagarColumns();
        setTableContasReceberColumns();
    }

    private void getValues() {
        try {
            ObservableList<FluxoCaixa> lista = FXCollections.observableArrayList(this.fluxo_service.consultar());

            Double recebimentos = 0.0;

            Double pagamentos = 0.0;

            for(FluxoCaixa fluxo : lista) {
                if (fluxo.getCodCat().getPositiva() == true) {
                    recebimentos = recebimentos + fluxo.getValor();
                } else {
                    pagamentos = pagamentos + pagamentos;
                }
            }

            Double total = recebimentos - pagamentos;

            this.subtotal.setText(total.toString());
            this.totalPagar.setText(pagamentos.toString());
            this.totalRecebimentos.setText(recebimentos.toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
//            JOptionPane.showMessageDßialog(null, ex.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setTableContasReceberColumns() {
        TableColumn colDatePagar = new TableColumn("Data de ocorrência");

        colDatePagar.setCellFactory(column -> {
            TableCell<FluxoCaixa, Date> cell = new TableCell<FluxoCaixa, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        colDatePagar.setCellValueFactory(new PropertyValueFactory<FluxoCaixa, Date>("dataOcorrencia"));

        TableColumn descriptionColumn = new TableColumn("Descrição");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn valueColumn = new TableColumn("valor");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

        this.tablecontasreceber.getColumns().addAll(colDatePagar, descriptionColumn, valueColumn);
    }

    private void setTableContasPagarColumns() {
        TableColumn colDatePagar = new TableColumn("Data de ocorrência");

        colDatePagar.setCellFactory(column -> {
            TableCell<FluxoCaixa, Date> cell = new TableCell<FluxoCaixa, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        colDatePagar.setCellValueFactory(new PropertyValueFactory<FluxoCaixa, Date>("dataOcorrencia"));

        TableColumn descriptionColumn = new TableColumn("Descrição");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn valueColumn = new TableColumn("valor");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tablecontas.getColumns().addAll(colDatePagar, descriptionColumn, valueColumn);
    }

    private void getCategories() {
        this.category_service = new CategoriaContaDAO();
        try {
            this.categorias = FXCollections.observableArrayList(category_service.consultar());
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        setChartData();
    }

    private void setChartData() {
        ArrayList<PieChart.Data> data = new ArrayList<PieChart.Data>();

        for(CategoriasContas cat : categorias) {
            data.add(new PieChart.Data(cat.getDescricao(), cat.getFluxoCaixaCollection().size()));
        }

        this.chartContasPerCatData = FXCollections.observableArrayList(data);
        this.chartContasPorCat.setData(chartContasPerCatData);
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
        } finally {
            this.tablecontas.setItems(contas);
        }
    }

    private void getContasReceber() {
        LocalDate date = this.dateFilterReceber.getValue();

        this.fluxo_service = new FluxoCaixaDAO();

        try {
            this.contasreceber = FXCollections.observableArrayList(
                    this.fluxo_service.findByDateReceber(Helper.convertToDate(date))
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            this.tablecontasreceber.setItems(contasreceber);
        }
    }

    @FXML
    private void openPaymentsPage(ActionEvent event) {
    }

    @FXML
    private void openSubCategoryPage(ActionEvent event) {
        try {
            openView("SubCategoryPage.fxml");
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            ex.printStackTrace();
        }

    @FXML
    private void filterByDate(ActionEvent event) {
        this.getContasPagar();
    }

    @FXML
    private void filterReceberByDate(ActionEvent event) {
        this.getContasReceber();
    }

}
