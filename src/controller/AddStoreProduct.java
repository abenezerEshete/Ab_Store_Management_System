package controller;

import Utility.AlertClass;
import Utility.AutoCompleteTextField;
import Utility.FieldValues;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;
import model.ProductType;
import model.Purchase;
import model.StoreProduct;
import model.Supplier;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class AddStoreProduct {


    AutoCompleteTextField    nameField;
    AutoCompleteTextField    supplierField;
    List<ProductType>        productTypeList;
    List<Supplier>           supplierList;
    List<String>             productTypeNameList = new ArrayList<>();
    List<String>             suppliernameList    = new ArrayList<>();
    String                   INVOICE_NUMBER      = "";
    String                   date                = "";
    String                   currentUser         = "";
    String                   productID           = "";
    JSONArray                inputList           = new JSONArray();
    int                      nameIndex           = -1;
    int                      supplierIndex       = -1;
    Purchase                 isEditing           = null;
    ObservableList<Purchase> purchasedProducts   = FXCollections.observableArrayList();
    @FXML
    private AnchorPane             addProduct;
    @FXML
    private VBox                   tobesavedList;
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
    private JFXTextField           invoiceNumber;
    @FXML
    private RequiredFieldValidator totalCostRequierValidator;
    @FXML
    private DoubleValidator        totalCostNumberValidator;
    @FXML
    private HBox                   supplierHbox;
    @FXML
    private JFXButton              cancelButton;
    @FXML
    private JFXButton              resetInput;
    @FXML
    private JFXButton              saveAndStayButton;
    @FXML
    private JFXButton              saveButton;
    @FXML
    private Label                  totalPriceFDsplay;
    @FXML
    private Label                  notification;
    private JFXButton              addedList;

    @FXML
    private void initialize() {
        try {

            nameFieldSetup();
            supplierFieldSetup();


            unitSellingPrice.getValidators().addAll(sellingPriceNumberValidator);
            unitSellingPrice.getValidators().addAll(sellingPriceRequierValidator);
            unitCostPrice.getValidators().add(unitCostPriceRequierValidator);
            unitCostPrice.getValidators().add(unitCostNumberValidator);
            totalCostPrice.getValidators().add(totalCostRequierValidator);
            totalCostPrice.getValidators().add(totalCostNumberValidator);
            amount.getValidators().add(amountNumberValidator);
            amount.getValidators().add(aountRequierValidator);

            //   INVOICE_NUMBER = new FildValues().invoiceNumber();

            fieldPropertySetup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fieldPropertySetup() {
        unitCostPrice.setText("0");
        unitSellingPrice.setText("0");
        totalCostPrice.setText("0");
        amount.setText("0");
        unitCostPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty())
                    return;
                double newval = Double.parseDouble(newValue);
                if (newValue.contains("d") || newValue.contains("f") ||
                        newValue.contains("D") || newValue.contains("F")) {
                    unitCostPrice.setText(oldValue);
                } else unitCostPrice.setText(newValue);
            } catch (NumberFormatException e) {
                unitCostPrice.setText(oldValue);
            } finally {

            }
        });
        unitSellingPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty())
                    return;
                double newval = Double.parseDouble(newValue);
                if (newValue.contains("d") || newValue.contains("f") ||
                        newValue.contains("D") || newValue.contains("F")) {
                    unitSellingPrice.setText(oldValue);
                } else unitSellingPrice.setText(newValue);
            } catch (NumberFormatException e) {
                unitSellingPrice.setText(oldValue);
            } finally {

            }
        });
        totalCostPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty())
                    return;
                double newval = Double.parseDouble(newValue);
                if (newValue.contains("d") || newValue.contains("f") ||
                        newValue.contains("D") || newValue.contains("F")) {
                    totalCostPrice.setText(oldValue);
                } else totalCostPrice.setText(newValue);
            } catch (NumberFormatException e) {
                totalCostPrice.setText(oldValue);
            } finally {

            }
        });
        amount.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty())
                    return;
                double newval = Double.parseDouble(newValue);
                if (newValue.contains("d") || newValue.contains("f") ||
                        newValue.contains("D") || newValue.contains("F")) {
                    amount.setText(oldValue);
                } else amount.setText(newValue);
            } catch (NumberFormatException e) {
                amount.setText(oldValue);
            } finally {

            }
        });
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
            nameField.getValidators().add(nameRequiredValidator);

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

    private void getSelectedproductIndex(String proName) {
        nameIndex = productTypeNameList.indexOf(proName);
        if (nameIndex < 0) {

            for (int i = 0; i < productTypeList.size(); i++) {
                if (productTypeList.get(i).getName().equalsIgnoreCase(proName)) {
                    nameIndex = i;
                    break;
                }
            }
            if (nameIndex == -1) {
                notification.setText("This Product is not registered in Product Type");
                return;
            }
        }


        ProductType productType = productTypeList.get(nameIndex);
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
        getFeildValue();

        purchasedProducts.forEach(purchasedProduct -> {

            if (purchasedProduct.getProductType() != null) {
                List<StoreProduct> storeProductList = Main.getSession().createQuery("from StoreProduct where productType = " + purchasedProduct.getProductType().getId()).getResultList();
                double amnt = purchasedProduct.getAmount();
                StoreProduct storeProduct = new StoreProduct();
                if (storeProductList != null && !storeProductList.isEmpty()) {
                    storeProduct = storeProductList.get(0);
                    double storeAmount = storeProduct.getAmount();
                    amnt = storeAmount + purchasedProduct.getAmount();
                }
                storeProduct.setAmount(amnt);
                storeProduct.setProductType(purchasedProduct.getProductType());
                Session session = Main.getUpdateSession();
                session.saveOrUpdate(storeProduct);
                session.getTransaction().commit();
                session.close();
                notification.setText("Sucessfully Saved");

                session = Main.getUpdateSession();
                session.save(purchasedProduct);
                session.getTransaction().commit();
                session.close();
                //    notification.setText("Successfully Saved");
            }
            {
                notification.setText("Unregistered Product Type");

            }

        });
        new AlertClass().Alert("Info", "successfully Saved",
                Alert.AlertType.INFORMATION, "", amount.getScene().getWindow());
        deletInput(null);

    }

    @FXML
    void deletInput(ActionEvent event) {

        nameIndex = -1;
        supplierIndex = -1;
        notification.setText("");
        nameField.setText("");
        model.setText("");
        type.setText("");
        grade.setText("");
        size.setText("");
        brand.setText("");
        amount.setText("");
        invoiceNumber.setText("");
        unitSellingPrice.setText("");
        totalCostPrice.setText("");
        unitCostPrice.setText("");
        supplierField.setText("");


    }

    @FXML
    void saveAndStay(ActionEvent event) {

        if (validatefield())
            return;

        Purchase purchasedProduct = getFeildValue();

        if (purchasedProduct == null)
            return;

        addedList = new JFXButton();
        addedList.setMaxWidth(95);
        addedList.setPrefWidth(95);
        addedList.setMinWidth(95);
        addedList.paddingProperty().set(new Insets(2, 1, 2, 1));
        addedList.setText(nameField.getText());
        addedList.setAlignment(Pos.CENTER);
        addedList.fontProperty().set(Font.font("System Bold", 12));
        addedList.styleProperty().set("-fx-background-color: #F2F3F4;-fx-border-color:#2E86C1;-fx-border-width:1; -fx-background-radius:10;-fx-border-radius:10");
        addedList.setId(purchasedProduct.getId() + "");
        FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView(FontAwesomeIcon.REMOVE);
        fontAwesomeIconView.setSize("16");
        fontAwesomeIconView.setFill(Paint.valueOf("#ff0000"));
        JFXButton deleteButton = new JFXButton();
        deleteButton.setGraphic(fontAwesomeIconView);
        deleteButton.styleProperty().set("-fx-background-color: #BDC3C7; -fx-background-radius:17;-fx-border-radius:17");
        deleteButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        addedList.setGraphic(deleteButton);
        addedList.setGraphicTextGap(2);
        addedList.setContentDisplay(ContentDisplay.RIGHT);
        tobesavedList.getChildren().add(addedList);
        addedList.setOnAction(event1 -> {


            if (purchasedProduct.getSupplier() != null)
                supplierField.setText(purchasedProduct.getSupplier().getName());
            if (purchasedProduct.getProductType() != null) {
                nameField.setText(purchasedProduct.getProductType().getName() + "");
                model.setText(purchasedProduct.getProductType().getModel());
                type.setText(purchasedProduct.getProductType().getType());
                grade.setText(purchasedProduct.getProductType().getGrade());
                size.setText(purchasedProduct.getProductType().getSize());
                brand.setText(purchasedProduct.getProductType().getBrand());
                unitSellingPrice.setText(purchasedProduct.getProductType().getSellingPrice() + "");
            }
            amount.setText(purchasedProduct.getAmount() + "");
            invoiceNumber.setText(purchasedProduct.getInvoiceNumber());
            unitCostPrice.setText(purchasedProduct.getUnitCostPrice() + "");
            totalCostPrice.setText(purchasedProduct.getTotalPrice() + "");
//            supplierIndex = purchasedProduct.getSupplier();
//            nameIndex = purchasedProduct.getProductid();
            isEditing = purchasedProduct;

        });
        deleteButton.setOnAction(event1 -> {
            purchasedProducts.remove(purchasedProduct);
            tobesavedList.getChildren().remove(deleteButton.getParent());
            //    totalPriceDisplayMethod();

        });


        //  totalPriceDisplayMethod();


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

    public Purchase getFeildValue() {

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

        ProductType productType = null;
        if (nameIndex != -1) {
            productType = productTypeList.get(nameIndex);
        } else {
            notification.setText("Please register this product type before add/save");
            return null;
        }

        System.out.println("product Type==" + productType);
        double amountText = Double.parseDouble(amount.getText());
        double totalpriceText = Double.parseDouble(totalCostPrice.getText());
        double unitPrice = Double.parseDouble(unitCostPrice.getText());

        Purchase purchasedProduct = new Purchase();
        purchasedProduct.setProductType(productType);
        purchasedProduct.setSupplier(supplier);
        purchasedProduct.setAmount(amountText);
        purchasedProduct.setUnitCostPrice(unitPrice);
        purchasedProduct.setTotalPrice(totalpriceText);
        purchasedProduct.setInvoiceNumber(invoiceNumber.getText());
        purchasedProduct.setInvoiceNumber(invoiceNumber.getText());

        boolean duplicated = false;
        for (int l = 0; l < purchasedProducts.size(); l++) {
            if (purchasedProducts.get(l).getProductType().getId() == purchasedProduct.getProductType().getId()) {
                notification.setText("Duplicate Product");
                return null;
            }
        }
        if (isEditing != null) {
            int eindex = purchasedProducts.indexOf(isEditing);
            purchasedProducts.add(eindex, purchasedProduct);
            return null;
        } else
            purchasedProducts.add(purchasedProduct);

        return purchasedProduct;
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

}