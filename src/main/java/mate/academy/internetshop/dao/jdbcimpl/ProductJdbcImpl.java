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
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class ProductJdbcImpl implements ProductDao {

    @Override
    public Product create(Product element) {
        String sql = "INSERT INTO product(name, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getName());
            statement.setDouble(2, element.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                element.setId(resultSet.getLong(1));
            }
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add product to DB", e);
        }
    }

    @Override
    public Optional<Product> get(Long element) {
        String sql = String.format("SELECT * FROM product WHERE product_id=%d;", element);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Product product = null;
            while (resultSet.next()) {
                product = getProductResultSet(resultSet);
            }
            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive product from DB by id ", e);
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            Product product;
            while (resultSet.next()) {
                product = getProductResultSet(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t receive all products from DB", e);
        }
    }

    @Override
    public Product update(Product element) {
        String sql = "UPDATE product SET name=?, price=? WHERE product_id="
                + element.getId() + ";";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, element.getName());
            statement.setDouble(2, element.getPrice());
            return get(element.getId()).get();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update product in DB", e);
        }
    }

    @Override
    public boolean delete(Long element) {
        String sql = "DELETE FROM product WHERE product_id=" + element + ";";
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete product from DB", e);
        }
    }

    private Product getProductResultSet(ResultSet resultSet) throws SQLException {
        Long productId = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        Double price = resultSet.getDouble("price");
        Product product = new Product(name, price);
        product.setId(productId);
        return product;
    }
}
