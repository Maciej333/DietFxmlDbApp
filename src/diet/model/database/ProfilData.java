package diet.model.database;

import diet.model.Profil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfilData {

    private static ObservableList<Profil> profilsList;

    private static final String TABLE = "profil";
    public static final String PROFIL_ID = "ID_PROFIL";
    private static final String PROFIL_NAME = "NAME";
    private static final String PROFIL_AGE = "AGE";
    private static final String PROFIL_GROWTH = "GROWTH";
    private static final String PROFIL_WEIGHT = "WEIGHT";
    private static final String PROFIL_SEX = "SEX";
    private static final String PROFIL_GOAL = "GOAL";

    private static final String READ_ALL_QUERY = "SELECT * FROM "+TABLE;

    public static ObservableList<Profil> readAllProfils(){
        List<Profil> profils = new ArrayList<>();
        try(Connection conn = Datasource.getInstance().getConnect();
            PreparedStatement statement = conn.prepareStatement(READ_ALL_QUERY)){

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Profil profil = new Profil();
                profil.setIdPerson(resultSet.getInt(PROFIL_ID));
                profil.setName(resultSet.getString(PROFIL_NAME));
                profil.setAge(resultSet.getInt(PROFIL_AGE));
                profil.setGrowth(resultSet.getInt(PROFIL_GROWTH));
                profil.setWeight(resultSet.getInt(PROFIL_WEIGHT));
                profil.setSex(resultSet.getString(PROFIL_SEX));
                profil.setGoal(resultSet.getString(PROFIL_GOAL));

                            System.out.println(profil);
                profils.add(profil);
            }
        }catch(SQLException e){
            System.out.println("Query failed "+e.getMessage());
        }
        profilsList = FXCollections.observableList(profils);
        return profilsList;
    }
}
