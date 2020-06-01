package diet.model.Test;

import diet.model.*;
import diet.model.profilEnums.ProfilGoal;
import diet.model.profilEnums.ProfilSex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DietTest extends Food {

    private Product p1 = new Product(1,"p1",150,20,5,2,5);
    private Product p2 = new Product(2,"p2",90,5,50,5,10);
    private Product p10 = new Product(10,"pp1",200,8,45,20,2);
    private Product p11 = new Product(11,"pp2",180,15,30,10,8);
    private Meal dietMeal;

    private Diet testDiet = new Diet();
    private Map<Food, Integer> dietMap = new HashMap<>();

    @BeforeEach
    void initializeDiet(){
        Map<Product, Integer> mealProduct = new HashMap<>();
        mealProduct.put(p10, 60);
        mealProduct.put(p11, 110);
        dietMeal = new Meal(100,"m1", mealProduct);
        dietMap.put(p1, 120);
        dietMap.put(p2, 200);
        dietMap.put(dietMeal, 250);

        testDiet.setIdDiet(1);
        testDiet.setIdOsoba(1);
        testDiet.setDate(LocalDateTime.of(2020,06,01,15,01,05));
        testDiet.addMapToDietMealsProductsMap(dietMap);

        Diet.countMacrosForDiet(testDiet);
        Diet.setSelectedDiet(testDiet);
    }

    @Test
    void getName1() {
        assertEquals("",testDiet.getName());
    }

    @Test
    void setName1() {
        testDiet.setName("new diet name");
        assertEquals("",testDiet.getName());
    }

    @Test
    void getKcal1() {
        assertEquals(827.65,testDiet.getKcal());
    }

    @Test
    void setKcal1() {
        testDiet.setKcal(1001);
        assertEquals(1001,testDiet.getKcal());
    }

    @Test
    void getProtein1() {
        assertEquals(65.33,testDiet.getProtein());
    }

    @Test
    void setProtein1() {
        testDiet.setProtein(1002);
        assertEquals(1002,testDiet.getProtein());
    }

    @Test
    void getFat1() {
        assertEquals(46.23,testDiet.getFat());
    }

    @Test
    void setFat1() {
        testDiet.setFat(1003);
        assertEquals(1003,testDiet.getFat());
    }

    @Test
    void getCarbs1() {
        assertEquals(194.23,testDiet.getCarbs());
    }

    @Test
    void setCarbs1() {
        testDiet.setCarbs(1004);
        assertEquals(1004,testDiet.getCarbs());
    }

    @Test
    void getFiber1() {
        assertEquals(40.70,testDiet.getFiber());
    }

    @Test
    void setFiber1() {
        testDiet.setFiber(1005);
        assertEquals(1005,testDiet.getFiber());
    }

    @Test
    void getSelectedDiet() {
        assertEquals(testDiet, Diet.getSelectedDiet());
    }

    @Test
    void setSelectedDiet() {
        Diet newDiet = new Diet();
        Diet.setSelectedDiet(newDiet);
        assertEquals(newDiet, Diet.getSelectedDiet());
    }

    @Test
    void getIdDiet() {
        assertEquals(1, testDiet.getIdDiet());
    }

    @Test
    void setIdDiet() {
        testDiet.setIdDiet(2);
        assertEquals(2, testDiet.getIdDiet());
    }

    @Test
    void setIdOsoba() {
        testDiet.setIdOsoba(1);
    }

    @Test
    void getDate() {
        assertEquals(LocalDateTime.of(2020,06,01,15,01,05), testDiet.getDate());
    }

    @Test
    void setDate() {
        testDiet.setDate(LocalDateTime.of(2010,12,12,18,01,05));
        assertEquals(LocalDateTime.of(2010,12,12,18,01,05), testDiet.getDate());
    }

    @Test
    void getFormatDate() {
        assertEquals("2020-06-01 15:01:05",testDiet.getFormatDate());
    }

    @Test
    void getDietMealsProductsMap() {
        assertEquals(dietMap, testDiet.getDietMealsProductsMap());
    }

    @Test
    void addMapToDietMealsProductsMap() {
        Map<Food, Integer> foodMap1 = new HashMap<>();
        foodMap1.put(p1,120);
        foodMap1.put(dietMeal,250);
        Diet newDiet = new Diet();
        newDiet.addMapToDietMealsProductsMap(foodMap1);
        assertEquals(foodMap1,newDiet.getDietMealsProductsMap());
    }

    @Test
    void countKcalForDiet() {
        Map<Food, Integer> foodMap1 = new HashMap<>();
        foodMap1.put(new Product(1,"p1",150,20,5,2,5),20);
        foodMap1.put(new Product(2,"p2",90,5,50,5,10),200);
        foodMap1.put(dietMeal,250);
        Diet newDiet = new Diet();
        newDiet.addMapToDietMealsProductsMap(foodMap1);
        Diet.countMacrosForDiet(newDiet);

        assertEquals(677.65,newDiet.getKcal());
    }

    @Test
    void countProteinForDiet() {
        Map<Food, Integer> foodMap1 = new HashMap<>();
        foodMap1.put(new Product(1,"p1",150,20,5,2,5),120);
        foodMap1.put(new Product(2,"p2",50,5,50,5,10),200);
        foodMap1.put(dietMeal,250);
        Diet newDiet = new Diet();
        newDiet.addMapToDietMealsProductsMap(foodMap1);
        Diet.countMacrosForDiet(newDiet);

        assertEquals(65.33,newDiet.getProtein());
    }

    @Test
    void countFatForDiet() {
        Map<Food, Integer> foodMap1 = new HashMap<>();
        foodMap1.put(new Product(1,"p1",150,20,5,2,5),120);
        foodMap1.put(dietMeal,250);
        Diet newDiet = new Diet();
        newDiet.addMapToDietMealsProductsMap(foodMap1);
        Diet.countMacrosForDiet(newDiet);

        assertEquals(36.23,newDiet.getFat());
    }

    @Test
    void countCarbsForDiet() {
        Map<Food, Integer> foodMap1 = new HashMap<>();
        foodMap1.put(new Product(1,"p1",150,20,5,2,5),50);
        foodMap1.put(new Product(2,"p2",90,5,50,5,10),50);
        foodMap1.put(dietMeal,250);
        Diet newDiet = new Diet();
        newDiet.addMapToDietMealsProductsMap(foodMap1);
        Diet.countMacrosForDiet(newDiet);

        assertEquals(115.73,newDiet.getCarbs());
    }

    @Test
    void countFiberForDiet() {
        Map<Food, Integer> foodMap1 = new HashMap<>();
        foodMap1.put(new Product(1,"p1",150,20,5,2,5),120);
        foodMap1.put(new Product(2,"p2",1,5,50,5,10),20);
        foodMap1.put(dietMeal,250);
        Diet newDiet = new Diet();
        newDiet.addMapToDietMealsProductsMap(foodMap1);
        Diet.countMacrosForDiet(newDiet);

        assertEquals(22.70,newDiet.getFiber());
    }

    @Test
    void getDietsByDate() {
        Diet diet1 = new Diet();
        diet1.setDate(LocalDateTime.of(2020,06,01,15,15,15));
        Diet diet2 = new Diet();
        diet2.setDate(LocalDateTime.of(2020,06,02,15,15,15));
        Diet diet3 = new Diet();
        diet3.setDate(LocalDateTime.of(2020,06,03,15,15,15));
        ObservableList<Diet> diets = FXCollections.observableList(Arrays.asList(diet1,diet2,diet3));

        ObservableList<Diet> dietsSelected = FXCollections.observableList(Arrays.asList(diet1));

        assertEquals(dietsSelected,Diet.getDietsByDate(diets, LocalDate.of(2020,06,01)));
    }

    @Test
    void countStatsForDiets1() {
        Diet diet1 = new Diet();
        Map<Food, Integer> diet1Map = new HashMap<>();
        diet1Map.put(p1,120);
        diet1Map.put(p2,200);
        diet1Map.put(dietMeal,250);
        diet1.addMapToDietMealsProductsMap(diet1Map);
        Diet.countMacrosForDiet(diet1);
        Diet diet2 = new Diet();
        Map<Food, Integer> diet2Map = new HashMap<>();
        diet2Map.put(p1,170);
        diet2Map.put(p10,35);
        diet2.addMapToDietMealsProductsMap(diet2Map);
        Diet.countMacrosForDiet(diet2);
        Profil testProfil = new Profil(1,"test",20,180,80, ProfilSex.male.toString(), ProfilGoal.keep.toString());
        testProfil.countKcal();
        Profil.setSelectedProfil(testProfil);
        ObservableList<Diet> listDiet = FXCollections.observableList(Arrays.asList(diet1, diet2));

        Double[] stats = Diet.countStatsForDiets(listDiet);
        assertEquals(1152.65,stats[0]);
        assertEquals(102.13,stats[1]);
        assertEquals(56.63,stats[2]);
        assertEquals(218.48,stats[3]);
        assertEquals(49.90,stats[4]);
    }
}