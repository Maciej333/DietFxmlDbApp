package diet.model.database;

import diet.model.Product;
import diet.model.Profil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductData {

    private Connection conn = Datasource.getInstance().getConnect();
    private static ProductData singletonProductData = new ProductData();
    private static ObservableList<Product> productsList;

    private static final String TABLE_PROFIL_PRODUCT = "PROFIL_PRODUCT";
    private static final String TABLE_PROFIL_PRODUCT_PROFIL_ID= "ID_PROFIL";
    private static final String TABLE_PROFIL_PRODUCT_PRODUCT_ID= "ID_PRODUCT";

    private static final String TABLE_MEAL_PRODUCT = "MEAL_PRODUCT";
    private static final String TABLE_MEAL_PRODUCT_ID_MEAL = "ID_MEAL";
    private static final String TABLE_MEAL_PRODUCT_ID_PRODUCT = "ID_PRODUCT";
    private static final String TABLE_MEAL_PRODUCT_AMOUNT = "AMOUNT";

    private static final String TABLE = "PRODUCT";
    private static final String PRODUCT_ID = "ID_PRODUCT";
    private static final String NAME ="NAME";
    private static final String KCAL ="KCAL";
    private static final String PROTEIN = "PROTEIN";
    private static final String FAT = "FAT";
    private static final String CARBS = "CARBS";
    private static final String FIBER = "FIBER";

    private static final String READ_PRODUCTS_FOR_PROFIL = "SELECT * FROM "+TABLE+" WHERE "+PRODUCT_ID+" IN " +
            "(SELECT "+TABLE_PROFIL_PRODUCT_PRODUCT_ID+" FROM "+TABLE_PROFIL_PRODUCT+" " +
            "WHERE "+TABLE_PROFIL_PRODUCT_PROFIL_ID+" = "+Profil.getSelectedProfil().getIdPerson()+")";

    private static final String READ_PRODUCTS_FOR_MEAL ="SELECT "+TABLE+"."+PRODUCT_ID+", "
            +TABLE+"."+NAME+", "+TABLE+"."+KCAL+", "+TABLE+"."+PROTEIN+", "
            +TABLE+"."+FAT+", "+TABLE+"."+CARBS+", "+TABLE+"."+FIBER+", "
            +TABLE_MEAL_PRODUCT+"."+TABLE_MEAL_PRODUCT_AMOUNT
            +" FROM "+TABLE+", (SELECT "+TABLE_MEAL_PRODUCT_ID_PRODUCT+", "+TABLE_MEAL_PRODUCT_AMOUNT
                                 +" FROM "+TABLE_MEAL_PRODUCT+" WHERE "+TABLE_MEAL_PRODUCT_ID_MEAL+" =?)" +
            " AS '"+TABLE_MEAL_PRODUCT+"' WHERE "+TABLE+"."+PRODUCT_ID
            +" = "+TABLE_MEAL_PRODUCT+"."+TABLE_MEAL_PRODUCT_ID_PRODUCT;

    private ProductData(){
    }

    public static ProductData getInstance(){
        return singletonProductData;
    }

    public void readAllProductForProfil(){
        List<Product> products = new ArrayList<>();
        try(PreparedStatement preparedStatement = conn.prepareStatement(READ_PRODUCTS_FOR_PROFIL);
            ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()){
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
        }catch(SQLException e){
            System.out.println("Query failed "+e.getMessage());
        }
        productsList = FXCollections.observableList(products);
    }

    public Map<Product, Integer> readAllProductForMeal(int mealId){
        Map<Product, Integer> productForMealMap = new HashMap<>();
        try(PreparedStatement preparedStatement = conn.prepareStatement(READ_PRODUCTS_FOR_MEAL)){
            preparedStatement.setInt(1, mealId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
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
        }catch (SQLException e){
            System.out.println("Query failed "+e.getMessage());
        }
        return productForMealMap;
    }

    public static ObservableList<Product> getProductsList(){
        return productsList;
    }
}
