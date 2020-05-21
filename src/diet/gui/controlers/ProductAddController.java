package diet.gui.controlers;

import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.database.ProfilData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductAddController {
    @FXML
    private TextField textFieldProductName;
    @FXML
    private TextField textFieldProductKcal;
    @FXML
    private TextField textFieldProductProtein;
    @FXML
    private TextField textFieldProductFat;
    @FXML
    private TextField textFieldProductCarbs;
    @FXML
    private TextField textFieldProductFiber;

    @FXML
    private Label invalidProductName;
    @FXML
    private Label invalidProductKcal;
    @FXML
    private Label invalidProductProtein;
    @FXML
    private Label invalidProductFat;
    @FXML
    private Label invalidProductCarbs;
    @FXML
    private Label invalidProductFiber;

    @FXML
    private Button buttonAddNewProduct;
    @FXML
    private Button buttonCancelProduct;
    @FXML
    private Button buttonEditProduct;

    public void initialize() {
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductName,invalidProductName,"\\S+","invalid name","e.q. Egg");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductKcal,invalidProductKcal,"\\d{1,5}\\.?\\d{1,2}?","invalid kcal","e.g. 124.5");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductProtein,invalidProductProtein,"\\d{1,4}\\.?\\d{1,2}?","invalid protein","e.g. 15.2");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductFat,invalidProductFat,"\\d{1,4}\\.?\\d{1,2}?","invalid fat","e.g. 10.1");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductCarbs,invalidProductCarbs,"\\d{1,4}\\.?\\d{1,2}?","invalid carbs","e.g. 50.4");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductFiber,invalidProductFiber,"\\d{1,4}\\.?\\d{1,2}?","invalid fiber","e.g. 5.1");
    }

    @FXML
    public void setButtonAddNewProduct(){
        String name = null;
        int kcal = -1;
        int protein = -1;
        int fat = -1;
        int carbs = -1;
        int fiber = -1;

        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductName.getText(),"\\S+")) {
            name = textFieldProductName.getText();
        }
        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductKcal.getText(),"\\d{1,5}\\.?\\d{1,2}?")) {
            kcal = Integer.parseInt(textFieldProductKcal.getText());
        }
        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductProtein.getText(),"\\d{1,4}\\.?\\d{1,2}?")) {
            protein = Integer.parseInt(textFieldProductProtein.getText());
        }
        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductFat.getText(),"\\d{1,4}\\.?\\d{1,2}?")) {
            fat = Integer.parseInt(textFieldProductFat.getText());
        }
        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductCarbs.getText(),"\\d{1,4}\\.?\\d{1,2}?")) {
            carbs = Integer.parseInt(textFieldProductCarbs.getText());
        }
        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductFiber.getText(),"\\d{1,4}\\.?\\d{1,2}?")) {
            fiber = Integer.parseInt(textFieldProductFiber.getText());
        }

        if( name != null &&
                kcal != -1 &&
                protein != -1 &&
                fat != -1 &&
                carbs != -1 &&
                fiber != -1
        ) {

                            System.out.println("wprowadzic odpowiedniego inswerta");


            Stage stage = (Stage) buttonAddNewProduct.getScene().getWindow();
            stage.close();
        }else{
            Alert alertIncorrectValues = new Alert(Alert.AlertType.WARNING);
            alertIncorrectValues.setTitle("Incorect values");
            alertIncorrectValues.setContentText("enter complete valid data");
            alertIncorrectValues.show();
        }
    }






    @FXML
    public void setButtonEditProduct(){
        String name = null;
        int kcal = -1;
        int protein = -1;
        int fat = -1;
        int carbs = -1;
        int fiber = -1;

        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductName.getText(),"\\S+")) {
            name = textFieldProductName.getText();
        }
        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductKcal.getText(),"\\d{1,5}\\.?\\d{1,2}?")) {
            kcal = Integer.parseInt(textFieldProductKcal.getText());
        }
        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductProtein.getText(),"\\d{1,4}\\.?\\d{1,2}?")) {
            protein = Integer.parseInt(textFieldProductProtein.getText());
        }
        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductFat.getText(),"\\d{1,4}\\.?\\d{1,2}?")) {
            fat = Integer.parseInt(textFieldProductFat.getText());
        }
        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductCarbs.getText(),"\\d{1,4}\\.?\\d{1,2}?")) {
            carbs = Integer.parseInt(textFieldProductCarbs.getText());
        }
        if(ClassOfStaticMethod.checkTextFieldValid(textFieldProductFiber.getText(),"\\d{1,4}\\.?\\d{1,2}?")) {
            fiber = Integer.parseInt(textFieldProductFiber.getText());
        }

        if(     name != null &&
                kcal != -1 &&
                protein != -1 &&
                fat != -1 &&
                carbs != -1 &&
                fiber != -1
        ) {

            System.out.println("wprowadzic odpowiedniego updata");


            Stage stage = (Stage) buttonEditProduct.getScene().getWindow();
            stage.close();
        }else{
            Alert alertIncorrectValues = new Alert(Alert.AlertType.WARNING);
            alertIncorrectValues.setTitle("Incorect values");
            alertIncorrectValues.setContentText("enter complete valid data");
            alertIncorrectValues.show();
        }
    }






    @FXML
    public void setButtonCancelProduct(){
        Stage stage = (Stage) buttonCancelProduct.getScene().getWindow();
        stage.close();
    }


}
