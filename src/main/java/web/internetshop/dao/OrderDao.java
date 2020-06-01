package web.internetshop.dao;

import java.util.List;
import web.internetshop.model.Order;

public interface OrderDao extends InterfaceDao<Order, Long> {
    List<Order> getByUser(Long id);
}
