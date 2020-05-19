package diet.gui.controlers;

import diet.model.database.ProfilData;
import diet.model.profilEnums.ProfilGoal;
import diet.model.profilEnums.ProfilSex;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Arrays;

public class ProfilCreateController {

    @FXML
    BorderPane borderPaneProfilCreate;

    @FXML
    Button buttonAddProfil;

    @FXML
    Button buttonCancelAddProfil;

    @FXML
    TextField textFieldProfilName;

    @FXML
    TextField textFieldProfilAge;

    @FXML
    TextField textFieldProfilWeight;

    @FXML
    TextField textFieldProfilGrowth;

    @FXML
    ChoiceBox<ProfilSex> choiceBoxProfilSex;

    @FXML
    ChoiceBox<ProfilGoal> choiceBoxProfilGoal;

    public void initialize() {
        choiceBoxProfilSex.setItems(FXCollections.observableList(Arrays.asList(ProfilSex.values())));
        choiceBoxProfilGoal.setItems(FXCollections.observableList(Arrays.asList(ProfilGoal.values())));
    }


    @FXML
    public void setButtonAddProfil(){
        String name = textFieldProfilName.getText();
        int age = Integer.parseInt(textFieldProfilAge.getText());
        int weight = Integer.parseInt(textFieldProfilWeight.getText());
        int growth = Integer.parseInt(textFieldProfilGrowth.getText());
        String sex = choiceBoxProfilSex.getSelectionModel().getSelectedItem().toString();
        String goal = choiceBoxProfilGoal.getSelectionModel().getSelectedItem().toString();

        ProfilData.getInstance().insetNewProfil(name,age,weight,growth,sex,goal);

        Stage stage = (Stage) buttonCancelAddProfil.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void setButtonCancelAddProfil(){
       Stage stage = (Stage) buttonCancelAddProfil.getScene().getWindow();
       stage.close();
    }


}
