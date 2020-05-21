package diet.model;

import diet.model.RoundDouble.RoundDouble;
import diet.model.profilEnums.ProfilGoal;
import diet.model.profilEnums.ProfilSex;

public class Profil {

    private static Profil selectedProfil = null;

    private int idPerson;
    private String name;
    private int age;
    private int weight;
    private int growth;
    private ProfilSex sex;
    private ProfilGoal goal;

    private double kcal;
    private double protein;
    private double carbs;
    private double fat;
    private double fiber;

    public Profil() {

    }

    public Profil(int idPerson, String name, int age, int growth, int weight, String sex, String goal) {
        this.idPerson = idPerson;
        this.name = name;
        this.age = age;
        this.growth = growth;
        this.weight = weight;
        if (sex.equalsIgnoreCase(ProfilSex.male.toString())) {
            this.sex = ProfilSex.male;
        } else {
            this.sex = ProfilSex.female;
        }

        if (goal.equals(ProfilGoal.loss.toString())) {
            this.goal = ProfilGoal.loss;
        } else if (goal.equals(ProfilGoal.gain.toString())) {
            this.goal = ProfilGoal.gain;
        } else {
            this.goal = ProfilGoal.keep;
        }
    }

    public static Profil getSelectedProfil() {
        return selectedProfil;
    }

    public static void setSelectedProfil(Profil selectedProfil) {
        Profil.selectedProfil = selectedProfil;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public ProfilSex getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if (sex.equals(ProfilSex.male.toString())) {
            this.sex = ProfilSex.male;
        } else {
            this.sex = ProfilSex.female;
        }
    }

    public ProfilGoal getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        if (goal.equals(ProfilGoal.loss.toString())) {
            this.goal = ProfilGoal.loss;
        } else if (goal.equals(ProfilGoal.gain.toString())) {
            this.goal = ProfilGoal.gain;
        } else {
            this.goal = ProfilGoal.keep;
        }
    }

    public double getKcal() {
        return kcal;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }

    public double getFiber() {
        return fiber;
    }

    public void countKcal() {
        double kcal = 0;
        if (sex.equals(ProfilSex.male)) {
            kcal = (9.99 * this.weight) + (6.25 * this.growth) - (4.92 * this.age) + 5;
            if (goal.equals(ProfilGoal.loss)) {
                kcal -= 300;
            } else if (goal.equals(ProfilGoal.gain)) {
                kcal += 300;
            }
        } else {
            kcal = (9.99 * this.weight) + (6.25 * this.growth) - (4.92 * this.age) - 161;
            if (goal.equals(ProfilGoal.loss)) {
                kcal -= 300;
            } else if (goal.equals(ProfilGoal.gain)) {
                kcal += 300;
            }
        }
        this.kcal = kcal;
    }

    public void countProtein(){
        this.protein = Double.parseDouble(RoundDouble.roundDouble(kcal*0.15/4));
    }

    public void countFat(){
        this.fat = Double.parseDouble(RoundDouble.roundDouble(kcal*0.25/9));
    }

    public void countCarbs(){
        this.carbs = Double.parseDouble(RoundDouble.roundDouble(kcal*0.52/4));
    }

    public void countFiber(){
        this.fiber = Double.parseDouble(RoundDouble.roundDouble(kcal*0.08/4));
    }

    @Override
    public String toString() {
        return this.name;
    }
}





