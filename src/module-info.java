module DietFxmlDbApp {
    requires java.sql;
    requires javafx.fxml;
    requires javafx.controls;

    requires org.junit.jupiter.api;

    opens diet;
    opens diet.gui.controlers;
    opens diet.gui.fxml;
    opens diet.model;
    opens diet.model.additionalClasses;
    opens diet.model.database;
}