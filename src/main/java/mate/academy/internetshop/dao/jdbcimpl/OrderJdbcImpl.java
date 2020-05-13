package mate.academy.internetshop.dao.jdbcimpl;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderJdbcImpl implements OrderDao {
    @Override
    public List<Order> getByUser(Long id) {
        return null;
    }

    @Override
    public Order create(Order element) {
        return null;
    }

    @Override
    public Optional<Order> get(Long element) {
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public Order update(Order element) {
        return null;
    }

    @Override
    public boolean delete(Long element) {
        return false;
    }
}
