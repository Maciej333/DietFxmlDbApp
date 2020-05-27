package diet.gui.controlers;

import diet.model.Food;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.database.MealData;
import diet.model.database.ProductData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DietAddMealProductController {

    private List<Food> mealsProductsList = new ArrayList<>();

    @FXML
    private TextField textFieldSearchMealProductDietAdd;
    @FXML
    private TableView<Food> TableViewMealProductDietAdd;
    @FXML
    private TableColumn<Food, String> productMealColumn;
    @FXML
    private TextField textFieldAmountMealProductDietAdd;
    @FXML
    private Label labelInvalidAmountMealProductDietAdd;
    @FXML
    private Button buttonAddMealProductDietAdd;
    @FXML
    private Button buttonCancelMealProductDietAdd;

    public void initialize() {
        mealsProductsList.addAll(ProductData.getProductsList());
        mealsProductsList.addAll(MealData.getMealsList());

        productMealColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (mealsProductsList != null) {
            TableViewMealProductDietAdd.setItems(FXCollections.observableList(mealsProductsList));

            textFieldSearchMealProductDietAdd.setOnKeyTyped((change) -> {
                List<Food> sortedProductList = mealsProductsList.stream().filter((productMeal) ->
                        productMeal.getName().toLowerCase().matches(".*(" + textFieldSearchMealProductDietAdd.getText().toLowerCase() + ").*"))
                        .collect(Collectors.toList());
                TableViewMealProductDietAdd.setItems(FXCollections.observableList(sortedProductList));
            });
        }
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldAmountMealProductDietAdd, labelInvalidAmountMealProductDietAdd, "\\d+", "Invalid", "");
    }

    @FXML
    public void setButtonAddMealProductDietAdd() {
        Food productMealToAdd = TableViewMealProductDietAdd.getSelectionModel().getSelectedItem();
        String amount = textFieldAmountMealProductDietAdd.getText();
        if (productMealToAdd != null) {
            if (amount.matches("\\d+")) {
                int intAmount = Integer.parseInt(amount);

                DietAddEditController.putNewMealProductAmount(productMealToAdd, intAmount);

                Stage stage = (Stage) TableViewMealProductDietAdd.getScene().getWindow();
                stage.close();
            } else {
                Alert alertNoChoosen = new Alert(Alert.AlertType.INFORMATION);
                alertNoChoosen.setTitle("Invalid amount ");
                alertNoChoosen.setContentText("Invalid amount, enter integer number >0");
                alertNoChoosen.show();
            }
        } else {
            Alert alertNoChoosen = new Alert(Alert.AlertType.INFORMATION);
            alertNoChoosen.setTitle("No product/meal to add ");
            alertNoChoosen.setContentText("add product/meal to diet or click cancel");
            alertNoChoosen.show();
        }
    }

    @FXML
    public void setButtonCancelMealProductDietAdd() {
        Stage stage = (Stage) TableViewMealProductDietAdd.getScene().getWindow();
        stage.close();
    }
}
