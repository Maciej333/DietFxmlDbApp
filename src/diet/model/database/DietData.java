package diet.model.database;

import diet.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class DietData {

    private Connection conn = Datasource.getInstance().getConnect();
    private static DietData singletonDietData = new DietData();
    private static ObservableList<Diet> dietsList;

    private static final String TABLE = "DIET";
    private static final String TABLE_ID_DIET = "ID_DIET";
    private static final String TABLE_ID_PROFIL = "ID_PROFIL";
    private static final String TABLE_EATDATE = "EATDATE";

    private static final String TABLE_DIET_MEAL = "DIET_MEAL";
    private static final String TABLE_DIET_MEAL_ID_DIET = "ID_DIET";
    private static final String TABLE_DIET_MEAL_ID_MEAL = "ID_MEAL";
    private static final String TABLE_DIET_MEAL_AMOUNT = "AMOUNT";

    private static final String TABLE_DIET_PRODUCT = "DIET_PRODUCT";
    private static final String TABLE_DIET_PRODUCT_ID_DIET = "ID_DIET";
    private static final String TABLE_DIET_PRODUCT_ID_PRODUCT = "ID_PRODUCT";
    private static final String TABLE_DIET_PRODUCT_AMOUNT = "AMOUNT";

    private static final String READ_DIET_FOR_PROFIL = "SELECT " + TABLE_ID_DIET + ", " + TABLE_EATDATE + " FROM " + TABLE +
            " WHERE " + TABLE_ID_PROFIL + " = " + Profil.getSelectedProfil().getIdPerson();

    private static final String READ_MEALS_FOR_DIET = "SELECT " + TABLE_DIET_MEAL_ID_MEAL + ", " + TABLE_DIET_MEAL_AMOUNT
            + " FROM " + TABLE_DIET_MEAL + " WHERE " + TABLE_DIET_MEAL_ID_DIET + " =?";

    private static final String READ_PRODUCTS_FOR_DIET = "SELECT " + TABLE_DIET_PRODUCT_ID_PRODUCT + ", " + TABLE_DIET_PRODUCT_AMOUNT
            + " FROM " + TABLE_DIET_PRODUCT + " WHERE " + TABLE_DIET_PRODUCT_ID_DIET + " =?";

    private DietData() {

    }

    public static DietData getInstance() {
        return singletonDietData;
    }

    public void readDietForProfil() {
        List<Diet> diets = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(READ_DIET_FOR_PROFIL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Diet diet = new Diet();
                diet.setIdDiet(resultSet.getInt(TABLE_ID_DIET));
                diet.setIdOsoba(Profil.getSelectedProfil().getIdPerson());
                String date = resultSet.getString(TABLE_EATDATE);
                diet.setDate(parseStringToDate(date));
                diet.addMapToDietMealsProductsMap(readMealsForDied(diet.getIdDiet()));
                diet.addMapToDietMealsProductsMap(readProductsForDied(diet.getIdDiet()));
                diet.countKcalForDiet();
                diet.countProteinForDiet();
                diet.countFatForDiet();
                diet.countCarbsForDiet();
                diet.countFiberForDiet();

                diets.add(diet);
            }
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
        }
        dietsList = FXCollections.observableList(diets);
    }

    private Map<Food, Integer> readMealsForDied(int diedId) {
        Map<Food, Integer> meals = new HashMap<>();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement(READ_MEALS_FOR_DIET)) {
            preparedStatement.setInt(1, diedId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int meal_id = resultSet.getInt(TABLE_DIET_MEAL_ID_MEAL);
                MealData.getInstance().readAllMealForProfil();
                ObservableList<Meal> mealsList = MealData.getMealsList();
                for (int i = 0; i < mealsList.size(); i++) {
                    if (meal_id == mealsList.get(i).getIdMeal()) {
                        meals.put(mealsList.get(i), resultSet.getInt(TABLE_DIET_MEAL_AMOUNT));
                        i = mealsList.size();
                    }
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return meals;
    }

    private Map<Food, Integer> readProductsForDied(int diedId) {
        Map<Food, Integer> meals = new HashMap<>();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement(READ_PRODUCTS_FOR_DIET)) {
            preparedStatement.setInt(1, diedId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int product_id = resultSet.getInt(TABLE_DIET_PRODUCT_ID_PRODUCT);
                ProductData.getInstance().readAllProductForProfil();
                ObservableList<Product> productsList = ProductData.getProductsList();
                for (int i = 0; i < productsList.size(); i++) {
                    if (product_id == productsList.get(i).getIdProduct()) {
                        meals.put(productsList.get(i), resultSet.getInt(TABLE_DIET_PRODUCT_AMOUNT));
                        i = productsList.size();
                    }
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return meals;
    }

    private LocalDateTime parseStringToDate(String stringDate) {
        String dateToSet = stringDate.replace(" ", "T");
        LocalDateTime localDateTime = LocalDateTime.parse(dateToSet);
        return localDateTime;
    }

    public static ObservableList<Diet> getDietsList() {
        return dietsList;
    }
}
