package diet.gui.controlers;

import diet.model.Diet;
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
        datePickerDiet.valueProperty().addListener((observable, oldDate, newDate )->{
            tableViewDiet.setItems(Diet.getDietsByDate(dietsList,newDate));
        } );

        tableViewDiet.setItems(Diet.getDietsByDate(dietsList,datePickerDiet.getValue()));
    }

    @FXML
    public void setButtonAddProductMeal(){

    }
}
