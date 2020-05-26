package diet.gui.controlers;

import diet.model.Product;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.database.ProductData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class MealAddProductController {

    private List<Product> productsList;

    @FXML
    private TextField textFieldSearchMealProductAdd;
    @FXML
    private TableView<Product> TableViewMealProductAdd;
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
            TableViewMealProductAdd.setItems(FXCollections.observableList(productsList));

            textFieldSearchMealProductAdd.setOnKeyTyped((change) -> {
                List<Product> sortedProductList = productsList.stream().filter((product) ->
                        product.getName().toLowerCase().matches(".*(" + textFieldSearchMealProductAdd.getText().toLowerCase() + ").*"))
                        .collect(Collectors.toList());
                TableViewMealProductAdd.setItems(FXCollections.observableList(sortedProductList));
            });
        }
        ClassOfStaticMethod.checkCorrectOfTextField(textFieldAmountMealProductAdd, labelInvalidAmountMealProductAdd, "\\d+", "Invalid", "");
    }

    @FXML
    public void setButtonAddMealProductAdd() {
        Product productToAdd = TableViewMealProductAdd.getSelectionModel().getSelectedItem();
        String amount = textFieldAmountMealProductAdd.getText();
        if (productToAdd != null) {
            if (amount.matches("\\d+")) {
                int intAmount = Integer.parseInt(amount);

                MealAddEditController.putNewProductAmount(productToAdd, intAmount);

                Stage stage = (Stage) TableViewMealProductAdd.getScene().getWindow();
                stage.close();
            } else {
                Alert alertNoChoosen = new Alert(Alert.AlertType.INFORMATION);
                alertNoChoosen.setTitle("Invalid amount ");
                alertNoChoosen.setContentText("Invalid amount, enter integer number >0");
                alertNoChoosen.show();
            }
        } else {
            Alert alertNoChoosen = new Alert(Alert.AlertType.INFORMATION);
            alertNoChoosen.setTitle("No products to add ");
            alertNoChoosen.setContentText("add products to meal or click cancel");
            alertNoChoosen.show();
        }
    }

    @FXML
    public void setButtonCancelMealProductAdd() {
        Stage stage = (Stage) TableViewMealProductAdd.getScene().getWindow();
        stage.close();
    }
}
