package diet.gui.controlers;

import diet.model.database.ProfilData;
import diet.model.profilEnums.ProfilGoal;
import diet.model.profilEnums.ProfilSex;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    Label invalidProfilName;
    @FXML
    Label invalidProfilAge;
    @FXML
    Label invalidProfilWeight;
    @FXML
    Label invalidProfilGrowth;

    @FXML
    ChoiceBox<ProfilSex> choiceBoxProfilSex;
    @FXML
    ChoiceBox<ProfilGoal> choiceBoxProfilGoal;

    public void initialize() {
        choiceBoxProfilSex.setItems(FXCollections.observableList(Arrays.asList(ProfilSex.values())));
        choiceBoxProfilSex.getSelectionModel().select(0);
        choiceBoxProfilGoal.setItems(FXCollections.observableList(Arrays.asList(ProfilGoal.values())));
        choiceBoxProfilGoal.getSelectionModel().select(1);

        invalidProfilName.setText("e.q. Ania");
        invalidProfilAge.setText("years e.g. 24");
        invalidProfilWeight.setText("kg e.g. 75");
        invalidProfilGrowth.setText("cm e.g. 181");
        checkCorrectOfTextField(textFieldProfilName,invalidProfilName,"\\S+","invalid name","e.q. Ania");
        checkCorrectOfTextField(textFieldProfilAge,invalidProfilAge,"\\d{1,3}","invalid age","years e.g. 24");
        checkCorrectOfTextField(textFieldProfilWeight,invalidProfilWeight,"\\d{2,3}","invalid weight","kg e.g. 75");
        checkCorrectOfTextField(textFieldProfilGrowth,invalidProfilGrowth,"\\d{2,3}","invalid growth","cm e.g. 181");
    }

    @FXML
    public void setButtonAddProfil(){
        String name = null;
        int age = -1;
        int weight = -1;
        int growth = -1;
        if(checkTextFieldValid(textFieldProfilName.getText(),"\\S+")) {
            name = textFieldProfilName.getText();
        }
        if(checkTextFieldValid(textFieldProfilAge.getText(),"\\d{1,3}")) {
            age = Integer.parseInt(textFieldProfilAge.getText());
        }
        if(checkTextFieldValid(textFieldProfilWeight.getText(),"\\d{2,3}")) {
            weight = Integer.parseInt(textFieldProfilWeight.getText());
        }
        if(checkTextFieldValid(textFieldProfilGrowth.getText(),"\\d{2,3}")) {
            growth = Integer.parseInt(textFieldProfilGrowth.getText());
        }
        String sex =  choiceBoxProfilSex.getSelectionModel().getSelectedItem().toString();
        String goal = choiceBoxProfilGoal.getSelectionModel().getSelectedItem().toString();

        if( name != null &&
            age != -1 &&
            weight != -1 &&
            growth != -1
            ) {

            ProfilData.getInstance().insetNewProfil(name, age, weight, growth, sex, goal);
            Stage stage = (Stage) buttonCancelAddProfil.getScene().getWindow();
            stage.close();
        }else{
            Alert alertIncorrectValues = new Alert(Alert.AlertType.WARNING);
            alertIncorrectValues.setTitle("Incorect values");
            alertIncorrectValues.setContentText("enter complete valid data");
            alertIncorrectValues.show();
        }
    }

    @FXML
    public void setButtonCancelAddProfil(){
       Stage stage = (Stage) buttonCancelAddProfil.getScene().getWindow();
       stage.close();
    }


    public void checkCorrectOfTextField(TextField textField, Label invalidLabel, String regex, String errorMassage, String correctMassage){
        textField.textProperty().addListener(
                (observable,oldString,newString)->{
                    if (!newString.matches(regex)) {
                        invalidLabel.setText(errorMassage);
                    }else{
                        invalidLabel.setText(correctMassage);
                    }
                });
    }

    public boolean checkTextFieldValid(String value, String regex){
        boolean valid = false;
        String check =""+value;
            if(check.matches(regex))
                valid = true;
        return  valid;
    }

}
