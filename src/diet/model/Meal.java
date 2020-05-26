package diet.model;

import diet.model.additionalClasses.ClassOfStaticMethod;

import java.util.Map;

public class Meal {

    private static Meal selectedMeal = null;

    private int idMeal;
    private String name;
    private Map<Product, Integer> productsForMeal;
    private double kcal;
    private double protein;
    private double fat;
    private double carbs;
    private double fiber;

    public Meal() {

    }

    public Meal(int idMeal, String name, Map<Product, Integer> productsForMeal) {
        this.idMeal = idMeal;
        this.name = name;
        this.productsForMeal = productsForMeal;
        countKcalForMeal();
        countProteinForMeal();
        countFatForMeal();
        countCarbsForMeal();
        countFiberForMeal();
    }

    public static Meal getSelectedMeal() {
        return selectedMeal;
    }

    public static void setSelectedMeal(Meal selectedMeal) {
        Meal.selectedMeal = selectedMeal;
    }

    public int getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(int idMeal) {
        this.idMeal = idMeal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Product, Integer> getProductsForMeal() {
        return productsForMeal;
    }

    public void setProductsForMeal(Map<Product, Integer> productsForMeal) {
        this.productsForMeal = productsForMeal;
    }

    public double getKcal() {
        return kcal;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFiber() {
        return fiber;
    }

    public void countKcalForMeal() {
        double kcalForMeal = 0;
        double amountOfProducts = 0;
        for (Map.Entry<Product, Integer> productAmount : productsForMeal.entrySet()) {
            kcalForMeal += productAmount.getValue() * productAmount.getKey().getKcal() / 100;
            amountOfProducts += productAmount.getValue();
        }
        kcalForMeal = kcalForMeal / amountOfProducts * 100;
        this.kcal = Double.parseDouble(ClassOfStaticMethod.roundDouble(kcalForMeal));
    }

    public void countProteinForMeal() {
        double proteinForMeal = 0;
        double amountOfProducts = 0;
        for (Map.Entry<Product, Integer> productAmount : productsForMeal.entrySet()) {
            proteinForMeal += productAmount.getValue() * productAmount.getKey().getProtein() / 100;
            amountOfProducts += productAmount.getValue();
        }
        proteinForMeal = proteinForMeal / amountOfProducts * 100;
        this.protein = Double.parseDouble(ClassOfStaticMethod.roundDouble(proteinForMeal));
    }

    public void countFatForMeal() {
        double fatForMeal = 0;
        double amountOfProducts = 0;
        for (Map.Entry<Product, Integer> productAmount : productsForMeal.entrySet()) {
            fatForMeal += productAmount.getValue() * productAmount.getKey().getFat() / 100;
            amountOfProducts += productAmount.getValue();
        }
        fatForMeal = fatForMeal / amountOfProducts * 100;
        this.fat = Double.parseDouble(ClassOfStaticMethod.roundDouble(fatForMeal));
    }

    public void countCarbsForMeal() {
        double carbsForMeal = 0;
        double amountOfProducts = 0;
        for (Map.Entry<Product, Integer> productAmount : productsForMeal.entrySet()) {
            carbsForMeal += productAmount.getValue() * productAmount.getKey().getCarbs() / 100;
            amountOfProducts += productAmount.getValue();
        }
        carbsForMeal = carbsForMeal / amountOfProducts * 100;
        this.carbs = Double.parseDouble(ClassOfStaticMethod.roundDouble(carbsForMeal));
    }

    public void countFiberForMeal() {
        double fiberForMeal = 0;
        double amountOfProducts = 0;
        for (Map.Entry<Product, Integer> productAmount : productsForMeal.entrySet()) {
            fiberForMeal += productAmount.getValue() * productAmount.getKey().getFiber() / 100;
            amountOfProducts += productAmount.getValue();
        }
        fiberForMeal = fiberForMeal / amountOfProducts * 100;
        this.fiber = Double.parseDouble(ClassOfStaticMethod.roundDouble(fiberForMeal));
    }

    @Override
    public String toString() {
        return idMeal + " name= " + name + " kcal= " + kcal + " protein= " + protein + " fat= " + fat + " carbs= " + carbs + " fiber= " + fiber;
    }
}