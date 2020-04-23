package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Order completeOrder(List<Product> products, User user);

    List<Order> getUserOrders(User user);

    Optional<Order> get(Long id);

    List<Order> getAll();

    boolean delete(Long id);

}
