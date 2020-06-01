package web.internetshop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import web.internetshop.dao.OrderDao;
import web.internetshop.exception.DataProcessingException;
import web.internetshop.lib.Dao;
import web.internetshop.model.Order;
import web.internetshop.model.Product;
import web.internetshop.util.ConnectionUtil;

@Dao
public class OrderJdbcImpl implements OrderDao {
    @Override
    public List<Order> getByUser(Long id) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive orders from DB by user id ", e);
        }
    }

    @Override
    public Order create(Order order) {
        String sql = "INSERT INTO orders(user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setOrderId(resultSet.getLong(1));
            }
            addProductToOrder(order);
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add product to DB", e);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        String sql = "SELECT * FROM orders WHERE order_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getOrderFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive orders from DB by user id ", e);
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive orders from DB by user id ", e);
        }
    }

    @Override
    public Order update(Order order) {
        deleteProductFromOrder(order);
        addProductToOrder(order);
        return order;
    }

    private boolean deleteProductFromOrder(Order order) {
        String sql = "DELETE FROM orders_product as op "
                + "JOIN orders as o on op.prudct_id = o.product_id WHERE o.sorder_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, order.getOrderId());
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete product from orders in DB", e);
        }
    }

    private void addProductToOrder(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO orders_product(order_id, product_id) VALUES (?,?);";
            for (Product product : order.getProducts()) {
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, order.getOrderId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();

            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add product to orders in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM orders WHERE order_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete shopping cart from DB", e);
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Long orderId = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        List<Product> products = getProductFromOrder(orderId);
        Order order = new Order(products, userId);
        order.setOrderId(orderId);
        return order;
    }

    private List<Product> getProductFromOrder(Long id) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_id, p.name, pt.price FROM orders_product AS op "
                + "INNER JOIN product AS p on op.product_id = p.product_id WHERE op.order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Product product;
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                product = new Product(name, price);
                product.setId(productId);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get product from orders", e);
        }
    }
}
