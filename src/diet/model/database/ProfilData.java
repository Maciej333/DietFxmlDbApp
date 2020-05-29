package diet.model.database;

import diet.model.Diet;
import diet.model.Meal;
import diet.model.Product;
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

    private static final String READ_ALL_QUERY = "SELECT * FROM " + TABLE;
    private static final String READ_MAX_ID_PROFIL = "SELECT MAX(" + PROFIL_ID + ") FROM " + TABLE;
    private static final String INSERT_NEW_PROFIL = "INSERT INTO " + TABLE + " VALUES (?,?,?,?,?,?,?)";
    private static final String UPDETE_PROFIL = "UPDATE " + TABLE + " SET " + PROFIL_NAME + " =?, "
            + PROFIL_AGE + " =?, " + PROFIL_WEIGHT + " =?, " + PROFIL_GROWTH + " =?, "
            + PROFIL_SEX + " =?, " + PROFIL_GOAL + " =? WHERE " + PROFIL_ID + " = ?";
    private static final String DELETE_PROFIL = "DELETE FROM " + TABLE + " WHERE " + PROFIL_ID + " = ?";

    private ProfilData() {
    }

    public static ProfilData getInstance() {
        return singletonProfilData;
    }

    public void readAllProfils() {
        List<Profil> profils = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(READ_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
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
        } catch (SQLException e) {
            System.out.println("Query read all profils failed " + e.getMessage());
        }
        profilsList = FXCollections.observableList(profils);
    }

    public int readMaxProfilId() {
        int maxProfilId = 0;
        try (PreparedStatement statement = conn.prepareStatement(READ_MAX_ID_PROFIL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                maxProfilId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Query read max profil id failed " + e.getMessage());
        }
        return maxProfilId;
    }

    public void insetNewProfil(String name, int age, int growth, int weight, String sex, String goal) {
        try (PreparedStatement statement = conn.prepareStatement(INSERT_NEW_PROFIL)) {
            int profilId = readMaxProfilId() + 1;
            statement.setInt(1, profilId);
            statement.setString(2, name);
            statement.setInt(3, age);
            statement.setInt(4, growth);
            statement.setInt(5, weight);
            statement.setString(6, sex);
            statement.setString(7, goal);
            statement.executeUpdate();
            profilsList.add(new Profil(profilId, name, age, growth, weight, sex, goal));
        } catch (SQLException e) {
            System.out.println("Insert new profil failed " + e.getMessage());
        }
    }

    public void updateProfil(String name, int age, int weight, int growth, String sex, String goal, int profilId) {
        try (PreparedStatement statement = conn.prepareStatement(UPDETE_PROFIL)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setInt(3, weight);
            statement.setInt(4, growth);
            statement.setString(5, sex);
            statement.setString(6, goal);
            statement.setInt(7, profilId);
            statement.executeUpdate();

            Profil.getSelectedProfil().setName(name);
            Profil.getSelectedProfil().setAge(age);
            Profil.getSelectedProfil().setWeight(weight);
            Profil.getSelectedProfil().setGrowth(growth);
            Profil.getSelectedProfil().setSex(sex);
            Profil.getSelectedProfil().setGoal(goal);
        } catch (SQLException e) {
            System.out.println("Update profil failed " + e.getMessage());
        }
    }

    public void deleteProfil(int profilId) {
        try (PreparedStatement statement = conn.prepareStatement(DELETE_PROFIL)) {
            statement.setInt(1, profilId);

            DietData.getInstance().readDietForProfil();
            MealData.getInstance().readAllMealForProfil();
            ProductData.getInstance().readAllProductForProfil();
            for (Diet diet : DietData.getDietsList()) {
                DietData.getInstance().deleteDiet(diet);
            }
            for (Meal meal : MealData.getMealsList()) {
                MealData.getInstance().deleteMealForProfil(Profil.getSelectedProfil().getIdPerson(), meal, false);
            }
            for (Product product : ProductData.getProductsList()) {
                ProductData.getInstance().deleteProductProfil(product, false);
            }
            statement.executeUpdate();
            profilsList.remove(Profil.getSelectedProfil());
        } catch (SQLException e) {
            System.out.println("Delete profil failed " + e.getMessage());
        }
    }

    public static ObservableList<Profil> getProfilsList() {
        return profilsList;
    }
}
