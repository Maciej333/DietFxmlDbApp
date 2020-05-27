package diet.model;

import diet.model.additionalClasses.ClassOfStaticMethod;

import java.util.Map;

public class Meal extends Food{

    private static Meal selectedMeal = null;

    private int idMeal;
    private Map<Product, Integer> productsForMeal;

    public Meal() {

    }

    public Meal(int idMeal, String name, Map<Product, Integer> productsForMeal) {
        this.idMeal = idMeal;
        setName(name);
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

    public Map<Product, Integer> getProductsForMeal() {
        return productsForMeal;
    }

    public void setProductsForMeal(Map<Product, Integer> productsForMeal) {
        this.productsForMeal = productsForMeal;
    }

    public void countKcalForMeal() {
        double kcalForMeal = 0;
        double amountOfProducts = 0;
        for (Map.Entry<Product, Integer> productAmount : productsForMeal.entrySet()) {
            kcalForMeal += productAmount.getValue() * productAmount.getKey().getKcal() / 100;
            amountOfProducts += productAmount.getValue();
        }
        kcalForMeal = kcalForMeal / amountOfProducts * 100;
        setKcal(Double.parseDouble(ClassOfStaticMethod.roundDouble(kcalForMeal)));
    }

    public void countProteinForMeal() {
        double proteinForMeal = 0;
        double amountOfProducts = 0;
        for (Map.Entry<Product, Integer> productAmount : productsForMeal.entrySet()) {
            proteinForMeal += productAmount.getValue() * productAmount.getKey().getProtein() / 100;
            amountOfProducts += productAmount.getValue();
        }
        proteinForMeal = proteinForMeal / amountOfProducts * 100;
        setProtein(Double.parseDouble(ClassOfStaticMethod.roundDouble(proteinForMeal)));
    }

    public void countFatForMeal() {
        double fatForMeal = 0;
        double amountOfProducts = 0;
        for (Map.Entry<Product, Integer> productAmount : productsForMeal.entrySet()) {
            fatForMeal += productAmount.getValue() * productAmount.getKey().getFat() / 100;
            amountOfProducts += productAmount.getValue();
        }
        fatForMeal = fatForMeal / amountOfProducts * 100;
        setFat(Double.parseDouble(ClassOfStaticMethod.roundDouble(fatForMeal)));
    }

    public void countCarbsForMeal() {
        double carbsForMeal = 0;
        double amountOfProducts = 0;
        for (Map.Entry<Product, Integer> productAmount : productsForMeal.entrySet()) {
            carbsForMeal += productAmount.getValue() * productAmount.getKey().getCarbs() / 100;
            amountOfProducts += productAmount.getValue();
        }
        carbsForMeal = carbsForMeal / amountOfProducts * 100;
        setCarbs(Double.parseDouble(ClassOfStaticMethod.roundDouble(carbsForMeal)));
    }

    public void countFiberForMeal() {
        double fiberForMeal = 0;
        double amountOfProducts = 0;
        for (Map.Entry<Product, Integer> productAmount : productsForMeal.entrySet()) {
            fiberForMeal += productAmount.getValue() * productAmount.getKey().getFiber() / 100;
            amountOfProducts += productAmount.getValue();
        }
        fiberForMeal = fiberForMeal / amountOfProducts * 100;
        setFiber(Double.parseDouble(ClassOfStaticMethod.roundDouble(fiberForMeal)));
    }

    @Override
    public String toString() {
        return idMeal + " name= "+getName();
    }
}