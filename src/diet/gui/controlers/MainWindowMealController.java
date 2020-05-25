package diet.gui.controlers;

import diet.model.Meal;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.database.MealData;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainWindowMealController {

    private static String loadedMealFxml = null;
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

    @FXML
    private TextField textFieldMealSearch;
    @FXML
    private Button buttonAddNewMeal;
    @FXML
    private Button buttonEditMeal;
    @FXML
    private Button buttonDeleteMeal;

    public void initialize() {
        mealsList = MealData.getMealsList();

        mealName.setCellValueFactory(new PropertyValueFactory<>("name"));
        mealKcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        mealProtein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        mealFat.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        mealCarbs.setCellValueFactory(new PropertyValueFactory<>("fat"));
        mealFiber.setCellValueFactory(new PropertyValueFactory<>("fiber"));
        tableViewMeal.setItems(mealsList);

        textFieldMealSearch.setOnKeyTyped((change)->{
            List<Meal> sortedMealList = mealsList.stream().filter((meal)->
                    meal.getName().toLowerCase().matches(".*("+textFieldMealSearch.getText().toLowerCase()+").*"))
                    .collect( Collectors.toList());
            tableViewMeal.setItems(FXCollections.observableList(sortedMealList));
        });

        MealData.getMealsList().addListener(new ListChangeListener<Meal>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Meal> change) {
                tableViewMeal.setItems(mealsList);
                while (change.next()) {
                    if (change.wasAdded()) {
                        tableViewMeal.refresh();
                        tableViewMeal.getSortOrder().add(mealName);
                    }
                    if (change.wasRemoved()) {
                        tableViewMeal.getSortOrder().add(mealName);
                    }
                }
            }
        });
    }

    @FXML
    public void setButtonAddNewMeal() {
        loadedMealFxml = "Add";
        Path pathNewMeal = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MealAdd.fxml");
        ClassOfStaticMethod.loadUrl(pathNewMeal,"Meal");
    }

    @FXML
    public void setButtonEditMeal() {
        loadedMealFxml = "Edit";
        Meal.setSelectedMeal(tableViewMeal.getSelectionModel().getSelectedItem());
        if (Meal.getSelectedMeal() != null) {
            Path pathNewMeal = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MealAdd.fxml");
            ClassOfStaticMethod.loadUrl(pathNewMeal,"Meal");
        } else {
            Alert alertNoChoosen = new Alert(Alert.AlertType.INFORMATION);
            alertNoChoosen.setTitle("No meal selected");
            alertNoChoosen.setContentText("choose meal by clicking it in table");
            alertNoChoosen.show();
        }
    }

    @FXML
    public void setButtonDeleteMeal() {
        Meal.setSelectedMeal(tableViewMeal.getSelectionModel().getSelectedItem());
        if (Meal.getSelectedMeal() != null) {
            Alert alertNoChoosen = new Alert(Alert.AlertType.WARNING);
            alertNoChoosen.setContentText("Do you really want to delete meal " + Meal.getSelectedMeal().getName() + "?");
            alertNoChoosen.setTitle("Delete confirmation");

            Optional<ButtonType> result = alertNoChoosen.showAndWait();
            if (result.get() == ButtonType.OK) {

            } else {
                alertNoChoosen.close();
            }
        } else {
            Alert alertNoChoosen = new Alert(Alert.AlertType.INFORMATION);
            alertNoChoosen.setTitle("No meal selected");
            alertNoChoosen.setContentText("choose meal by clicking it in table");
            alertNoChoosen.show();
        }
    }

    public static String getLoadedMealFxml() {
        return loadedMealFxml;
    }

}