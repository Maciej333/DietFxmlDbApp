package diet.model;

public class Product extends Food{

    private static Product selectedProduct = null;

    private int idProduct;

    public Product() {
    }

    public Product(int idProduct, String name, double kcal, double protein, double carbs, double fat, double fiber) {
        super(kcal, protein, fat, carbs, fiber);
        this.idProduct = idProduct;
        setName(name);
    }

    public static Product getSelectedProduct() {
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

    @Override
    public String toString() {
        return getName();
    }
}
