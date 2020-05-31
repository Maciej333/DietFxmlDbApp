package diet.gui.controlers;

import diet.model.Diet;
import diet.model.Profil;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.additionalClasses.ClassOfStaticMethodForControllers;
import diet.model.database.DietData;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

public class MainWindowDietController {

    private static String loadedDietFxml;
    private static LocalDate choosenDate;
    private static ObservableList<Diet> dietsList;

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
        ObservableList<Diet> currentDiets = Diet.getDietsByDate(dietsList, datePickerDiet.getValue());
        initializeDatePickerAddDietListener();
        tableViewDiet.setItems(currentDiets);
        initializeProfilCount();
        initializeStatsOfDiets(currentDiets);
        initializeAddContextMenuToTableDiet();
        initializeAddListenerToDietList();
    }

    @FXML
    public void setButtonAddProductMeal() {
        choosenDate = datePickerDiet.getValue();
        loadedDietFxml = "Add";
        Path pathNewDiet = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\DietAdd.fxml");
        ClassOfStaticMethod.loadUrl(pathNewDiet, "Diet", "");
    }

    public static String getLoadedMealFxml() {
        return loadedDietFxml;
    }

    public static LocalDate getChoosenDate() {
        return choosenDate;
    }

    private void initializeDatePickerAddDietListener() {
        datePickerDiet.valueProperty().addListener((observable, oldDate, newDate) -> {
            countStatsForDiets(newDate);
        });
    }

    private void initializeProfilCount() {
        Profil.getSelectedProfil().countKcal();
        Profil.getSelectedProfil().countProtein();
        Profil.getSelectedProfil().countFat();
        Profil.getSelectedProfil().countCarbs();
        Profil.getSelectedProfil().countFiber();
        labelMaxKcal.setText(Profil.getSelectedProfil().getKcal() + "");
        labelMaxProtein.setText(Profil.getSelectedProfil().getProtein() + "");
        labelMaxFat.setText(Profil.getSelectedProfil().getFat() + "");
        labelMaxCarbs.setText(Profil.getSelectedProfil().getCarbs() + "");
        labelMaxFiber.setText(Profil.getSelectedProfil().getFiber() + "");
    }

    private void initializeAddContextMenuToTableDiet() {
        tableViewDiet.setRowFactory(new Callback<TableView<Diet>, TableRow<Diet>>() {
            @Override
            public TableRow<Diet> call(TableView<Diet> dietTableView) {
                TableRow<Diet> returnTableRow = new TableRow<>();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem edit = new MenuItem("Edit");
                edit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Diet.setSelectedDiet(tableViewDiet.getSelectionModel().getSelectedItem());
                        loadedDietFxml = "Edit";
                        Path pathNewDiet = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\DietAdd.fxml");
                        ClassOfStaticMethod.loadUrl(pathNewDiet, "Diet", "");
                    }
                });
                MenuItem delete = new MenuItem("Delete");
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Diet.setSelectedDiet(tableViewDiet.getSelectionModel().getSelectedItem());
                        Alert alertNoChoosen = ClassOfStaticMethodForControllers.createAlertTypeConfirmation("Delete confirmation",
                                "Do you really want to delete diet " + "?");
                        Optional<ButtonType> result = alertNoChoosen.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            DietData.getInstance().deleteDiet(Diet.getSelectedDiet());
                            DietData.getDietsList().remove(Diet.getSelectedDiet());
                        } else {
                            alertNoChoosen.close();
                        }
                    }
                });
                contextMenu.getItems().addAll(edit, delete);
                returnTableRow.contextMenuProperty().bind(Bindings.when(returnTableRow.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
                return returnTableRow;
            }
        });
    }

    private void initializeAddListenerToDietList() {
        dietsList.addListener(new ListChangeListener<Diet>() {
            @Override
            public void onChanged(Change<? extends Diet> change) {
                countStatsForDiets(datePickerDiet.getValue());
            }
        });
    }

    private void countStatsForDiets(LocalDate date) {
        tableViewDiet.refresh();
        ObservableList<Diet> newCurrentDiets = Diet.getDietsByDate(dietsList, date);
        tableViewDiet.setItems(newCurrentDiets);
        initializeStatsOfDiets(newCurrentDiets);
    }

    private void initializeStatsOfDiets(ObservableList<Diet> currentDiets) {
        Double[] statsOfDiets = Diet.countStatsForDiets(currentDiets);
        labelEatenKcal.setText(statsOfDiets[0].toString());
        labelEatenProtein.setText(statsOfDiets[1].toString());
        labelEatenFat.setText(statsOfDiets[2].toString());
        labelEatenCarbs.setText(statsOfDiets[3].toString());
        labelEatenFiber.setText(statsOfDiets[4].toString());
        kcalBilans.setText(statsOfDiets[5].toString());
    }
}