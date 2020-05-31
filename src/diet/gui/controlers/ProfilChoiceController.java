package diet.gui.controlers;

import diet.Main;
import diet.model.Profil;
import diet.model.additionalClasses.ClassOfStaticMethodForControllers;
import diet.model.database.MealData;
import diet.model.database.ProductData;
import diet.model.database.ProfilData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ProfilChoiceController {

    @FXML
    private ComboBox<Profil> comboBoxProfil;

    @FXML
    private Button buttonAddProfil;
    @FXML
    private Button buttonLoadProfil;
    @FXML
    private Button buttonDeleteProfil;
    @FXML
    private Button buttonCancel;


    public void initialize() {
        ProfilData.getInstance().readAllProfils();
        comboBoxProfil.itemsProperty().setValue(ProfilData.getProfilsList());
        if (ProfilData.getProfilsList().size() > 0) {
            comboBoxProfil.getSelectionModel().select(0);
        }
    }

    @FXML
    public void setButtonAddProfil() {
        Path path = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\ProfilCreate.fxml");
        loadProfilUrl(path, buttonAddProfil.getText());
    }

    @FXML
    public void setButtonLoadProfil() {
        setSelectedProfil();
        if (getSelectedProfil() != null) {
            MealData.getInstance().readAllMealForProfil();
            ProductData.getInstance().readAllProductForProfil();
            Path path = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindow.fxml");
            loadProfilUrl(path, buttonLoadProfil.getText());
            ((Stage) buttonLoadProfil.getScene().getWindow()).close();
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("choose profil", "befor moving on, choose profil You would like to load");
        }
    }

    @FXML
    public void setButtonDeleteProfil() {
        setSelectedProfil();
        if (getSelectedProfil() != null) {
            Alert alterProfilDelete = ClassOfStaticMethodForControllers.createAlertTypeConfirmation("Delete confirmation", "Do you really want to delete profile " + getSelectedProfil().getName() + "?");
            Optional<ButtonType> result = alterProfilDelete.showAndWait();
            if (result.get() == ButtonType.OK) {
                ProfilData.getInstance().deleteProfil(getSelectedProfil().getIdPerson());
                comboBoxProfil.getSelectionModel().select(0);
            }
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("choose profil", "befor moving on, choose profil You would like to delete");
        }
    }

    @FXML
    public void setButtonCancel() {
        Platform.exit();
    }

    private Profil getSelectedProfil() {
        return comboBoxProfil.getSelectionModel().getSelectedItem();
    }

    public void setSelectedProfil() {
        Profil.setSelectedProfil(getSelectedProfil());
    }

    private void loadProfilUrl(Path path, String buttonText) {
        try {
            URL url = path.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Diet");
                stage.setScene(new Scene(root));
                if (buttonText.equals(buttonAddProfil.getText()))
                    stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                if (buttonText.equals(buttonLoadProfil.getText())) {
                    Stage primaryStage = Main.getPrimaryStage();
                    primaryStage.close();
                }
            } catch (IOException e) {
                System.out.println("Cannot load fxml file " + e.getMessage());
            }
        } catch (MalformedURLException m) {
            System.out.println("Incorect URL " + m.getMessage());
        }
    }
}