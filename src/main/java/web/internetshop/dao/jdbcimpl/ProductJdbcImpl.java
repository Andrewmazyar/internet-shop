package web.internetshop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import web.internetshop.dao.ProductDao;
import web.internetshop.exception.DataProcessingException;
import web.internetshop.lib.Dao;
import web.internetshop.model.Product;
import web.internetshop.util.ConnectionUtil;

@Dao
public class ProductJdbcImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        String sql = "INSERT INTO product(name, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong(1));
            }
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add product to DB", e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String sql = "SELECT * FROM product WHERE product_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Product product = null;
            while (resultSet.next()) {
                product = getProductFromResultSet(resultSet);
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
                product = getProductFromResultSet(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t receive all products from DB", e);
        }
    }

    @Override
    public Product update(Product product) {
        String sql = "UPDATE product SET name=?, price=? WHERE product_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());
            return get(product.getId()).get();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update product in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM product WHERE product_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete product from DB", e);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long productId = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        Double price = resultSet.getDouble("price");
        Product product = new Product(name, price);
        product.setId(productId);
        return product;
    }
}
