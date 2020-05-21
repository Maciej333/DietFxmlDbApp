package diet.gui.controlers;

import diet.model.Diet;
import diet.model.Profil;
import diet.model.database.DietData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class MainWindowDietController {

    private ObservableList<Diet> dietsList;

    @FXML
    private TableView<Diet> tableViewDiet;
    @FXML
    private TableColumn<Diet, String> dietDate;
    @FXML
    private TableColumn<Diet, Double> dietKcal;
    @FXML
    private TableColumn<Diet, Double> dietProtein;
    @FXML
    private TableColumn<Diet, Double> dietFat;
    @FXML
    private TableColumn<Diet, Double> dietCarbs;
    @FXML
    private TableColumn<Diet, Double> dietFiber;

    @FXML
    private Button buttonAddProductMeal;

    @FXML
    private DatePicker datePickerDiet;

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
    @FXML
    private Label labelEatenCarbs;
    @FXML
    private Label labelMaxCarbs;
    @FXML
    private Label labelEatenFiber;
    @FXML
    private Label labelMaxFiber;
    @FXML
    private Label kcalBilans;


    public void initialize() {
        DietData.getInstance().readDietForProfil();
        dietsList = DietData.getDietsList();

        dietDate.setCellValueFactory(new PropertyValueFactory<>("formatDate"));
        dietKcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        dietProtein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        dietFat.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        dietCarbs.setCellValueFactory(new PropertyValueFactory<>("fat"));
        dietFiber.setCellValueFactory(new PropertyValueFactory<>("fiber"));

        datePickerDiet.setValue(LocalDate.now());
        ObservableList<Diet> currentDiets = Diet.getDietsByDate(dietsList,datePickerDiet.getValue());

        datePickerDiet.valueProperty().addListener((observable, oldDate, newDate )->{
            ObservableList<Diet>  newCurrentDiets = Diet.getDietsByDate(dietsList,newDate);
            tableViewDiet.setItems(newCurrentDiets);
            Double[] newStatsOfDiets = Diet.countStatsForDiets(newCurrentDiets);
            labelEatenKcal.setText(newStatsOfDiets[0].toString());
            labelEatenProtein.setText(newStatsOfDiets[1].toString());
            labelEatenFat.setText(newStatsOfDiets[2].toString());
            labelEatenCarbs.setText(newStatsOfDiets[3].toString());
            labelEatenFiber.setText(newStatsOfDiets[4].toString());
            kcalBilans.setText(newStatsOfDiets[5].toString());
        } );

        tableViewDiet.setItems(currentDiets);

        Profil.getSelectedProfil().countKcal();
        Profil.getSelectedProfil().countProtein();
        Profil.getSelectedProfil().countFat();
        Profil.getSelectedProfil().countCarbs();
        Profil.getSelectedProfil().countFiber();
        labelMaxKcal.setText(Profil.getSelectedProfil().getKcal()+"");
        labelMaxProtein.setText(Profil.getSelectedProfil().getProtein()+"");
        labelMaxFat.setText(Profil.getSelectedProfil().getFat()+"");
        labelMaxCarbs.setText(Profil.getSelectedProfil().getCarbs()+"");
        labelMaxFiber.setText(Profil.getSelectedProfil().getFiber()+"");

        Double[] statsOfDiets = Diet.countStatsForDiets(currentDiets);
        labelEatenKcal.setText(statsOfDiets[0].toString());
        labelEatenProtein.setText(statsOfDiets[1].toString());
        labelEatenFat.setText(statsOfDiets[2].toString());
        labelEatenCarbs.setText(statsOfDiets[3].toString());
        labelEatenFiber.setText(statsOfDiets[4].toString());
        kcalBilans.setText(statsOfDiets[5].toString());
    }

    @FXML
    public void setButtonAddProductMeal(){

    }
}
