package diet.gui.controlers;

import diet.model.Product;
import diet.model.database.ProductData;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class MainWindowProductController {

    private static String loadedFxml = null;
    private ObservableList<Product> productsList;

    @FXML
    private TableView<Product> tableViewProduct;
    @FXML
    private TableColumn<Product, SimpleStringProperty> productName;
    @FXML
    private TableColumn<Product, SimpleDoubleProperty> productKcal;
    @FXML
    private TableColumn<Product, SimpleDoubleProperty> productProtein;
    @FXML
    private TableColumn<Product, SimpleDoubleProperty> productFat;
    @FXML
    private TableColumn<Product, SimpleDoubleProperty> productCarbs;
    @FXML
    private TableColumn<Product, SimpleDoubleProperty> productFiber;

    @FXML
    private Button buttonAddNewProduct;
    @FXML
    private Button buttonEditProduct;
    @FXML
    private Button buttonDeleteProduct;

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
        loadedFxml = "Add";
        Product.setSelectedProduct(tableViewProduct.getSelectionModel().getSelectedItem());
        Path pathNewProduct = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\ProductAdd.fxml");
        loadUrl(pathNewProduct);
    }

    @FXML
    public void setButtonEditProduct(){
        loadedFxml = "Edit";
        Product.setSelectedProduct(tableViewProduct.getSelectionModel().getSelectedItem());
        if(Product.getSelectedProduct() != null ) {
            Path pathNewProduct = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\ProductEdit.fxml");
            loadUrl(pathNewProduct);
        }else{
            Alert alertNoChoosen = new Alert(Alert.AlertType.INFORMATION);
            alertNoChoosen.setTitle("No product selected");
            alertNoChoosen.setContentText("choose product by clicking it in table");
            alertNoChoosen.show();
        }
    }

    @FXML
    public void setButtonDeleteProduct(){
        Product.setSelectedProduct(tableViewProduct.getSelectionModel().getSelectedItem());
        if(Product.getSelectedProduct() != null ) {
            Alert alertNoChoosen = new Alert(Alert.AlertType.WARNING);
            alertNoChoosen.setContentText("Do you really want to delete profile "+Product.getSelectedProduct().getName()+"?");
            alertNoChoosen.setTitle("Delete confirmation");

            Optional<ButtonType> result = alertNoChoosen.showAndWait();
            if (result.get() == ButtonType.OK){
                ProductData.getInstance().deleteProduct();
            } else {
                alertNoChoosen.close();
            }
        }else{
            Alert alertNoChoosen = new Alert(Alert.AlertType.INFORMATION);
            alertNoChoosen.setTitle("No product selected");
            alertNoChoosen.setContentText("choose product by clicking it in table");
            alertNoChoosen.show();
        }
    }

    public TableView<Product> getTableViewProduct() {
        return tableViewProduct;
    }

    public static String getLoadedFxml() {
        return loadedFxml;
    }

    private void loadUrl(Path path){
        try {
            URL url = path.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Product");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }catch (IOException e){
                System.out.println("Cannot load fxml file "+e.getMessage());
            }
        } catch (MalformedURLException m) {
            System.out.println("Incorect URL "+m.getMessage());
        }
    }
}
