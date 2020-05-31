package diet.model.additionalClasses;

import diet.model.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.*;

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

}
