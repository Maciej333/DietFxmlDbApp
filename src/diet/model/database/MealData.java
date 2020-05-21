package diet.model.database;

import diet.model.Meal;
import diet.model.Profil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealData {
    private Connection conn = Datasource.getInstance().getConnect();
    private static MealData singletonMealData = new MealData();
    private static ObservableList<Meal> mealsList;

    private static final String TABLE_PROFIL_MEAL = "PROFIL_MEAL";
    private static final String TABLE_PROFIL_MEAL_PROFIL_ID= "ID_PROFIL";
    private static final String TABLE_PROFIL_MEAL_MEAL_ID= "ID_MEAL";

    private static final String TABLE = "MEAL";
    private static final String MEAL_ID = "ID_MEAL";
    private static final String NAME ="NAME";

    public static final String READ_MEAL_FOR_PROFIL = "SELECT "+MEAL_ID+", "+NAME+" FROM "+TABLE+" WHERE "
                +MEAL_ID+" IN (SELECT "+TABLE_PROFIL_MEAL_MEAL_ID +" FROM "+TABLE_PROFIL_MEAL+
                            " WHERE "+TABLE_PROFIL_MEAL_PROFIL_ID+" = "+Profil.getSelectedProfil().getIdPerson()+")";

    private MealData(){
    }

    public static MealData getInstance(){
        return singletonMealData;
    }

    public void readAllMealForProfil(){
        List<Meal> meals = new ArrayList<>();
        try(PreparedStatement preparedStatement = conn.prepareStatement(READ_MEAL_FOR_PROFIL);
            ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()){
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
        }catch(SQLException e){
            System.out.println("Query failed "+e.getMessage());
        }
        mealsList = FXCollections.observableList(meals);
    }

    public static ObservableList<Meal> getMealsList(){
        return mealsList;
    }
}
