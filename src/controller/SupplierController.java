package controller;

import Utility.FieldValues;
import Utility.HardCodedValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import model.Customer;
import model.Supplier;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class SupplierController {

    List<Supplier> supplierList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane invoice;
    @FXML
    private JFXComboBox<?> searchBy;
    @FXML
    private JFXTextField searchText;
    @FXML
    private JFXButton productTypeSearch;
    @FXML
    private JFXButton addSupplier;
    @FXML
    private Label countList;
    @FXML
    private TableView<Supplier> supplierTable;
    @FXML
    private TableColumn<?, ?> supplierId;
    @FXML
    private TableColumn<?, ?> supplierNumber;
    @FXML
    private TableColumn<?, ?> supplierName;
    @FXML
    private TableColumn<?, ?> supplierAddress;
    @FXML
    private TableColumn<?, ?> supplierPhone;
    @FXML
    private TableColumn<?, ?> supplierTin;
    @FXML
    private JFXButton next;
    @FXML
    private Label itemPage;
    @FXML
    private JFXButton back;

    int                currentPage      = 1;
    int                totalRows        = 0;
    int                totalPage        = 1;
    String             qText            = "";
    String             searchquery      = "";
    int                pos;

    @FXML
    private void initialize() throws IOException {



        supplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        supplierNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        supplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplierAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        supplierPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        supplierTin.setCellValueFactory(new PropertyValueFactory<>("tin"));
        supplierTable.styleProperty().set("-fx-font-size:14");

        loadData(currentPage);
    }
    private void loadData(int position) {
        this.pos = position;

        try {

            int pageLimit = HardCodedValue.PAGELIMIT;
            String formatedSearchQuery = "";
            if (!searchquery.isEmpty()) formatedSearchQuery = " where name like '%" + searchquery + "%'";
            // get row count
            Query<Customer> countQuery = Main.getSession().createQuery(
                    " from Supplier  " + formatedSearchQuery);
            int totalRows = countQuery.list().size();
            totalPage = totalRows / pageLimit;
            if (totalRows % pageLimit != 0)
                totalPage++;
            itemPage.setText(" " + currentPage + "/" + totalPage);

            countList.setText("Supplier (" + totalRows + ")");
            // get list of tasks
            pos--;
            int startlimit = pos * pageLimit;
            Query<Supplier> storeProductQuery = Main.getSession().createQuery(
                    "from Supplier " + formatedSearchQuery + " order by updateDateTime desc ");
            storeProductQuery.setFirstResult(startlimit);
            storeProductQuery.setMaxResults(pageLimit);

             supplierList= storeProductQuery.getResultList();
            supplierTable.getItems().clear();
            supplierTable.getItems().addAll(supplierList);


        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }

    }

    @FXML
    void addSupplier(ActionEvent event) {

        System.out.println("please add product Type");
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane anchorPane = loader.load(getClass().getResource(HardCodedValue.addSupplierLayout).openStream());


            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(addSupplier.getScene().getWindow());
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.setOnCloseRequest(event1 -> {
                loadData(currentPage);
            });
            stage.setOnHidden(event3 -> {
                loadData(currentPage);
            });



            stage.initStyle(StageStyle.DECORATED);
            stage.showAndWait();


        } catch (Exception e) {

            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }

    @FXML
    void backpage(ActionEvent event) throws IOException {
        if (currentPage > 1) {
            currentPage--;
            loadData(currentPage);
            itemPage.setText(" " + currentPage + "/" + totalPage);

        }
    }

    @FXML
    void getSelectedItem(MouseEvent event) {

        if (event.getClickCount() == 2)
            openEdit();

    }

    @FXML
    void onkeyPresed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER)
            openEdit();

    }

    private void openEdit() {
        Supplier prodType = supplierTable.getSelectionModel().getSelectedItem();
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane anchorPane = loader.load(getClass().getResource(HardCodedValue.editSupplierLayout).openStream());

            System.out.print("output--------" + prodType.getName());
            EditSupplierController typeController = loader.getController();
            typeController.setSupplier(prodType);
            typeController.setName(prodType.getName());
            typeController.setAddress(prodType.getAddress());
            typeController.setPhone(prodType.getPhone());
            typeController.setTin(prodType.getTin());

            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(supplierTable.getScene().getWindow());
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);

            stage.setOnCloseRequest(event1 -> {
                loadData(currentPage);
            });
            stage.setOnHidden(event3 -> {
                loadData(currentPage);
            });



            stage.initStyle(StageStyle.DECORATED);
            stage.showAndWait();


        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }


    }


    @FXML
    void nextload(ActionEvent event) throws IOException {
        if (currentPage < totalPage) {
            currentPage++;
            loadData(currentPage);
            itemPage.setText(" " + currentPage + "/" + totalPage);
        }
    }

    @FXML
    void productSearch(ActionEvent event) {
        currentPage = 1;
        searchquery = searchText.getText();
        loadData(currentPage);
    }




}
