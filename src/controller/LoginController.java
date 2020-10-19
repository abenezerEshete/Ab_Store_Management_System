package controller;

import Utility.FileAccessControl;
import Utility.HardCodedValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import main.Main;
import model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log
public class LoginController {


    String LOGINFILEPATH = HardCodedValue.LOGINFILEPATH;
    String USERCATCHFILE = HardCodedValue.USERCATCHFILE;
    Stage  stage         = null;
    @FXML
    private Label                  bugText;
    @FXML
    private Label                  errorNotification;
    @FXML
    private JFXTextField           userNameField;
    @FXML
    private JFXPasswordField       passwordField;
    @FXML
    private JFXButton              loginButton;
    @FXML
    private Label                  unsuccessfulLabel;
    @FXML
    private RequiredFieldValidator userNameValidator;
    @FXML
    private RequiredFieldValidator passwordValidator;
    @FXML
    private VBox                   depthVBox;
    @FXML
    private AnchorPane             headerPane;
    private boolean                inputValidated;

    @FXML
    private void initialize() {

        userNameField.getValidators().add(userNameValidator);
        passwordField.getValidators().add(passwordValidator);

        userNameField.setOnKeyPressed(k -> {
            errorNotification.setText("");
            if (k.getCode().equals(KeyCode.ENTER)) {
                try {

                    LoginCheck();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        passwordField.setOnKeyPressed(k -> {
            errorNotification.setText("");
            if (k.getCode().equals(KeyCode.ENTER)) {
                try {

                    LoginCheck();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }


    @FXML
    private void LoginCheck() throws IOException {

        System.out.println("thank tyou1");
        if (!userNameField.validate() || !passwordField.validate())
            return;
        System.out.println("thank ");

        errorNotification.setText("");
        try {
            if (!Main.getSession().isConnected()) {
                errorNotification.setText("Error in connection with Database: ");
                return;
            }

            System.out.println("thank 2 ");

            Session sesion = Main.getSession();
            CriteriaBuilder criteriaBuilder = sesion.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = criteriaQuery.from(User.class);
            List<Predicate> restrictions = new ArrayList<Predicate>();
            restrictions.add(criteriaBuilder.equal(userRoot.get("username"), userNameField.getText()));
            restrictions.add(criteriaBuilder.equal(userRoot.get("password"), passwordField.getText()));
            criteriaQuery.where(restrictions.toArray(new Predicate[restrictions.size()]));
          //  Query<User> query = sesion.createQuery(criteriaQuery);
            Query<User> query = sesion.createQuery("from User where username = '"+userNameField.getText()+"' and " +
                    " password = '"+passwordField.getText()+"'");
            List<User> users = query.getResultList();
//          Query<User> query = sesion.createQuery("from User");
//            List<User> users = query.getResultList();
System.out.println("query="+"from User where username = '"+userNameField.getText()+"' and " +
        " password = '"+passwordField.getText()+"'");
System.out.println(users);


            if (users == null || users.isEmpty())
                bugText.setText("Error in Username or Password"+users);
            else if (users.get(0).getActive() == true) {
                User user= users.get(0);
                JSONObject loginedUser = new JSONObject();
                loginedUser.put("id", user.getId());
                loginedUser.put("name", user.getName());
                loginedUser.put("username", user.getUsername());
                loginedUser.put("phone", user.getPhone());
                loginedUser.put("type", user.getType());
                loginedUser.put("pw", user.getPassword() + "");

                new FileAccessControl().writetoFile(new HardCodedValue().USERCATCHFILE, loginedUser.toString());
                Scene scene = loginButton.getScene();
                scene.setUserData(user);
                Parent home = FXMLLoader.load(getClass().getResource(HardCodedValue.homeLayout));
                scene.setRoot(home);
                System.out.println("thank user is not getter= " + user.getActive());

            } else if (!users.get(0).getActive()) {
                bugText.setText("your account is Deactive \n please connect to manager");
            }

        } catch (Exception e) {
            e.printStackTrace();
            errorNotification.setText("Error in connection with Database: " + e.getMessage());
            System.out.println("In excecption");
            return;

        }


    }

}
