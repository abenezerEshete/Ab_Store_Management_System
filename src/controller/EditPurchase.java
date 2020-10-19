package controller;

import Utility.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;
import model.ProductType;
import model.Purchase;
import model.Supplier;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


public class EditPurchase {

    AutoCompleteTextField nameField;
    AutoCompleteTextField supplierField;
    List<ProductType>     productTypeList;
    List<Supplier>        supplierList;
    List<String>          productTypeNameList = new ArrayList<>();
    List<String>          suppliernameList    = new ArrayList<>();
    int                   productID           = 0;
    Purchase              purchase;
    JSONArray             inputList           = new JSONArray();
    int                   nameIndex           = -1;
    int                   supplierIndex       = -1;
    int                   purchaseID          = 0;
    ProductType           productType;
    @FXML
    private AnchorPane             addProduct;
    @FXML
    private Label                  notification;
    @FXML
    private HBox                   nameHbox;
    @FXML
    private RequiredFieldValidator nameRequiredValidator;
    @FXML
    private JFXTextField           model;
    @FXML
    private JFXTextField           brand;
    @FXML
    private JFXTextField           grade;
    @FXML
    private JFXTextField           type;
    @FXML
    private JFXTextField           size;
    @FXML
    private JFXTextField           unitSellingPrice;
    @FXML
    private DoubleValidator        sellingPriceNumberValidator;
    @FXML
    private RequiredFieldValidator sellingPriceRequierValidator;
    @FXML
    private JFXTextField           amount;
    @FXML
    private RequiredFieldValidator aountRequierValidator;
    @FXML
    private DoubleValidator        amountNumberValidator;
    @FXML
    private JFXTextField           unitCostPrice;
    @FXML
    private RequiredFieldValidator unitCostPriceRequierValidator;
    @FXML
    private DoubleValidator        unitCostNumberValidator;
    @FXML
    private JFXTextField           totalCostPrice;
    @FXML
    private RequiredFieldValidator totalCostRequierValidator;
    @FXML
    private DoubleValidator        totalCostNumberValidator;
    @FXML
    private HBox                   supplierHbox;
    @FXML
    private JFXTextField           invoiceNumber;
    @FXML
    private RequiredFieldValidator aountRequierValidator1;
    @FXML
    private DoubleValidator        amountNumberValidator1;
    @FXML
    private JFXButton              cancelButton;
    @FXML
    private JFXButton              delete;
    @FXML
    private JFXButton              saveButton;
    @FXML
    private ButtonBar              buttonList;
    @FXML
    private Label                  totalPriceFDsplay;
    private JFXButton              addedList;

    @FXML
    private void initialize() {

        nameFieldSetup();

        supplierFieldSetup();

        nameField.getValidators().add(nameRequiredValidator);
        unitSellingPrice.getValidators().addAll(sellingPriceNumberValidator);
        unitSellingPrice.getValidators().addAll(sellingPriceRequierValidator);
        unitCostPrice.getValidators().add(unitCostPriceRequierValidator);
        unitCostPrice.getValidators().add(unitCostNumberValidator);
        totalCostPrice.getValidators().add(totalCostRequierValidator);
        totalCostPrice.getValidators().add(totalCostNumberValidator);
        amount.getValidators().add(amountNumberValidator);
        amount.getValidators().add(aountRequierValidator);
        //     expDateField.setValue(new LocalDate().);

        JSONObject loginedUser = new JSONObject(new FileAccessControl().readfromFileStringBuffer(HardCodedValue.USERCATCHFILE).toString());
        System.out.println("type=" + loginedUser.optString("type"));
        if (!loginedUser.optString("type").isEmpty()) {

            if (loginedUser.optString("type").equalsIgnoreCase("Finance")) {

                buttonList.getButtons().remove(saveButton);
                buttonList.getButtons().remove(delete);
                delete.setVisible(false);
                supplierField.setEditable(false);
                nameField.setEditable(false);
                amount.setEditable(false);
                totalCostPrice.setEditable(false);
                unitCostPrice.setEditable(false);
                invoiceNumber.setEditable(false);

            }
        }

    }

