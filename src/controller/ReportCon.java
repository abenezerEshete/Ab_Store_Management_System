package controller;

import Utility.FieldValues;
import Utility.HardCodedValue;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import model.Selled;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public class ReportCon {


    List<Selled> selledreports = FXCollections.observableArrayList();
    int          currentPage   = 0;
    int          totalRows     = 0;
    int          totalPage     = 1;
    String       queryText;
    @FXML
    private JFXComboBox<String>         reportYear;
    @FXML
    private JFXComboBox<String>         reportMonth;
    @FXML
    private Pagination                  reportPagination;
    @FXML
    private TableView<Selled>           reportTable;
    @FXML
    private TableColumn<Selled, String> reportid;
    @FXML
    private TableColumn<Selled, String> reportNo;
    @FXML
    private TableColumn<Selled, String> reportProductName;
    @FXML
    private TableColumn<Selled, String> reportProductType;
    @FXML
    private TableColumn<Selled, String> reportProductGrade;
    @FXML
    private TableColumn<Selled, String> reportProductModel;
    @FXML
    private TableColumn<Selled, String> reportProductSize;
    @FXML
    private TableColumn<Selled, String> reportProductBrand;
    @FXML
    private TableColumn<Selled, String> reportTotalAmount;
    @FXML
    private TableColumn<Selled, String> reportUnitPrice;
    @FXML
    private TableColumn<Selled, String> reportTotalPrice;
    @FXML
    private TableColumn<Selled, String> reportCustomerName;
    @FXML
    private TableColumn<Selled, String> reportDate;
    @FXML
    private TableColumn<Selled, String> reportInvoiceNumber;
    @FXML
    private TableColumn<Selled, String> reportBroker;
    @FXML
    private TableColumn<Selled, String> selledBy;
    @FXML
    private JFXButton                   next;
    @FXML
    private Label                       itemPage;
    @FXML
    private JFXButton                   back;

    @FXML
    private void initialize() throws IOException {

        reportid.setCellValueFactory(new PropertyValueFactory<>("reportid"));
        reportNo.setCellValueFactory(cf -> {
            return new SimpleStringProperty((cf.getTableView().getItems().indexOf(cf.getValue()) + 1) + "");
        });
        reportProductName.setCellValueFactory(cd -> {
            if (cd.getValue().getProductType() != null)
                return new SimpleStringProperty(cd.getValue().getProductType().getName());
            return new SimpleStringProperty("");
        });
        reportProductGrade.setCellValueFactory(cd -> {
            if (cd.getValue().getProductType() != null)
                return new SimpleStringProperty(cd.getValue().getProductType().getGrade());
            return new SimpleStringProperty("");
        });
        reportProductSize.setCellValueFactory(cd -> {
            if (cd.getValue().getProductType() != null)
                return new SimpleStringProperty(cd.getValue().getProductType().getSize());
            return new SimpleStringProperty("");
        });
        reportProductName.setCellValueFactory(cd -> {
            if (cd.getValue().getProductType() != null)
                return new SimpleStringProperty(cd.getValue().getProductType().getName());
            return new SimpleStringProperty("");
        });
        reportProductType.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportProductBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        reportTotalAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        reportUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        reportTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        reportCustomerName.setCellValueFactory(cd -> {
            if (cd.getValue().getCustomer() != null)
                return new SimpleStringProperty(cd.getValue().getCustomer().getName());
            return new SimpleStringProperty("");
        });
        reportDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        reportInvoiceNumber.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        reportBroker.setCellValueFactory(cd -> {
            if (cd.getValue().getBroker() != null)
                return new SimpleStringProperty(cd.getValue().getBroker().getName());
            return new SimpleStringProperty("");
        });
        selledBy.setCellValueFactory(cd -> {
            if (cd.getValue().getSelledBy() != null)
                return new SimpleStringProperty(cd.getValue().getSelledBy().getName());
            return new SimpleStringProperty("");
        });
        reportTable.styleProperty().set("-fx-font-size:14");

        System.out.println("localdate+" + LocalDate.now());

        reportYear.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030");
        reportMonth.getItems().addAll("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec");

        String nowdate = LocalDate.now().getMonth().toString().substring(0, 3).toLowerCase();
        String nowyear = (LocalDate.now().getYear() + "");
        reportMonth.setValue(nowdate);
        reportYear.setValue(nowyear);
        reportPagination.setCurrentPageIndex(LocalDate.now().getDayOfMonth() - 1);

        updateReport();
        reportPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("oudvalue" + oldValue);
            System.out.println("oudvalue" + newValue);
            try {
                updateReport();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void changeMonth(ActionEvent event) throws IOException {

        if (reportMonth.getValue().equalsIgnoreCase("jan")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(31);
        } else if (reportMonth.getValue().equalsIgnoreCase("feb")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(29);
        } else if (reportMonth.getValue().equalsIgnoreCase("mar")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(31);
        } else if (reportMonth.getValue().equalsIgnoreCase("apr")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(30);
        } else if (reportMonth.getValue().equalsIgnoreCase("may")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(31);
        } else if (reportMonth.getValue().equalsIgnoreCase("jun")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(30);
        } else if (reportMonth.getValue().equalsIgnoreCase("jul")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(31);
        } else if (reportMonth.getValue().equalsIgnoreCase("aug")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(31);
        } else if (reportMonth.getValue().equalsIgnoreCase("sep")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(30);
        } else if (reportMonth.getValue().equalsIgnoreCase("oct")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(31);
        } else if (reportMonth.getValue().equalsIgnoreCase("nov")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(30);
        } else if (reportMonth.getValue().equalsIgnoreCase("dec")) {
            reportPagination.setCurrentPageIndex(0);
            reportPagination.setPageCount(31);
        }


        updateReport();


    }

    @FXML
    private void updateReport() throws IOException {


        try {
            int mo = (reportMonth.getSelectionModel().getSelectedIndex()) + 1;
            String mounth = "0" + mo + "";
            if (mounth.length() > 2)
                mounth = mounth.substring(1);

            int day = reportPagination.getCurrentPageIndex() + 1;
            String dayval = day + "";
            if (day < 10)
                dayval = "0" + dayval;


            queryText = reportYear.getValue() + "-" + mounth + "-" + dayval;
            String dateStart = queryText + " 00:00:00";
            String dateEnd = queryText + " 24:00:00";
            selledreports = Main.getSession().createQuery("from Selled where date > '" + dateStart + "' and date < '" + dateEnd + "'").getResultList();

            totalRows = selledreports.size();
            totalPage = totalRows / HardCodedValue.PAGELIMIT;
            if (totalRows % new HardCodedValue().PAGELIMIT != 0)
                totalPage++;
            currentPage = 1;
            itemPage.setText(" " + currentPage + "/" + totalPage);

            System.out.println("finished tread exec");
            System.out.println("query==" + queryText);
            reportTable.getItems().clear();
            reportTable.getItems().addAll(selledreports);

            System.out.println("after running ");
        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }

    }

    @FXML
    void changeYear(ActionEvent event) throws IOException {
        reportPagination.setCurrentPageIndex(0);
        reportMonth.setValue("jan");
        updateReport();


    }


    @FXML
    void getSelectedItem(MouseEvent event) {

        if (event.getClickCount() == 2)
            openEdit();

    }

    private void openEdit() {
        Selled prodType = reportTable.getSelectionModel().getSelectedItem();
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            AnchorPane anchorPane = loader.load(getClass().getResource(HardCodedValue.editSaleLayout).openStream());


            EditSelled typeController = loader.getController();
            typeController.setSalesID(prodType);
            if (prodType.getProductType() != null) {
                typeController.setNameText(prodType.getProductType().getName());
                typeController.setProductType(prodType.getProductType());
                typeController.setType(prodType.getProductType().getType());
                typeController.setGrade(prodType.getProductType().getGrade());
                typeController.setModel(prodType.getProductType().getModel());
                typeController.setSize(prodType.getProductType().getSize());
                typeController.setBrand(prodType.getProductType().getBrand());
                typeController.setProductMeasure(prodType.getProductType().getSellingMeasurment());
                typeController.brokerVisibility(prodType.getProductType().isHasBroker());
                typeController.setProductSalingRatio(prodType.getProductType().getSellingAmountRatio() + "");
            }
            if (prodType.getCustomer() != null) {
                typeController.setCustomerText(prodType.getCustomer().getName());
                typeController.setCustomerId(prodType.getCustomer());
            }
            if (prodType.getBroker() != null)
                typeController.setBrokerText(prodType.getBroker().getName());
            typeController.setUnitPrice(prodType.getUnitPrice() + "");
            typeController.setTotalPrice(prodType.getTotalPrice() + "");
            typeController.setAmount(prodType.getAmount() + "");
            typeController.setBrokerPrice(prodType.getBrokerPrice() + "");
            typeController.setBrokerPayedChekcBox(prodType.isBrokerPayed());

            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(reportTable.getScene().getWindow());
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);


            stage.setOnCloseRequest(event1 -> {

                try {
                    updateReport();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            stage.setOnHidden(event3 -> {
                try {
                    updateReport();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            updateReport();
            itemPage.setText(" " + currentPage + "/" + totalPage);

        }
    }


    @FXML
    void nextload(ActionEvent event) throws IOException {
        if (currentPage < totalPage) {
            currentPage++;
            updateReport();
            itemPage.setText(" " + currentPage + "/" + totalPage);
        }
    }
}
