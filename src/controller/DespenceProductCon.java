package controller;

import Utility.FieldValues;
import Utility.FileAccessControl;
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
import model.DespenceProduct;
import model.StoreProduct;
import org.hibernate.query.Query;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class DespenceProductCon {

    List<DespenceProduct> despenceProductList = FXCollections.observableArrayList();
    int                   currentPage         = 1;
    int                   totalRows           = 0;
    int                   totalPage           = 1;
    String                qText               = "";
    String                searchquery         = "";
    int                   pos;
    @FXML
    private AnchorPane                           invoice;
    @FXML
    private JFXComboBox<?>                       searchBy;
    @FXML
    private JFXTextField                         searchText;
    @FXML
    private JFXButton                            productTypeSearch;
    @FXML
    private JFXButton                            addprodut;
    @FXML
    private Label                                countList;
    @FXML
    private TableView<DespenceProduct>           productTypeTable;
    @FXML
    private TableColumn<DespenceProduct, String> productId;
    @FXML
    private TableColumn<DespenceProduct, String> productNumber;
    @FXML
    private TableColumn<DespenceProduct, String> productName;
    @FXML
    private TableColumn<DespenceProduct, String> productType;
    @FXML
    private TableColumn<DespenceProduct, String> productGrade;
    @FXML
    private TableColumn<DespenceProduct, String> productModel;
    @FXML
    private TableColumn<DespenceProduct, String> productSize;
    @FXML
    private TableColumn<DespenceProduct, String> productBrand;
    @FXML
    private TableColumn<DespenceProduct, String> productAmount;
    @FXML
    private TableColumn<DespenceProduct, String> productSellingPrice;
    @FXML
    private JFXButton                            next;
    @FXML
    private Label                                itemPage;
    @FXML
    private JFXButton                            back;

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
        productSellingPrice.setCellValueFactory(cd -> {
            if (cd.getValue().getProductType() != null)
                return new SimpleStringProperty(cd.getValue().getProductType().getSellingPrice() + "");
            return new SimpleStringProperty("");
        });
        productAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        productTypeTable.styleProperty().set("-fx-font-size:14");

        loadData(currentPage);

        JSONObject loginedUser = new JSONObject(new FileAccessControl().readfromFileStringBuffer(new HardCodedValue().USERCATCHFILE).toString());
        if (!loginedUser.optString("type").isEmpty()) {

            if (loginedUser.optString("type").equalsIgnoreCase("sales")) {
                addprodut.setVisible(false);

            }

        }

    }

    private void loadData(int position) {
        this.pos = position;

        try {

            int pageLimit = HardCodedValue.PAGELIMIT;
            String formatedSearchQuery = "";
            if (!searchquery.isEmpty()) formatedSearchQuery = " where productType.name like '%" + searchquery + "%'";
            // get row count
            Query<StoreProduct> countQuery = Main.getSession().createQuery(
                    " from DespenceProduct  " + formatedSearchQuery);
            int totalRows = countQuery.list().size();
            totalPage = totalRows / pageLimit;
            if (totalRows % pageLimit != 0)
                totalPage++;
            itemPage.setText(" " + currentPage + "/" + totalPage);

            countList.setText("Despence Product (" + totalRows + ")");
            // get list of tasks
            pos--;
            int startlimit = pos * pageLimit;
            Query<DespenceProduct> storeProductQuery = Main.getSession().createQuery(
                    "from DespenceProduct " + formatedSearchQuery + " order by updateDateTime desc ");
            storeProductQuery.setFirstResult(startlimit);
            storeProductQuery.setMaxResults(pageLimit);

            despenceProductList = storeProductQuery.getResultList();
            productTypeTable.getItems().clear();
            productTypeTable.getItems().addAll(despenceProductList);


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
            AnchorPane anchorPane = loader.load(getClass().
                    getResource(HardCodedValue.moveProductToDespenceTypeLayout).openStream());


            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(addprodut.getScene().getWindow());
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
        DespenceProduct prodType = productTypeTable.getSelectionModel().getSelectedItem();
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane anchorPane = loader.load(getClass().getResource(HardCodedValue.editDespenceProductTypeLayout).openStream());

            EditProductDespence typeController = loader.getController();
            if (prodType.getProductType() != null) {
                typeController.setId(prodType);
                typeController.setNameField(prodType.getProductType().getName());
                typeController.setType(prodType.getProductType().getType());
                typeController.setGrade(prodType.getProductType().getGrade());
                typeController.setModel(prodType.getProductType().getModel());
                typeController.setSize(prodType.getProductType().getSize());
                typeController.setBrand(prodType.getProductType().getBrand());
                typeController.setUnitSellingPrice(prodType.getProductType().getSellingPrice() + "");
            }
            typeController.setAmount(prodType.getAmount() + "");

            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(addprodut.getScene().getWindow());
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
