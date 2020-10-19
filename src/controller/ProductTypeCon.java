package controller;

import Utility.FieldValues;
import Utility.HardCodedValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import model.ProductType;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class ProductTypeCon {


    List<ProductType> productTypes = FXCollections.observableArrayList();
    int               currentPage  = 1;
    int               totalRows    = 0;
    int               totalPage    = 1;
    String            qText        = "";
    String            searchquery  = "";
    int               pos;
    @FXML
    private AnchorPane                       invoice;
    @FXML
    private JFXComboBox<?>                   searchBy;
    @FXML
    private JFXTextField                     searchText;
    @FXML
    private JFXButton                        productTypeSearch;
    @FXML
    private JFXButton                        addprodut;
    @FXML
    private TableView<ProductType>           productTypeTable;
    @FXML
    private TableColumn<ProductType, String> productId;
    @FXML
    private TableColumn<ProductType, String> productNumber;
    @FXML
    private TableColumn<ProductType, String> productName;
    @FXML
    private TableColumn<ProductType, String> productType;
    @FXML
    private TableColumn<ProductType, String> productGrade;
    @FXML
    private TableColumn<ProductType, String> productModel;
    @FXML
    private TableColumn<ProductType, String> productSize;
    @FXML
    private TableColumn<ProductType, String> productBrand;
    @FXML
    private TableColumn<ProductType, String> productSellingPrice;
    @FXML
    private TableColumn<ProductType, String> productSellingMeasurment;
    @FXML
    private TableColumn<ProductType, String> productsellingAmountRatio;
    @FXML
    private JFXButton                        next;
    @FXML
    private Label                            itemPage;
    @FXML
    private Label                            countList;
    @FXML
    private JFXButton                        back;

    @FXML
    private void initialize() throws IOException {


        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNumber.setCellValueFactory(cf -> {
            return new SimpleStringProperty((cf.getTableView().getItems().indexOf(cf.getValue()) + 1) + "");
        });
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productType.setCellValueFactory(new PropertyValueFactory<>("type"));
        productGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        productModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        productSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        productBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        productSellingPrice.setCellValueFactory(new PropertyValueFactory<>("SellingPrice"));
        productSellingMeasurment.setCellValueFactory(new PropertyValueFactory<>("sellingMeasurement"));
        productsellingAmountRatio.setCellValueFactory(new PropertyValueFactory<>("sellingAmountRatio"));

        productTypeTable.styleProperty().set("-fx-font-size:14");

        loadData(currentPage);
    }

    private void loadData(int position) {
        this.pos = position;

        try {

            int pageLimit = HardCodedValue.PAGELIMIT;
            String formatedSearchQuery = "";
            if (!searchquery.isEmpty()) formatedSearchQuery = " where name like '%" + searchquery + "%'";
            // get row count
            Query<ProductType> countQuery = Main.getSession().createQuery(
                    " from ProductType  " + formatedSearchQuery);
            int totalRows = countQuery.list().size();
            totalPage = totalRows / pageLimit;
            if (totalRows % pageLimit != 0)
                totalPage++;
            itemPage.setText(" " + currentPage + "/" + totalPage);

            countList.setText("Product Type (" + totalRows + ")");
            // get list of tasks
            pos--;
            int startlimit = pos * pageLimit;
            Query<ProductType> productTypeQuery = Main.getSession().createQuery(
                    "from ProductType " + formatedSearchQuery + " order by updateDateTime desc ");
            productTypeQuery.setFirstResult(startlimit);
            productTypeQuery.setMaxResults(pageLimit);

            List<ProductType> productTypeList = productTypeQuery.getResultList();
            productTypeTable.getItems().clear();
            productTypeTable.getItems().addAll(productTypeList);


        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }

    }


    @FXML
    void addproduct(ActionEvent event) {

        System.out.println("please add product Type");
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane anchorPane = loader.load(getClass().getResource(HardCodedValue.addProductTypeLayout).openStream());

            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(addprodut.getScene().getWindow());
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.setOnCloseRequest(event1 -> {

                System.out.print("On close action");
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
        ProductType prodType = productTypeTable.getSelectionModel().getSelectedItem();

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane anchorPane = loader.load(getClass().getResource(HardCodedValue.editProductTypeLayout).openStream());

            EditProductType typeController = loader.getController();
            typeController.setProductType(prodType);
            typeController.setName(prodType.getName());
            typeController.setType(prodType.getType());
            typeController.setGrade(prodType.getGrade());
            typeController.setModel(prodType.getModel());
            typeController.setSize(prodType.getSize());
            typeController.setBrand(prodType.getBrand());
            typeController.setSellingPrice(prodType.getSellingPrice() + "");
            typeController.setSellingMeasurment(prodType.getSellingMeasurment() + "");
            typeController.setUnitToSellingMeasurment(prodType.getSellingAmountRatio()+"");
            typeController.setHasBroker(prodType.isHasBroker());
            typeController.setOneMeasurePrice(prodType.getMeasurePrice() + "");

            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(addprodut.getScene().getWindow());
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.setOnHidden(event -> {

                loadData(currentPage);
            });


            stage.setOnCloseRequest(event1 -> {
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
