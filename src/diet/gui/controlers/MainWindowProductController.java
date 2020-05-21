package diet.gui.controlers;

import diet.model.Product;
import diet.model.database.ProductData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainWindowProductController {

    private ObservableList<Product> productsList;

    @FXML
    private TableView<Product> tableViewProduct;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, Double> productKcal;
    @FXML
    private TableColumn<Product, Double> productProtein;
    @FXML
    private TableColumn<Product, Double> productFat;
    @FXML
    private TableColumn<Product, Double> productCarbs;
    @FXML
    private TableColumn<Product, Double> productFiber;

    @FXML
    private Button buttonAddNewProduct;


    public void initialize() {
        productsList = ProductData.getProductsList();

        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productKcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        productProtein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        productFat.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        productCarbs.setCellValueFactory(new PropertyValueFactory<>("fat"));
        productFiber.setCellValueFactory(new PropertyValueFactory<>("fiber"));
        tableViewProduct.setItems(productsList);
    }

    @FXML
    public void setButtonAddNewProduct(){
        buttonAddNewProduct.setText("kliknieto");
    }
}
