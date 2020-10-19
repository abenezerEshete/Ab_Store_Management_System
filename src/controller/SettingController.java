package controller;

import Utility.AlertClass;
import Utility.FileAccessControl;
import Utility.HardCodedValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONObject;


public class SettingController {


    @FXML
    private JFXComboBox<Integer> itemPerPage;

    @FXML
    private JFXCheckBox autoPrint;


    @FXML
    private JFXTextField campanyName;

    @FXML
    private JFXTextField cumpanyAddress;

    @FXML
    private JFXTextField campanyContact;

    @FXML
    private JFXTextField cumpanyPhone;

    @FXML
    private JFXTextField companyEmail;

    @FXML
    private JFXTextField companyMessage;

    @FXML
    private JFXButton save;

    @FXML
    private IntegerValidator integerValidator;

    @FXML
    private DoubleValidator doubleValidator;

    @FXML
    private RequiredFieldValidator requieredValidator;

    @FXML
    private void initialize() {

        itemPerPage.getItems().addAll(50, 100, 200, 500);


        campanyName.setValidators(requieredValidator);
        cumpanyPhone.setValidators(requieredValidator);
        //    itemPerPage.setValue(50);

        JSONArray jsonArray = getDatas();
        JSONObject object = jsonArray.getJSONObject(0);
        itemPerPage.setValue(object.getInt("itemperpage"));
        autoPrint.setSelected(object.getBoolean("autoprint"));
        this.campanyName.setText(object.getString("name"));
        this.campanyContact.setText(object.getString("contact"));
        this.companyEmail.setText(object.getString("email"));
        this.cumpanyAddress.setText(object.getString("address"));
        cumpanyPhone.setText(object.getString("phone"));
        companyMessage.setText(object.getString("message"));
    }

    private JSONArray getDatas() {


        JSONArray jsonArray = new FileAccessControl().readfromFileJsonArray(HardCodedValue.SETTING);

        return jsonArray;
    }

    @FXML
    void saveData(ActionEvent event) {

        if (campanyName.validate() && cumpanyPhone.validate()) {

            int itemperpage = itemPerPage.getValue();
            boolean autoprint = autoPrint.isSelected();
            String name = campanyName.getText();
            String contact = campanyContact.getText();
            String email = companyEmail.getText();
            String address = cumpanyAddress.getText();
            String phone = cumpanyPhone.getText();
            String message = companyMessage.getText();

            JSONObject object = new JSONObject();
            object.put("itemperpage", itemperpage);
            object.put("autoprint", autoprint);
            object.put("name", name);
            object.put("contact", contact);
            object.put("email", email);
            object.put("address", address);
            object.put("phone", phone);
            object.put("message", message);


            if (new FileAccessControl().writetoFile(new HardCodedValue().SETTING, object.toString() + ",")) {
                new AlertClass().Alert("Info", "successfully Updated", Alert.AlertType.INFORMATION, "", campanyContact.getScene().getWindow());
            }


        }


    }

}
