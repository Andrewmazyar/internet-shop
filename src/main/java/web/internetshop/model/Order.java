package web.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long userId;
    private List<Product> products = new ArrayList<>();
    private Long orderId;

    public Order(List<Product> products, Long user) {
        this.products = products;
        this.userId = user;
    }

    public Long getUser() {
        return userId;
    }

    public void setUserId(Long user) {
        this.userId = user;
    }

    public Long getUserId() {
        return userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String toString() {
        return "Order{ id: " + orderId
                + ", user" + userId
                + ", product" + products;
    }
}
