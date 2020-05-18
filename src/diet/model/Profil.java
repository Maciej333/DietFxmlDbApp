package diet.model;

import diet.model.ProfilEnums.ProfilGoal;
import diet.model.ProfilEnums.ProfilSex;

public class Profil {
    private int idPerson;
    private String name;
    private int age;
    private int weight;
    private int growth;
    private ProfilSex sex;
    private ProfilGoal goal;

    public Profil(int idPerson, String name, int age, int weight, int growth, ProfilSex sex, ProfilGoal goal) {
        this.idPerson = idPerson;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.growth = growth;
        this.sex = sex;
        this.goal = goal;
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

    public void setSex(ProfilSex sex) {
        this.sex = sex;
    }

    public ProfilGoal getGoal() {
        return goal;
    }

    public void setGoal(ProfilGoal goal) {
        this.goal = goal;
    }

    public double countBalans(){
        double bilans =0;
        if(sex.equals(ProfilSex.male)){
            bilans = (9.99*this.weight)+(6.25*this.growth)-(4.92*this.age)+5;
            if(goal.equals(ProfilGoal.loss)){
                bilans -=300;
            }else if(goal.equals(ProfilGoal.gain)){
                bilans +=300;
            }
            return bilans;
        }else{
            bilans = (9.99*this.weight)+(6.25*this.growth)-(4.92*this.age)-161;
            if(goal.equals(ProfilGoal.loss)){
                bilans -=300;
            }else if(goal.equals(ProfilGoal.gain)){
                bilans +=300;
            }
            return bilans;
        }
    }

}





