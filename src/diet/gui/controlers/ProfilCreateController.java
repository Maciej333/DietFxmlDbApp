package diet.gui.controlers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ProfilCreateController {

    @FXML
    BorderPane borderPaneProfilCreate;

    @FXML
    Button buttonAddProfil;

    @FXML
    Button buttonCancelAddProfil;


    @FXML
    public void setButtonAddProfil(){

        Stage stage = (Stage) buttonCancelAddProfil.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void setButtonCancelAddProfil(){
       Stage stage = (Stage) buttonCancelAddProfil.getScene().getWindow();
       stage.close();
    }
}