    private void nameFieldSetup() {
        try {
            nameField = new AutoCompleteTextField();
            nameField.setPrefWidth(250);
            nameField.setMinHeight(30);
            nameField.fontProperty().set(Font.font("System Bold", 14));
            nameField.styleProperty().set("-fx-background-color: #D6EAF8;");
            nameField.promptTextProperty().set("Search product here");
            nameField.labelFloatProperty().set(true);
            nameField.paddingProperty().set(new Insets(0, 0, 0, 0));
            nameField.alignmentProperty().set(Pos.BOTTOM_LEFT);
            nameHbox.getChildren().add(1, nameField);

            productTypeList = Main.getSession().createQuery("from ProductType ").getResultList();
            productTypeList.forEach(productType -> {
                String pro = productType.getName();
                if (!productType.getType().isEmpty()) pro += " - " + productType.getType();
                if (!productType.getModel().isEmpty()) pro += " - " + productType.getModel();
                if (!productType.getBrand().isEmpty()) pro += " - " + productType.getBrand();
                if (!productType.getSize().isEmpty()) pro += " - " + productType.getSize();
                if (!productType.getGrade().isEmpty()) pro += " - " + productType.getGrade();
                productTypeNameList.add(pro);
            });
            nameField.getEntries().addAll(productTypeNameList);
            nameField.setOnKeyPressed(event -> {

                if (event.getCode() == KeyCode.ENTER) {
                    String proName = nameField.getText();
                    if (proName.isEmpty())
                        return;
                    getSelectedproductIndex(proName);
                } else
                    nameIndex = -1;
            });
        } catch (HibernateException e) {
            e.printStackTrace();
            FieldValues.LogError(e);

        }

    }

