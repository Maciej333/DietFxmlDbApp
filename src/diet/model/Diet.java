package diet.model;

import diet.model.RoundDouble.RoundDouble;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Diet {

    private static Diet selectedDiet = null;

    private int idDiet;
    private int idOsoba;
    private LocalDateTime date;
    private String formatDate;
    private Map<Product, Integer> dietProducts;
    private Map<Meal, Integer> dietMeals;
    private double kcal;
    private double protein;
    private double fat;
    private double carbs;
    private double fiber;

    public Diet() {
    }

    public void setSelectedDiet(Diet diet){
        selectedDiet = diet;
    }

    public static Diet getSelectedDiet(){
        return selectedDiet;
    }

    public int getIdDiet() {
        return idDiet;
    }

    public void setIdDiet(int idDiet) {
        this.idDiet = idDiet;
    }

    public int getIdOsoba() {
        return idOsoba;
    }

    public void setIdOsoba(int idOsoba) {
        this.idOsoba = idOsoba;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setFormatDate(LocalDateTime dateToFormat){
        String format= "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        formatDate = dateToFormat.format(formatter);
    }

    public String getFormatDate(){
        return formatDate;
    }

    public void setDate(LocalDateTime date) {
        setFormatDate(date);
        this.date = date;
    }

    public Map<Product, Integer> getDietProducts() {
        return dietProducts;
    }

    public void setDietProducts(Map<Product, Integer> dietProducts) {
        this.dietProducts = dietProducts;
    }

    public Map<Meal, Integer> getDietMeals() {
        return dietMeals;
    }

    public void setDietMeals(Map<Meal, Integer> dietMeals) {
        this.dietMeals = dietMeals;
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

    public void countKcalForDiet(){
        double kcalForDiet = 0;
        for(Map.Entry<Product, Integer> productAmount: dietProducts.entrySet()){
            kcalForDiet += productAmount.getValue()*productAmount.getKey().getKcal()/100;
        }
        for(Map.Entry<Meal, Integer> mealAmount: dietMeals.entrySet()){
            kcalForDiet += mealAmount.getValue()*mealAmount.getKey().getKcal()/100;
        }
        this.kcal = Double.parseDouble(RoundDouble.roundDouble(kcalForDiet));
    }

    public void countProteinForDiet(){
        double proteinForDiet = 0;
        for(Map.Entry<Product, Integer> productAmount: dietProducts.entrySet()){
            proteinForDiet += productAmount.getValue()*productAmount.getKey().getProtein()/100;
        }
        for(Map.Entry<Meal, Integer> mealAmount: dietMeals.entrySet()){
            proteinForDiet += mealAmount.getValue()*mealAmount.getKey().getProtein()/100;
        }
        this.protein = Double.parseDouble(RoundDouble.roundDouble(proteinForDiet));
    }

    public void countFatForDiet(){
        double fatForDiet = 0;
        for(Map.Entry<Product, Integer> productAmount: dietProducts.entrySet()){
            fatForDiet += productAmount.getValue()*productAmount.getKey().getFat()/100;
        }
        for(Map.Entry<Meal, Integer> mealAmount: dietMeals.entrySet()){
            fatForDiet += mealAmount.getValue()*mealAmount.getKey().getFat()/100;
        }
        this.fat = Double.parseDouble(RoundDouble.roundDouble(fatForDiet));
    }

    public void countCarbsForDiet(){
        double carbsForDiet = 0;
        for(Map.Entry<Product, Integer> productAmount: dietProducts.entrySet()){
            carbsForDiet += productAmount.getValue()*productAmount.getKey().getCarbs()/100;
        }
        for(Map.Entry<Meal, Integer> mealAmount: dietMeals.entrySet()){
            carbsForDiet += mealAmount.getValue()*mealAmount.getKey().getCarbs()/100;
        }
        this.carbs = Double.parseDouble(RoundDouble.roundDouble(carbsForDiet));
    }

    public void countFiberForDiet(){
        double fiberForDiet = 0;
        for(Map.Entry<Product, Integer> productAmount: dietProducts.entrySet()){
            fiberForDiet += productAmount.getValue()*productAmount.getKey().getFiber()/100;
        }
        for(Map.Entry<Meal, Integer> mealAmount: dietMeals.entrySet()){
            fiberForDiet += mealAmount.getValue()*mealAmount.getKey().getFiber()/100;
        }
        this.fiber = Double.parseDouble(RoundDouble.roundDouble(fiberForDiet));
    }

    public static ObservableList<Diet> getDietsByDate(ObservableList<Diet> dietsListAll,LocalDate localDate){
        List<Diet> dietsForDate = new ArrayList<>();

        for(Diet oneDiet: dietsListAll){
            if( oneDiet.getDate().getYear() == localDate.getYear() &&
                oneDiet.getDate().getMonth() == localDate.getMonth() &&
                oneDiet.getDate().getDayOfMonth() == localDate.getDayOfMonth()){
                dietsForDate.add(oneDiet);
            }
        }

        return FXCollections.observableList(dietsForDate);
    }

    public static Double[] countStatsForDiets(ObservableList<Diet> dietsToCount){
        Double[] statsOfDiets = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        for(Diet diet: dietsToCount){
            statsOfDiets[0] += diet.getKcal();
            statsOfDiets[1] += diet.getProtein();
            statsOfDiets[2] += diet.getFat();
            statsOfDiets[3] += diet.getCarbs();
            statsOfDiets[4] += diet.getFiber();
        }
        statsOfDiets[0] = Double.parseDouble(RoundDouble.roundDouble(statsOfDiets[0]));
        statsOfDiets[1] = Double.parseDouble(RoundDouble.roundDouble(statsOfDiets[1]));
        statsOfDiets[2] = Double.parseDouble(RoundDouble.roundDouble(statsOfDiets[2]));
        statsOfDiets[3] = Double.parseDouble(RoundDouble.roundDouble(statsOfDiets[3]));
        statsOfDiets[4] = Double.parseDouble(RoundDouble.roundDouble(statsOfDiets[4]));
        statsOfDiets[5] = Double.parseDouble(RoundDouble.roundDouble((statsOfDiets[0] - Profil.getSelectedProfil().getKcal())));
        return statsOfDiets;
    }
}
