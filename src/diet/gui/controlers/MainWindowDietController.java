package diet.gui.controlers;

import diet.model.Diet;
import diet.model.database.DietData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    Button buttonAddProductMeal;


    public void initialize() {
        DietData.getInstance().readDietForProfil();
        dietsList = DietData.getDietsList();

        dietDate.setCellValueFactory(new PropertyValueFactory<>("formatDate"));
        dietKcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        dietProtein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        dietFat.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        dietCarbs.setCellValueFactory(new PropertyValueFactory<>("fat"));
        dietFiber.setCellValueFactory(new PropertyValueFactory<>("fiber"));
        tableViewDiet.setItems(dietsList);
    }

    @FXML
    public void setButtonAddProductMeal(){

    }
}
