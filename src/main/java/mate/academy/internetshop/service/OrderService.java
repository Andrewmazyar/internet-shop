package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;

public interface OrderService extends GenericService<Order, Long> {
    Order completeOrder(List<Product> products, User user);

    List<Order> getUserOrders(User user);

    Optional<Order> get(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
