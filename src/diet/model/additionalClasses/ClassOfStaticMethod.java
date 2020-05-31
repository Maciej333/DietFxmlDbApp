package diet.model.additionalClasses;

import diet.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDateTime;

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

    public static void loadUrl(Path path, String name, String typeOfLoad) {
        try {
            URL url = path.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle(name);
                stage.setScene(new Scene(root));
                if (typeOfLoad.equals("loadProfil")) {
                    Stage primaryStage = Main.getPrimaryStage();
                    primaryStage.close();
                } else {
                    stage.initModality(Modality.APPLICATION_MODAL);
                }
                stage.show();
            } catch (IOException e) {
                System.out.println("Cannot load fxml file " + e.getMessage());
            }
        } catch (MalformedURLException m) {
            System.out.println("Incorect URL " + m.getMessage());
        }
    }

    public static LocalDateTime parseStringToDate(String stringDate) {
        String dateToSet = stringDate.replace(" ", "T");
        LocalDateTime localDateTime = LocalDateTime.parse(dateToSet);
        return localDateTime;
    }
}
