package diet.gui.controlers;

import diet.model.Profil;
import diet.model.profilEnums.ProfilGoal;
import diet.model.profilEnums.ProfilSex;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class MainWindowProfilInfoController {

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldAge;
    @FXML
    private TextField textFieldWeight;
    @FXML
    private TextField textFieldGrowth;
    @FXML
    private ChoiceBox<ProfilSex> choiceBoxProfilSex;
    @FXML
    private ChoiceBox<ProfilGoal> choiceBoxProfilGoal;
    @FXML
    private Label invalidProfilAge;
    @FXML
    private Label invalidProfilName;
    @FXML
    private Label invalidProfilGrowth;

    public void initialize() {
        Profil selectedProfil = Profil.getSelectedProfil();
        textFieldName.setText(selectedProfil.getName());
        textFieldAge.setText(selectedProfil.getAge()+"");
        textFieldWeight.setText(selectedProfil.getWeight()+"");
        textFieldGrowth.setText(selectedProfil.getGrowth()+"");
        choiceBoxProfilSex.setItems(FXCollections.observableList(Arrays.asList(ProfilSex.values())));
        choiceBoxProfilSex.getSelectionModel().select(selectedProfil.getSex());
        choiceBoxProfilGoal.setItems(FXCollections.observableList(Arrays.asList(ProfilGoal.values())));
        choiceBoxProfilGoal.getSelectionModel().select(selectedProfil.getGoal());
    }
}
