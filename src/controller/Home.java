package controller;

import Utility.FileAccessControl;
import Utility.HardCodedValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.IOException;

public class Home {

    FileAccessControl fileAccessControl;
    String            USERCATCHFILE = HardCodedValue.USERCATCHFILE;
    AnchorPane        customer, productMovent,
            supplier, notification, userManagement;
    VBox report, dashboard, productType, pos, perchase, product, setting;
    ContextMenu contextMenu;
    @FXML
    private BorderPane home_borderPane;
    @FXML
    private AnchorPane secondAnchorBar;
    @FXML
    private VBox       taskListVbox;
    @FXML
    private JFXButton  dashboardButton;
    @FXML
    private JFXButton  posButton;
    @FXML
    private JFXButton  sellesReportButton;
    @FXML
    private JFXButton  purchaseReportButton;
    @FXML
    private JFXButton  despenceProductButton;
    @FXML
    private JFXButton  storeProductButton;
    @FXML
    private JFXButton  totalProductButton;
    @FXML
    private JFXButton  productTypeButton;
    @FXML
    private JFXButton  supplierButton;
    @FXML
    private JFXButton  customerButton;
    @FXML
    private JFXButton  userManagment;
    @FXML
    private JFXButton  SettingButton;
    @FXML
    private JFXButton  brockerButton;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private JFXToolbar jfxtoolbar;
    @FXML
    private Text       userLabel;
    @FXML
    private JFXButton  userPic;
    @FXML
    private Label      errorNotification;

    public Home() {
        System.out.println("cons");
    }

    @FXML
    private void initialize() {


        //  errorNotification.setText("hiiii");

        try {

            Parent loginPage = FXMLLoader.load(getClass().getResource(HardCodedValue.loginLayout));
            Parent profileEdit = FXMLLoader.load(getClass().getResource(HardCodedValue.editProfileLayout));

            contextMenu = new ContextMenu();
            contextMenu.styleProperty().set("-fx-background-color: #D6EAF8;-fx-font-size:14");
            MenuItem profileMenu = new MenuItem("profile");
            profileMenu.setOnAction(event -> {
                setNode(profileEdit);
            });
            MenuItem logout = new MenuItem("Logout");
            logout.setOnAction(event -> {
                Scene scene = userLabel.getScene();
                scene.setUserData(null);
                scene.getRoot().setUserData(null);
                scene.setRoot(loginPage);

            });
            contextMenu.getItems().add(profileMenu);
            contextMenu.getItems().add(logout);

            JSONObject loginedUser = new JSONObject(new FileAccessControl().readfromFileStringBuffer(new HardCodedValue().USERCATCHFILE).toString());

            userLabel.setText(loginedUser.optString("name"));
            userPic.setContextMenu(contextMenu);

            if (!loginedUser.optString("type").isEmpty()) {

                switch (loginedUser.optString("type")) {
                    case "Administrator": {
                        System.out.println("type===admin");
                        dashboard = FXMLLoader.load(getClass().getResource(HardCodedValue.dashbordLayout));
                        setNode(dashboard);

                        break;
                    }
                    case "Finance": {
                        taskListVbox.getChildren().removeAll(storeProductButton, productTypeButton, customerButton,
                                despenceProductButton, posButton,
                                supplierButton, brockerButton, userManagment, SettingButton);
                        dashboard = FXMLLoader.load(getClass().getResource(HardCodedValue.dashbordLayout));
                        setNode(dashboard);

                        break;
                    }
                    case "Sales": {
                        taskListVbox.getChildren().removeAll(dashboardButton, purchaseReportButton,
                                storeProductButton, totalProductButton, productTypeButton, supplierButton,
                                userManagment, SettingButton);
                        pos = FXMLLoader.load(getClass().getResource(HardCodedValue.sellingLayout));
                        setNode(pos);

                        break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setNode(Node node) {

        home_borderPane.setUserData(null);
        node.setUserData(null);
        home_borderPane.setCenter(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }


    @FXML
    public void userPicClicked(ActionEvent mouseEvent) {
        contextMenu.show(this.userPic, Side.BOTTOM, -25, 0);
    }


    @FXML
    void openDashboard(ActionEvent event) throws IOException {
        dashboard = FXMLLoader.load(getClass().getResource(HardCodedValue.dashbordLayout));

        setNode(dashboard);
    }

    @FXML
    void openPos(ActionEvent event) throws IOException {
        pos = FXMLLoader.load(getClass().getResource(HardCodedValue.sellingLayout));
        setNode(pos);
    }

    @FXML
    void storeProductButton(ActionEvent event) throws IOException {
        product = FXMLLoader.load(getClass().getResource(HardCodedValue.storeProductLayout));
        setNode(product);
    }

    @FXML
    void totalProductButton(ActionEvent event) throws IOException {
        product = FXMLLoader.load(getClass().getResource(HardCodedValue.totalProductLayout));
        setNode(product);
    }

    @FXML
    void despenceProductButton(ActionEvent event) throws IOException {
        product = FXMLLoader.load(getClass().getResource(HardCodedValue.despenceProductLayout));
        setNode(product);
    }

    @FXML
    void openUserManagment(ActionEvent event) throws IOException {
        userManagement = FXMLLoader.load(getClass().getResource(HardCodedValue.userProductLayout));
        setNode(userManagement);
    }


    @FXML
    void openReport(ActionEvent event) throws IOException {
        report = FXMLLoader.load(getClass().getResource(HardCodedValue.reportLayout));
        setNode(report);
    }

    @FXML
    void openSetting(ActionEvent event) throws IOException {
        setting = FXMLLoader.load(getClass().getResource(HardCodedValue.settingLayout));
        setNode(setting);
    }

    @FXML
    void openPerchasedReport(ActionEvent event) throws IOException {
        perchase = FXMLLoader.load(getClass().getResource(HardCodedValue.purchaseLayout));

        setNode(perchase);
    }

    @FXML
    void openCustomer(ActionEvent event) throws IOException {
        customer = FXMLLoader.load(getClass().getResource(HardCodedValue.customerLayout));

        setNode(customer);
    }


    @FXML
    void opensupplier(ActionEvent event) throws IOException {
        supplier = FXMLLoader.load(getClass().getResource(HardCodedValue.supplierLayout));
        setNode(supplier);
    }

    @FXML
    void openBrocker(ActionEvent event) throws IOException {
        supplier = FXMLLoader.load(getClass().getResource(HardCodedValue.brokerLayout));
        setNode(supplier);
    }


    @FXML
    void productType(ActionEvent event) throws IOException {
        productType = FXMLLoader.load(getClass().getResource(HardCodedValue.productTypeLayout));
        setNode(productType);

    }


    public void setErrorNotification(Node node, String message) {
        try {
            AnchorPane anchorPane = (AnchorPane) node.getScene().getRoot().getChildrenUnmodifiable().get(1);
            JFXToolbar jfxToolbar = (JFXToolbar) anchorPane.getChildrenUnmodifiable().get(0);
            Label errorNotifi = (Label) jfxToolbar.getBottom();
            errorNotifi.setText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void changeName(Node node, String name) {
        JFXToolbar jfxToolbar = (JFXToolbar) node.getScene().getRoot().getChildrenUnmodifiable().get(1);
        HBox box = (HBox) jfxToolbar.getRight();
        Text text = (Text) box.getChildren().get(0);
        text.setText(name);

    }
}
