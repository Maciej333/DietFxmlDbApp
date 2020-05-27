package diet.model.database;

import diet.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
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
            " WHERE " + TABLE_ID_PROFIL + " = ?";

    private static final String READ_MEALS_FOR_DIET = "SELECT " + TABLE_DIET_MEAL_ID_MEAL + ", " + TABLE_DIET_MEAL_AMOUNT
            + " FROM " + TABLE_DIET_MEAL + " WHERE " + TABLE_DIET_MEAL_ID_DIET + " =?";

    private static final String READ_PRODUCTS_FOR_DIET = "SELECT " + TABLE_DIET_PRODUCT_ID_PRODUCT + ", " + TABLE_DIET_PRODUCT_AMOUNT
            + " FROM " + TABLE_DIET_PRODUCT + " WHERE " + TABLE_DIET_PRODUCT_ID_DIET + " =?";

    private static final String READ_MAX_DIET_ID = "SELECT MAX(" + TABLE_ID_DIET + ") FROM " + TABLE;
    private static final String INSERT_DIET = "INSERT INTO " + TABLE + " VALUES (?,?,?)";
    private static final String INSERT_DIET_MEAL = "INSERT INTO " + TABLE_DIET_MEAL + " VALUES (?,?,?)";
    private static final String INSERT_DIET_PRODUCT = "INSERT INTO " + TABLE_DIET_PRODUCT + " VALUES (?,?,?)";

    private static final String UPDATE_DIET = "UPDATE " + TABLE + " SET " + TABLE_EATDATE + " =? WHERE " + TABLE_ID_DIET + " =? AND " + TABLE_ID_PROFIL + " =?";

    private static final String DELETE_DIET = "DELETE FROM " + TABLE + " WHERE " + TABLE_ID_DIET + " =? AND " + TABLE_ID_PROFIL + " =?";
    private static final String DELETE_DIET_MEAL = "DELETE FROM " + TABLE_DIET_MEAL + " WHERE " + TABLE_DIET_MEAL_ID_DIET + " =? AND " + TABLE_DIET_MEAL_ID_MEAL + " =?";
    private static final String DELETE_DIET_PRODUCT = "DELETE FROM " + TABLE_DIET_PRODUCT + " WHERE " + TABLE_DIET_PRODUCT_ID_DIET + " =? AND " + TABLE_DIET_PRODUCT_ID_PRODUCT + " =?";

    private DietData() {

    }

    public static DietData getInstance() {
        return singletonDietData;
    }

    public void readDietForProfil() {
        List<Diet> diets = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(READ_DIET_FOR_PROFIL)) {
            preparedStatement.setInt(1,Profil.getSelectedProfil().getIdPerson());

            ResultSet resultSet = preparedStatement.executeQuery();
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

    public void insertDiet(Diet diet) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_DIET)) {
            conn.setAutoCommit(false);

            preparedStatement.setInt(1, (getMaxDietId() + 1));
            preparedStatement.setInt(2, Profil.getSelectedProfil().getIdPerson());
            preparedStatement.setString(3, diet.getFormatDate());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();

                diet.setIdDiet(getMaxDietId());
                for (Map.Entry<Food, Integer> mapEntry : diet.getDietMealsProductsMap().entrySet()) {
                    if (mapEntry.getKey().getClass().getName().matches(".*Meal")) {
                        insertDietMeal(diet, (Meal) mapEntry.getKey(), mapEntry.getValue());
                    } else if (mapEntry.getKey().getClass().getName().matches(".*Product")) {
                        insertDietProduct(diet, (Product) mapEntry.getKey(), mapEntry.getValue());
                    }
                }
                diet.countKcalForDiet();
                diet.countProteinForDiet();
                diet.countFatForDiet();
                diet.countCarbsForDiet();
                diet.countFiberForDiet();

                dietsList.add(diet);
            } else {
                conn.rollback();
                throw new SQLException("Insert diet error");
            }
        } catch (SQLException e) {
            System.out.println("Insert diet Failed" + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getMaxDietId() {
        int maxId = 0;
        try (PreparedStatement preparedStatement = conn.prepareStatement(READ_MAX_DIET_ID);
             ResultSet resultset = preparedStatement.executeQuery()) {
            while (resultset.next()) {
                maxId = resultset.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Query Failed" + e.getMessage());
        }
        return maxId;
    }

    private void insertDietMeal(Diet diet, Meal meal, int amount) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_DIET_MEAL)) {
            conn.setAutoCommit(false);

            preparedStatement.setInt(1, diet.getIdDiet());
            preparedStatement.setInt(2, meal.getIdMeal());
            preparedStatement.setInt(3, amount);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
            } else {
                conn.rollback();
                throw new SQLException("Insert diet meal error");
            }
        } catch (SQLException e) {
            System.out.println("Insert diet meal Failed" + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertDietProduct(Diet diet, Product product, int amount) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_DIET_PRODUCT)) {
            conn.setAutoCommit(false);

            preparedStatement.setInt(1, diet.getIdDiet());
            preparedStatement.setInt(2, product.getIdProduct());
            preparedStatement.setInt(3, amount);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
            } else {
                conn.rollback();
                throw new SQLException("Insert diet product error");
            }
        } catch (SQLException e) {
            System.out.println("Insert diet product Failed" + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDiet(Diet diet) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_DIET)) {
            conn.setAutoCommit(false);

            preparedStatement.setString(1, diet.getFormatDate());
            preparedStatement.setInt(2, diet.getIdDiet());
            preparedStatement.setInt(3, Profil.getSelectedProfil().getIdPerson());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();

                Map<Food, Integer> mapOfDietFood = new HashMap<>();
                mapOfDietFood.putAll(readMealsForDied(diet.getIdDiet()));
                mapOfDietFood.putAll(readProductsForDied(diet.getIdDiet()));

                for (Map.Entry<Food, Integer> mapEntry : mapOfDietFood.entrySet()) {
                    if (mapEntry.getKey().getClass().getName().matches(".*Meal")) {
                        deleteDietMeal(diet, ((Meal) mapEntry.getKey()).getIdMeal());
                    } else if (mapEntry.getKey().getClass().getName().matches(".*Product")) {
                        deleteDietProduct(diet, ((Product) mapEntry.getKey()).getIdProduct());
                    }
                }

                for (Map.Entry<Food, Integer> mapEntry : diet.getDietMealsProductsMap().entrySet()) {
                    if (mapEntry.getKey().getClass().getName().matches(".*Meal")) {
                        insertDietMeal(diet, (Meal) mapEntry.getKey(), mapEntry.getValue());
                    } else if (mapEntry.getKey().getClass().getName().matches(".*Product")) {
                        insertDietProduct(diet, (Product) mapEntry.getKey(), mapEntry.getValue());
                    }
                }

                dietsList.add(diet);
            } else {
                conn.rollback();
                throw new SQLException("Update diet error");
            }
        } catch (SQLException e) {
            System.out.println("Update diet Failed" + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDiet(Diet diet) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_DIET)) {
            conn.setAutoCommit(false);

            preparedStatement.setInt(1, diet.getIdDiet());
            preparedStatement.setInt(2, Profil.getSelectedProfil().getIdPerson());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();

                Map<Food, Integer> mapOfDietFood = new HashMap<>();
                mapOfDietFood.putAll(readMealsForDied(diet.getIdDiet()));
                mapOfDietFood.putAll(readProductsForDied(diet.getIdDiet()));
                for (Map.Entry<Food, Integer> mapEntry : mapOfDietFood.entrySet()) {
                    if (mapEntry.getKey().getClass().getName().matches(".*Meal")) {
                        deleteDietMeal(diet, ((Meal) mapEntry.getKey()).getIdMeal());
                    } else if (mapEntry.getKey().getClass().getName().matches(".*Product")) {
                        deleteDietProduct(diet, ((Product) mapEntry.getKey()).getIdProduct());
                    }
                }
            } else {
                conn.rollback();
                throw new SQLException("Delete diet error");
            }
        } catch (SQLException e) {
            System.out.println("Delete diet Failed" + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDietMeal(Diet diet, int mealId) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_DIET_MEAL)) {
            conn.setAutoCommit(false);

            preparedStatement.setInt(1, diet.getIdDiet());
            preparedStatement.setInt(2, mealId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
            } else {
                conn.rollback();
                throw new SQLException("Delete diet meal error");
            }
        } catch (SQLException e) {
            System.out.println("Delete diet meal Failed" + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDietProduct(Diet diet, int productId) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_DIET_PRODUCT)) {
            conn.setAutoCommit(false);

            preparedStatement.setInt(1, diet.getIdDiet());
            preparedStatement.setInt(2, productId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
            } else {
                conn.rollback();
                throw new SQLException("Delete diet product error");
            }
        } catch (SQLException e) {
            System.out.println("Delete diet product Failed" + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
