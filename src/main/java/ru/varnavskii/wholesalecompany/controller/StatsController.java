package ru.varnavskii.wholesalecompany.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.varnavskii.wholesalecompany.dao.GoodsDao;
import ru.varnavskii.wholesalecompany.dao.SalesDao;
import ru.varnavskii.wholesalecompany.dao.TopGoodsDao;
import ru.varnavskii.wholesalecompany.entity.GoodsEntity;
import ru.varnavskii.wholesalecompany.entity.SalesEntity;
import ru.varnavskii.wholesalecompany.entity.TopGoodsEntity;
import ru.varnavskii.wholesalecompany.util.ReportExcel;

public class StatsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button demandButton;

    @FXML
    private Button demandChartButton;

    @FXML
    private TextField endDate;

    @FXML
    private TextField goodIdChartTextField;

    @FXML
    private TextField goodIdTextField;

    @FXML
    private TableColumn<TopGoodsEntity, Integer> good_count;

    @FXML
    private TableColumn<TopGoodsEntity, String> good_name;

    @FXML
    private Button menuButton;

    @FXML
    private TextField resultTextField;

    @FXML
    private TextField startDate;

    @FXML
    private TableView<TopGoodsEntity> topGoods;

    @FXML
    private LineChart<String, Integer> lineChart;

    @FXML
    void checkDemand(ActionEvent event) {
        String startDateFormated = startDate.getText().concat(" 00:00:00");
        String endDateFormated = endDate.getText().concat(" 00:00:00");
        Integer id = Integer.parseInt(goodIdTextField.getText());
        Timestamp start = Timestamp.valueOf(startDateFormated);
        Timestamp end = Timestamp.valueOf(endDateFormated);
        List<Integer> result = SalesDao.getInstance().selectDemain(id, start, end);
        int resultDemain = 0;
        for (int i = 0; i < result.size(); i++) {
            if (resultDemain > result.get(i)) {
                resultDemain -= result.get(i);
            } else {
                resultDemain += result.get(i);
            }
        }
        if (resultDemain < 0) {
            resultTextField.setText("Спрос упал на: " + resultDemain / -1);
        } else {
            resultTextField.setText("Спрос увеличился на: " + resultDemain);
        }
    }

    @FXML
    void checkDemandChart(ActionEvent event) {
        lineChart.getXAxis().setAutoRanging(true);
        Integer id = Integer.parseInt(goodIdChartTextField.getText());
        Map<String, Integer> map = SalesDao.getInstance().selectDemain(id);
        XYChart.Series series = new XYChart.Series();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }
        lineChart.getData().add(series);
    }

    @FXML
    void goMenu(ActionEvent event) {
        menuButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/menu-view.fxml"));
        try {
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        good_name.setCellValueFactory(new PropertyValueFactory<TopGoodsEntity, String>("name"));
        good_count.setCellValueFactory(new PropertyValueFactory<TopGoodsEntity, Integer>("goodCount"));
        ObservableList<TopGoodsEntity> list = TopGoodsDao.getInstance().select();
        topGoods.setItems(list);
    }
}
