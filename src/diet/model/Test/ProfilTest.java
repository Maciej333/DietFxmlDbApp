package diet.model.Test;

import diet.model.Food;
import diet.model.Profil;
import diet.model.profilEnums.ProfilGoal;
import diet.model.profilEnums.ProfilSex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfilTest extends Food {

    private Profil testProfil;

    @BeforeEach
    void initializeProfil(){
        testProfil = new Profil(1,"test",25,180,80, ProfilSex.male.toString(), ProfilGoal.keep.toString());
        Profil.setSelectedProfil(testProfil);
    }

    @Test
    void getSelectedProfil() {
        assertEquals(testProfil, Profil.getSelectedProfil());
    }

    @Test
    void setSelectedProfil() {
        Profil newSelectedProfil = new Profil();
        Profil.setSelectedProfil(newSelectedProfil);
        assertEquals(newSelectedProfil, Profil.getSelectedProfil());
    }

    @Test
    void getIdPerson() {
        assertEquals(1, testProfil.getIdPerson());
    }

    @Test
    void setIdPerson() {
        testProfil.setIdPerson(2);
        assertEquals(2, testProfil.getIdPerson());
    }

    @Test
    void getName1() {
        assertEquals("test", testProfil.getName());
    }

    @Test
    void setName1() {
        testProfil.setName("newTest");
        assertEquals("newTest", testProfil.getName());
    }

    @Test
    void getAge() {
        assertEquals(25, testProfil.getAge());
    }

    @Test
    void setAge() {
        testProfil.setAge(26);
        assertEquals(26, testProfil.getAge());
    }

    @Test
    void getWeight() {
        assertEquals(80, testProfil.getWeight());
    }

    @Test
    void setWeight() {
        testProfil.setWeight(81);
        assertEquals(81, testProfil.getWeight());
    }

    @Test
    void getGrowth() {
        assertEquals(180, testProfil.getGrowth());
    }

    @Test
    void setGrowth() {
        testProfil.setGrowth(181);
        assertEquals(181, testProfil.getGrowth());
    }

    @Test
    void getSex() {
        assertEquals(ProfilSex.male, testProfil.getSex());
    }

    @Test
    void setSex() {
        testProfil.setSex(ProfilSex.female.toString());
        assertEquals(ProfilSex.female, testProfil.getSex());
    }

    @Test
    void getGoal() {
        assertEquals(ProfilGoal.keep, testProfil.getGoal());
    }

    @Test
    void setGoal() {
        testProfil.setGoal(ProfilGoal.loss.toString());
        assertEquals(ProfilGoal.loss, testProfil.getGoal());
    }

    @Test
    void getKcal1() {
        testProfil.countKcal();
        assertEquals(1806.20, testProfil.getKcal());
    }

    @Test
    void getProtein1() {
        testProfil.countKcal();
        testProfil.countProtein();
        assertEquals(67.73, testProfil.getProtein());
    }

    @Test
    void getCarbs1() {
        testProfil.countKcal();
        testProfil.countCarbs();
        assertEquals(234.81, testProfil.getCarbs());
    }

    @Test
    void getFat1() {
        testProfil.countKcal();
        testProfil.countFat();
        assertEquals(50.17, testProfil.getFat());
    }

    @Test
    void getFiber1() {
        testProfil.countKcal();
        testProfil.countFiber();
        assertEquals(36.12, testProfil.getFiber());
    }

    @Test
    void countKcal() {
        testProfil.setGoal(ProfilGoal.keep.toString());
        testProfil.countKcal();
        assertEquals(1806.20, testProfil.getKcal());
    }

    @Test
    void countProtein() {
        testProfil.setAge(30);
        testProfil.countKcal();
        testProfil.countProtein();
        assertEquals(66.81, testProfil.getProtein());
    }

    @Test
    void countFat() {
        testProfil.setGrowth(190);
        testProfil.countKcal();
        testProfil.countFat();
        assertEquals(51.91, testProfil.getFat());
    }

    @Test
    void countCarbs() {
        testProfil.setWeight(90);
        testProfil.countKcal();
        testProfil.countCarbs();
        assertEquals(247.79, testProfil.getCarbs());
    }

    @Test
    void countFiber() {
        testProfil.setSex(ProfilSex.female.toString());
        testProfil.countKcal();
        testProfil.countFiber();
        assertEquals(32.80, testProfil.getFiber());
    }
}