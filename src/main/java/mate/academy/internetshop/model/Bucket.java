package mate.academy.internetshop.model;

import java.util.List;

public class Bucket {
    private Long id;
    private List<Item> items;
    private Long orderId;
    private User user;

    public Long getUser() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
