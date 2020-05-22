package diet.model.additionalClasses;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClassOfStaticMethod {

    public static String roundDouble(Double doubleToRound) {
        String doubleFormat = String.format("%.2f", doubleToRound);
        String stringToParse = doubleFormat.replace(",", ".");
        return stringToParse;
    }

    public static void checkCorrectOfTextField(TextField textField, Label invalidLabel, String regex, String errorMassage, String correctMassage) {
        textField.textProperty().addListener(
                (observable, oldString, newString) -> {
                    if (!newString.matches(regex)) {
                        invalidLabel.setText(errorMassage);
                    } else {
                        invalidLabel.setText(correctMassage);
                    }
                });
    }

    public static boolean checkTextFieldValid(String value, String regex) {
        boolean valid = false;
        String check = "" + value;
        if (check.matches(regex))
            valid = true;
        return valid;
    }
}
