package mate.academy.internetshop.dao;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;

public class Storage {
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();
    public static final List<Product> products = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();

    private static Long bucketId = 0L;
    private static Long itemId = 0L;
    private static Long orderId = 0L;
    private static Long userId = 0L;

    public static void addToProduct(Product product) {
        itemId++;
        product.setId(itemId);
        products.add(product);
    }

    public static void addToShoppingCart(ShoppingCart shoppingCart) {
        bucketId++;
        shoppingCart.setId(bucketId);
        shoppingCarts.add(shoppingCart);
    }

    public static User addToUser(User user) {
        userId++;
        user.setId(userId);
        users.add(user);
        return user;
    }

    public static void addToOrder(Order order) {
        orderId++;
        order.setOrderId(orderId);
        orders.add(order);
    }
}
