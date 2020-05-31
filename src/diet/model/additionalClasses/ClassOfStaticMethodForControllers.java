package diet.model.additionalClasses;

import diet.model.Food;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassOfStaticMethodForControllers {
    public static Alert createAlertTypeConfirmation(String title, String contextText) {
        Alert alterConfirmaton = new Alert(Alert.AlertType.CONFIRMATION);
        alterConfirmaton.setContentText(contextText);
        alterConfirmaton.setTitle(title);
        return alterConfirmaton;
    }

    public static void createAlertTypeWarning(String title, String contextText) {
        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
        alertWarning.setContentText(contextText);
        alertWarning.setTitle(title);
        alertWarning.show();
    }

    public static <S extends Food> void initializeTextFieldSearchForList(TextField textField, TableView tableView, List<S> list) {
        textField.setOnKeyTyped((change) -> {
            List<S> sortedList = list.stream().filter((listElement) ->
                    listElement.getName().toLowerCase().matches(".*(" + textField.getText().toLowerCase() + ").*"))
                    .collect(Collectors.toList());
            tableView.setItems(FXCollections.observableList(sortedList));
        });
    }

    public static <S extends Food> void initializeTextFieldSearchForMap(TextField textField, TableView tableView, ObservableMap<S, Integer> map) {
        textField.setOnKeyTyped((change) -> {
            if (map != null) {
                Map<Food, Integer> sortedMap = map.entrySet().stream().filter((productMealEntrySet) ->
                        productMealEntrySet.getKey().getName().toLowerCase().matches(".*(" + textField.getText().toLowerCase() + ").*"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                tableView.setItems(FXCollections.observableArrayList(sortedMap.entrySet()));
            }
        });
    }

    public static <S extends Food> void initializeTableForEdit(TableView<Map.Entry<S, Integer>>  tableView, TableColumn<Map.Entry<S, Integer>, String>  tableColumnString, TableColumn<Map.Entry<S, Integer>, Integer>  tableColumnInteger) {
        tableView.setEditable(true);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableColumnInteger.setEditable(true);
        tableColumnInteger.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumnInteger.setOnEditCommit(event -> {
            int editAmount = event.getNewValue() > 0 ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setValue(editAmount);
            tableView.refresh();
        });
        tableColumnString.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<S, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<S, Integer>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey().getName());
            }
        });
        tableColumnInteger.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<S, Integer>, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Map.Entry<S, Integer>, Integer> p) {
                SimpleIntegerProperty sip = new SimpleIntegerProperty();
                sip.set(p.getValue().getValue());
                return new SimpleIntegerProperty(p.getValue().getValue()).asObject();
            }
        });
    }
}
