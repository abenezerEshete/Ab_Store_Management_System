package controller;

import Utility.FieldValues;
import Utility.HardCodedValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
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
import model.Purchase;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class PurchaseCon {


    List<Purchase> purchasedProductList = FXCollections.observableArrayList();
    int            currentPage          = 1;
    int            totalRows            = 0;
    int            totalPage            = 1;
    String         qText                = "";
    String         searchquery          = "";
    int            pos;
    @FXML
    private AnchorPane                    invoice;
    @FXML
    private Label                         countList;
    @FXML
    private JFXTextField                  searchText;
    @FXML
    private JFXButton                     productTypeSearch;
    @FXML
    private TableView<Purchase>           purchasedTable;
    @FXML
    private TableColumn<Purchase, String> productId;
    @FXML
    private TableColumn<Purchase, String> productNumber;
    @FXML
    private TableColumn<Purchase, String> productName;
    @FXML
    private TableColumn<Purchase, String> productType;
    @FXML
    private TableColumn<Purchase, String> productGrade;
    @FXML
    private TableColumn<Purchase, String> productModel;
    @FXML
    private TableColumn<Purchase, String> productSize;
    @FXML
    private TableColumn<Purchase, String> productBrand;
    @FXML
    private TableColumn<Purchase, String> productAmount;
    @FXML
    private TableColumn<Purchase, String> productCostPrice;
    @FXML
    private TableColumn<Purchase, String> totalCost;
    @FXML
    private TableColumn<Purchase, String> date;
    @FXML
    private TableColumn<Purchase, String> supplier;
    @FXML
    private TableColumn<Purchase, String> invoiceNumber;
    @FXML
    private TableColumn<Purchase, String> purchasedBy;
    @FXML
    private JFXButton                     next;
    @FXML
    private Label                         itemPage;
    @FXML
    private JFXButton                     back;

    @FXML
    private void initialize() throws IOException {


        currentPage = 1;

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNumber.setCellValueFactory(cf -> {
            return new SimpleStringProperty((cf.getTableView().getItems().indexOf(cf.getValue()) + 1) + "");
        });
        productName.setCellValueFactory(cd -> {
            if (cd.getValue().getProductType() != null)
                return new SimpleStringProperty(cd.getValue().getProductType().getName());
            return new SimpleStringProperty("");
        });
        productType.setCellValueFactory(cd -> {
            if (cd.getValue().getProductType() != null)
                return new SimpleStringProperty(cd.getValue().getProductType().getType());
            return new SimpleStringProperty("");
        });
        productGrade.setCellValueFactory(cd -> {
            if (cd.getValue().getProductType() != null)
                return new SimpleStringProperty(cd.getValue().getProductType().getGrade());
            return new SimpleStringProperty("");
        });
        productModel.setCellValueFactory(cd -> {
            if (cd.getValue().getProductType() != null)
                return new SimpleStringProperty(cd.getValue().getProductType().getModel());
            return new SimpleStringProperty("");
        });
        productSize.setCellValueFactory(cd -> {
            if (cd.getValue().getProductType() != null)
                return new SimpleStringProperty(cd.getValue().getProductType().getSize());
            return new SimpleStringProperty("");
        });
        productBrand.setCellValueFactory(cd -> {
            if (cd.getValue().getProductType() != null)
                return new SimpleStringProperty(cd.getValue().getProductType().getBrand());
            return new SimpleStringProperty("");
        });
        supplier.setCellValueFactory(cd -> {
            if (cd.getValue().getSupplier() != null)
                return new SimpleStringProperty(cd.getValue().getSupplier().getName());
            return new SimpleStringProperty("");
        });
        purchasedBy.setCellValueFactory(cd -> {
            if (cd.getValue().getPurchasedBy() != null)
                return new SimpleStringProperty(cd.getValue().getPurchasedBy().getName());
            return new SimpleStringProperty("");
        });
        productAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        productCostPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalCost.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        invoiceNumber.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));

        purchasedTable.styleProperty().set("-fx-font-size:14");

        loadData(currentPage);

    }

    private void loadData(int position) {
        this.pos = position;

        try {

            int pageLimit = HardCodedValue.PAGELIMIT;
            String formatedSearchQuery = "";
            if (!searchquery.isEmpty()) formatedSearchQuery = " where productType.name like '%" + searchquery + "%'";
            // get row count
            Query<Purchase> countQuery = Main.getSession().createQuery(
                    " from Purchase  " + formatedSearchQuery);
            int totalRows = countQuery.list().size();
            totalPage = totalRows / pageLimit;
            if (totalRows % pageLimit != 0)
                totalPage++;
            itemPage.setText(" " + currentPage + "/" + totalPage);

            countList.setText("Purchased (" + totalRows + ")");
            // get list of tasks
            pos--;
            int startlimit = pos * pageLimit;
            Query<Purchase> purchaseQuery = Main.getSession().createQuery(
                    "from Purchase " + formatedSearchQuery + " order by updateDateTime desc ");
            purchaseQuery.setFirstResult(startlimit);
            purchaseQuery.setMaxResults(pageLimit);

            purchasedProductList = purchaseQuery.getResultList();
            purchasedTable.getItems().clear();
            purchasedTable.getItems().addAll(purchasedProductList);


        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
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
        Purchase prodType = purchasedTable.getSelectionModel().getSelectedItem();
        try {
            Stage stage = new Stage();
            EditPurchase typeController = new EditPurchase();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane anchorPane = loader.load(getClass().getResource(HardCodedValue.editPurchaseLayout).openStream());
            typeController = loader.getController();

            System.out.print("output--------" + prodType.getId());

            typeController.setPurchase(prodType);
            typeController.setPurchaseID(prodType.getId());
            if (prodType.getProductType() != null) {
                typeController.setNameField(prodType.getProductType().getName());
                typeController.setType(prodType.getProductType().getType());
                typeController.setGrade(prodType.getProductType().getGrade());
                typeController.setModel(prodType.getProductType().getModel());
                typeController.setSize(prodType.getProductType().getSize());
                typeController.setBrand(prodType.getProductType().getBrand());
                typeController.setUnitSellingPrice(prodType.getProductType().getSellingPrice() + "");
            }
            typeController.setAmount(prodType.getAmount() + "");
            typeController.setInvoiceNumber(prodType.getInvoiceNumber());
            typeController.setTotalCostPrice(prodType.getTotalPrice() + "");
            typeController.setUnitCostPrice(prodType.getUnitCostPrice() + "");

            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(purchasedTable.getScene().getWindow());
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
