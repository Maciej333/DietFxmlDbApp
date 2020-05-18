package diet;

import diet.model.database.Datasource;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui/fxml/ProfilChoice.fxml"));

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 200, 220));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        if(!Datasource.getInstance().openConnection()){
            System.out.println("ERROR during connecting to database, application won't run ");
            Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Datasource.getInstance().closeConnectio();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
