package web.internetshop.service;

import java.util.List;
import web.internetshop.model.Order;
import web.internetshop.model.Product;
import web.internetshop.model.User;

public interface OrderService extends GenericService<Order, Long> {
    Order completeOrder(List<Product> products, Long user);

    List<Order> getUserOrders(User user);

    Order get(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
