package diet.gui.controlers;

import diet.Main;
import diet.model.Product;
import diet.model.database.ProductData;
import diet.model.database.ProfilData;
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
        Path pathNewProduct = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\ProductAdd.fxml");
        loadUrl(pathNewProduct);
    }

    @FXML
    public void setButtonEditProduct(){
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

                                System.out.println("to co do usuniecia deleta zrobic ");

            } else {
            }
        }else{
            Alert alertNoChoosen = new Alert(Alert.AlertType.INFORMATION);
            alertNoChoosen.setTitle("No product selected");
            alertNoChoosen.setContentText("choose product by clicking it in table");
            alertNoChoosen.show();
        }
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