    private void supplierFieldSetup() {
        try {
            supplierField = new AutoCompleteTextField();
            supplierField.setPrefWidth(250);
            supplierField.setMinHeight(30);
            supplierField.fontProperty().set(Font.font("System Bold", 14));
            supplierField.styleProperty().set("-fx-background-color: #D6EAF8;");
            supplierField.promptTextProperty().set("Search Supplier name");
            supplierField.labelFloatProperty().set(true);
            supplierField.paddingProperty().set(new Insets(0, 0, 0, 5));
            supplierField.alignmentProperty().set(Pos.BOTTOM_LEFT);
            supplierHbox.getChildren().add(1, supplierField);

            supplierList = Main.getSession().createQuery("from Supplier ").getResultList();
            supplierList.forEach(supplier -> {
                String sup = supplier.getName();
                suppliernameList.add(sup);
            });
            supplierField.getEntries().addAll(suppliernameList);

            supplierField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {

                    String supName = supplierField.getText();
                    if (supName.isEmpty())
                        return;

                    supplierIndex = suppliernameList.indexOf(supName);
                    if (supplierIndex < 0)
                        notification.setText("Supplier is not registered " + supplierIndex);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }

    private void getSelectedproductIndex(String proName) {
        nameIndex = productTypeNameList.indexOf(proName);
        if (nameIndex < 0) {
            notification.setText("This Product is not registered in Product Type");
            return;
        }

        productType = productTypeList.get(nameIndex);
        nameField.setText(productType.getName());
        type.setText(productType.getType());
        model.setText(productType.getModel());
        brand.setText(productType.getBrand());
        size.setText(productType.getSize());
        grade.setText(productType.getGrade());
        unitSellingPrice.setText(productType.getSellingPrice() + "");
    }

    @FXML
    void closeWindows(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveData(ActionEvent event) throws IOException {

        if (validatefield())
            return;


        notification.setText("");

        Supplier supplier = null;
        System.out.print("supplier index ==" + supplierIndex);
        if (!supplierField.getText().isEmpty())
            if (supplierIndex == -1) {
                if (suppliernameList.indexOf(supplierField.getText()) != -1)
                    supplier = supplierList.get(suppliernameList.indexOf(supplierField.getText()));

                else if (!supplierField.getText().isEmpty()) {
                    supplier = new Supplier();
                    supplier.setName(supplierField.getText());
                    Session session = Main.getUpdateSession();
                    session.save(supplier);
                    session.getTransaction().commit();
                    session.close();
                }
            } else
                supplier = supplierList.get(supplierIndex);
        if (productType == null)
            if (nameIndex != -1) {
                productType = productTypeList.get(nameIndex);
            } else {
                notification.setText("Please register this product type before add/save");
                return;
            }
        double amountText = Double.parseDouble(amount.getText());
        double totalpriceText = Double.parseDouble(totalCostPrice.getText());
        double unitPrice = Double.parseDouble(unitCostPrice.getText());
        purchase.setAmount(amountText);
        purchase.setUnitCostPrice(unitPrice);
        purchase.setTotalPrice(totalpriceText);
        purchase.setTotalPrice(totalpriceText);
        purchase.setSupplier(supplier);
        purchase.setProductType(productType);
        Session session = Main.getUpdateSession();
        session.saveOrUpdate(purchase);
        session.getTransaction().commit();
        session.close();
        Stage stage = (Stage) amount.getScene().getWindow();
        stage.close();
        new AlertClass().Alert("Info", "successfully Saved",
                Alert.AlertType.INFORMATION, "", amount.getScene().getWindow());

    }


    @FXML
    void delete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are You Sure?");
        alert.setHeaderText("Deleting Task");
        alert.setContentText("Are you ok with this?");
        alert.initOwner(amount.getScene().getWindow());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {


            Session session = Main.getUpdateSession();
//            if(task.getPeriodicTask() != null)
//            {
//                PeriodicTask periodicTask = task.getPeriodicTask();
//                session.delete(periodicTask);
//            }
            session.delete(purchase);
            session.getTransaction().commit();
            session.close();
            closeWindows(null);
            new AlertClass().Alert("Info", "Successfully Deleted",
                    Alert.AlertType.INFORMATION, "", amount.getScene().getWindow());
        } else {
            // ... user chose CANCEL or closed the dialog
        }
        System.out.println("delete Input");
    }

    public void totalPriceDisplayMethod() {
        AtomicReference<Double> totalproduct = new AtomicReference<>((double) 0);
        inputList.forEach(o -> {
            JSONObject object = (JSONObject) o;
            totalproduct.set(totalproduct.get() + Double.parseDouble(object.getString("costprice")) *
                    Double.parseDouble(object.getString("amount")));
        });
        totalPriceFDsplay.setVisible(true);
        totalPriceFDsplay.setText(totalproduct + " Birr");

    }

    public boolean validatefield() {
        boolean invalid = true;
        if (nameField.validate())
            invalid = false;
        System.out.println("validation statment1 ==" + invalid);
        if (unitSellingPrice.validate())
            invalid = false;
        System.out.println("validation statment21 ==" + invalid);
        if (unitCostPrice.validate())
            invalid = false;
        System.out.println("validation statment31 ==" + invalid);
        if (amount.validate())
            invalid = false;
        System.out.println("validation statment41 ==" + invalid);
        if (totalCostPrice.validate())
            invalid = false;
        System.out.println("validation statment51 ==" + invalid);
        if (!nameField.getText().isEmpty() && nameIndex == -1) {
            getSelectedproductIndex(nameField.getText());
            if (nameIndex == -1) {
                notification.setText("Please register this product type before save");
                invalid = false;
            }
        }

        System.out.println("validation statment ==" + invalid);

        return invalid;
    }


    @FXML
    void culculateTotal(KeyEvent event) throws NumberFormatException {

        if (amount.getText().isEmpty())
            amount.setText("0");

        if (unitCostPrice.getText().isEmpty())
            unitCostPrice.setText("0");


        try {
            double am = Integer.parseInt(amount.getText());
            double co = Double.parseDouble(unitCostPrice.getText());


            double total = am * co;
            totalCostPrice.setText("" + total);
        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }


    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public void setModel(String model) {
        this.model.setText(model);
    }

    public void setBrand(String brand) {
        this.brand.setText(brand);
    }

    public void setGrade(String grade) {
        this.grade.setText(grade);
    }

    public void setType(String type) {
        this.type.setText(type);
    }

    public void setSize(String size) {
        this.size.setText(size);
    }

    public void setUnitSellingPrice(String unitSellingPrice) {
        this.unitSellingPrice.setText(unitSellingPrice);
    }

    public void setAmount(String amount) {
        this.amount.setText(amount);
    }

    public void setUnitCostPrice(String unitCostPrice) {
        this.unitCostPrice.setText(unitCostPrice);
    }

    public void setTotalCostPrice(String totalCostPrice) {
        this.totalCostPrice.setText(totalCostPrice);
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber.setText(invoiceNumber);
    }

    public void setNameField(String nameField) {

        if (this.nameField != null)
            this.nameField.setText(nameField);
    }

    public void setSupplierField(String supplierField) {
        if (this.supplierField != null)
            this.supplierField.setText(supplierField);
    }
}