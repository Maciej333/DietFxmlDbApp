package diet.model;

import diet.model.profilEnums.ProfilGoal;
import diet.model.profilEnums.ProfilSex;

public class Profil {
    private int idPerson;
    private String name;
    private int age;
    private int weight;
    private int growth;
    private ProfilSex sex;
    private ProfilGoal goal;

    public Profil(){

    }
    public Profil(int idPerson, String name, int age,  int growth,int weight, String sex, String goal) {
        this.idPerson = idPerson;
        this.name = name;
        this.age = age;
        this.growth = growth;
        this.weight = weight;
        if(sex.equalsIgnoreCase(ProfilSex.male.toString())){
            this.sex = ProfilSex.male;
        }else {
            this.sex = ProfilSex.female;
        }

        if(goal.equals(ProfilGoal.loss.toString())){
            this.goal = ProfilGoal.loss;
        }else if(goal.equals(ProfilGoal.gain.toString())) {
            this.goal = ProfilGoal.gain;
        }else{
            this.goal = ProfilGoal.keep;
        }
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

    public String getSex() {
        return sex.toString();
    }

    public void setSex(String sex) {
        if(sex.equals(ProfilSex.male.toString())){
            this.sex = ProfilSex.male;
        }else {
            this.sex = ProfilSex.female;
        };
    }

    public ProfilGoal getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        if(goal.equals(ProfilGoal.loss.toString())){
            this.goal = ProfilGoal.loss;
        }else if(goal.equals(ProfilGoal.gain.toString())) {
            this.goal = ProfilGoal.gain;
        }else{
            this.goal = ProfilGoal.keep;
        }
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

    @Override
    public String toString() {
        return this.name;
    }
}





