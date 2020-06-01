package web.internetshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import web.internetshop.dao.OrderDao;
import web.internetshop.lib.Inject;
import web.internetshop.lib.Service;
import web.internetshop.model.Order;
import web.internetshop.model.Product;
import web.internetshop.model.User;
import web.internetshop.service.OrderService;
import web.internetshop.service.ShoppingCartService;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;

    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(List<Product> products, Long userId) {
        List<Product> newProducts = new ArrayList<>(products);
        return orderDao.create(new Order(newProducts, userId));
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderDao.getByUser(user.getId());
    }

    @Override
    public Order create(Order element) {
        return orderDao.create(element);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order update(Order element) {
        return orderDao.update(element);
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}

