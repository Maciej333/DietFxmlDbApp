package diet.model;

import diet.model.additionalClasses.ClassOfStaticMethod;

import java.util.Map;

public class Meal extends Food {

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
        setKcal(Double.parseDouble(ClassOfStaticMethod.roundDouble(countSelectedMacro("kcal"))));
    }

    public void countProteinForMeal() {
        setProtein(Double.parseDouble(ClassOfStaticMethod.roundDouble(countSelectedMacro("protein"))));
    }

    public void countFatForMeal() {
        setFat(Double.parseDouble(ClassOfStaticMethod.roundDouble(countSelectedMacro("fat"))));
    }

    public void countCarbsForMeal() {
        setCarbs(Double.parseDouble(ClassOfStaticMethod.roundDouble(countSelectedMacro("carbs"))));
    }

    public void countFiberForMeal() {
        setFiber(Double.parseDouble(ClassOfStaticMethod.roundDouble(countSelectedMacro("fiber"))));
    }

    private double countSelectedMacro(String macro) {
        double makroForMeal = 0;
        double amountOfProducts = 0;
        for (Map.Entry<Product, Integer> productAmount : productsForMeal.entrySet()) {
            if (macro.equals("kcal")) {
                makroForMeal += productAmount.getValue() * productAmount.getKey().getKcal() / 100;
            }
            if (macro.equals("protein")) {
                makroForMeal += productAmount.getValue() * productAmount.getKey().getProtein() / 100;
            }
            if (macro.equals("fat")) {
                makroForMeal += productAmount.getValue() * productAmount.getKey().getFat() / 100;
            }
            if (macro.equals("carbs")) {
                makroForMeal += productAmount.getValue() * productAmount.getKey().getCarbs() / 100;
            }
            if (macro.equals("fiber")) {
                makroForMeal += productAmount.getValue() * productAmount.getKey().getFiber() / 100;
            }
            amountOfProducts += productAmount.getValue();
        }
        makroForMeal = makroForMeal / amountOfProducts * 100;
        return makroForMeal;
    }

    @Override
    public String toString() {
        return idMeal + " name= " + getName();
    }
}