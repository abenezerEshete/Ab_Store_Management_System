package controller;

import Utility.AlertClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import model.ProductType;
import org.hibernate.Session;
import org.json.JSONObject;

public class AddProductType {


    JSONObject loginedUser = null;
    int        id;
    @FXML
    private AnchorPane             addProduct;
    @FXML
    private RequiredFieldValidator nameRequiredValidator;
    @FXML
    private Label                  saveNotification;
    @FXML
    private JFXTextField           name;
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
    private JFXTextField           oneMeasurePrice;
    @FXML
    private DoubleValidator        sellingPriceNumberValidator;
    @FXML
    private DoubleValidator        unitSellingPriceDoubleValidator;
    @FXML
    private RequiredFieldValidator sellingPriceRequierValidator;
    @FXML
    private JFXTextField           sellingPrice;
    @FXML
    private JFXComboBox<String>    sellingMeasurment;
    @FXML
    private JFXTextField           unitToSellingMeasurment;
    @FXML
    private JFXButton              cancel;
    @FXML
    private JFXButton              saveButton;
    @FXML
    private JFXCheckBox            hasBroker;

    @FXML
    private void initialize() {

        System.out.println("in Edit Profile controle");

        name.getValidators().add(nameRequiredValidator);
        sellingPrice.getValidators().add(sellingPriceNumberValidator);
        sellingPrice.getValidators().add(sellingPriceRequierValidator);
        unitToSellingMeasurment.getValidators().add(unitSellingPriceDoubleValidator);

        sellingMeasurment.getItems().addAll("", "Piece", "Kit", "Packet", "Carton",
                "Gallon", "Millimeter", "Centimeter", "meter",
                "Gram", "Kilogram", "Milliliter", "Liter",
                "Meter square", "Meter Cube");

        unitToSellingMeasurment.setTooltip(new Tooltip("One unit Product to how mach selling measurement "));

        fieldPropertySetup();


    }

    private void fieldPropertySetup() {
        sellingPrice.setText("0");
        unitToSellingMeasurment.setText("0");
        oneMeasurePrice.setText("0");
        sellingPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty())
                    return;
                double newval = Double.parseDouble(newValue);
                if (newValue.contains("d") || newValue.contains("f") ||
                        newValue.contains("D") || newValue.contains("F")) {
                    sellingPrice.setText(oldValue);
                } else sellingPrice.setText(newValue);
            } catch (NumberFormatException e) {
                sellingPrice.setText(oldValue);
            } finally {

            }
        });
        unitToSellingMeasurment.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty())
                    return;
                double newval = Double.parseDouble(newValue);
                if (newValue.contains("d") || newValue.contains("f") ||
                        newValue.contains("D") || newValue.contains("F")) {
                    unitToSellingMeasurment.setText(oldValue);
                } else unitToSellingMeasurment.setText(newValue);
            } catch (NumberFormatException e) {
                unitToSellingMeasurment.setText(oldValue);
            } finally {

            }
        });
        oneMeasurePrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty())
                    return;
                double newval = Double.parseDouble(newValue);
                if (newValue.contains("d") || newValue.contains("f") ||
                        newValue.contains("D") || newValue.contains("F")) {
                    oneMeasurePrice.setText(oldValue);
                } else oneMeasurePrice.setText(newValue);
            } catch (NumberFormatException e) {
                oneMeasurePrice.setText(oldValue);
            } finally {

            }
        });
    }


    @FXML
    void cancelCloseWindow(ActionEvent event) {
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveData(ActionEvent event) {

        System.out.print("Save data");

        saveNotification.setText("");

        // validation
        boolean valid = true;
        if (!name.validate() || !sellingPrice.validate())
            return;

        ProductType productType = new ProductType();
        productType.setName(name.getText());
        productType.setType(type.getText());
        productType.setGrade(grade.getText());
        productType.setModel(model.getText());
        productType.setSize(size.getText());
        productType.setBrand(brand.getText());
        if (!sellingPrice.getText().isEmpty())
            productType.setSellingPrice(Double.parseDouble(sellingPrice.getText()));
        productType.setSellingMeasurment(sellingMeasurment.getSelectionModel().getSelectedItem());
        if (!unitToSellingMeasurment.getText().isEmpty())
            productType.setSellingAmountRatio(Double.parseDouble(unitToSellingMeasurment.getText()));
        productType.setHasBroker(hasBroker.isSelected());
        if (!oneMeasurePrice.getText().isEmpty())
            productType.setSellingAmountRatio(Double.parseDouble(oneMeasurePrice.getText()));
        Session session = Main.getUpdateSession();
        session.save(productType);
        session.getTransaction().commit();
        session.close();
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
        new AlertClass().Alert("Info", "successfully Saved",
                Alert.AlertType.INFORMATION, "", name.getScene().getWindow());

    }

    @FXML
    void deletInput(ActionEvent event) {
        System.out.println("delete Input");
        saveNotification.setText("");
        name.setText("");
        model.setText("");
        name.setText("");
        size.setText("");
        brand.setText("");
        grade.setText("");
        type.setText("");
        sellingPrice.setText("");
        sellingMeasurment.getSelectionModel().select("");
        unitToSellingMeasurment.setText("");
        oneMeasurePrice.setText("");
        hasBroker.setSelected(false);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name.setText(name);
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

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice.setText(sellingPrice);
    }

    public void setSellingMeasurment(String sellingMeasurment) {
        this.sellingMeasurment.getSelectionModel().select(sellingMeasurment);
    }

    public void setUnitToSellingMeasurment(String unitToSellingMeasurment) {
        this.unitToSellingMeasurment.setText(unitToSellingMeasurment);
    }


}

