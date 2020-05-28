package diet.model.database;

import diet.model.Product;
import diet.model.Profil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProductData {

    private Connection conn = Datasource.getInstance().getConnect();
    private static ProductData singletonProductData = new ProductData();
    private static ObservableList<Product> productsList;

    private static final String TABLE_PROFIL_PRODUCT = "PROFIL_PRODUCT";
    private static final String TABLE_PROFIL_PRODUCT_PROFIL_ID = "ID_PROFIL";
    private static final String TABLE_PROFIL_PRODUCT_PRODUCT_ID = "ID_PRODUCT";

    private static final String TABLE_MEAL_PRODUCT = "MEAL_PRODUCT";
    private static final String TABLE_MEAL_PRODUCT_ID_MEAL = "ID_MEAL";
    private static final String TABLE_MEAL_PRODUCT_ID_PRODUCT = "ID_PRODUCT";
    private static final String TABLE_MEAL_PRODUCT_AMOUNT = "AMOUNT";

    private static final String TABLE = "PRODUCT";
    private static final String PRODUCT_ID = "ID_PRODUCT";
    private static final String NAME = "NAME";
    private static final String KCAL = "KCAL";
    private static final String PROTEIN = "PROTEIN";
    private static final String FAT = "FAT";
    private static final String CARBS = "CARBS";
    private static final String FIBER = "FIBER";

    private static final String READ_MAX_ID_PRODUCT = "SELECT MAX(" + PRODUCT_ID + ") FROM " + TABLE;
    private static final String INSERT_PRODUCT = "INSERT INTO " + TABLE + " VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "UPDATE " + TABLE + " SET " + NAME + " =?, "
            + KCAL + " =?, " + PROTEIN + " =?, " + FAT + " =?, "
            + CARBS + " =?, " + FIBER + " =? WHERE " + PRODUCT_ID + " = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM " + TABLE + " WHERE " + PRODUCT_ID + " = ?";

    private static final String READ_PRODUCTS_FOR_PROFIL = "SELECT * FROM " + TABLE + " WHERE " + PRODUCT_ID + " IN " +
            "(SELECT " + TABLE_PROFIL_PRODUCT_PRODUCT_ID + " FROM " + TABLE_PROFIL_PRODUCT + " " +
            "WHERE " + TABLE_PROFIL_PRODUCT_PROFIL_ID + " = ?)";
    private static final String INSERT_PRODUCT_PROFIL = "INSERT INTO " + TABLE_PROFIL_PRODUCT + " VALUES (?,?)";
    private static final String DELETE_PRODUCT_PROFIL = "DELETE FROM " + TABLE_PROFIL_PRODUCT + " WHERE " + TABLE_PROFIL_PRODUCT_PRODUCT_ID + " = ?";

    private static final String READ_PRODUCTS_FOR_MEAL = "SELECT " + TABLE + "." + PRODUCT_ID + ", "
            + TABLE + "." + NAME + ", " + TABLE + "." + KCAL + ", " + TABLE + "." + PROTEIN + ", "
            + TABLE + "." + FAT + ", " + TABLE + "." + CARBS + ", " + TABLE + "." + FIBER + ", "
            + TABLE_MEAL_PRODUCT + "." + TABLE_MEAL_PRODUCT_AMOUNT
            + " FROM " + TABLE + ", (SELECT " + TABLE_MEAL_PRODUCT_ID_PRODUCT + ", " + TABLE_MEAL_PRODUCT_AMOUNT
            + " FROM " + TABLE_MEAL_PRODUCT + " WHERE " + TABLE_MEAL_PRODUCT_ID_MEAL + " =?)" +
            " AS '" + TABLE_MEAL_PRODUCT + "' WHERE " + TABLE + "." + PRODUCT_ID
            + " = " + TABLE_MEAL_PRODUCT + "." + TABLE_MEAL_PRODUCT_ID_PRODUCT;
    private static final String CHECK_EXIST_OF_PRODUCT_IN_MEAL_PRODUCT = "SELECT (1) FROM " + TABLE_MEAL_PRODUCT + " WHERE " + TABLE_MEAL_PRODUCT_ID_PRODUCT + " = ?";
    private static final String INSERT_PRODUCTS_FOR_MEAL = "INSERT INTO " + TABLE_MEAL_PRODUCT + " VALUES (?,?,?)";
    private static final String DELETE_PRODUCTS_FOR_MEAL = "DELETE FROM " + TABLE_MEAL_PRODUCT + " WHERE " + TABLE_MEAL_PRODUCT_ID_MEAL + " =? AND " + TABLE_MEAL_PRODUCT_ID_PRODUCT + " =? ";

    private ProductData() {
    }

    public static ProductData getInstance() {
        return singletonProductData;
    }

    public void readAllProductForProfil() {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(READ_PRODUCTS_FOR_PROFIL);) {
            preparedStatement.setInt(1, Profil.getSelectedProfil().getIdPerson());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setIdProduct(resultSet.getInt(PRODUCT_ID));
                product.setName(resultSet.getString(NAME));
                product.setKcal(resultSet.getDouble(KCAL));
                product.setProtein(resultSet.getDouble(PROTEIN));
                product.setFat(resultSet.getDouble(FAT));
                product.setCarbs(resultSet.getDouble(CARBS));
                product.setFiber(resultSet.getDouble(FIBER));

                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
        }
        productsList = FXCollections.observableList(products);
    }

    public void insertProduct(String name, double kcal, double protein, double fat, double carbs, double fiber) {
        insetNewProduct(name, kcal, protein, fat, carbs, fiber);
        insertProductToProfil();
    }

    private void insetNewProduct(String name, double kcal, double protein, double fat, double carbs, double fiber) {
        try (PreparedStatement statement = conn.prepareStatement(INSERT_PRODUCT)) {
            conn.setAutoCommit(false);

            int productId = getMaxProductId() + 1;
            statement.setInt(1, productId);
            statement.setString(2, name);
            statement.setDouble(3, kcal);
            statement.setDouble(4, protein);
            statement.setDouble(5, fat);
            statement.setDouble(6, carbs);
            statement.setDouble(7, fiber);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
                Product insertedProduct = new Product(productId, name, kcal, protein, fat, carbs, fiber);
                productsList.add(insertedProduct);
                Product.setSelectedProduct(insertedProduct);
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

    private int getMaxProductId() {
        int maxProductId = 0;
        try (PreparedStatement statement = conn.prepareStatement(READ_MAX_ID_PRODUCT);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                maxProductId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
        }
        return maxProductId;
    }

    private void insertProductToProfil() {
        try (PreparedStatement statement = conn.prepareStatement(INSERT_PRODUCT_PROFIL)) {
            conn.setAutoCommit(false);

            statement.setInt(1, Profil.getSelectedProfil().getIdPerson());
            statement.setInt(2, Product.getSelectedProduct().getIdProduct());
            int affectedRows = statement.executeUpdate();
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

    public void updateProduct(String name, double kcal, double protein, double fat, double carbs, double fiber) {
        try (PreparedStatement statement = conn.prepareStatement(UPDATE_PRODUCT)) {
            statement.setString(1, name);
            statement.setDouble(2, kcal);
            statement.setDouble(3, protein);
            statement.setDouble(4, fat);
            statement.setDouble(5, carbs);
            statement.setDouble(6, fiber);
            statement.setInt(7, Product.getSelectedProduct().getIdProduct());

            statement.executeUpdate();

            productsList.remove(Product.getSelectedProduct());
            Product.getSelectedProduct().setName(name);
            Product.getSelectedProduct().setKcal(kcal);
            Product.getSelectedProduct().setProtein(protein);
            Product.getSelectedProduct().setFat(fat);
            Product.getSelectedProduct().setCarbs(carbs);
            Product.getSelectedProduct().setFiber(fiber);
            productsList.add(Product.getSelectedProduct());

        } catch (SQLException e) {
            System.out.println("Update failed " + e.getMessage());
        }
    }

    private void deleteProduct(Product product) {
        try (PreparedStatement statement = conn.prepareStatement(DELETE_PRODUCT)) {
            statement.setInt(1, product.getIdProduct());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete failed " + e.getMessage());
        }
    }

    public void deleteProductProfil(Product product, boolean checkDietMealData) {
        try (PreparedStatement statement = conn.prepareStatement(DELETE_PRODUCT_PROFIL)) {
            statement.setInt(1, product.getIdProduct());
            statement.executeUpdate();
            if (checkDietMealData) {
                if (checkExistOfProductInMealProduct() == 0 && DietData.getInstance().checkExistOfProductInDietProduct() == 0) {
                    deleteProduct(product);
                }
            } else {
                deleteProduct(product);
            }
        } catch (SQLException e) {
            System.out.println("Delete failed " + e.getMessage());
        }
    }

    public Map<Product, Integer> readAllProductForMeal(int mealId) {
        Map<Product, Integer> productForMealMap = new HashMap<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(READ_PRODUCTS_FOR_MEAL)) {
            preparedStatement.setInt(1, mealId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setIdProduct(resultSet.getInt(PRODUCT_ID));
                product.setName(resultSet.getString(NAME));
                product.setKcal(resultSet.getDouble(KCAL));
                product.setProtein(resultSet.getDouble(PROTEIN));
                product.setFat(resultSet.getDouble(FAT));
                product.setCarbs(resultSet.getDouble(CARBS));
                product.setFiber(resultSet.getDouble(FIBER));

                productForMealMap.put(product, resultSet.getInt(TABLE_MEAL_PRODUCT_AMOUNT));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
        }
        return productForMealMap;
    }

    private int checkExistOfProductInMealProduct() {
        int countExist = 0;
        try (PreparedStatement statement = conn.prepareStatement(CHECK_EXIST_OF_PRODUCT_IN_MEAL_PRODUCT)) {
            statement.setInt(1, Product.getSelectedProduct().getIdProduct());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                countExist++;
            }
        } catch (SQLException e) {
            System.out.println("Check exist failed " + e.getMessage());
        } finally {

        }
        return countExist;
    }

    public void insertAllProductForMeal(int mealId, int productId, int amount) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_PRODUCTS_FOR_MEAL)) {
            conn.setAutoCommit(false);

            preparedStatement.setInt(1, mealId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, amount);
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

    public void deleteProductForMeal(int mealId, int productId) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_PRODUCTS_FOR_MEAL)) {
            conn.setAutoCommit(false);

            preparedStatement.setInt(1, mealId);
            preparedStatement.setInt(2, productId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                conn.commit();
            } else {
                conn.rollback();
                throw new SQLException("Delete product error");
            }
        } catch (SQLException e) {
            System.out.println("Delete failed " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ObservableList<Product> getProductsList() {
        return productsList;
    }
}
