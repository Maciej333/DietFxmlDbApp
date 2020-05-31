package diet.gui.controlers;

import diet.model.Diet;
import diet.model.Profil;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.additionalClasses.ClassOfStaticMethodForControllers;
import diet.model.database.DietData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class MainWindowStatsController {

    private ObservableList<Diet> dietsList;
    private ObservableList<XYChart.Series<String, Number>> dietsStackBarList;

    @FXML
    private StackedBarChart<String, Number> stackedBarChartStats;

    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;
    @FXML
    private Button buttonSeeSelectDateStats;

    @FXML
    private Button buttonWeekStats;
    @FXML
    private Button buttonMonthStats;
    @FXML
    private Button buttonYearStats;

    @FXML
    private Label labelEatenKcal;
    @FXML
    private Label labelMaxKcal;
    @FXML
    private Label labelEatenProtein;
    @FXML
    private Label labelMaxProtein;
    @FXML
    private Label labelEatenFat;
    @FXML
    private Label labelMaxFat;
    ;
    @FXML
    private Label labelEatenCarbs;
    @FXML
    private Label labelMaxCarbs;
    @FXML
    private Label labelEatenFiber;
    @FXML
    private Label labelMaxFiber;

    @FXML
    private Label labelKcalBilans;

    public void initialize() {
        dietsList = DietData.getDietsList();
        stackedBarChartStats.getXAxis().setLabel("Date");
        stackedBarChartStats.getYAxis().setLabel("Kcal");
        setButtonWeekStats();
        datePickerTo.setValue(LocalDate.now());
        datePickerFrom.setValue(LocalDate.now().minusDays(7));
    }

    @FXML
    public void setButtonSeeSelectDateStats() {
        stackedBarChartStats.getData().clear();
        stackedBarChartStats.layout();
        LocalDate to = datePickerTo.getValue();
        LocalDate from = datePickerFrom.getValue();
        int limit = (int) ChronoUnit.DAYS.between(from, to);
        if (limit > 0) {
            ((CategoryAxis) stackedBarChartStats.getXAxis()).setCategories(FXCollections.<String>observableList(Arrays.asList(generateTable(limit))));
            createStackedarChartStats(createSelectedDietList(generateTable(limit), to));
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("invalid period of time", "Date to must be higher to date from");
        }
    }

    @FXML
    public void setButtonWeekStats() {
        stackedBarChartStats.getData().clear();
        stackedBarChartStats.layout();
        int limit = 7;
        ((CategoryAxis) stackedBarChartStats.getXAxis()).setCategories(FXCollections.<String>observableList(Arrays.asList(generateTable(limit))));
        createStackedarChartStats(createSelectedDietList(generateTable(limit), LocalDate.now()));
    }

    @FXML
    public void setButtonMonthStats() {
        stackedBarChartStats.getData().clear();
        stackedBarChartStats.layout();
        int limit = 30;
        ((CategoryAxis) stackedBarChartStats.getXAxis()).setCategories(FXCollections.<String>observableList(Arrays.asList(generateTable(limit))));
        createStackedarChartStats(createSelectedDietList(generateTable(limit), LocalDate.now()));
    }

    @FXML
    public void setButtonYearStats() {
        stackedBarChartStats.getData().clear();
        stackedBarChartStats.layout();
        int limit = 365;
        ((CategoryAxis) stackedBarChartStats.getXAxis()).setCategories(FXCollections.<String>observableList(Arrays.asList(generateTable(limit))));
        createStackedarChartStats(createSelectedDietList(generateTable(limit), LocalDate.now()));
    }

    private Double[] createSelectedDietList(String[] table, LocalDate to) {
        Double[] selectedDietKcal = new Double[table.length];
        double eatenKcal = 0.0;
        double eatenProtein = 0.0;
        double eatenFat = 0.0;
        double eatenCarbs = 0.0;
        double eatenFiber = 0.0;
        double maxKcal = Profil.getSelectedProfil().getKcal() * table.length;
        double maxProtein = Profil.getSelectedProfil().getProtein() * table.length;
        double maxFat = Profil.getSelectedProfil().getFat() * table.length;
        double maxCarbs = Profil.getSelectedProfil().getCarbs() * table.length;
        double maxFiber = Profil.getSelectedProfil().getFiber() * table.length;
        for (int i = 0; i < selectedDietKcal.length; i++) {
            selectedDietKcal[i] = 0.0;
        }
        for (Diet diet : dietsList) {
            for (int i = table.length - 1; i >= 0; i--) {
                if (diet.getDate().toLocalDate().isEqual(to.minusDays(Integer.parseInt(table[i]) - 1))) {
                    selectedDietKcal[Integer.parseInt(table[table.length - 1]) - Integer.parseInt(table[i])] += diet.getKcal();
                    eatenKcal += diet.getKcal();
                    eatenProtein += diet.getProtein();
                    eatenFat += diet.getFat();
                    eatenCarbs += diet.getCarbs();
                    eatenFiber += diet.getFiber();
                }
            }
        }
        labelEatenKcal.setText(ClassOfStaticMethod.roundDouble(eatenKcal) + "");
        labelEatenProtein.setText(ClassOfStaticMethod.roundDouble(eatenProtein) + "");
        labelEatenFat.setText(ClassOfStaticMethod.roundDouble(eatenFat) + "");
        labelEatenCarbs.setText(ClassOfStaticMethod.roundDouble(eatenCarbs) + "");
        labelEatenFiber.setText(ClassOfStaticMethod.roundDouble(eatenFiber) + "");
        labelMaxKcal.setText(ClassOfStaticMethod.roundDouble(maxKcal) + "");
        labelMaxProtein.setText(ClassOfStaticMethod.roundDouble(maxProtein) + "");
        labelMaxFat.setText(ClassOfStaticMethod.roundDouble(maxFat) + "");
        labelMaxCarbs.setText(ClassOfStaticMethod.roundDouble(maxCarbs) + "");
        labelMaxFiber.setText(ClassOfStaticMethod.roundDouble(maxFiber) + "");
        labelKcalBilans.setText((ClassOfStaticMethod.roundDouble(eatenKcal - maxKcal)) + "");
        datePickerTo.setValue(to);
        datePickerFrom.setValue(to.minusDays(table.length));
        return selectedDietKcal;
    }

    private void createStackedarChartStats(Double[] tableKcal) {
        StackedBarChart.Series<String, Number> dataSeries1 = new StackedBarChart.Series<>();
        StackedBarChart.Series<String, Number> dataSeries2 = new StackedBarChart.Series<>();
        StackedBarChart.Series<String, Number> dataSeries3 = new StackedBarChart.Series<>();
        for (int i = 0; i < tableKcal.length; i++) {
            String date = (i + 1) + "";
            if (tableKcal[i] == 0) {
            } else if (tableKcal[i] > Profil.getSelectedProfil().getKcal()) {
                dataSeries1.getData().add(new XYChart.Data<>(date, Profil.getSelectedProfil().getKcal()));
                dataSeries2.getData().add(new XYChart.Data<>(date, tableKcal[i] - Profil.getSelectedProfil().getKcal()));
                dataSeries3.getData().add(new XYChart.Data<>(date, 0));
            } else if (tableKcal[i] < Profil.getSelectedProfil().getKcal()) {
                dataSeries1.getData().add(new XYChart.Data<>(date, tableKcal[i]));
                dataSeries2.getData().add(new XYChart.Data<>(date, 0));
                dataSeries3.getData().add(new XYChart.Data<>(date, Profil.getSelectedProfil().getKcal() - tableKcal[i]));
            }
        }
        stackedBarChartStats.getData().addAll(dataSeries1, dataSeries2, dataSeries3);
    }

    private String[] generateTable(int limit) {
        String[] table = new String[limit];
        for (int i = 0; i < limit; i++)
            table[i] = (i + 1) + "";
        return table;
    }
}
