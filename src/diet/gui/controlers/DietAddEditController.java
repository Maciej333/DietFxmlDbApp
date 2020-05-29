package diet.gui.controlers;

import diet.model.*;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.additionalClasses.ClassOfStaticMethodForControllers;
import diet.model.database.DietData;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DietAddEditController {

    private static Diet newDiet;
    private static ObservableMap<Food, Integer> productFoodMap = FXCollections.observableMap(new HashMap<>());

    @FXML
    private DatePicker datePickerDietDate;
    @FXML
    private TextField textFieldTimeHourDietAdd;
    @FXML
    private TextField textFieldTimeMinuteDietAdd;
    @FXML
    private Label labelInvalidTime;
    @FXML
    private TextField textFieldDietAddSearch;
    @FXML
    private TableView<Map.Entry<Food, Integer>> tableViewDietAdd;
    @FXML
    private TableColumn<Map.Entry<Food, Integer>, String> tableColumnDietProductMealAdd;
    @FXML
    private TableColumn<Map.Entry<Food, Integer>, Integer> tableColumnAmountDietAdd;

    @FXML
    private Button buttonAddProductMealToDiet;
    @FXML
    private Button buttonDeleteProductMealFromDiet;
    @FXML
    private Button buttonDoDiet;
    @FXML
    private Button buttonCancelDietAdd;

    public void initialize() {
        productFoodMap = FXCollections.observableMap(new HashMap<>());
        initializeTableForDiet();
        initializeTextFieldSearchForDiet();
        if (MainWindowDietController.getLoadedMealFxml().equals("Edit")) {
            productFoodMap = FXCollections.observableMap(Diet.getSelectedDiet().getDietMealsProductsMap());
            datePickerDietDate.setValue(Diet.getSelectedDiet().getDate().toLocalDate());
            textFieldTimeHourDietAdd.setText(Diet.getSelectedDiet().getDate().getHour() + "");
            textFieldTimeMinuteDietAdd.setText(Diet.getSelectedDiet().getDate().getMinute() + "");
            buttonDoDiet.setText("Edit");
        } else {
            newDiet = new Diet();
            datePickerDietDate.setValue(MainWindowDietController.getChoosenDate());
            textFieldTimeHourDietAdd.setText(LocalDateTime.now().getHour() + "");
            textFieldTimeMinuteDietAdd.setText(LocalDateTime.now().getMinute() + "");
        }
        initializeAddListenerToFoodMap();
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldTimeHourDietAdd, labelInvalidTime, "\\d{1,2}", "invalid", "hh:MM");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldTimeMinuteDietAdd, labelInvalidTime, "\\d{1,2}", "invalid", "hh:MM");
        tableViewDietAdd.setItems(FXCollections.observableArrayList(productFoodMap.entrySet()));
        initializeAddContextMenuToTableDiet();
    }

    @FXML
    public void setButtonAddProductMealToDiet() {
        Path pathNewProduct = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\DietAddMealProduct.fxml");
        ClassOfStaticMethod.loadUrl(pathNewProduct, "Add product");
    }

    @FXML
    public void setButtonDeleteProductMealFromDiet() {
        if (tableViewDietAdd.getSelectionModel().getSelectedItem() != null) {
            Alert alertNoChoosen = ClassOfStaticMethodForControllers.createAlertTypeConfirmation("Delete confirmation",
                    "Do you really want to delete product " + tableViewDietAdd.getSelectionModel().getSelectedItem().getKey().getName() + "?");
            Optional<ButtonType> result = alertNoChoosen.showAndWait();
            if (result.get() == ButtonType.OK) {
                productFoodMap.remove(tableViewDietAdd.getSelectionModel().getSelectedItem().getKey());
            } else {
                alertNoChoosen.close();
            }
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("No product selected", "choose product by clicking it in table");
        }
    }

    @FXML
    public void setButtonDoDiet() {

        String hour = textFieldTimeHourDietAdd.getText();
        String minute = textFieldTimeMinuteDietAdd.getText();
        if (productFoodMap != null &&
                productFoodMap.size() > 0 &&
                hour.matches("\\d{1,2}") &&
                minute.matches("\\d{1,2}")
        ) {
            int intHour = Integer.parseInt(hour);
            int intMinute = Integer.parseInt(minute);

            if (intHour < 24 && intMinute < 60) {
                if (buttonDoDiet.getText().equals("Edit")) {
                    DietData.getDietsList().remove(Diet.getSelectedDiet());

                    Diet.getSelectedDiet().setDate(LocalDateTime.of(datePickerDietDate.getValue(), LocalTime.of(intHour, intMinute)));
                    Diet.getSelectedDiet().addMapToDietMealsProductsMap(productFoodMap);
                    Diet.getSelectedDiet().countKcalForDiet();
                    Diet.getSelectedDiet().countProteinForDiet();
                    Diet.getSelectedDiet().countFatForDiet();
                    Diet.getSelectedDiet().countCarbsForDiet();
                    Diet.getSelectedDiet().countFiberForDiet();

                    DietData.getInstance().updateDiet(Diet.getSelectedDiet());
                } else {
                    newDiet.setIdOsoba(Profil.getSelectedProfil().getIdPerson());
                    newDiet.setDate(LocalDateTime.of(datePickerDietDate.getValue(), LocalTime.of(intHour, intMinute)));
                    newDiet.addMapToDietMealsProductsMap(productFoodMap);

                    DietData.getInstance().insertDiet(newDiet);
                }
                Stage stage = (Stage) textFieldDietAddSearch.getScene().getWindow();
                stage.close();
            } else {
                ClassOfStaticMethodForControllers.createAlertTypeWarning("Invalid Hour ", "Hour should be between 0-23\nMinute should be between 0-59");
            }
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("No products/meals to add ", "add meal and products to diet or click cancel");
        }
    }

    @FXML
    public void setButtonCancelDietAdd() {
        Stage stage = (Stage) textFieldDietAddSearch.getScene().getWindow();
        stage.close();
    }

    public static void putNewMealProductAmount(Food newEditProduct, Integer amount) {
        productFoodMap.put(newEditProduct, amount);
    }

    private void initializeTableForDiet() {
        tableViewDietAdd.setEditable(true);
        tableViewDietAdd.getSelectionModel().setCellSelectionEnabled(true);
        tableColumnAmountDietAdd.setEditable(true);
        tableColumnAmountDietAdd.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumnAmountDietAdd.setOnEditCommit(event -> {
            int editAmount = event.getNewValue() > 0 ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setValue(editAmount);
            tableViewDietAdd.refresh();
        });
        tableColumnDietProductMealAdd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Food, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Food, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey().getName());
            }
        });
        tableColumnAmountDietAdd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Food, Integer>, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Map.Entry<Food, Integer>, Integer> p) {
                SimpleIntegerProperty sip = new SimpleIntegerProperty();
                sip.set(p.getValue().getValue());
                return new SimpleIntegerProperty(p.getValue().getValue()).asObject();
            }
        });
    }

    private void initializeAddContextMenuToTableDiet() {
        tableViewDietAdd.setRowFactory(new Callback<TableView<Map.Entry<Food, Integer>>, TableRow<Map.Entry<Food, Integer>>>() {
            @Override
            public TableRow<Map.Entry<Food, Integer>> call(TableView<Map.Entry<Food, Integer>> foodTableView) {
                TableRow<Map.Entry<Food, Integer>> returnTableRow = new TableRow<>();

                ContextMenu contextMenu = new ContextMenu();
                MenuItem delete = new MenuItem("Delete");
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Alert alertNoChoosen = ClassOfStaticMethodForControllers.createAlertTypeConfirmation("Delete confirmation",
                                "Do you really want to delete product " + tableViewDietAdd.getSelectionModel().getSelectedItem().getKey().getName() + "?");
                        Optional<ButtonType> result = alertNoChoosen.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            productFoodMap.remove(tableViewDietAdd.getSelectionModel().getSelectedItem().getKey());
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

    private void initializeTextFieldSearchForDiet() {
        textFieldDietAddSearch.setOnKeyTyped((change) -> {
            if (productFoodMap != null) {
                Map<Food, Integer> sortedProductMap = productFoodMap.entrySet().stream().filter((productMealEntrySet) ->
                        productMealEntrySet.getKey().getName().toLowerCase().matches(".*(" + textFieldDietAddSearch.getText().toLowerCase() + ").*"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                tableViewDietAdd.setItems(FXCollections.observableArrayList(sortedProductMap.entrySet()));
            }
        });
    }

    private void initializeAddListenerToFoodMap() {
        productFoodMap.addListener(new MapChangeListener<Food, Integer>() {
            @Override
            public void onChanged(Change<? extends Food, ? extends Integer> change) {
                tableViewDietAdd.setItems(FXCollections.observableArrayList(productFoodMap.entrySet()));
                tableViewDietAdd.getSortOrder().add(tableColumnDietProductMealAdd);
            }
        });
    }
}
