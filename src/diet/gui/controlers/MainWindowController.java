package diet.gui.controlers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainWindowController {

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
