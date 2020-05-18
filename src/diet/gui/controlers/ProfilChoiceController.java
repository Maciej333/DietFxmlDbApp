package diet.gui.controlers;

import diet.model.Profil;
import diet.model.database.ProfilData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class ProfilChoiceController {

    @FXML
    ChoiceBox<Profil> choiceBoxProfil;

    @FXML
    Button buttonAddProfil;

    @FXML
    Button buttonLoadProfil;

    @FXML
    Button buttonCancel;


    public void initialize() {
        choiceBoxProfil.setItems(ProfilData.readAllProfils());
    }

    @FXML
    public void setButtonAddProfil(){

    }

    @FXML
    public void setButtonLoadProfil(){

    }

    @FXML
    public void setButtonCancel(){
        Platform.exit();
    }
}
