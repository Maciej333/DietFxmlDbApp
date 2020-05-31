package diet.gui.controlers;

import diet.model.Product;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.additionalClasses.ClassOfStaticMethodForControllers;
import diet.model.database.ProductData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductAddEditController {

    @FXML
    private Label labelProductActionInfo;

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
    private Button buttonDoProduct;
    @FXML
    private Button buttonCancelProduct;

    public void initialize() {
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductName, invalidProductName, "(\\S)+(\\s.+)*", "invalid name", "e.q. Egg");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductKcal, invalidProductKcal, "\\d{1,5}([\\.,]\\d{1,2})?", "invalid kcal", "e.g. 124.5");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductProtein, invalidProductProtein, "\\d{1,4}([\\.,]\\d{1,2})?", "invalid protein", "e.g. 15.2");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductFat, invalidProductFat, "\\d{1,4}([\\.,]\\d{1,2})?", "invalid fat", "e.g. 10.1");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductCarbs, invalidProductCarbs, "\\d{1,4}([\\.,]\\d{1,2})?", "invalid carbs", "e.g. 50.4");
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldProductFiber, invalidProductFiber, "\\d{1,4}([\\.,]\\d{1,2})?", "invalid fiber", "e.g. 5.1");
        if (MainWindowProductController.getLoadedProductFxml().equals("Edit")) {
            labelProductActionInfo.setText("Edit product");
            textFieldProductName.setText(Product.getSelectedProduct().getName());
            textFieldProductKcal.setText(Product.getSelectedProduct().getKcal() + "");
            textFieldProductProtein.setText(Product.getSelectedProduct().getProtein() + "");
            textFieldProductFat.setText(Product.getSelectedProduct().getFat() + "");
            textFieldProductCarbs.setText(Product.getSelectedProduct().getCarbs() + "");
            textFieldProductFiber.setText(Product.getSelectedProduct().getFiber() + "");
            buttonDoProduct.setText("Edit");
        }
    }

    @FXML
    public void setButtonDoProduct() {
        String name = null;
        double kcal = -1;
        double protein = -1;
        double fat = -1;
        double carbs = -1;
        double fiber = -1;
        if (ClassOfStaticMethod.checkTextFieldValid(textFieldProductName.getText(), "(\\S)+(\\s.+)*")) {
            name = textFieldProductName.getText();
        }
        if (ClassOfStaticMethod.checkTextFieldValid(textFieldProductKcal.getText(), "\\d{1,5}([\\.,]\\d{1,2})?")) {
            kcal = Double.parseDouble(textFieldProductKcal.getText());
        }
        if (ClassOfStaticMethod.checkTextFieldValid(textFieldProductProtein.getText(), "\\d{1,4}([\\.,]\\d{1,2})?")) {
            protein = Double.parseDouble(textFieldProductProtein.getText());
        }
        if (ClassOfStaticMethod.checkTextFieldValid(textFieldProductFat.getText(), "\\d{1,4}([\\.,]\\d{1,2})?")) {
            fat = Double.parseDouble(textFieldProductFat.getText());
        }
        if (ClassOfStaticMethod.checkTextFieldValid(textFieldProductCarbs.getText(), "\\d{1,4}([\\.,]\\d{1,2})?")) {
            carbs = Double.parseDouble(textFieldProductCarbs.getText());
        }
        if (ClassOfStaticMethod.checkTextFieldValid(textFieldProductFiber.getText(), "\\d{1,4}([\\.,]\\d{1,2})?")) {
            fiber = Double.parseDouble(textFieldProductFiber.getText());
        }
        if (name != null &&
                kcal != -1 &&
                protein != -1 &&
                fat != -1 &&
                carbs != -1 &&
                fiber != -1
        ) {
            if (MainWindowProductController.getLoadedProductFxml().equals("Edit")) {
                ProductData.getInstance().updateProduct(name, kcal, protein, fat, carbs, fiber);
            } else {
                ProductData.getInstance().insertProduct(name, kcal, protein, fat, carbs, fiber);
            }
            Stage stage = (Stage) buttonDoProduct.getScene().getWindow();
            stage.close();
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("Incorect values", "enter complete valid data");
        }
    }

    @FXML
    public void setButtonCancelProduct() {
        Stage stage = (Stage) buttonCancelProduct.getScene().getWindow();
        stage.close();
    }
}