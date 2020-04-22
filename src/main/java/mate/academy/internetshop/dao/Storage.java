package mate.academy.internetshop.dao;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public class Storage {
    private static Long bucketId = 0L;
    private static Long itemId = 0L;
    private static Long orderId = 0L;
    private static Long userId = 0L;

    public static final List<Item> items = new ArrayList<>();
    public static final List<Bucket> buckets = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();

    public static void addToList(Item item) {
        itemId++;
        item.setId(itemId);
        items.add(item);
    }

    public static void addToList(Bucket bucket) {
        bucketId++;
        bucket.setId(bucketId);
        buckets.add(bucket);
    }

    public static void addToList(User user) {
        userId++;
        user.setId(userId);
        users.add(user);
    }

    public static void addToList(Order order) {
        orderId++;
        order.setOrderId(orderId);
        orders.add(order);
    }
}
