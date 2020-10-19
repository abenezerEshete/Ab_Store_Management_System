package controller;

import Utility.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


public class EditSelled {
    AutoCompleteTextField    nameField;
    AutoCompleteTextField    brokerField;
    AutoCompleteTextField    customerField;
    List<ProductType>        productTypeList;
    List<Customer>           customerList;
    List<Broker>             brokerList;
    List<String>             productTypeNameList = new ArrayList<>();
    List<String>             customernameList    = new ArrayList<>();
    List<String>             brokernameList      = new ArrayList<>();
    String                   INVOICE_NUMBER      = "";
    String                   date                = "";
    String                   currentUser         = "";
    int                      productID           = 0;
    JSONArray                inputList           = new JSONArray();
    int                      nameIndex           = -1;
    int                      customerIndex       = -1;
    int                      brokerIndex         = -1;
    String                   productMeasure      = "";
    String                   productSalingRatio  = "";
    Purchase                 isEditing           = null;
    ObservableList<Purchase> purchasedProducts   = FXCollections.observableArrayList();
    Selled                   selledProduct;
    Customer                 customer;
    ProductType              productType;
    @FXML
    private AnchorPane             addProduct;
    @FXML
    private Label                  notification;
    @FXML
    private RowConstraints         brokerRow;
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
    private JFXTextField           unitPrice;
    @FXML
    private RequiredFieldValidator unitCostPriceRequierValidator;
    @FXML
    private DoubleValidator        unitCostNumberValidator;
    @FXML
    private JFXTextField           totalPrice;
    @FXML
    private RequiredFieldValidator totalCostRequierValidator;
    @FXML
    private DoubleValidator        totalCostNumberValidator;
    @FXML
    private HBox                   customerHbox;
    @FXML
    private HBox                   brokerHbox;
    @FXML
    private RequiredFieldValidator aountRequierValidator1;
    @FXML
    private DoubleValidator        amountNumberValidator1;
    @FXML
    private HBox                   brokerpriceHbox;
    @FXML
    private JFXTextField           brokerPrice;
    @FXML
    private RequiredFieldValidator aountRequierValidator11;
    @FXML
    private DoubleValidator        amountNumberValidator11;
    @FXML
    private HBox                   brokerPayedHbox;
    @FXML
    private RequiredFieldValidator aountRequierValidator12;
    @FXML
    private DoubleValidator        amountNumberValidator12;
    @FXML
    private JFXCheckBox            brokerPayedChekcBox;
    @FXML
    private JFXButton              cancelButton;
    @FXML
    private ButtonBar              buttonList;
    @FXML
    private JFXButton              delete;
    @FXML
    private JFXButton              saveButton;
    @FXML
    private Label                  totalPriceFDsplay;

    @FXML
    private void initialize() {
        nameFieldSetup();

        customerField = new AutoCompleteTextField();
        customerField.setPrefWidth(250);
        customerField.setMinHeight(30);
        customerField.fontProperty().set(Font.font("System Bold", 14));
        customerField.styleProperty().set("-fx-background-color: #D6EAF8;");
        customerField.promptTextProperty().set("Search Customer name");
        customerField.labelFloatProperty().set(true);
        customerField.paddingProperty().set(new Insets(0, 0, 0, 5));
        customerField.alignmentProperty().set(Pos.BOTTOM_LEFT);
        customerHbox.getChildren().add(1, customerField);

        customerList = Main.getSession().createQuery("from Customer ").getResultList();
        customerList.forEach(customer -> {
            String sup = customer.getName();
            customernameList.add(sup);
        });
        customerField.getEntries().addAll(customernameList);

        customerField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                String supName = customerField.getText();
                if (supName.isEmpty())
                    return;

                customerIndex = customernameList.indexOf(supName);
                if (customerIndex < 0)
                    notification.setText("Supplier is not registered ");
            }

        });


        brokerField = new AutoCompleteTextField();
        brokerField.setPrefWidth(250);
        brokerField.setMinHeight(30);
        brokerField.fontProperty().set(Font.font("System Bold", 14));
        brokerField.styleProperty().set("-fx-background-color: #D6EAF8;");
        brokerField.promptTextProperty().set("Search Broker name");
        brokerField.labelFloatProperty().set(true);
        brokerField.paddingProperty().set(new Insets(0, 0, 0, 5));
        brokerField.alignmentProperty().set(Pos.BOTTOM_LEFT);
        brokerHbox.getChildren().add(1, brokerField);

        brokerList = Main.getSession().createQuery("from Broker ").getResultList();
        brokerList.forEach(broker -> {
            String sup = broker.getName();
            brokernameList.add(sup);
        });
        brokerField.getEntries().addAll(brokernameList);

        brokerField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                String supName = brokerField.getText();
                if (supName.isEmpty())
                    return;

                brokerIndex = brokernameList.indexOf(supName);
                if (brokerIndex < 0)
                    notification.setText("Supplier is not registered ");
            }

        });


        amount.textProperty().addListener((observable, oldValue, newValue) -> {

            try {
                double newval = Double.parseDouble(newValue);
                double uPrice = Double.parseDouble(unitPrice.getText());

                double amount = newval * uPrice;

                totalPrice.setText(amount + "");

            } catch (Exception e) {
                e.printStackTrace();
                FieldValues.LogError(e);

            }
        });


        unitPrice.setEditable(false);

        nameField.getValidators().add(nameRequiredValidator);
        unitPrice.getValidators().add(unitCostPriceRequierValidator);
        unitPrice.getValidators().add(unitCostNumberValidator);
        totalPrice.getValidators().add(totalCostRequierValidator);
        totalPrice.getValidators().add(totalCostNumberValidator);
        brokerPrice.getValidators().add(totalCostNumberValidator);
        amount.getValidators().add(amountNumberValidator);
        amount.getValidators().add(aountRequierValidator);
        //     expDateField.setValue(new LocalDate().);

