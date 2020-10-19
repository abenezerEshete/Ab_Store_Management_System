package controller;

import Utility.FieldValues;
import Utility.HardCodedValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import main.Main;
import model.StoreProduct;
import org.hibernate.query.Query;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Dashboard {

    @FXML
    private PieChart sellingDrugPieChart;

    @FXML
    private StackedBarChart<?, ?> barchart;

    @FXML
    private BarChart<String, Number> weeklyReportBarChart;

    @FXML
    private BarChart<String, Number> monthlyReportBarChart;

    @FXML
    private ListView<HBox> alertProductListView;

    @FXML
    private CategoryAxis saleXAxis;

    @FXML
    private NumberAxis saleYAxis;

    @FXML
    private CategoryAxis incomeXAxis;

    @FXML
    private NumberAxis incomeYAxis;

    @FXML
    private void initialize() throws IOException {

        System.out.println("hiiii");
        pieChart();


        weeklySellingGraph();

        weeklyIncomeGraph();
        alertProduct();


    }

    private void weeklyIncomeGraph() {
        try {

            LocalDate last7Day = LocalDate.now().minusMonths(1);
            Query weeklySellingIncomeGraphQuery = Main.getSession().createQuery("SELECT  day(date) as DATE, SUM(totalPrice) " +
                    "FROM      Selled  WHERE DATE(date)  > '" + last7Day + "' GROUP BY  DATE(date)");

            List<Object[]> weeklySellingIncome = weeklySellingIncomeGraphQuery.list();

            System.out.println("weeklySellingIncome="+weeklySellingIncome);

            incomeXAxis = new CategoryAxis();
            incomeYAxis = new NumberAxis();
            XYChart.Series series = new XYChart.Series();
            series = new XYChart.Series();
            HBox hb = new HBox();
            hb = new HBox();
            hb.setStyle("-fx-bar-fill: green");
            series.setNode(hb);
            series.setName("total income from sales per Day");

            String dayText = "";
            if (weeklySellingIncome != null && !weeklySellingIncome.isEmpty()) {
                int firstDay = (int) weeklySellingIncome.get(0)[0] - 1;
                System.out.println("fisrt day: " + firstDay + "--" + weeklySellingIncome.size());
                double count = 0;
                for (int i = 0; i < weeklySellingIncome.size(); i++) {


                    dayText =  weeklySellingIncome.get(i)[0].toString();
                    count = Double.parseDouble(weeklySellingIncome.get(i)[1] + "");

                    series.getData().add(new XYChart.Data(dayText, count));

                }
            }
//        monthlyReportBarChart.getXAxis().setLabel("Date");
//        monthlyReportBarChart.getYAxis().setLabel("Number of sells");
            monthlyReportBarChart.getData().add(series);


            //sellingDrugPieChart.setLegendVisible(true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            int dayOfTheYear = LocalDate.parse("2019-10-31", formatter).getDayOfYear();
            int dayOfTheWeek = LocalDate.parse("2019-10-31", formatter).getDayOfWeek().getValue();
            int year = LocalDate.parse("2019-10-31", formatter).getYear();

            System.out.println("===" + dayOfTheYear);
            System.out.println("+++" + dayOfTheWeek);
            if (dayOfTheWeek != 1) {
                dayOfTheWeek = dayOfTheWeek - 1;
                dayOfTheYear = dayOfTheYear - dayOfTheWeek;
                System.out.println("****" + dayOfTheYear);

                System.out.println("_____" + LocalDate.ofYearDay(year, dayOfTheYear).getDayOfWeek());
            }
            Date date = new Date();
            //  date.setTime(LocalDate.now().);
            // boolean before = date.before(new Date(LocalDate.now().toEpochDay()));

            System.out.println("date" + date);


        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }

    private void alertProduct() {
        //     alertProductListView.item
        try {
            System.out.println("lo==" + HardCodedValue.reportItemLayout);
            System.out.println("lo==" + Main.class.getResource(HardCodedValue.reportItemLayout));
            HBox hBoxtitle = FXMLLoader.load(Main.class.getResource(HardCodedValue.reportItemLayout));
            Label nameTitle = (Label) hBoxtitle.getChildren().get(0);
            nameTitle.setText("Name");
            HBox rightitemtitle = (HBox) hBoxtitle.getChildren().get(2);
            Label amountTitle = (Label) rightitemtitle.getChildren().get(0);
            amountTitle.setText("Amount");
            hBoxtitle.paddingProperty().set(new Insets(5, 5, 5, 5));

            alertProductListView.getItems().addAll(hBoxtitle);

            Query alertAmountQuery = Main.getSession().createQuery("FROM StoreProduct where amount  < 5 ");
            List<StoreProduct> storeProducts = alertAmountQuery.getResultList();
            storeProducts.forEach(product -> {


                try {
                    HBox hBox = FXMLLoader.load(getClass().getResource(HardCodedValue.reportItemLayout));
                    Label name = (Label) hBox.getChildren().get(0);
                    Label detail = (Label) hBox.getChildren().get(1);
                    HBox rightitem = (HBox) hBox.getChildren().get(2);
                    Label amount = (Label) rightitem.getChildren().get(0);


                    name.setText("");

                    String text = "";
                    if (product.getProductType() != null) {
                        product.getProductType().getName();
                        if (!product.getProductType().getType().isEmpty())
                            text = text + " - " + product.getProductType().getType();
                        if (!product.getProductType().getModel().isEmpty())
                            text = text + " - " + product.getProductType().getModel();
                        if (!product.getProductType().getBrand().isEmpty())
                            text = text + " - " + product.getProductType().getBrand();
                        if (!product.getProductType().getSize().isEmpty())
                            text = text + " - " + product.getProductType().getSize();
                        if (!product.getProductType().getGrade().isEmpty())
                            text = text + " - " + product.getProductType().getGrade();

                        detail.setText(text);
                        amount.setText("" + (int) product.getAmount());


                        alertProductListView.getItems().addAll(hBox);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }

    private void weeklySellingGraph() {
        try {
            LocalDate last7Day = LocalDate.now().minusMonths(1);
            Query weeklySellingGraphQuery = Main.getSession().createQuery("SELECT count(*), day(date) FROM Selled  " +
                    "where DATE(date)  > '" + last7Day + "' group by day(date) order by date");

            List<Object[]> weeklySelling = weeklySellingGraphQuery.list();

            saleXAxis.setLabel("Date");
            saleYAxis.setLabel("Number of sells");
            XYChart.Series series = new XYChart.Series();
            HBox hb = new HBox();
            hb.setStyle("-fx-bar-fill: green");
            series.setNode(hb);
            series.setName("Number of sales per Day");
            String dayText = "";
            if (weeklySelling != null && !weeklySelling.isEmpty()) {
                int firstDay = (int) weeklySelling.get(0)[1] - 1;
                System.out.println("fisrt day: " + firstDay + "--" + weeklySelling.size());
                int count = 0;
                for (int i = 0; i < weeklySelling.size(); i++) {

                    dayText = weeklySelling.get(i)[1].toString();
                    count = Integer.parseInt(weeklySelling.get(i)[0] + "");

                    series.getData().add(new XYChart.Data(dayText, count));

                }
            }
            weeklyReportBarChart.getXAxis().setLabel("Date");
            weeklyReportBarChart.getYAxis().setLabel(" sales");
            weeklyReportBarChart.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }

    private void pieChart() {
        try {
            ArrayList<PieChart.Data> data = new ArrayList<>();
            LocalDate lastMonth = LocalDate.now().minusMonths(1);
            Query piaChartDataQuery = Main.getSession().createQuery(" select count(*), productType.name  FROM Selled "+
                    "where DATE(date) > '" + lastMonth + "'  group by productType HAVING COUNT(productType) > 2");


            List<Object[]> piaChartData = piaChartDataQuery.list();

            System.out.println("Piachart="+piaChartData);

            for (int i = 0; i < piaChartData.size(); i++) {
                String name = (String) piaChartData.get(i)[1];
                int value = Integer.parseInt(piaChartData.get(i)[0] + "");
                data.add(new PieChart.Data(name, value));
            }

            sellingDrugPieChart.getData().addAll(data);
            sellingDrugPieChart.setStartAngle(10);
            sellingDrugPieChart.setLabelsVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            FieldValues.LogError(e);
        }
    }
}
