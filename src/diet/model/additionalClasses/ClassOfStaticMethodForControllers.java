package diet.model.additionalClasses;

import javafx.scene.control.Alert;

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
}
