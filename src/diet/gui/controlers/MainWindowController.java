package diet.gui.controlers;

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
    BorderPane borderPaneMainWindow;

    @FXML
    Button buttonProfil;

    @FXML
    Button buttonProducts;

    @FXML
    Button buttonMeals;

    @FXML
    Button buttonDiet;

    @FXML
    Button buttonStats;

    public void initialize() {
        Path p = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowDiet.fxml");
        try {
            URL u = p.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(u);
            try {
                this.borderPaneMainWindow.setCenter(loader.load());
            }catch (IOException e){
                System.out.println("Cannot load MainWindowDiet "+e.getMessage());
            }
        } catch (MalformedURLException m) {
            System.out.println("Incorect URL "+m.getMessage());
        }
    }

    @FXML
    public void setButtonProfil(){
        Path p = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowProfilInfo.fxml");
        try {
            URL u = p.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(u);
            try {
                this.borderPaneMainWindow.setCenter(loader.load());
            }catch (IOException e){
                System.out.println("Cannot load MainWindowProfil "+e.getMessage());
            }
        } catch (MalformedURLException m) {
            System.out.println("Incorect URL "+m.getMessage());
        }
    }

    @FXML
    public void setButtonProducts(){
        Path p = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowProduct.fxml");
        try {
            URL u = p.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(u);
            try {
                this.borderPaneMainWindow.setCenter(loader.load());
            }catch (IOException e){
                System.out.println("Cannot load MainWindowProduct "+e.getMessage());
            }
        } catch (MalformedURLException m) {
            System.out.println("Incorect URL "+m.getMessage());
        }
    }

    @FXML
    public void setButtonMeals(){
        Path p = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowMeal.fxml");
        try {
            URL u = p.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(u);
            try {
                this.borderPaneMainWindow.setCenter(loader.load());
            }catch (IOException e){
                System.out.println("Cannot load MainWindowMeal "+e.getMessage());
            }
        } catch (MalformedURLException m) {
            System.out.println("Incorect URL "+m.getMessage());
        }
    }

    @FXML
    public void setButtonDiet(){
        Path p = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowDiet.fxml");
        try {
            URL u = p.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(u);
            try {
                this.borderPaneMainWindow.setCenter(loader.load());
            }catch (IOException e){
                System.out.println("Cannot load MainWindowDiet "+e.getMessage());
            }
        } catch (MalformedURLException m) {
            System.out.println("Incorect URL "+m.getMessage());
        }
    }

    @FXML
    public void setButtonStats(){
        Path p = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindowStats.fxml");
        try {
            URL u = p.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(u);
            try {
                this.borderPaneMainWindow.setCenter(loader.load());
            }catch (IOException e){
                System.out.println("Cannot load MainWindowStats "+e.getMessage());
            }
        } catch (MalformedURLException m) {
            System.out.println("Incorect URL "+m.getMessage());
        }
    }
}
