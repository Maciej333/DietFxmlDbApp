module DietFxmlDbApp {
    requires javafx.fxml;
    requires javafx.controls;

    opens diet;
    opens diet.gui.controlers;
    opens diet.gui.fxml;
}