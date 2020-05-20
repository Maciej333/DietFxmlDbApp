package diet.model;

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

    public Meal(){

    }

    public static Meal getSelectedMeal(){
        return selectedMeal;
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

    public void countKcalForMeal(){
        double kcalForMeal = 0;
        for(Map.Entry<Product, Integer> productAmount: productsForMeal.entrySet()){
            kcalForMeal += productAmount.getValue()*productAmount.getKey().getKcal()/100;
        }
        this.kcal = kcalForMeal;
    }

    public void countProteinForMeal(){
        double proteinForMeal = 0;
        for(Map.Entry<Product, Integer> productAmount: productsForMeal.entrySet()){
            proteinForMeal += productAmount.getValue()*productAmount.getKey().getProtein()/100;
        }
        this.protein = proteinForMeal;
    }

    public void countFatForMeal(){
        double fatForMeal = 0;
        for(Map.Entry<Product, Integer> productAmount: productsForMeal.entrySet()){
            fatForMeal += productAmount.getValue()*productAmount.getKey().getFat()/100;
        }
        this.fat = fatForMeal;
    }

    public void countCarbsForMeal(){
        double carbsForMeal = 0;
        for(Map.Entry<Product, Integer> productAmount: productsForMeal.entrySet()){
            carbsForMeal += productAmount.getValue()*productAmount.getKey().getCarbs()/100;
        }
        this.carbs = carbsForMeal;
    }

    public void countFiberForMeal(){
        double fiberForMeal = 0;
        for(Map.Entry<Product, Integer> productAmount: productsForMeal.entrySet()){
            fiberForMeal += productAmount.getValue()*productAmount.getKey().getFiber()/100;
        }
        this.fiber = fiberForMeal;
    }

    @Override
    public String toString() {
        return idMeal+" name= "+name+" kcal= "+kcal+" protein= "+protein+" fat= "+fat+" carbs= "+carbs+" fiber= "+fiber;
    }

    public void showProduct(){
        for(Map.Entry<Product, Integer> productAmount: productsForMeal.entrySet()){
            System.out.println(productAmount.getKey()+ " ilosc  = "+productAmount.getValue());
        }
    }
}