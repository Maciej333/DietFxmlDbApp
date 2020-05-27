package diet.gui.controlers;

import diet.model.Meal;
import diet.model.Product;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.database.MealData;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class MealAddEditController {

    private static Meal newMeal;
    private static ObservableMap<Product, Integer> productMap = FXCollections.observableMap(new HashMap<>());

    @FXML
    private TextField textFieldMealAddName;
    @FXML
    private Label labelMealNameInvalid;
    @FXML
    private TextField textFieldMealAddSearch;
    @FXML
    private TableView<Map.Entry<Product, Integer>> tableViewMealAdd;
    @FXML
    private TableColumn<Map.Entry<Product, Integer>, String> tableColumnProductMealAdd;
    @FXML
    private TableColumn<Map.Entry<Product, Integer>, Integer> tableColumnAmountMealAdd;

    @FXML
    private Button buttonAddProductToMeal;
    @FXML
    private Button buttonDeleteProductFromMeal;
    @FXML
    private Button buttonDoMeal;
    @FXML
    private Button buttonCancelMealAdd;

    public void initialize() {
        tableViewMealAdd.setEditable(true);
        tableViewMealAdd.getSelectionModel().setCellSelectionEnabled(true);
        tableColumnAmountMealAdd.setEditable(true);
        tableColumnAmountMealAdd.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumnAmountMealAdd.setOnEditCommit(event -> {
            int editAmount = event.getNewValue() > 0 ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setValue(editAmount);
            tableViewMealAdd.refresh();
        });
        tableColumnProductMealAdd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Product, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Product, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey().getName());
            }
        });
        tableColumnAmountMealAdd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Product, Integer>, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Map.Entry<Product, Integer>, Integer> p) {
                SimpleIntegerProperty sip = new SimpleIntegerProperty();
                sip.set(p.getValue().getValue());
                return new SimpleIntegerProperty(p.getValue().getValue()).asObject();
            }
        });

        textFieldMealAddSearch.setOnKeyTyped((change) -> {
            if (productMap != null) {
                Map<Product, Integer> sortedProductMap = productMap.entrySet().stream().filter((productEntrySet) ->
                        productEntrySet.getKey().getName().toLowerCase().matches(".*(" + textFieldMealAddSearch.getText().toLowerCase() + ").*"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                tableViewMealAdd.setItems(FXCollections.observableArrayList(sortedProductMap.entrySet()));
            }
        });

        ClassOfStaticMethod.checkCorrectOfTextField(textFieldMealAddName, labelMealNameInvalid, "(\\S)+(\\s.+)*", "Invalid", "");

        if (MainWindowMealController.getLoadedMealFxml().equals("Edit")) {
            productMap = FXCollections.observableMap(Meal.getSelectedMeal().getProductsForMeal());
            textFieldMealAddName.setText(Meal.getSelectedMeal().getName());
            buttonDoMeal.setText("Edit");
        } else {
            newMeal = new Meal();
        }

        productMap.addListener(new MapChangeListener<Product, Integer>() {
            @Override
            public void onChanged(Change<? extends Product, ? extends Integer> change) {
                tableViewMealAdd.setItems(FXCollections.observableArrayList(productMap.entrySet()));
                tableViewMealAdd.getSortOrder().add(tableColumnProductMealAdd);
            }
        });

        tableViewMealAdd.setItems(FXCollections.observableArrayList(productMap.entrySet()));
    }

    @FXML
    public void setButtonAddProductToMeal() {
        Path pathNewProduct = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MealAddProduct.fxml");
        ClassOfStaticMethod.loadUrl(pathNewProduct, "Add product");
    }

    @FXML
    public void setButtonDeleteProductFromMeal() {
        if (tableViewMealAdd.getSelectionModel().getSelectedItem() != null) {
            Alert alertNoChoosen = new Alert(Alert.AlertType.CONFIRMATION);
            alertNoChoosen.setContentText("Do you really want to delete product " + tableViewMealAdd.getSelectionModel().getSelectedItem().getKey().getName() + "?");
            alertNoChoosen.setTitle("Delete confirmation");

            Optional<ButtonType> result = alertNoChoosen.showAndWait();
            if (result.get() == ButtonType.OK) {
                productMap.remove(tableViewMealAdd.getSelectionModel().getSelectedItem().getKey());
            } else {
                alertNoChoosen.close();
            }
        } else {
            Alert alertNoChoosen = new Alert(Alert.AlertType.INFORMATION);
            alertNoChoosen.setTitle("No product selected");
            alertNoChoosen.setContentText("choose product by clicking it in table");
            alertNoChoosen.show();
        }
    }

    @FXML
    public void setButtonDoMeal() {
        String mealName = null;

        if (ClassOfStaticMethod.checkTextFieldValid(textFieldMealAddName.getText(), "(\\S)+(\\s.+)*")) {
            mealName = textFieldMealAddName.getText();
        }
        if (productMap != null &&
                productMap.size() > 0 &&
                mealName != null) {

            if (buttonDoMeal.getText().equals("Edit")) {
                MealData.getMealsList().remove(Meal.getSelectedMeal());
                Meal.getSelectedMeal().setName(mealName);
                Meal.getSelectedMeal().setProductsForMeal(new HashMap<>(productMap));
                Meal.getSelectedMeal().countKcalForMeal();
                Meal.getSelectedMeal().countProteinForMeal();
                Meal.getSelectedMeal().countFatForMeal();
                Meal.getSelectedMeal().countCarbsForMeal();
                Meal.getSelectedMeal().countFiberForMeal();

                MealData.getMealsList().add(Meal.getSelectedMeal());
                MealData.getInstance().updateMeal(mealName, Meal.getSelectedMeal().getIdMeal(), productMap);
            } else {
                newMeal.setIdMeal(MealData.getInstance().readMaxMealId() + 1);
                newMeal.setName(mealName);
                newMeal.setProductsForMeal(new HashMap<>(productMap));
                newMeal.countKcalForMeal();
                newMeal.countProteinForMeal();
                newMeal.countFatForMeal();
                newMeal.countCarbsForMeal();
                newMeal.countFiberForMeal();

                MealData.getInstance().insertNewMeal(mealName, productMap);
            }
            productMap = FXCollections.observableMap(new HashMap<>());
            Stage stage = (Stage) textFieldMealAddSearch.getScene().getWindow();
            stage.close();
        } else {
            Alert alertNoChoosen = new Alert(Alert.AlertType.INFORMATION);
            alertNoChoosen.setTitle("No products to add ");
            alertNoChoosen.setContentText("add meal name and products to meal or click cancel");
            alertNoChoosen.show();
        }
    }

    @FXML
    public void setButtonCancelMealAdd() {
        productMap = FXCollections.observableMap(new HashMap<>());
        Stage stage = (Stage) textFieldMealAddSearch.getScene().getWindow();
        stage.close();
    }

    public static void putNewProductAmount(Product newEditProduct, Integer amount) {
        productMap.put(newEditProduct, amount);
    }
}
