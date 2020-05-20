package diet.gui.controlers;

import diet.model.Meal;
import diet.model.database.MealData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainWindowMealController {

    private ObservableList<Meal> mealsList;

    @FXML
    private TableView<Meal> tableViewMeal;
    @FXML
    private TableColumn<Meal, String> mealName;
    @FXML
    private TableColumn<Meal, Double> mealKcal;
    @FXML
    private TableColumn<Meal, Double> mealProtein;
    @FXML
    private TableColumn<Meal, Double> mealFat;
    @FXML
    private TableColumn<Meal, Double> mealCarbs;
    @FXML
    private TableColumn<Meal, Double> mealFiber;

    public void initialize() {
        MealData.getInstance().readAllMealForProfil();
        mealsList = MealData.getMealsListList();


        mealName.setCellValueFactory(new PropertyValueFactory<>("name"));
        mealKcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        mealProtein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        mealFat.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        mealCarbs.setCellValueFactory(new PropertyValueFactory<>("fat"));
        mealFiber.setCellValueFactory(new PropertyValueFactory<>("fiber"));
        tableViewMeal.setItems(mealsList);
    }
}
