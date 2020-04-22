package mate.academy.internetshop.model;

import java.util.List;

public class Order {
    private User user;
    private List<Item> items;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