//        INVOICE_NUMBER = new FeildValues().invoiceNumber();
//        date = new FeildValues().getTime();
//        currentUser = new FeildValues().currentUser();

        fieldPropertySetup();

        JSONObject loginedUser = new JSONObject(new FileAccessControl().readfromFileStringBuffer(new HardCodedValue().USERCATCHFILE).toString());
        System.out.println("type==" + loginedUser.optString("type"));
        if (!loginedUser.optString("type").isEmpty()) {

            if (loginedUser.optString("type").equalsIgnoreCase("Finance")) {
                delete.setVisible(false);
                saveButton.setVisible(false);
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

    private void fieldPropertySetup() {
        unitPrice.setText("0");
        totalPrice.setText("0");
        brokerPrice.setText("0");
        amount.setText("0");
        unitPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty())
                    return;
                double newval = Double.parseDouble(newValue);
                if (newValue.contains("d") || newValue.contains("f") ||
                        newValue.contains("D") || newValue.contains("F")) {
                    unitPrice.setText(oldValue);
                } else unitPrice.setText(newValue);
            } catch (NumberFormatException e) {
                unitPrice.setText(oldValue);
            } finally {

            }
        });
        totalPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty())
                    return;
                double newval = Double.parseDouble(newValue);
                if (newValue.contains("d") || newValue.contains("f") ||
                        newValue.contains("D") || newValue.contains("F")) {
                    totalPrice.setText(oldValue);
                } else totalPrice.setText(newValue);
            } catch (NumberFormatException e) {
                totalPrice.setText(oldValue);
            } finally {

            }
        });
        brokerPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty())
                    return;
                double newval = Double.parseDouble(newValue);
                if (newValue.contains("d") || newValue.contains("f") ||
                        newValue.contains("D") || newValue.contains("F")) {
                    brokerPrice.setText(oldValue);
                } else brokerPrice.setText(newValue);
            } catch (NumberFormatException e) {
                brokerPrice.setText(oldValue);
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


        if (customerField != null && !customerField.getText().isEmpty()) {
            if (customerIndex == -1) {
                if (customernameList.indexOf(customerField.getText()) != -1)
                    customer = customerList.get(customernameList.indexOf(customerField.getText()));

                else if (!customerField.getText().isEmpty()) {
                    customer = new Customer();
                    customer.setName(customerField.getText());
                    Session session = Main.getUpdateSession();
                    session.save(customer);
                    session.getTransaction().commit();
                    session.close();
                }
            } else
                customer = customerList.get(customerIndex);
        }


        Broker broker = null;
        if (brokerField.getText() != null && !brokerField.getText().isEmpty())
            if (brokerIndex == -1) {
                if (brokernameList.indexOf(brokerField.getText()) != -1)
                    broker = brokerList.get(brokernameList.indexOf(brokerField.getText()));

                else if (!customerField.getText().isEmpty()) {
                    broker = new Broker();
                    broker.setName(brokerField.getText());
                    Session session = Main.getUpdateSession();
                    session.save(broker);
                    session.getTransaction().commit();
                    session.close();
                }
            } else
                broker = brokerList.get(brokerIndex);

        if (productType == null)
            if (nameIndex != -1) {
                productType = productTypeList.get(nameIndex);
            } else {
                notification.setText("Please register this product type before add/save");
                return;
            }



        double amountText = 0;
        if (!amount.getText().isEmpty())
            amountText = Double.parseDouble(amount.getText());
        double totalpriceText = 0;
        if (!totalPrice.getText().isEmpty())
            totalpriceText = Double.parseDouble(totalPrice.getText());
        double brokerPriceText = 0;
        if (!brokerField.getText().isEmpty())
            try {
                brokerPriceText = Double.parseDouble(brokerPrice.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        double uPrice = 0;
        if (!unitPrice.getText().isEmpty())
            uPrice = Double.parseDouble(unitPrice.getText());

        selledProduct.setProductType(productType);
        selledProduct.setAmount(amountText);
        selledProduct.setCustomer(customer);
        selledProduct.setUnitPrice(uPrice);
        selledProduct.setTotalPrice(totalpriceText);
        selledProduct.setBroker(broker);
        selledProduct.setBrokerPrice(brokerPriceText);
        selledProduct.setBrokerPayed(brokerPayedChekcBox.isSelected());

        Session session = Main.getUpdateSession();
        session.update(selledProduct);
        session.getTransaction().commit();
        session.close();
        notification.setText("Successfully Saved");

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

            List<DespenceProduct> dProList = session.createQuery("from DespenceProduct  where productType = " + selledProduct.getProductType().getId()).getResultList();
            if (dProList != null && !dProList.isEmpty()) {
                DespenceProduct despenceProduct = dProList.get(0);
                despenceProduct.setAmount(despenceProduct.getAmount() + selledProduct.getAmount());
                session.update(despenceProduct);
            }

            session.delete(selledProduct);
            session.getTransaction().commit();
            session.close();
            closeWindows(null);
            new AlertClass().Alert("Info", "Successfully Deleted",
                    Alert.AlertType.INFORMATION, "", amount.getScene().getWindow());

        } else {
            // ... user chose CANCEL or closed the dialog
        }
        notification.setText("");

        Stage stage = (Stage) nameHbox.getScene().getWindow();
        stage.close();
        new AlertClass().Alert("Info", "successfully Deleted",
                Alert.AlertType.INFORMATION, "", nameHbox.getScene().getWindow());


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
        if (unitPrice.validate())
            invalid = false;
        System.out.println("validation statment31 ==" + invalid);
        if (amount.validate())
            invalid = false;
        System.out.println("validation statment41 ==" + invalid);
        if (totalPrice.validate())
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

        if (unitPrice.getText().isEmpty())
            unitPrice.setText("0");


        try {
            double am = Integer.parseInt(amount.getText());
            double co = Double.parseDouble(unitPrice.getText());


            double total = am * co;
            totalPrice.setText("" + total);
        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }

    public void setSalesID(Selled salesID) {
        this.selledProduct = salesID;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
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

    public void setAmount(String amount) {
        try {
            Double.parseDouble(amount);
            this.amount.setText(amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUnitPrice(String unitPrice) {

        try {
            Double.parseDouble(unitPrice);
            this.unitPrice.setText(unitPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTotalPrice(String totalPrice) {

        try {
            Double.parseDouble(totalPrice);
            this.totalPrice.setText(totalPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBrokerPrice(String brokerPrice) {
        try {
            Double.parseDouble(brokerPrice);
            this.brokerPrice.setText(brokerPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setCustomerIndex(int customerIndex) {
        this.customerIndex = customerIndex;
    }

    public void setBrokerIndex(int brokerIndex) {
        this.brokerIndex = brokerIndex;
    }

    public void brokerVisibility(boolean vis) {
        brokerHbox.setVisible(vis);
        brokerpriceHbox.setVisible(vis);
        brokerPayedHbox.setVisible(vis);
    }

    public void setBrokerPayedChekcBox(boolean brokerPayedChekcBox) {
        this.brokerPayedChekcBox.setSelected(brokerPayedChekcBox);
    }


    public void setNameText(String nameField) {
        if (nameField == null) nameField = "";
        this.nameField.setText(nameField);
    }

    public void setBrokerText(String brokerField) {
        if (brokerField == null) brokerField = "";
        this.brokerField.setText(brokerField);
    }

    public void setCustomerText(String customerField) {
        if (customerField == null) customerField = "";
        this.customerField.setText(customerField);
    }


    public void setCustomerId(Customer customerId) {
        this.customer = customerId;
    }

    public void setProductMeasure(String productMeasure) {
        this.productMeasure = productMeasure;
    }

    public void setProductSalingRatio(String productSalingRatio) {
        this.productSalingRatio = productSalingRatio;
    }
}