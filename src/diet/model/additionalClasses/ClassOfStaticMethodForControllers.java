package diet.model.additionalClasses;

import diet.model.Food;
import javafx.collections.FXCollections;
import javafx.scene.control.*;

import java.util.List;
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
}
