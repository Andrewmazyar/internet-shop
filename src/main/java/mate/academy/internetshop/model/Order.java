package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private User user;
    private List<Product> products = new ArrayList<>();
    private Long orderId;

    public Order(List<Product> products, User user) {
        this.products = products;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                + ", user" + user.getUserName()
                + ", product" + products;
    }
}
