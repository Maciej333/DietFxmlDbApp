package diet.gui.controlers;

import diet.model.Profil;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.additionalClasses.ClassOfStaticMethodForControllers;
import diet.model.database.MealData;
import diet.model.database.ProductData;
import diet.model.database.ProfilData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

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
        ClassOfStaticMethod.loadUrl(path,"Add Profil","");
    }

    @FXML
    public void setButtonLoadProfil() {
        setSelectedProfil();
        if (getSelectedProfil() != null) {
            MealData.getInstance().readAllMealForProfil();
            ProductData.getInstance().readAllProductForProfil();
            Path path = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindow.fxml");
            ClassOfStaticMethod.loadUrl(path,"Add Profil","loadProfil");
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
}