package diet.model;

public class Meal {
    private int idMeal;
    private String name;

    public Meal(int idMeal, String name) {
        this.idMeal = idMeal;
        this.name = name;
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
}
