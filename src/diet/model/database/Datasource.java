package diet.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datasource {
    public static final String DB_NAME = "diet.db";
    public static final String CONNECTION = "jdbc:sqlite:"+DB_NAME;

    //public static final String

    private Connection connect;


    private static Datasource singletonDs = new Datasource();

    private Datasource(){

    }

    public static Datasource getInstance(){
        return singletonDs;
    }

    public boolean openConnection(){
        try {
            connect = DriverManager.getConnection(CONNECTION);
            return true;
        } catch (SQLException e) {
            System.out.println("Connection to database failed "+e.getMessage());
            return false;
        }
    }

    public void closeConnectio(){
        try {
            if (connect != null) {
                connect.close();
            }
        }catch(SQLException e){
            System.out.println("Failure to close database "+e.getMessage());
        }
    }


}