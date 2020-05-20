package diet.model;

public class Product {

    private static Product selectedProduct = null;

    private int idProduct;
    private String name;
    private double kcal;
    private double protein;
    private double carbs;
    private double fat;
    private double fiber;

    public Product(){
    }

    public Product(int idProduct, String name, double kcal, double protein, double carbs, double fat, double fiber) {
        this.idProduct = idProduct;
        this.name = name;
        this.kcal = kcal;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
    }

    public static Product getSelectedProduct(){
        return selectedProduct;
    }

    public static void setSelectedProduct(Product selectedProduct) {
        Product.selectedProduct = selectedProduct;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    @Override
    public String toString() {
        return name;
    }
}
