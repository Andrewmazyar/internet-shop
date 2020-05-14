package mate.academy.internetshop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class OrderJdbcImpl implements OrderDao {
    @Override
    public List<Order> getByUser(Long id) {
        List<Order> orders = new ArrayList<>();
        String sql ="SELECT * FROM orders WHERE user_id=?;";
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
    public Order create(Order element) {
        String sql = "INSERT INTO orders(user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, element.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                element.setOrderId(resultSet.getLong(1));
            }
            addProductToOrder(element);
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add product to DB", e);
        }
    }

    @Override
    public Optional<Order> get(Long element) {
        String sql = "SELECT * FROM orders WHERE order_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, element);
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
    public Order update(Order element) {
        deleteProductFromOrder(element);
        addProductToOrder(element);
        return element;
    }

    private boolean deleteProductFromOrder(Order order) {
        String sql = "DELETE FROM orders_product as op "
                + "JOIN orders as o on op.prudct_id = o.product_id "
                + "WHERE o.sorder_id=?;";
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
            for (Product product : order.getProducts()) {
                String sql = "INSERT INTO orders_product(order_id, product_id) VALUES (?,?);";
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
    public boolean delete(Long element) {
        String sql = "DELETE FROM orders WHERE order_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, element);
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
        String sql = "SELECT product.product_id, product.name, product.price FROM orders_product "
                + "INNER JOIN product on orders_product.product_id = product.product_id "
                + "WHERE orders_product.order_id = ?;";
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
