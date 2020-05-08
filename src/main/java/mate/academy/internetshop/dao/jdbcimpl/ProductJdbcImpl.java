package mate.academy.internetshop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class ProductJdbcImpl implements ProductDao {

    @Override
    public Product create(Product element) {
        String sql = "INSERT INTO internet-shop.product(name, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, element.getName());
            statement.setDouble(2, element.getPrice());
        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }
        return null;
    }

    @Override
    public Optional<Product> get(Long element) {
        String sql = "SELECT * FROM internet-shop.product WHERE product_id=" + element;
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Product product = null;
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                product = new Product(name, price);
                product.setId(productId);
            }
            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM internet-shop.product";
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
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
            throw new RuntimeException("", e);
        }
    }

    @Override
    public Product update(Product element) {
        String sql = "UPDATE internet-shop.product SET name=?, price=? WHERE product_id="
                + element.getId();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, element.getName());
            statement.setDouble(2, element.getPrice());
            return get(element.getId()).get();
        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }
    }

    @Override
    public boolean delete(Long element) {
        String sql = "DELETE FROM internet-shop.product WHERE product_id=" + element;
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql) > 0;
        }catch (SQLException e) {
            throw new RuntimeException("", e);
        }
    }
}
