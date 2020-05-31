package diet.gui.controlers;

import diet.model.Product;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.additionalClasses.ClassOfStaticMethodForControllers;
import diet.model.database.ProductData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class MealAddProductController {

    private List<Product> productsList;

    @FXML
    private TextField textFieldSearchMealProductAdd;
    @FXML
    private TableView<Product> tableViewMealProductAdd;
    @FXML
    private TableColumn<Product, String> productColumn;
    @FXML
    private TextField textFieldAmountMealProductAdd;
    @FXML
    private Label labelInvalidAmountMealProductAdd;
    @FXML
    private Button buttonAddMealProductAdd;
    @FXML
    private Button buttonCancelMealProductAdd;

    public void initialize() {
        productsList = ProductData.getProductsList();
        productColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (productsList != null) {
            tableViewMealProductAdd.setItems(FXCollections.observableList(productsList));
            ClassOfStaticMethodForControllers.initializeTextFieldSearchForList(textFieldSearchMealProductAdd, tableViewMealProductAdd, productsList);
        }
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldAmountMealProductAdd, labelInvalidAmountMealProductAdd, "\\d+", "Invalid", "");
    }

    @FXML
    public void setButtonAddMealProductAdd() {
        Product productToAdd = tableViewMealProductAdd.getSelectionModel().getSelectedItem();
        String amount = textFieldAmountMealProductAdd.getText();
        if (productToAdd != null) {
            if (amount.matches("\\d+")) {
                int intAmount = Integer.parseInt(amount);
                MealAddEditController.putNewProductAmount(productToAdd, intAmount);
                Stage stage = (Stage) tableViewMealProductAdd.getScene().getWindow();
                stage.close();
            } else {
                ClassOfStaticMethodForControllers.createAlertTypeWarning("Invalid amount ", "Invalid amount, enter integer number >0");
            }
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("No products to add ", "add products to meal or click cancel");
        }
    }

    @FXML
    public void setButtonCancelMealProductAdd() {
        Stage stage = (Stage) tableViewMealProductAdd.getScene().getWindow();
        stage.close();
    }
}