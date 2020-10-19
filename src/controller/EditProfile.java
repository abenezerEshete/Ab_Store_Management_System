package controller;

import Utility.AlertClass;
import Utility.FieldValues;
import Utility.FileAccessControl;
import Utility.HardCodedValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.Main;
import model.User;
import org.hibernate.Session;
import org.json.JSONObject;

public class EditProfile {

    JSONObject loginedUser = null;
    @FXML
    private VBox                   editWindows;
    @FXML
    private RequiredFieldValidator nameRequiredValidator;
    @FXML
    private JFXTextField           name;
    @FXML
    private RequiredFieldValidator userNameRequiredValidator;
    @FXML
    private RequiredFieldValidator phoneRequiredValidator;
    @FXML
    private JFXTextField           userName;
    @FXML
    private JFXButton              save;
    @FXML
    private NumberValidator        phoneNumberValidator;
    @FXML
    private JFXTextField           phone;
    @FXML
    private Label                  err;
    @FXML
    private Label                  savedNotification;
    @FXML
    private RequiredFieldValidator oldPasswordRequiredValidator;
    @FXML
    private JFXPasswordField       oldPasswordf;
    @FXML
    private RequiredFieldValidator newPasswordRequiredValidator;
    @FXML
    private JFXPasswordField       newPasswordf;

    @FXML
    private void initialize() {

        System.out.println("in Edit Profile controle");

        oldPasswordf.getValidators().add(oldPasswordRequiredValidator);
        newPasswordf.getValidators().add(newPasswordRequiredValidator);
        phone.getValidators().add(phoneRequiredValidator);
        phone.getValidators().add(phoneNumberValidator);
        name.getValidators().add(nameRequiredValidator);
        userName.getValidators().add(userNameRequiredValidator);
        save.setOnAction(event -> {
            saveuser(event);
        });
        try {
            loginedUser = new JSONObject(new FileAccessControl().readfromFileStringBuffer(HardCodedValue.USERCATCHFILE).toString());
            String namet = loginedUser.optString("name");
            String usernamet = loginedUser.optString("username");
            String phonet = loginedUser.optString("phone");
            String password = loginedUser.optString("pw");

            name.setText(namet);
            userName.setText(usernamet);
            phone.setText(phonet);


        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }

    }


    @FXML
    public void saveuser(javafx.event.ActionEvent actionEvent) {

        //check Validation
        try {
            oldPasswordRequiredValidator.setMessage("This field is required");
            savedNotification.setText("");

            boolean inValid = false;
            if (!name.validate()) inValid = true;
            if (!userName.validate()) inValid = true;
            if (!phone.validate()) inValid = true;
            if (!oldPasswordf.validate()) inValid = true;
            //     if(!newPasswordf.validate()) inValid = true;
            if (inValid) return;

            if (loginedUser != null) {
                if (!oldPasswordf.getText().equals(loginedUser.optString("pw"))) {
                    err.setText("Password Do not Match");
                    return;
                } else
                    err.setText("");

                String passWord = oldPasswordf.getText();
                if (!newPasswordf.getText().isEmpty())
                    passWord = newPasswordf.getText();


                User user = new User();
                if (loginedUser.optInt("id") != 0)
                    user.setId(loginedUser.optInt("id"));
                user.setName(name.getText());
                user.setUsername(userName.getText());
                user.setPhone(phone.getText());
                user.setPassword(passWord);
                user.setActive(true);

                Session session = Main.getUpdateSession();
                session.saveOrUpdate(user);
                session.getTransaction().commit();

                System.out.println("logined id :" + user.getId());

                savedNotification.setText("successfully Saved");
                new AlertClass().Alert("Info", "successfully Saved",
                        Alert.AlertType.INFORMATION, "", name.getScene().getWindow());

                Text changeName = (Text) name.getScene().lookup("#userLabel");
                if (changeName != null)
                    changeName.setText(name.getText());
                System.out.println("change name:" + changeName);
                JSONObject logined = new JSONObject();
                loginedUser.put("id", loginedUser.getInt("id"));
                loginedUser.put("name", name.getText());
                loginedUser.put("username", userName.getText());
                loginedUser.put("phone", phone.getText());
                loginedUser.put("pw", passWord);
            } else
                savedNotification.setText("Error in saving");
        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }

    }

}
