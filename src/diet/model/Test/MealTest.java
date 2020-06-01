package diet.model.Test;

import diet.model.Food;
import diet.model.Meal;
import diet.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MealTest extends Food {

    private Meal testMeal;

    private Map<Product, Integer> map = new HashMap<>();

    @BeforeEach
    void initializeTest(){
        map.put(new Product(1,"p1",200,20,10,5,5),80);
        map.put(new Product(2,"p2",600,15,50,70,2),50);
        map.put(new Product(3,"p3",320,5,120,2,10),150);
        testMeal = new Meal(1,"test",map);
        Meal.setSelectedMeal(testMeal);
    }

    @Test
    void getName1() {
        assertEquals("test", testMeal.getName());
    }

    @Test
    void setName1() {
        testMeal.setName("newTest");
        assertEquals("newTest", testMeal.getName());
    }

    @Test
    void getKcal1() {
        assertEquals(335.71,testMeal.getKcal());
    }

    @Test
    void setKcal1() {
        testMeal.setKcal(500);
        assertEquals(500,testMeal.getKcal());
    }

    @Test
    void getProtein1() {
        assertEquals(11.07,testMeal.getProtein());
    }

    @Test
    void setProtein1() {
        testMeal.setProtein(501);
        assertEquals(501,testMeal.getProtein());
    }

    @Test
    void getFat1() {
        assertEquals(15.00,testMeal.getFat());
    }

    @Test
    void setFat1() {
        testMeal.setFat(501);
        assertEquals(501,testMeal.getFat());
    }

    @Test
    void getCarbs1() {
        assertEquals(76.07,testMeal.getCarbs());
    }

    @Test
    void setCarbs1() {
        testMeal.setCarbs(502);
        assertEquals(502,testMeal.getCarbs());
    }

    @Test
    void getFiber1() {
        assertEquals(7.14,testMeal.getFiber());
    }

    @Test
    void setFiber1() {
        testMeal.setFiber(503);
        assertEquals(503,testMeal.getFiber());
    }

    @Test
    void getSelectedMeal() {
        assertEquals(testMeal, Meal.getSelectedMeal());
    }

    @Test
    void setSelectedMeal() {
        Meal newMeal = new Meal();
        Meal.setSelectedMeal(newMeal);
        assertEquals(newMeal, Meal.getSelectedMeal());
    }

    @Test
    void getIdMeal() {
        assertEquals(1, testMeal.getIdMeal());
    }

    @Test
    void setIdMeal() {
        testMeal.setIdMeal(2);
        assertEquals(2, testMeal.getIdMeal());
    }

    @Test
    void getProductsForMeal() {
        assertEquals(map, testMeal.getProductsForMeal());
    }

    @Test
    void setProductsForMeal() {
        Map<Product, Integer> newMap = new HashMap<>();
        newMap.put(new Product(1,"newP1",200,20,10,5,5),120);
        newMap.put(new Product(2,"newP2",600,15,50,70,2),5);
        testMeal.setProductsForMeal(newMap);
        assertEquals(newMap,testMeal.getProductsForMeal());
    }

    @Test
    void countKcalForMeal() {
        Map<Product, Integer> newMap = new HashMap<>();
        newMap.put(new Product(1,"p1",200,20,10,5,5),150);
        newMap.put(new Product(2,"p2",600,15,50,70,2),50);
        newMap.put(new Product(3,"p3",320,5,120,2,10),150);
        testMeal.setProductsForMeal(newMap);
        testMeal.countKcalForMeal();
        assertEquals(308.57,testMeal.getKcal());
    }

    @Test
    void countProteinForMeal() {
        Map<Product, Integer> newMap = new HashMap<>();
        newMap.put(new Product(1,"p1",200,20,10,5,5),80);
        newMap.put(new Product(3,"p3",320,5,120,2,10),150);
        testMeal.setProductsForMeal(newMap);
        testMeal.countProteinForMeal();
        assertEquals(10.22,testMeal.getProtein());
    }

    @Test
    void countFatForMeal() {
        Map<Product, Integer> newMap = new HashMap<>();
        newMap.put(new Product(1,"p1",200,20,10,5,5),80);
        newMap.put(new Product(2,"p2",600,15,50,70,2),50);
        newMap.put(new Product(3,"p3",320,5,120,2,10),10);
        testMeal.setProductsForMeal(newMap);
        testMeal.countFatForMeal();
        assertEquals(28.00,testMeal.getFat());
    }

    @Test
    void countCarbsForMeal() {
        Map<Product, Integer> newMap = new HashMap<>();
        newMap.put(new Product(1,"p1",200,20,10,5,5),80);
        newMap.put(new Product(2,"p2",40,15,50,70,2),50);
        newMap.put(new Product(3,"p3",320,5,120,2,10),150);
        testMeal.setProductsForMeal(newMap);
        testMeal.countCarbsForMeal();
        assertEquals(76.07,testMeal.getCarbs());
    }

    @Test
    void countFiberForMeal() {
        Map<Product, Integer> newMap = new HashMap<>();
        newMap.put(new Product(1,"p1",200,20,10,5,5),80);
        newMap.put(new Product(3,"p3",320,5,120,2,10),150);
        testMeal.setProductsForMeal(newMap);
        testMeal.countFiberForMeal();
        assertEquals(8.26,testMeal.getFiber());
    }
}