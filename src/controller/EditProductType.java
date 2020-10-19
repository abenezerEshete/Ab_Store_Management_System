package controller;

import Utility.AlertClass;
import Utility.FieldValues;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Main;
import model.ProductType;
import org.hibernate.Session;
import org.json.JSONObject;

import java.util.Optional;

public class EditProductType {


    JSONObject  loginedUser = null;
    int         id;
    ProductType productType;
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
    private HBox                   oneMeasurePriceHbox;
    @FXML
    private JFXTextField           oneMeasurePrice;
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
        System.out.println("measurment===");
        fieldPropertySetup();
    }

    private void fieldPropertySetup() {
        sellingPrice.setText("0");
        unitToSellingMeasurment.setText("0");
        oneMeasurePrice.setText("0");
        sellingPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue == null)
                    sellingPrice.setText("");
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
                if (newValue == null)
                    unitToSellingMeasurment.setText("");
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
                if (newValue == null)
                    oneMeasurePrice.setText("");
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
    void delet(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are You Sure?");
        alert.setHeaderText("Deleting Task");
        alert.setContentText("Are you ok with this?");
        alert.initOwner(name.getScene().getWindow());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {


            Session session = Main.getUpdateSession();
//            if(task.getPeriodicTask() != null)
//            {
//                PeriodicTask periodicTask = task.getPeriodicTask();
//                session.delete(periodicTask);
//            }
            session.delete(productType);
            session.getTransaction().commit();
            session.close();
            closeWindows(null);
            new AlertClass().Alert("Info", "Successfully Deleted",
                    Alert.AlertType.INFORMATION, "", name.getScene().getWindow());
        } else {
            // ... user chose CANCEL or closed the dialog
        }
        System.out.println("delete Input");
        saveNotification.setText("");
    }

    @FXML
    void cancelCloseWindow(ActionEvent event) {

        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    @FXML
    void closeWindows(ActionEvent event) {
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveData(ActionEvent event) {

        System.out.print("Save data");

        try {
            saveNotification.setText("");
            // validation
            if (!name.validate() || !sellingPrice.validate())
                return;

            productType.setName(name.getText());
            productType.setType(type.getText());
            productType.setGrade(grade.getText());
            productType.setModel(model.getText());
            productType.setSize(size.getText());
            productType.setBrand(brand.getText());
            if (sellingPrice != null && !sellingPrice.getText().isEmpty())
                productType.setSellingPrice(Double.parseDouble(sellingPrice.getText()));
            productType.setSellingMeasurment(sellingMeasurment.getSelectionModel().getSelectedItem());
            if (unitToSellingMeasurment != null && !unitToSellingMeasurment.getText().isEmpty())
                productType.setSellingAmountRatio(Double.parseDouble(unitToSellingMeasurment.getText()));
            productType.setHasBroker(hasBroker.isSelected());
            if (oneMeasurePrice != null && !oneMeasurePrice.getText().isEmpty())
                productType.setSellingAmountRatio(Double.parseDouble(oneMeasurePrice.getText()));

            Session session = Main.getUpdateSession();
            session.update(productType);
            session.getTransaction().commit();
            session.close();
            Stage stage = (Stage) name.getScene().getWindow();
            stage.close();
            new AlertClass().Alert("Info", "successfully Updated",
                    Alert.AlertType.INFORMATION, "", name.getScene().getWindow());
        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }


    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
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


    public void setHasBroker(boolean hasBroker) {
        this.hasBroker.setSelected(hasBroker);
    }

    public void setOneMeasurePrice(String oneMeasurePrice) {
        this.oneMeasurePrice.setText(oneMeasurePrice);
    }
}

