package diet.model.database;

import diet.model.Meal;
import diet.model.Product;
import diet.model.Profil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MealData {
    private Connection conn = Datasource.getInstance().getConnect();
    private static MealData singletonMealData = new MealData();
    private static ObservableList<Meal> mealsList;

    private static final String TABLE_PROFIL_MEAL = "PROFIL_MEAL";
    private static final String TABLE_PROFIL_MEAL_PROFIL_ID = "ID_PROFIL";
    private static final String TABLE_PROFIL_MEAL_MEAL_ID = "ID_MEAL";

    private static final String TABLE = "MEAL";
    private static final String MEAL_ID = "ID_MEAL";
    private static final String NAME = "NAME";

    private static final String READ_MEAL_FOR_PROFIL = "SELECT " + MEAL_ID + ", " + NAME + " FROM " + TABLE + " WHERE "
            + MEAL_ID + " IN (SELECT " + TABLE_PROFIL_MEAL_MEAL_ID + " FROM " + TABLE_PROFIL_MEAL +
            " WHERE " + TABLE_PROFIL_MEAL_PROFIL_ID + " = ?)";
    private static final String READ_MEAL_MAX_ID = "SELECT MAX(" + MEAL_ID + ") FROM " + TABLE;

    private static final String INSERT_NEW_MEAL = "INSERT INTO " + TABLE + " VALUES (?,?)";
    private static final String INSERT_NEW_MEAL_FOR_PROFIL = "INSERT INTO " + TABLE_PROFIL_MEAL + " VALUES (?,?)";

    private static final String UPDATE_MEAL = "UPDATE " + TABLE + " SET " + NAME + " =? WHERE " + MEAL_ID + " =?";

    private static final String DELETE_MEAL = "DELETE FROM " + TABLE + " WHERE " + MEAL_ID + " = ?";
    private static final String DELETE_MEAL_FOR_PROFIL = "DELETE FROM " + TABLE_PROFIL_MEAL + " WHERE " +
            TABLE_PROFIL_MEAL_PROFIL_ID + " = ? AND " + TABLE_PROFIL_MEAL_MEAL_ID + " = ?";

    private MealData() {
    }

    public static MealData getInstance() {
        return singletonMealData;
    }

    public void readAllMealForProfil() {
        List<Meal> meals = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(READ_MEAL_FOR_PROFIL)) {
            preparedStatement.setInt(1, Profil.getSelectedProfil().getIdPerson());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Meal meal = new Meal();
                meal.setIdMeal(resultSet.getInt(MEAL_ID));
                meal.setName(resultSet.getString(NAME));
                meal.setProductsForMeal(ProductData.getInstance().readAllProductForMeal(meal.getIdMeal()));
                meal.countKcalForMeal();
                meal.countProteinForMeal();
                meal.countFatForMeal();
                meal.countCarbsForMeal();
                meal.countFiberForMeal();

                meals.add(meal);
            }
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
        }
        mealsList = FXCollections.observableList(meals);
    }

    public int readMaxMealId() {
        int maxMealId = 1;
        try (PreparedStatement preparedStatement = conn.prepareStatement(READ_MEAL_MAX_ID);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                maxMealId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
        }
        return maxMealId;
    }

    public void insertNewMeal(String name, Map<Product, Integer> productsMap) {
        insertMeal(name, productsMap);
        insertMealForProfil();
    }

    private void insertMeal(String name, Map<Product, Integer> productsMap) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_NEW_MEAL)) {
            conn.setAutoCommit(false);

            int mealId = readMaxMealId() + 1;
            preparedStatement.setInt(1, mealId);
            preparedStatement.setString(2, name);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();

                for (Map.Entry<Product, Integer> mapEntry : productsMap.entrySet()) {
                    ProductData.getInstance().insertAllProductForMeal(mealId, mapEntry.getKey().getIdProduct(), mapEntry.getValue());
                }

                Meal insertedMeal = new Meal(mealId, name, productsMap);
                mealsList.add(insertedMeal);
            } else {
                conn.rollback();
                throw new SQLException("Insert product error");
            }
        } catch (SQLException e) {
            System.out.println("Insert failed " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertMealForProfil() {
        try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_NEW_MEAL_FOR_PROFIL)) {
            conn.setAutoCommit(false);

            int mealId = readMaxMealId();
            preparedStatement.setInt(1, Profil.getSelectedProfil().getIdPerson());
            preparedStatement.setInt(2, mealId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
            } else {
                conn.rollback();
                throw new SQLException("Insert product error");
            }
        } catch (SQLException e) {
            System.out.println("Insert failed " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateMeal(String name, int mealId, Map<Product, Integer> productsMap) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_MEAL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, mealId);

            preparedStatement.executeUpdate();

            Map<Product, Integer> productToRemove = ProductData.getInstance().readAllProductForMeal(Meal.getSelectedMeal().getIdMeal());
            for (Map.Entry<Product, Integer> mapEntry : productToRemove.entrySet()) {
                ProductData.getInstance().deleteProductForMeal(Meal.getSelectedMeal().getIdMeal(), mapEntry.getKey().getIdProduct());
            }
            for (Map.Entry<Product, Integer> mapEntry : productsMap.entrySet()) {
                ProductData.getInstance().insertAllProductForMeal(Meal.getSelectedMeal().getIdMeal(), mapEntry.getKey().getIdProduct(), mapEntry.getValue());
            }

        } catch (SQLException e) {
            System.out.println("Update failed " + e.getMessage());
        }
    }

    private void deleteMeal(Meal meal) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_MEAL)) {
            preparedStatement.setInt(1, meal.getIdMeal());
            preparedStatement.executeUpdate();

            for (Map.Entry<Product, Integer> productMap : meal.getProductsForMeal().entrySet())
                ProductData.getInstance().deleteProductForMeal(meal.getIdMeal(), productMap.getKey().getIdProduct());
        } catch (SQLException e) {
            System.out.println("Delete failed " + e.getMessage());
        }
    }

    public void deleteMealForProfil(int profilId, Meal meal, boolean checkDietData) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_MEAL_FOR_PROFIL)) {
            preparedStatement.setInt(1, profilId);
            preparedStatement.setInt(2, meal.getIdMeal());

            if (checkDietData) {
                if (DietData.getInstance().checkExistOfMealInDietMeal() == 0) {
                    deleteMeal(meal);
                }
            } else {
                deleteMeal(meal);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete failed " + e.getMessage());
        }
    }

    public static ObservableList<Meal> getMealsList() {
        return mealsList;
    }
}
