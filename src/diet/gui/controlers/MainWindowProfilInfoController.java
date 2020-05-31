package diet.gui.controlers;

import diet.model.Profil;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.additionalClasses.ClassOfStaticMethodForControllers;
import diet.model.database.ProfilData;
import diet.model.profilEnums.ProfilGoal;
import diet.model.profilEnums.ProfilSex;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private Label invalidProfilName;
    @FXML
    private Label invalidProfilAge;
    @FXML
    private Label invalidProfilWeight;
    @FXML
    private Label invalidProfilGrowth;
    @FXML
    private Button buttonEditProfil;

    public void initialize() {
        Profil selectedProfil = Profil.getSelectedProfil();
        textFieldName.setText(selectedProfil.getName());
        textFieldAge.setText(selectedProfil.getAge() + "");
        textFieldWeight.setText(selectedProfil.getWeight() + "");
        textFieldGrowth.setText(selectedProfil.getGrowth() + "");
        choiceBoxProfilSex.setItems(FXCollections.observableList(Arrays.asList(ProfilSex.values())));
        choiceBoxProfilSex.getSelectionModel().select(selectedProfil.getSex());
        choiceBoxProfilGoal.setItems(FXCollections.observableList(Arrays.asList(ProfilGoal.values())));
        choiceBoxProfilGoal.getSelectionModel().select(selectedProfil.getGoal());
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldName, invalidProfilName, "\\S+", "invalid name", "e.q. Ania");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldAge, invalidProfilAge, "\\d{1,3}", "invalid age", "years e.g. 24");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldWeight, invalidProfilWeight, "\\d{2,3}", "invalid weight", "kg e.g. 75");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldGrowth, invalidProfilGrowth, "\\d{2,3}", "invalid growth", "cm e.g. 181");
    }

    @FXML
    public void setButtonEditProfil() {
        String name = null;
        int age = -1;
        int weight = -1;
        int growth = -1;
        if (ClassOfStaticMethod.checkTextFieldValid(textFieldName.getText(), "\\S+")) {
            name = textFieldName.getText();
        }
        if (ClassOfStaticMethod.checkTextFieldValid(textFieldAge.getText(), "\\d{1,3}")) {
            age = Integer.parseInt(textFieldAge.getText());
        }
        if (ClassOfStaticMethod.checkTextFieldValid(textFieldWeight.getText(), "\\d{2,3}")) {
            weight = Integer.parseInt(textFieldWeight.getText());
        }
        if (ClassOfStaticMethod.checkTextFieldValid(textFieldGrowth.getText(), "\\d{2,3}")) {
            growth = Integer.parseInt(textFieldGrowth.getText());
        }
        String sex = choiceBoxProfilSex.getSelectionModel().getSelectedItem().toString();
        String goal = choiceBoxProfilGoal.getSelectionModel().getSelectedItem().toString();
        if (name != null &&
                age != -1 &&
                weight != -1 &&
                growth != -1
        ) {
            ProfilData.getInstance().updateProfil(name, age, weight, growth, sex, goal, Profil.getSelectedProfil().getIdPerson());
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("Incorect values", "enter complete valid data");
        }
    }
}