package diet.gui.controlers;

import diet.model.Product;
import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.additionalClasses.ClassOfStaticMethodForControllers;
import diet.model.database.ProductData;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class MainWindowProductController {

    private static String loadedProductFxml = null;
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
    private TextField textFieldProductSearch;
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
        productFat.setCellValueFactory(new PropertyValueFactory<>("fat"));
        productCarbs.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        productFiber.setCellValueFactory(new PropertyValueFactory<>("fiber"));
        tableViewProduct.setItems(productsList);
        tableViewProduct.getSortOrder().add(productName);
        ClassOfStaticMethodForControllers.initializeTextFieldSearchForList(textFieldProductSearch, tableViewProduct, productsList);
        initializeAddListenerToProductList();
        initializeAddContextMenuToTableProduct();
    }

    @FXML
    public void setButtonAddNewProduct() {
        loadedProductFxml = "Add";
        Path pathNewProduct = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\ProductAdd.fxml");
        ClassOfStaticMethod.loadUrl(pathNewProduct, "Product","");
    }

    @FXML
    public void setButtonEditProduct() {
        loadedProductFxml = "Edit";
        Product.setSelectedProduct(tableViewProduct.getSelectionModel().getSelectedItem());
        if (Product.getSelectedProduct() != null) {
            Path pathNewProduct = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\ProductAdd.fxml");
            ClassOfStaticMethod.loadUrl(pathNewProduct, "Product","");
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("No product selected", "choose product by clicking it in table");
        }
    }

    @FXML
    public void setButtonDeleteProduct() {
        Product.setSelectedProduct(tableViewProduct.getSelectionModel().getSelectedItem());
        if (Product.getSelectedProduct() != null) {
            Alert alertNoChoosen = ClassOfStaticMethodForControllers.createAlertTypeConfirmation("Delete confirmation",
                    "Do you really want to delete product " + Product.getSelectedProduct().getName() + "?");
            Optional<ButtonType> result = alertNoChoosen.showAndWait();
            if (result.get() == ButtonType.OK) {
                ProductData.getInstance().deleteProductProfil(Product.getSelectedProduct(), true);
                ProductData.getProductsList().remove(Product.getSelectedProduct());
            } else {
                alertNoChoosen.close();
            }
        } else {
            ClassOfStaticMethodForControllers.createAlertTypeWarning("No product selected", "choose product by clicking it in table");
        }
    }

    public static String getLoadedProductFxml() {
        return loadedProductFxml;
    }

    private void initializeAddListenerToProductList() {
        ProductData.getProductsList().addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> change) {
                tableViewProduct.setItems(productsList);
                while (change.next()) {
                    if (change.wasAdded()) {
                        tableViewProduct.refresh();
                        tableViewProduct.getSortOrder().add(productName);
                    }
                    if (change.wasRemoved()) {
                        tableViewProduct.getSortOrder().add(productName);
                    }
                }
            }
        });
    }

    private void initializeAddContextMenuToTableProduct() {
        tableViewProduct.setRowFactory(new Callback<TableView<Product>, TableRow<Product>>() {
            @Override
            public TableRow<Product> call(TableView<Product> productTableView) {
                TableRow<Product> returnTableRow = new TableRow<>();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem edit = new MenuItem("Edit");
                edit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Product.setSelectedProduct(tableViewProduct.getSelectionModel().getSelectedItem());
                        loadedProductFxml = "Edit";
                        Path pathNewProduct = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\ProductAdd.fxml");
                        ClassOfStaticMethod.loadUrl(pathNewProduct, "Product","");
                    }
                });
                MenuItem delete = new MenuItem("Delete");
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Product.setSelectedProduct(tableViewProduct.getSelectionModel().getSelectedItem());
                        Alert alertNoChoosen = ClassOfStaticMethodForControllers.createAlertTypeConfirmation("Delete confirmation",
                                "Do you really want to delete product " + Product.getSelectedProduct().getName() + "?");
                        Optional<ButtonType> result = alertNoChoosen.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            ProductData.getInstance().deleteProductProfil(Product.getSelectedProduct(), true);
                            ProductData.getProductsList().remove(Product.getSelectedProduct());
                        } else {
                            alertNoChoosen.close();
                        }
                    }
                });
                contextMenu.getItems().addAll(edit, delete);
                returnTableRow.contextMenuProperty().bind(Bindings.when(returnTableRow.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
                return returnTableRow;
            }
        });
    }
}