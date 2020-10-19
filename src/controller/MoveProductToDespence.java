package controller;

import Utility.AlertClass;
import Utility.AutoCompleteTextField;
import Utility.FieldValues;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import model.DespenceProduct;
import model.StoreProduct;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class MoveProductToDespence {


    AutoCompleteTextField nameField;
    List<StoreProduct>    storeProductList = FXCollections.observableArrayList();
    List<String>          productNameList  = new ArrayList<>();
    int                   nameIndex        = -1;
    StoreProduct          selectStoreProduct;
    DespenceProduct       despenceProduct;
    @FXML
    private Label                  notification;
    @FXML
    private HBox                   nameHbox;
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
    private VBox                   tobesavedList;
    @FXML
    private Label                  totalAmount;
    @FXML
    private JFXTextField           amountField;
    @FXML
    private JFXButton              move;
    @FXML
    private VBox                   tobesavedList1;
    @FXML
    private Label                  despenceName;
    @FXML
    private Label                  despenceAmount;
    @FXML
    private DoubleValidator        amountDoubleValidator;
    @FXML
    private RequiredFieldValidator amountREquieredValidator;

    @FXML
    private void initialize() {
        try {
            nameFieldSetup();
            model.setVisible(false);
            type.setVisible(false);
            grade.setVisible(false);
            size.setVisible(false);
            brand.setVisible(false);
//         amountField.setTooltip("Amount");

            amountField.setValidators(amountDoubleValidator);
            amountField.setValidators(amountREquieredValidator);

        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }

    private void nameFieldSetup() {
        try {
            nameField = new AutoCompleteTextField();
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


            storeProductList = Main.getSession().createQuery("from StoreProduct where amount > 0").getResultList();
            storeProductList.forEach(productType -> {
                if (productType.getProductType() != null) {
                    String pro = productType.getProductType().getName();
                    if (!productType.getProductType().getType().isEmpty())
                        pro += " - " + productType.getProductType().getType();
                    if (!productType.getProductType().getModel().isEmpty())
                        pro += " - " + productType.getProductType().getModel();
                    if (!productType.getProductType().getBrand().isEmpty())
                        pro += " - " + productType.getProductType().getBrand();
                    if (!productType.getProductType().getSize().isEmpty())
                        pro += " - " + productType.getProductType().getSize();
                    if (!productType.getProductType().getGrade().isEmpty())
                        pro += " - " + productType.getProductType().getGrade();
                    productNameList.add(pro);
                }
            });
            nameField.getEntries().addAll(productNameList);
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
        nameIndex = productNameList.indexOf(proName);
        if (nameIndex != -1) {
            selectStoreProduct = storeProductList.get(nameIndex);
        } else
            return;


        //   nameField.setText(selectProduct.getName());
        if(selectStoreProduct.getProductType() != null)
        if (!selectStoreProduct.getProductType().getBrand().isEmpty()) {
            brand.setVisible(true);
            brand.setText(" Brand:  " + selectStoreProduct.getProductType().getBrand());

        } else
            brand.setVisible(false);

        try {
            if (selectStoreProduct.getProductType() != null)
                if (!selectStoreProduct.getProductType().getModel().isEmpty()) {
                    model.setVisible(true);
                    model.setText("Model:  " + selectStoreProduct.getProductType().getModel());
                } else
                    model.setVisible(false);
            if (selectStoreProduct.getProductType() != null)
                if (!selectStoreProduct.getProductType().getType().isEmpty()) {
                    grade.setVisible(true);
                    grade.setText("Grade:  " + selectStoreProduct.getProductType().getGrade());
                } else
                    grade.setVisible(false);
            if (!selectStoreProduct.getProductType().getSize().isEmpty()) {
                size.setVisible(true);
                size.setText("Size:  " + selectStoreProduct.getProductType().getSize());
            } else
                size.setVisible(false);
            if (!selectStoreProduct.getProductType().getType().isEmpty()) {
                type.setVisible(true);
                type.setText("Type:  " + selectStoreProduct.getProductType().getType());
            } else
                type.setVisible(false);

        }catch (Exception e){
            FieldValues.LogError(e);
        }

        totalAmount.setText("Total Amount in Store:  " + selectStoreProduct.getAmount());
        despenceFieldsFill();

    }

    private void despenceFieldsFill() {

        List<DespenceProduct> despenceProductList = Main.getSession().createQuery("from DespenceProduct  where productType = " + selectStoreProduct.getProductType().getId()).getResultList();
        if (despenceProductList == null || despenceProductList.isEmpty()) {
            despenceName.setText("Name : " + nameField.getText());
            despenceAmount.setText("Amount : 0");
        } else {
            despenceName.setText("Name : " + despenceProductList.get(0).getProductType().getName());
            despenceAmount.setText("Amount : " + despenceProductList.get(0).getAmount());
        }
    }

    @FXML
    void Move(ActionEvent event) {

        notification.setText("");

        if (nameIndex == -1) {
            getSelectedproductIndex(nameField.getText());
            if (nameIndex == -1) {
                notification.setText("This Product is not Register");
                return;
            }
        }


        if (!amountField.validate())
            return;

        Double amountNumber = Double.parseDouble(amountField.getText());
        Double totalAmountNumber = selectStoreProduct.getAmount();


        if (amountNumber > totalAmountNumber) {
            notification.setText("Input amount must be below the total amount");
            return;
        }
        if (totalAmountNumber <= 0) {
            notification.setText("Cannot move because less than or equal to zero amount in store");
            return;
        }
        double newAmount = totalAmountNumber - amountNumber;
        selectStoreProduct.setAmount(newAmount);

        System.out.println("store left amount=" + newAmount);
        Session session = Main.getUpdateSession();
        session.update(selectStoreProduct);
        session.getTransaction().commit();
        session.close();
        System.out.println("store left get amount=" + selectStoreProduct.getAmount());

        List<DespenceProduct> despenceProductList = Main.getSession().createQuery("from DespenceProduct  where productType = " + selectStoreProduct.getProductType().getId()).getResultList();
        DespenceProduct despenceProduct = new DespenceProduct();
        if (despenceProductList == null || despenceProductList.isEmpty()) {
            despenceProduct.setProductType(selectStoreProduct.getProductType());
            despenceProduct.setAmount(amountNumber);
        } else {
            despenceProduct = despenceProductList.get(0);
            double oldAmount = despenceProduct.getAmount();
            newAmount = oldAmount + amountNumber;
            despenceProduct.setAmount(newAmount);
        }
        System.out.println("store after left get amount=" + selectStoreProduct.getAmount());

        session = Main.getUpdateSession();
        session.saveOrUpdate(despenceProduct);
        session.getTransaction().commit();
        session.close();

        new AlertClass().Alert("Info", "successfully Moved " + amountNumber, Alert.AlertType.INFORMATION, "", despenceAmount.getScene().getWindow());

        totalAmount.setText("Total Amount in Store:  " + (totalAmountNumber - amountNumber));
        double updatedAmount = amountNumber;

        despenceFieldsFill();
        // nameFieldSetup();


    }
}
