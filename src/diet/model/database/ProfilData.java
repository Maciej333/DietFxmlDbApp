package diet.model.database;

import diet.model.Profil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfilData {

    private static ProfilData singletonProfilData = new ProfilData();
    private Connection conn = Datasource.getInstance().getConnect();
    private static ObservableList<Profil> profilsList;

    private static final String TABLE = "profil";
    private static final String PROFIL_ID = "ID_PROFIL";
    private static final String PROFIL_NAME = "NAME";
    private static final String PROFIL_AGE = "AGE";
    private static final String PROFIL_GROWTH = "GROWTH";
    private static final String PROFIL_WEIGHT = "WEIGHT";
    private static final String PROFIL_SEX = "SEX";
    private static final String PROFIL_GOAL = "GOAL";

    private static final String READ_ALL_QUERY = "SELECT * FROM "+TABLE;
    private static final String READ_MAX_ID_PROFIL  ="SELECT MAX("+PROFIL_ID+") FROM "+TABLE;
    private static final String INSERT_NEW_PROFIL = "INSERT INTO "+TABLE+" VALUES (?,?,?,?,?,?,?)";

    private ProfilData(){
    }

    public static ProfilData getInstance(){
        return singletonProfilData;
    }

    public void readAllProfils(){
        List<Profil> profils = new ArrayList<>();
        try(PreparedStatement statement = conn.prepareStatement(READ_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){
                Profil profil = new Profil();
                profil.setIdPerson(resultSet.getInt(PROFIL_ID));
                profil.setName(resultSet.getString(PROFIL_NAME));
                profil.setAge(resultSet.getInt(PROFIL_AGE));
                profil.setGrowth(resultSet.getInt(PROFIL_GROWTH));
                profil.setWeight(resultSet.getInt(PROFIL_WEIGHT));
                profil.setSex(resultSet.getString(PROFIL_SEX));
                profil.setGoal(resultSet.getString(PROFIL_GOAL));
                profils.add(profil);
            }
        }catch(SQLException e){
            System.out.println("Query failed "+e.getMessage());
        }
        profilsList = FXCollections.observableList(profils);
    }

    public void insetNewProfil(String name, int age, int growth, int weight, String sex, String goal){
        try(PreparedStatement statement = conn.prepareStatement(INSERT_NEW_PROFIL) ){
            conn.setAutoCommit(false);

            int profilId = getMaxProfilId()+1;
            statement.setInt(1,profilId);
            statement.setString(2,name);
            statement.setInt(3,age);
            statement.setInt(4,growth);
            statement.setInt(5,weight);
            statement.setString(6,sex);
            statement.setString(7,goal);

            int affectedRows = statement.executeUpdate();
            if(affectedRows == 1){
                conn.commit();
                profilsList.add(new Profil(profilId,name,age,growth,weight,sex,goal));
            }else {
                conn.rollback();
                throw new SQLException("Insert profil error");
            }
        } catch(SQLException e){
        System.out.println("Insert failed "+e.getMessage());
        }finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getMaxProfilId(){
        int maxProfilId = 0;
        try(PreparedStatement statement = conn.prepareStatement(READ_MAX_ID_PROFIL);
            ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){
                maxProfilId = resultSet.getInt(1);
            }
        } catch(SQLException e){
            System.out.println("Query failed "+e.getMessage());
        }
        return maxProfilId;
    }

    public static ObservableList<Profil> getProfilsList() {
        return profilsList;
    }
}
