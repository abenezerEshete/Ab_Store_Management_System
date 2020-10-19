package controller;

import Utility.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXToolbar;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import model.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Selling {


    List<DespenceProduct>  productObservableList;
    List<Customer>         customerObservableList;
    List<Broker>           brokerObservableList;
    List<String>           productNameList     = new ArrayList<>();
    List<String>           customerNameList    = new ArrayList<>();
    List<String>           brokerNameList      = new ArrayList<>();
    AutoCompleteTextField  productSearch       = new AutoCompleteTextField();
    AutoCompleteTextField  customerSearch      = new AutoCompleteTextField();
    AutoCompleteTextField  brokerSearch        = new AutoCompleteTextField();
    boolean                autoPrint           = false;
    int                    productIndex        = -1;
    String                 INVOICE_NUMBER;
    String                 companyName         = "";
    String                 massage             = "";
    ObservableList<Selled> selledProducts      = FXCollections.observableArrayList();
    double                 totalComissionPrice = 0;
    double                 totalPriceVal       = 0;
    double                 sellingAmountRatio  = 0;
    @FXML
    private HBox                   posFirstHbox;
    @FXML
    private VBox                   posfirstVbox;
    @FXML
    private RequiredFieldValidator required;
    @FXML
    private HBox                   posinputHbox;
    @FXML
    private JFXButton              addToSellListButton;
    @FXML
    private HBox                   customerHbox;
    @FXML
    private JFXButton              addCustomerToSellingList;
    @FXML
    private Label                  model;
    @FXML
    private Label                  grade;
    @FXML
    private Label                  brand;
    @FXML
    private Label                  size;
    @FXML
    private Label                  type;
    @FXML
    private Label                  prodactPrice;
    @FXML
    private Label                  commisionPrice;
    @FXML
    private HBox                   borkerHbox;
    @FXML
    private JFXCheckBox            brokerCheckBox;
    @FXML
    private JFXButton              addToSellListButton21;
    @FXML
    private JFXButton              addBrokerTosellingList;
    @FXML
    private VBox                   sellingList;
    @FXML
    private Label                  totalPriceDisplay;
    @FXML
    private Label                  notification;
    @FXML
    private JFXButton              cancel;
    @FXML
    private JFXButton              purchase;
    @FXML
    private HBox                   commisionPayedHbox;
    @FXML
    private JFXCheckBox            commisionPayedCheckBox;

    @FXML
    private void initialize() {

        try {
            getproductList();
            getCustomerList();
            getBrokerList();

            INVOICE_NUMBER = new FieldValues().invoiceNumber();
            JSONArray getjson = new FileAccessControl().readfromFileJsonArray(HardCodedValue.SETTING);
            if (!getjson.getJSONObject(0).isEmpty())
                autoPrint = getjson.getJSONObject(0).getBoolean("autoprint");
            if (autoPrint) {

                companyName = getjson.getJSONObject(0).get("name").toString();
                massage = getjson.getJSONObject(0).get("message").toString();
            }


            model.setVisible(false);
            type.setVisible(false);
            grade.setVisible(false);
            size.setVisible(false);
            brand.setVisible(false);
            prodactPrice.setVisible(false);
        } catch (JSONException e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }

    private void getBrokerList() {
        try {
            brokerSearch.setPrefWidth(250);
            brokerSearch.setPrefHeight(30);
            brokerSearch.fontProperty().set(Font.font(14));
            brokerSearch.styleProperty().set("-fx-background-color: #EBF5FB;");
            brokerSearch.promptTextProperty().set("Broker Name");
            brokerSearch.labelFloatProperty().set(true);
            brokerSearch.paddingProperty().set(new Insets(0, 0, 0, 5));
            brokerSearch.alignmentProperty().set(Pos.BOTTOM_LEFT);
            brokerSearch.setOnKeyPressed(k -> {
                if (k.getCode().equals(KeyCode.ENTER)) {
                    try {

                        addToSellList(null);


                    } catch (Exception e) {
                        new FieldValues().LogError(" Cause: " + e.getCause() +
                                "\nMassage: " + e.getMessage() +
                                "\nErrorText: " + e.toString() +
                                "\nLocalizedMessage: " + e.getLocalizedMessage() +
                                "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
                        );
                        e.printStackTrace();
                    }
                }
            });
            borkerHbox.getChildren().add(2, brokerSearch);
            brokerObservableList = Main.getSession().createQuery("from Broker ").getResultList();
            brokerObservableList.forEach(broker -> {
                String cus = broker.getName();
                brokerNameList.add(cus);
            });
            brokerSearch.getEntries().addAll(brokerNameList);
            brokerCheckBox.setSelected(false);
            brokerSearch.setVisible(false);
            addBrokerTosellingList.setVisible(false);
            commisionPayedHbox.setVisible(false);
            brokerCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {

                if (newValue == true) {
                    brokerSearch.setVisible(true);
                    addBrokerTosellingList.setVisible(true);
                    commisionPayedHbox.setVisible(true);
                } else {
                    brokerSearch.setVisible(false);
                    addBrokerTosellingList.setVisible(false);
                    customerSearch.setText("");
                    commisionPayedHbox.setVisible(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }

    private void getCustomerList() {

        try {
            customerSearch.getValidators().add(required);
            customerSearch.setPrefWidth(250);
            customerSearch.setPrefHeight(30);
            customerSearch.fontProperty().set(Font.font(14));
            customerSearch.styleProperty().set("-fx-background-color: #EBF5FB;");
            //      customerSearch.promptTextProperty().set("Search Petient name here");
            customerSearch.labelFloatProperty().set(true);
            customerSearch.paddingProperty().set(new Insets(0, 0, 0, 5));
            customerSearch.alignmentProperty().set(Pos.BOTTOM_LEFT);

            customerSearch.setOnKeyPressed(k -> {
                if (k.getCode().equals(KeyCode.ENTER)) {
                    try {

                        addToSellList(null);


                    } catch (Exception e) {
                        new FieldValues().LogError(" Cause: " + e.getCause() +
                                "\nMassage: " + e.getMessage() +
                                "\nErrorText: " + e.toString() +
                                "\nLocalizedMessage: " + e.getLocalizedMessage() +
                                "\n PrintStackTrace: " + Arrays.toString(e.getStackTrace())
                        );
                        e.printStackTrace();
                    }
                }
            });
            customerHbox.getChildren().add(1, customerSearch);
            customerObservableList = Main.getSession().createQuery("from Customer").getResultList();
            customerObservableList.forEach(customer -> {
                String cus = customer.getName();
                customerNameList.add(cus);
            });
            customerSearch.getEntries().addAll(customerNameList);
        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }

    }

    private void getproductList() {
        try {
            productSearch.getValidators().add(required);
            productSearch.setPrefWidth(300);
            productSearch.setPrefHeight(25);
            productSearch.fontProperty().set(Font.font(14));
            productSearch.styleProperty().set("-fx-background-color: #EBF5FB;");
            // productSearch.promptTextProperty().set("Search product here");
            productSearch.labelFloatProperty().set(true);
            productSearch.paddingProperty().set(new Insets(0, 0, 0, 5));
            productSearch.alignmentProperty().set(Pos.BOTTOM_LEFT);
            productSearch.setOnKeyReleased(k -> {
                        try {


                            getProductFromInput();
                            if (k.getCode() == KeyCode.ENTER)
                                addToSellList(null);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
            posinputHbox.getChildren().add(0, productSearch);

            Query<DespenceProduct> productObservableListQuery = Main.getSession().createQuery("from DespenceProduct where amount >0");

            productObservableList = productObservableListQuery.getResultList();
            productObservableList.forEach(product -> {
                if (product.getProductType() != null) {
                    String pro = product.getProductType().getName();
                    if (!product.getProductType().getType().isEmpty())
                        pro += " - " + product.getProductType().getType();
                    if (!product.getProductType().getModel().isEmpty())
                        pro += " - " + product.getProductType().getModel();
                    if (!product.getProductType().getBrand().isEmpty())
                        pro += " - " + product.getProductType().getBrand();
                    if (!product.getProductType().getSize().isEmpty())
                        pro += " - " + product.getProductType().getSize();
                    if (!product.getProductType().getGrade().isEmpty())
                        pro += " - " + product.getProductType().getGrade();
                    if (product.getProductType().getSellingPrice() != 0)
                        pro += " - " + product.getProductType().getSellingPrice();
                    productNameList.add(pro);
                }

            });

            productSearch.getEntries().addAll(productNameList);
        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }


    }

    private void getProductFromInput() {

        model.setVisible(false);
        type.setVisible(false);
        grade.setVisible(false);
        size.setVisible(false);
        brand.setVisible(false);
        prodactPrice.setVisible(false);

        System.out.println("input text==" + productSearch.getText());
        if (!productSearch.validate())
            return;
        productIndex = productNameList.indexOf(productSearch.getText());
        if (productIndex == -1) {
            for (int i = 0; i < productObservableList.size(); i++) {
                if (productObservableList.get(i).getProductType().getName().equalsIgnoreCase(productSearch.getText())) {
                    System.out.println("its ==");
                    productIndex = i;
                    break;
                }
            }
            if (productIndex == -1) {
                return;
            }
            DespenceProduct product = productObservableList.get(productIndex);

            System.out.println("product==" + product);

            ProductType productType = product.getProductType();

            if (!productType.getBrand().isEmpty()) {
                brand.setVisible(true);
                brand.setText(" Brand:  " + productType.getBrand());

            } else
                brand.setVisible(false);
            if (!productType.getModel().isEmpty()) {
                model.setVisible(true);
                model.setText("Model:  " + productType.getModel());
            } else
                model.setVisible(false);
            if (!productType.getType().isEmpty()) {
                grade.setVisible(true);
                grade.setText("Grade:  " + productType.getGrade());
            } else
                grade.setVisible(false);
            if (!productType.getSize().isEmpty()) {
                size.setVisible(true);
                size.setText("Size:  " + productType.getSize());
            } else
                size.setVisible(false);
            if (!productType.getType().isEmpty()) {
                type.setVisible(true);
                type.setText("Type:  " + productType.getType());
            } else
                type.setVisible(false);
            if (!productType.getSellingMeasurment().isEmpty()) {
                prodactPrice.setVisible(true);
                prodactPrice.setText("Price:  " + productType.getMeasurePrice());
            } else {
                prodactPrice.setVisible(true);
                prodactPrice.setText("Price:  " + productType.getSellingPrice());
            }

        }
    }

    @FXML
    void addToSellList(ActionEvent event) throws IOException {
        getProductFromInput();
        notification.setText("");
        if (productIndex == -1) {
            notification.setText("This Product is not registered");
            return;
        }

        DespenceProduct product = productObservableList.get(productIndex);
        ProductType productType = product.getProductType();

        // Duplicate check
        for (int i = 0; i < selledProducts.size(); i++) {
            if (selledProducts.get(i).getProductType() == product.getProductType()) {
                return;
            }
        }

        Selled selled_product = new Selled();

        double sellingprice = productType.getSellingPrice();
        double amounttext = product.getAmount();
        String nametext = productType.getName();
        String measureUnit = productType.getSellingMeasurment();
        double measureUnitPrice = productType.getMeasurePrice();

        if (productType.getSellingAmountRatio() > 0)
            sellingAmountRatio = productType.getSellingAmountRatio();
        double totalMeasureUnitAmount = sellingAmountRatio * amounttext;
        double singleMeasureUnitinAmount = 1 / sellingAmountRatio;

        JFXToolbar jfxToolbar = FXMLLoader.load(Main.class.getClass().getResource(HardCodedValue.sellingItemLayout));
        Label name = (Label) jfxToolbar.getChildren().get(0);
        HBox rightitem = (HBox) jfxToolbar.getChildren().get(1);
        Spinner sellingMeasureAmountField = (Spinner) rightitem.getChildren().get(0);
        Label sellingMeasureLabel = (Label) rightitem.getChildren().get(1);
        Label amount = (Label) rightitem.getChildren().get(2);
        Label price = (Label) rightitem.getChildren().get(3);


        //creating selled product object
        selled_product.setProductType(product.getProductType());


        name.setText(nametext);
        amount.setText(amounttext + "");

        System.out.println("is it zero==" + measureUnitPrice);

        SpinnerValueFactory spinnerValueFactory;
        if (measureUnitPrice == 0 || sellingAmountRatio == 0) {

            System.out.println("no measurment");
            int maxvalue = (int) amounttext;
            spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxvalue);
            spinnerValueFactory.setValue(1);
            sellingMeasureAmountField.setValueFactory(spinnerValueFactory);
            sellingMeasureLabel.setText("/" + amounttext);
            price.setText(sellingprice + " Birr");

            selled_product.setUnitPrice(productType.getSellingPrice());
            selled_product.setTotalPrice(productType.getSellingPrice());

        } else {
            System.out.println("in measurment");

            int maxvalue = (int) totalMeasureUnitAmount;
            spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxvalue);
            spinnerValueFactory.setValue(1);
            sellingMeasureAmountField.setValueFactory(spinnerValueFactory);

            sellingMeasureLabel.setText("/" + totalMeasureUnitAmount + " " + measureUnit);
            price.setText(measureUnitPrice + " Birr");

            selled_product.setUnitPrice(measureUnitPrice);
            selled_product.setTotalPrice(measureUnitPrice);
        }


        selled_product.setAmount(1.0);


        System.out.println("singleMeasureUnitinAmount--" + singleMeasureUnitinAmount);


        JFXButton delete = (JFXButton) rightitem.getChildren().get(4);
        sellingMeasureAmountField.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {

            try {
                int value = Integer.parseInt(newValue);

                if (value > ((SpinnerValueFactory.IntegerSpinnerValueFactory) spinnerValueFactory).getMax()) {
                    value = ((SpinnerValueFactory.IntegerSpinnerValueFactory) spinnerValueFactory).getMax();
                    sellingMeasureAmountField.getEditor().setText(((SpinnerValueFactory.IntegerSpinnerValueFactory) spinnerValueFactory).getMax() + "");
                }

                double priceUpdate = 0;
                System.out.println("---1-----" + measureUnit);
                System.out.println("---2-----" + measureUnit.equalsIgnoreCase("null"));
                System.out.println("---3-----" + measureUnitPrice);
                System.out.println("---4-----" + sellingAmountRatio);
                if (measureUnitPrice == 0 || sellingAmountRatio == 0) {
                    priceUpdate = value * sellingprice;
                    selled_product.setAmount(value + 0.0);
                } else {
                    priceUpdate = value * measureUnitPrice;
                    selled_product.setAmount(value * singleMeasureUnitinAmount);
                    System.out.println("singleMeasureUnitinAmount IS UPDATED--" + singleMeasureUnitinAmount + "---" + value);
                }

                price.setText(priceUpdate + " Birr");

                selled_product.setTotalPrice(priceUpdate);
                selled_product.setAmount(value + 0.0);
                onButtonPressAction();
                setCommisionValue();

            } catch (Exception e) {
                e.printStackTrace();
                sellingMeasureAmountField.getEditor().setText("1");
            }

        });


        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selledProducts.remove(selled_product);
                sellingList.getChildren().remove(jfxToolbar);
                onButtonPressAction();
                setCommisionValue();
            }
        });

        selledProducts.add(selled_product);
        sellingList.getChildren().add(jfxToolbar);

        brokerCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (brokerCheckBox.isSelected())
                setCommisionValue();
        });

        productSearch.setText("");
        onButtonPressAction();
        setCommisionValue();

    }

    private void setCommisionValue() {

        totalComissionPrice = 0;
        System.out.println("in commision ====");
        if (brokerCheckBox.isSelected())
            selledProducts.forEach(selled_product -> {
                if (selled_product.getProductType().isHasBroker()) {
                    double amount = selled_product.getAmount();
                    double productPrice = (selled_product.getTotalPrice());
                    totalComissionPrice = totalComissionPrice + (productPrice * 0.05);

                    System.out.println("in commision 1=" + (amount * productPrice));

                }
            });

        commisionPrice.setText("Commision Price: " + totalComissionPrice);
    }

    public void onButtonPressAction() {
        totalPriceVal = 0;

        selledProducts.forEach(product -> {

            double price = product.getTotalPrice();
            totalPriceVal += price;
        });


        System.out.println("in total size-" + selledProducts.size());
        System.out.println("in total cualculation-" + totalPriceVal);
        totalPriceDisplay.setText("   Total Price:  " + totalPriceVal);


    }

    @FXML
    void cancel(ActionEvent event) {
        sellingList.getChildren().clear();
        totalPriceDisplay.setText("Total:");
        productSearch.setText("");

    }

    @FXML
    void purchase(ActionEvent event) throws IOException {

        if (sellingList.getChildren().size() < 1)
            return;


        Customer customer = getSelectedCustomer();
        Broker broker = getSelectedBroker();

        double totalPriceVal = 0;
        int totalamount = 0;


        for (int i = 0; i < selledProducts.size(); i++) {

            Selled selledProduct = selledProducts.get(i);
            selledProduct.setDate(LocalDateTime.now());
            selledProduct.setCustomer(customer);

            //set broke
            if (brokerCheckBox.isSelected()) {
                selledProduct.setBroker(broker);
                selledProduct.setBrokerPayed(commisionPayedCheckBox.isSelected());
                double brokerpri = selledProduct.getTotalPrice() * 0.05;
                selledProduct.setBrokerPrice(brokerpri);
            }
            selledProduct.setInvoiceNumber(INVOICE_NUMBER);


            Session session = Main.getUpdateSession();
            session.save(selledProduct);
            session.getTransaction().commit();
            session.close();


            DespenceProduct dPro = (DespenceProduct) Main.getSession().createQuery("from DespenceProduct where productType =" +
                    selledProduct.getProductType().getId()).getSingleResult();

            if (selledProduct.getProductType().getMeasurePrice() > 0) {
                double ratio = selledProduct.getProductType().getSellingAmountRatio();
                if (ratio > 0) {
                    double singlepro = 1 / ratio;
                    double changeAmount = selledProduct.getAmount() * singlepro;
                    double oldAmount = dPro.getAmount();
                    double newAmount = oldAmount - changeAmount;
                    dPro.setAmount(newAmount);

                }
                else
                    dPro.setAmount(dPro.getAmount()-selledProduct.getAmount());
            }
            else {
                dPro.setAmount(dPro.getAmount()-selledProduct.getAmount());
            }
            System.out.println("product amount= "+selledProduct.getAmount());

            session = Main.getUpdateSession();
            session.update(dPro);
            session.getTransaction().commit();
            session.close();

        }


//        if (autoPrint) {
//
//            JSONObject productSaved = new JSONObject();
//            productSaved.put("ProductList", selledProducts);
//            productSaved.put("id", 0);
//            productSaved.put("totalPrice", totalPriceVal);
//            productSaved.put("totalAmount", totalamount);
//            productSaved.put("createdDate", date);
//            productSaved.put("customer", customerSearch.getText());
//            productSaved.put("invoiceNumber", INVOICE_NUMBER);
//            System.out.println("outside loop" + productSaved);
//
//
//            selledProducts.add(new Selled_product());
//            Selled_product selledProduct = new Selled_product();
//            selledProduct.setProductName("Total");
//            selledProduct.setAmount(0);
//            selledProduct.setTotalPrice(totalPriceDisplay.getText().replace("   Total Price:  ", ""));
//            selledProducts.add(selledProduct);
//
//            FXMLLoader loader = new FXMLLoader();
//            AnchorPane anchorPane = loader.load(getClass().getResource("/Main/component/view/print_layout.fxml").openStream());
//            Print_controller controller = loader.getController();
//            controller.setCumpanyName(companyName);
//            if (!customerSearch.getText().equals(""))
//                controller.setCustomerName("To:  " + customerSearch.getText());
//            controller.setInvoiceNumber("Invoice Number: " + INVOICE_NUMBER);
//            controller.getTableValue(selledProducts);
//            controller.setDate("Date: " + date);
//            controller.setMessage(massage);
//            Node node = anchorPane;//new Circle(100, 200, 200);
//            PrinterJob job = PrinterJob.createPrinterJob();
//            if (job != null) {
//
//
//                boolean success = job.printPage(node);
//                if (success) {
//                    job.endJob();
//                } else {
//
//                }
//            }
//        }

        new AlertClass().Alert("Info", "successfully Updated ordered", Alert.AlertType.INFORMATION, "", addToSellListButton.getScene().getWindow());


    }

    private Broker getSelectedBroker() {
        Broker broker = null;
        try {
            if (brokerSearch.getText().isEmpty())
                return broker;

            if (brokerNameList.indexOf(brokerSearch.getText()) != -1) {
                broker = brokerObservableList.get(customerNameList.indexOf(brokerSearch.getText()));
            } else {
                Session session = Main.getUpdateSession();
                broker = new Broker();
                broker.setName(brokerSearch.getText());
                session.saveOrUpdate(broker);
                session.getTransaction().commit();
                session.close();
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
        return broker;
    }

    private Customer getSelectedCustomer() {

        Customer customer = null;
        try {
            if (customerSearch.getText().isEmpty())
                return customer;

            if (customerNameList.indexOf(customerSearch.getText()) != -1) {
                customer = customerObservableList.get(customerNameList.indexOf(customerSearch.getText()));
            } else {
                Session session = Main.getUpdateSession();
                customer = new Customer();
                customer.setName(customerSearch.getText());
                session.saveOrUpdate(customer);
                session.getTransaction().commit();
                session.close();
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
        return customer;
    }


}


