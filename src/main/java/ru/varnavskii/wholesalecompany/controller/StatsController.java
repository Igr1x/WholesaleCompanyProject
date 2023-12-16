package ru.varnavskii.wholesalecompany.controller;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.varnavskii.wholesalecompany.dao.SalesDao;
import ru.varnavskii.wholesalecompany.dao.TopGoodsDao;
import ru.varnavskii.wholesalecompany.entity.TopGoodsEntity;
import ru.varnavskii.wholesalecompany.util.ControllerUtil;
import ru.varnavskii.wholesalecompany.util.ScenesPaths;

public class StatsController {

    private final Logger log = LoggerFactory.getLogger(StatsController.class);

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
        try {
            List<Integer> result = SalesDao.getInstance().selectDemain(id, start, end);
            calcDemandChanges(result, id);
        } catch (NumberFormatException e) {
            log.warn(ControllerUtil.INCORRECT_DATA);
        } finally {
            startDate.clear();
            endDate.clear();
            goodIdTextField.clear();
        }
    }

    @FXML
    void checkDemandChart(ActionEvent event) {
        lineChart.getData().clear();
        try {
            Integer id = Integer.parseInt(goodIdChartTextField.getText());
            Map<String, Integer> map = SalesDao.getInstance().selectMapDemain(id);
            XYChart.Series series = new XYChart.Series();
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
            }
            lineChart.getData().add(series);
        } catch (NumberFormatException e) {
            log.warn(ControllerUtil.INCORRECT_DATA);
        } finally {
            goodIdChartTextField.clear();
        }
    }

    @FXML
    void goMenu(ActionEvent event) {
        ControllerUtil.openScene(menuButton, ScenesPaths.MENU_PATH);
    }

    @FXML
    void initialize() {
        initializeTableOfGoods();
    }

    private void initializeTableOfGoods() {
        good_name.setCellValueFactory(new PropertyValueFactory<TopGoodsEntity, String>("name"));
        good_count.setCellValueFactory(new PropertyValueFactory<TopGoodsEntity, Integer>("goodCount"));
        ObservableList<TopGoodsEntity> list = TopGoodsDao.getInstance().select();
        topGoods.setItems(list);
    }

    private StringBuilder calcDemandChanges(List<Integer> demandList, int goodId) {
        int resultDemain = 0;
        for (int i = 0; i < demandList.size(); i++) {
            resultDemain = (resultDemain > demandList.get(i)) ? (resultDemain - demandList.get(i)) : (resultDemain + demandList.get(i));
        }
        if (resultDemain < 0) {
            return new StringBuilder("Спрос на товар " + goodId + " упал на: " + resultDemain / -1);
        }
        if (resultDemain == 0) {
            return new StringBuilder("Спрос на товар " + goodId + " не изменился");
        }
        return new StringBuilder("Спрос на товар " + goodId + " увеличился на: " + resultDemain);
    }
}
