package diet.gui.controlers;

import diet.Main;
import diet.model.Profil;
import diet.model.database.ProfilData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProfilChoiceController {

    @FXML
    private ComboBox<Profil> comboBoxProfil;

    @FXML
    private Button buttonAddProfil;

    @FXML
    private Button buttonLoadProfil;

    @FXML
    private Button buttonCancel;


    public void initialize() {
        ProfilData.getInstance().readAllProfils();
        comboBoxProfil.itemsProperty().setValue(ProfilData.getProfilsList());
        if(ProfilData.getProfilsList().size() > 0){
            comboBoxProfil.getSelectionModel().select(0);
        }

    }

    @FXML
    public void setButtonAddProfil(){
        Path p = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\ProfilCreate.fxml");
        try {
            URL u = p.toUri().toURL();
            FXMLLoader loader = new FXMLLoader(u);
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }catch (IOException e){
                System.out.println("Cannot load ProfilChange "+e.getMessage());
            }
        } catch (MalformedURLException m) {
            System.out.println("Incorect URL "+m.getMessage());
        }
    }

    @FXML
    public void setButtonLoadProfil(){
        if(getSelectedProfil() != null) {
            Path p = Paths.get("..\\DietFxmlDbApp\\src\\diet\\gui\\fxml\\MainWindow.fxml");
            try {
                URL u = p.toUri().toURL();
                FXMLLoader loader = new FXMLLoader(u);
                try {
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    Stage primaryStage = Main.getPrimaryStage();
                    primaryStage.close();
                } catch (IOException e) {
                    System.out.println("Cannot load MainWindow " + e.getMessage());
                }
            } catch (MalformedURLException m) {
                System.out.println("Incorect URL " + m.getMessage());
            }
        }else{
            Alert alterProfilNoChoice = new Alert(Alert.AlertType.WARNING);
            alterProfilNoChoice.setContentText("befor moving on, choose profil You would like to load");
            alterProfilNoChoice.setTitle("choose profil");
            alterProfilNoChoice.show();
        }
    }

    @FXML
    public void setButtonCancel(){
        Platform.exit();
    }

    public Profil getSelectedProfil(){
        return comboBoxProfil.getSelectionModel().getSelectedItem();
    }
}
