module DietFxmlDbApp {
    requires java.sql;
    requires javafx.fxml;
    requires javafx.controls;

    opens diet;
    opens diet.gui.controlers;
    opens diet.gui.fxml;
    opens diet.model;
    opens diet.model.database;
}