package diet.gui.controlers;

import diet.model.Meal;
import diet.model.Product;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.additionalClasses.ClassOfStaticMethodForControllers;
import diet.model.database.MealData;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
        initializeTableForMeal();
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldMealAddName, labelMealNameInvalid, "(\\S)+(\\s.+)*", "Invalid", "");
        if (MainWindowMealController.getLoadedMealFxml().equals("Edit")) {
            productMap = FXCollections.observableMap(Meal.getSelectedMeal().getProductsForMeal());
            textFieldMealAddName.setText(Meal.getSelectedMeal().getName());
            buttonDoMeal.setText("Edit");
        } else {
            productMap = FXCollections.observableMap(new HashMap<>());
            newMeal = new Meal();
        }
        ClassOfStaticMethodForControllers.initializeTextFieldSearchForMap(textFieldMealAddSearch, tableViewMealAdd, productMap);
        initializeAddListenerToProductMap();
        tableViewMealAdd.setItems(FXCollections.observableArrayList(productMap.entrySet()));
        initializeAddContextMenuToTableMeal();
    }

    @FXML
    public void setButtonAddProductToMeal() {
        Path pathNewProduct = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MealAddProduct.fxml");
        ClassOfStaticMethod.loadUrl(pathNewProduct, "Add product");
    }

    @FXML
    public void setButtonDeleteProductFromMeal() {
        if (tableViewMealAdd.getSelectionModel().getSelectedItem() != null) {
            Alert alertNoChoosen = ClassOfStaticMethodForControllers.createAlertTypeConfirmation("Delete confirmation",
                    "Do you really want to delete product " + tableViewMealAdd.getSelectionModel().getSelectedItem().getKey().getName() + "?");
            Optional<ButtonType> result = alertNoChoosen.showAndWait();
            if (result.get() == ButtonType.OK) {
                productMap.remove(tableViewMealAdd.getSelectionModel().getSelectedItem().getKey());
            } else {
                alertNoChoosen.close();
            }
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("No product selected", "choose product by clicking it in table");
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
            Stage stage = (Stage) textFieldMealAddSearch.getScene().getWindow();
            stage.close();
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("No products to add ", "add meal name and products to meal or click cancel");
        }
    }

    @FXML
    public void setButtonCancelMealAdd() {
        Stage stage = (Stage) textFieldMealAddSearch.getScene().getWindow();
        stage.close();
    }

    public static void putNewProductAmount(Product newEditProduct, Integer amount) {
        productMap.put(newEditProduct, amount);
    }

    private void initializeTableForMeal() {
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
    }

    private void initializeAddContextMenuToTableMeal() {
        tableViewMealAdd.setRowFactory(new Callback<TableView<Map.Entry<Product, Integer>>, TableRow<Map.Entry<Product, Integer>>>() {
            @Override
            public TableRow<Map.Entry<Product, Integer>> call(TableView<Map.Entry<Product, Integer>> productTableView) {
                TableRow<Map.Entry<Product, Integer>> returnTableRow = new TableRow<>();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem delete = new MenuItem("Delete");
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Alert alertNoChoosen = ClassOfStaticMethodForControllers.createAlertTypeConfirmation("Delete confirmation",
                                "Do you really want to delete product " + tableViewMealAdd.getSelectionModel().getSelectedItem().getKey().getName() + "?");
                        Optional<ButtonType> result = alertNoChoosen.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            productMap.remove(tableViewMealAdd.getSelectionModel().getSelectedItem().getKey());
                        } else {
                            alertNoChoosen.close();
                        }
                    }
                });
                contextMenu.getItems().addAll(delete);
                returnTableRow.contextMenuProperty().bind(Bindings.when(returnTableRow.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
                return returnTableRow;
            }
        });
    }

    private void initializeAddListenerToProductMap() {
        productMap.addListener(new MapChangeListener<Product, Integer>() {
            @Override
            public void onChanged(Change<? extends Product, ? extends Integer> change) {
                tableViewMealAdd.setItems(FXCollections.observableArrayList(productMap.entrySet()));
                tableViewMealAdd.getSortOrder().add(tableColumnProductMealAdd);
            }
        });
    }
}