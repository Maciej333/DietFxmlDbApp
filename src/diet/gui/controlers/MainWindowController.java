package diet.gui.controlers;

import diet.model.additionalClasses.ClassOfStaticMethod;
import diet.model.additionalClasses.ClassOfStaticMethodForControllers;
import diet.model.database.MealData;
import diet.model.database.ProductData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class MainWindowController {

    @FXML
    private MenuItem menuItemChangeProfil;
    @FXML
    private MenuItem menuItemDeleteUnuseProducts;
    @FXML
    private MenuItem menuItemDeleteUnuseMeals;
    @FXML
    private MenuItem menuItemClose;
    @FXML
    private MenuItem menuItemAutor;
    @FXML
    private BorderPane borderPaneMainWindow;

    @FXML
    private Button buttonProfil;

    @FXML
    private Button buttonProducts;

    @FXML
    private Button buttonMeals;

    @FXML
    private Button buttonDiet;

    @FXML
    private Button buttonStats;

    public void initialize() {
        Platform.runLater(() -> buttonDiet.requestFocus());
        Path path = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowDiet.fxml");
        loadUrl(path);
    }

    @FXML
    public void setButtonProfil() {
        Path path = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowProfilInfo.fxml");
        loadUrl(path);
    }

    @FXML
    public void setButtonProducts() {
        Path path = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowProduct.fxml");
        loadUrl(path);
    }

    @FXML
    public void setButtonMeals() {
        Path path = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowMeal.fxml");
        loadUrl(path);
    }

    @FXML
    public void setButtonDiet() {
        Path path = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowDiet.fxml");
        loadUrl(path);
    }

    @FXML
    public void setButtonStats() {
        Path path = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowStats.fxml");
        loadUrl(path);
    }

    @FXML
    public void setMenuItemChangeProfil() {
        Path path = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\ProfilChoice.fxml");
        ClassOfStaticMethod.loadUrl(path, "Change profil", "");
        Stage mainStage = (Stage) borderPaneMainWindow.getScene().getWindow();
        mainStage.close();
    }

    @FXML
    public void setMenuItemDeleteUnuseProducts() {
        Alert deleteProducts = ClassOfStaticMethodForControllers.createAlertTypeConfirmation("Products",
                "Do Tou really want to delete unuse products?");
        Optional<ButtonType> result = deleteProducts.showAndWait();
        if (result.get() == ButtonType.OK) {
            ProductData.getInstance().deleteUnuseProducts();
        } else {
            deleteProducts.close();
        }
    }

    @FXML
    public void setMenuItemDeleteUnuseMeals() {
        Alert deleteMeals = ClassOfStaticMethodForControllers.createAlertTypeConfirmation("Meals",
                "Do Tou really want to delete unuse meals?");
        Optional<ButtonType> result = deleteMeals.showAndWait();
        if (result.get() == ButtonType.OK) {
            MealData.getInstance().deleteUnuseProducts();
        } else {
            deleteMeals.close();
        }
    }

    @FXML
    public void setMenuItemClose() {
        Stage mainStage = (Stage) borderPaneMainWindow.getScene().getWindow();
        mainStage.close();
    }

    @FXML
    public void setMenuItemAutor() {
        Alert author = new Alert(Alert.AlertType.NONE);
        author.setTitle("Autor");
        author.setContentText("Maciej Rogowski");
        author.getButtonTypes().add(ButtonType.OK);
        author.show();
    }

    private void loadUrl(Path path) {
        try {
            URL url = path.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            try {
                this.borderPaneMainWindow.setCenter(loader.load());
            } catch (IOException e) {
                System.out.println("Cannot load MainWindow " + e.getMessage());
            }
        } catch (MalformedURLException m) {
            System.out.println("Incorect URL " + m.getMessage());
        }
    }
}