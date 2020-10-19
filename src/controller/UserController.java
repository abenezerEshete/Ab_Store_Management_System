package controller;

import Utility.FieldValues;
import Utility.HardCodedValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import model.User;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class UserController {


    @FXML
    private AnchorPane invoice;
    @FXML
    private JFXComboBox<?> searchBy;
    @FXML
    private JFXTextField searchText;
    @FXML
    private JFXButton productTypeSearch;
    @FXML
    private JFXButton addUser;
    @FXML
    private Label countList;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<?, ?> userId;
    @FXML
    private TableColumn<?, ?> number;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> userName;
    @FXML
    private TableColumn<?, ?> userPhone;
    @FXML
    private TableColumn<?, ?> userType;
    @FXML
    private TableColumn<?, ?> active;
    @FXML
    private JFXButton next;
    @FXML
    private Label itemPage;
    @FXML
    private JFXButton back;

    List<User> userList    = FXCollections.observableArrayList();
    int                currentPage      = 1;
    int                totalRows        = 0;
    int                totalPage        = 1;
    String             qText            = "";
    String             searchquery      = "";
    int                pos;

    @FXML
    private void initialize() throws IOException {


        userId.setCellValueFactory(new PropertyValueFactory<>("id"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        userName.setCellValueFactory(new PropertyValueFactory<>("username"));
        userPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        userType.setCellValueFactory(new PropertyValueFactory<>("type"));
        active.setCellValueFactory(new PropertyValueFactory<>("active"));
        userTable.styleProperty().set("-fx-font-size:14");

        loadData(currentPage);
    }
    private void loadData(int position) {
        this.pos = position;

        try {

            int pageLimit = HardCodedValue.PAGELIMIT;
            String formatedSearchQuery = "";
            if (!searchquery.isEmpty()) formatedSearchQuery = " where name like '%" + searchquery + "%'";
            // get row count
            Query<User > countQuery = Main.getSession().createQuery(
                    " from User  " + formatedSearchQuery);
            int totalRows = countQuery.list().size();
            totalPage = totalRows / pageLimit;
            if (totalRows % pageLimit != 0)
                totalPage++;
            itemPage.setText(" " + currentPage + "/" + totalPage);

            countList.setText("User (" + totalRows + ")");
            // get list of tasks
            pos--;
            int startlimit = pos * pageLimit;
            Query<User> storeProductQuery = Main.getSession().createQuery(
                    "from User " + formatedSearchQuery + "");
            storeProductQuery.setFirstResult(startlimit);
            storeProductQuery.setMaxResults(pageLimit);

            userList= storeProductQuery.getResultList();
            userTable.getItems().clear();
            userTable.getItems().addAll(userList);


        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }

    }



    @FXML
    void addUser(ActionEvent event) {

        System.out.println("please add product Type");
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane anchorPane = loader.load(getClass().getResource(HardCodedValue.addUserLayout).openStream());

            System.out.println("please add hiiii Type");

            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(userTable.getScene().getWindow());
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.setOnCloseRequest(event1 -> {
                loadData(currentPage);
            });
            stage.setOnHidden(event3 -> {
                loadData(currentPage);
            });

            stage.initStyle(StageStyle.DECORATED);
            stage.showAndWait();


        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }

    @FXML
    void backpage(ActionEvent event) throws IOException {
        if (currentPage > 1) {
            currentPage--;
            loadData(currentPage);
            itemPage.setText(" " + currentPage + "/" + totalPage);

        }
    }

    @FXML
    void getSelectedItem(MouseEvent event) {

        if (event.getClickCount() == 2)
            openEdit();

    }

    @FXML
    void onkeyPresed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER)
            openEdit();

    }

    private void openEdit() {
        User prodType = userTable.getSelectionModel().getSelectedItem();
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane anchorPane = loader.load(getClass().getResource(HardCodedValue.editUserLayout).openStream());
            EditUserController editUserController = loader.getController();

            editUserController.setUser(prodType);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(userTable.getScene().getWindow());
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);

            stage.setOnCloseRequest(event1 -> {
                loadData(currentPage);
            });
            stage.setOnHidden(event3 -> {
                loadData(currentPage);
            });

            stage.initStyle(StageStyle.DECORATED);
            stage.showAndWait();


        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }

    }

    @FXML
    void nextload(ActionEvent event) throws IOException {
        if (currentPage < totalPage) {
            currentPage++;
            loadData(currentPage);
            itemPage.setText(" " + currentPage + "/" + totalPage);
        }
    }

    @FXML
    void productSearch(ActionEvent event) {
        currentPage = 1;
        searchquery = searchText.getText();
        loadData(currentPage);
    }


}
