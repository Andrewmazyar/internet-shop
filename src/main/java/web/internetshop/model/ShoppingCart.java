package web.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private List<Product> products = new ArrayList<>();
    private Long userId;

    public ShoppingCart(Long user) {
        this.userId = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user) {
        this.userId = user;
    }
}
