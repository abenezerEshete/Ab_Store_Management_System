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
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class CustomerController {


    @FXML
    private AnchorPane          invoice;
    @FXML
    private JFXComboBox<?>      searchBy;
    @FXML
    private JFXTextField        searchText;
    @FXML
    private JFXButton           productTypeSearch;
    @FXML
    private JFXButton           addCustomer;
    @FXML
    private Label               countList;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<?, ?>   customerId;
    @FXML
    private TableColumn<?, ?>   customerNumber;
    @FXML
    private TableColumn<?, ?>   customerName;
    @FXML
    private TableColumn<?, ?>   customerAddress;
    @FXML
    private TableColumn<?, ?>   customerPhone;
    @FXML
    private TableColumn<?, ?>   customerTin;
    @FXML
    private JFXButton           next;
    @FXML
    private Label               itemPage;
    @FXML
    private JFXButton           back;

    List<Customer> customers   = FXCollections.observableArrayList();
    int                currentPage      = 1;
    int                totalRows        = 0;
    int                totalPage        = 1;
    String             qText            = "";
    String             searchquery      = "";
    int                pos;

    @FXML
    private void initialize() throws IOException {




        customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTin.setCellValueFactory(new PropertyValueFactory<>("tin"));
        customerTable.styleProperty().set("-fx-font-size:14");


//
//
//        searchBy.getItems().addAll("Name","batchNo","invoice Number","payment type");
//        searchBy.setValue("Name");

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
                    " from Customer  " + formatedSearchQuery);
            int totalRows = countQuery.list().size();
            totalPage = totalRows / pageLimit;
            if (totalRows % pageLimit != 0)
                totalPage++;
            itemPage.setText(" " + currentPage + "/" + totalPage);

            countList.setText("Customer (" + totalRows + ")");
            // get list of tasks
            pos--;
            int startlimit = pos * pageLimit;
            Query<Customer> storeProductQuery = Main.getSession().createQuery(
                    "from Customer " + formatedSearchQuery + " order by updateDateTime desc ");
            storeProductQuery.setFirstResult(startlimit);
            storeProductQuery.setMaxResults(pageLimit);

            customers = storeProductQuery.getResultList();
            customerTable.getItems().clear();
            customerTable.getItems().addAll(customers);


        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }

    }

    @FXML
    void addCustomer(ActionEvent event) {

        System.out.println("please add product Type");
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane anchorPane = loader.load(getClass().getResource(HardCodedValue.addCustomerLayout).openStream());


            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(addCustomer.getScene().getWindow());
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
        Customer prodType = customerTable.getSelectionModel().getSelectedItem();
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane anchorPane = loader.load(getClass().getResource(HardCodedValue.editCustomerLayout).openStream());

            EditCustomerController typeController = loader.getController();
            typeController.setCustomer(prodType);
            typeController.setName(prodType.getName());
            typeController.setAddress(prodType.getAddress());
            typeController.setPhone(prodType.getPhone());
            typeController.setTin(prodType.getTin());

            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(customerTable.getScene().getWindow());
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
