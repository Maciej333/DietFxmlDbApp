package diet.model.Test;

import diet.model.Food;
import diet.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest extends Food {

    private Product testProduct;

    @BeforeEach
    void initProduct(){
        testProduct = new Product(1,"egg", 50,10,5,1,7);
        Product.setSelectedProduct(testProduct);
    }

    @Test
    void getName1() {
        assertEquals("egg",testProduct.getName());
    }

    @Test
    void setName1() {
        testProduct.setName("newEgg");
        assertEquals("newEgg",testProduct.getName());
    }

    @Test
    void getKcal1() {
        assertEquals(50.0, testProduct.getKcal());
    }

    @Test
    void setKcal1() {
        testProduct.setKcal(55);
        assertEquals(55, testProduct.getKcal());
    }

    @Test
    void getProtein1() {
        assertEquals(10, testProduct.getProtein());
    }

    @Test
    void setProtein1() {
        testProduct.setProtein(15);
        assertEquals(15, testProduct.getProtein());
    }

    @Test
    void getFat1() {
        assertEquals(1, testProduct.getFat());
    }

    @Test
    void setFat1() {
        testProduct.setFat(6);
        assertEquals(6, testProduct.getFat());
    }

    @Test
    void getCarbs1() {
        assertEquals(5, testProduct.getCarbs());
    }

    @Test
    void setCarbs1() {
        testProduct.setCarbs(11);
        assertEquals(11, testProduct.getCarbs());
    }

    @Test
    void getFiber1() {
        assertEquals(7, testProduct.getFiber());
    }

    @Test
    void setFiber1() {
        testProduct.setFiber(8);
        assertEquals(8, testProduct.getFiber());
    }

    @Test
    void getSelectedProduct() {
        assertEquals(testProduct, Product.getSelectedProduct());
    }

    @Test
    void setSelectedProduct() {
        Product newSelectedProduct = new Product();
        Product.setSelectedProduct(newSelectedProduct);
        assertEquals(newSelectedProduct, Product.getSelectedProduct());
    }

    @Test
    void getIdProduct() {
        assertEquals(1, testProduct.getIdProduct());
    }

    @Test
    void setIdProduct() {
        testProduct.setIdProduct(2);
        assertEquals(2, testProduct.getIdProduct());
    }
}