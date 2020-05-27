package diet.model;

import diet.model.additionalClasses.ClassOfStaticMethod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diet extends Food{

    private static Diet selectedDiet = null;

    private int idDiet;
    private int idOsoba;
    private LocalDateTime date;
    private String formatDate;
    private Map<Food, Integer> dietMealsProductsMap = new HashMap<>();

    public Diet() {
    }

    public void setSelectedDiet(Diet diet) {
        selectedDiet = diet;
    }

    public static Diet getSelectedDiet() {
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

    public void setFormatDate(LocalDateTime dateToFormat) {
        String format = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        formatDate = dateToFormat.format(formatter);
    }

    public String getFormatDate() {
        return formatDate;
    }

    public void setDate(LocalDateTime date) {
        setFormatDate(date);
        this.date = date;
    }

    public Map<Food, Integer> getDietMealsProductsMap() {
        return dietMealsProductsMap;
    }

    public void addMapToDietMealsProductsMap(Map<Food, Integer> dietMealsProductsMap) {
        this.dietMealsProductsMap.putAll(dietMealsProductsMap);
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void setName(String name) {
    }

    public void countKcalForDiet() {
        double kcalForDiet = 0;
        for (Map.Entry<Food, Integer> mealProductAmount : dietMealsProductsMap.entrySet()) {
            kcalForDiet += mealProductAmount.getValue() * mealProductAmount.getKey().getKcal() / 100;
        }
        setKcal(Double.parseDouble(ClassOfStaticMethod.roundDouble(kcalForDiet)));
    }

    public void countProteinForDiet() {
        double proteinForDiet = 0;
        for (Map.Entry<Food, Integer> mealProductAmount : dietMealsProductsMap.entrySet()) {
            proteinForDiet += mealProductAmount.getValue() * mealProductAmount.getKey().getProtein() / 100;
        }
        setProtein(Double.parseDouble(ClassOfStaticMethod.roundDouble(proteinForDiet)));
    }

    public void countFatForDiet() {
        double fatForDiet = 0;
        for (Map.Entry<Food, Integer> mealProductAmount : dietMealsProductsMap.entrySet()) {
            fatForDiet += mealProductAmount.getValue() * mealProductAmount.getKey().getFat() / 100;
        }
        setFat(Double.parseDouble(ClassOfStaticMethod.roundDouble(fatForDiet)));
    }

    public void countCarbsForDiet() {
        double carbsForDiet = 0;
        for (Map.Entry<Food, Integer> mealProductAmount : dietMealsProductsMap.entrySet()) {
            carbsForDiet += mealProductAmount.getValue() * mealProductAmount.getKey().getCarbs() / 100;
        }
        setCarbs(Double.parseDouble(ClassOfStaticMethod.roundDouble(carbsForDiet)));
    }

    public void countFiberForDiet() {
        double fiberForDiet = 0;
        for (Map.Entry<Food, Integer> mealProductAmount : dietMealsProductsMap.entrySet()) {
            fiberForDiet += mealProductAmount.getValue() * mealProductAmount.getKey().getFiber() / 100;
        }
        setFiber(Double.parseDouble(ClassOfStaticMethod.roundDouble(fiberForDiet)));
    }

    public static ObservableList<Diet> getDietsByDate(ObservableList<Diet> dietsListAll, LocalDate localDate) {
        List<Diet> dietsForDate = new ArrayList<>();

        for (Diet oneDiet : dietsListAll) {
            if (oneDiet.getDate().getYear() == localDate.getYear() &&
                    oneDiet.getDate().getMonth() == localDate.getMonth() &&
                    oneDiet.getDate().getDayOfMonth() == localDate.getDayOfMonth()) {
                dietsForDate.add(oneDiet);
            }
        }

        return FXCollections.observableList(dietsForDate);
    }

    public static Double[] countStatsForDiets(ObservableList<Diet> dietsToCount) {
        Double[] statsOfDiets = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        for (Diet diet : dietsToCount) {
            statsOfDiets[0] += diet.getKcal();
            statsOfDiets[1] += diet.getProtein();
            statsOfDiets[2] += diet.getFat();
            statsOfDiets[3] += diet.getCarbs();
            statsOfDiets[4] += diet.getFiber();
        }
        statsOfDiets[0] = Double.parseDouble(ClassOfStaticMethod.roundDouble(statsOfDiets[0]));
        statsOfDiets[1] = Double.parseDouble(ClassOfStaticMethod.roundDouble(statsOfDiets[1]));
        statsOfDiets[2] = Double.parseDouble(ClassOfStaticMethod.roundDouble(statsOfDiets[2]));
        statsOfDiets[3] = Double.parseDouble(ClassOfStaticMethod.roundDouble(statsOfDiets[3]));
        statsOfDiets[4] = Double.parseDouble(ClassOfStaticMethod.roundDouble(statsOfDiets[4]));
        statsOfDiets[5] = Double.parseDouble(ClassOfStaticMethod.roundDouble((statsOfDiets[0] - Profil.getSelectedProfil().getKcal())));
        return statsOfDiets;
    }
}
