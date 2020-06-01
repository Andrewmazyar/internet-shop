package web.internetshop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import web.internetshop.dao.ShoppingCartDao;
import web.internetshop.exception.DataProcessingException;
import web.internetshop.lib.Dao;
import web.internetshop.model.Product;
import web.internetshop.model.ShoppingCart;
import web.internetshop.util.ConnectionUtil;

@Dao
public class ShoppingCartJdbcImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String sql = "INSERT INTO shopping_cart(user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, shoppingCart.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                shoppingCart.setId(resultSet.getLong(1));
            }
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add shopping cart to DB", e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String sql = "SELECT * FROM shopping_cart WHERE shopping_cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            ShoppingCart shoppingCart = null;
            while (resultSet.next()) {
                shoppingCart = getShoppingCartFromResultSet(resultSet);
            }
            return Optional.ofNullable(shoppingCart);
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive shopping cart from DB by id ", e);
        }
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long id) {
        String sql = "SELECT * FROM shopping_cart WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            ShoppingCart shoppingCart = null;
            while (resultSet.next()) {
                shoppingCart = getShoppingCartFromResultSet(resultSet);
            }
            return Optional.ofNullable(shoppingCart);
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive shopping cart from DB by id ", e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        String sql = "SELECT * FROM shopping_cart;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                shoppingCarts.add(getShoppingCartFromResultSet(resultSet));
            }
            return shoppingCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive all shopping cart from DB ", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        deleteProductFromShoppingCart(shoppingCart);
        addProductToShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM shopping_cart WHERE shopping_cart_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete shopping cart from DB", e);
        }
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long shoppingCartId = resultSet.getLong("shopping_cart_id");
        Long userId = resultSet.getLong("user_id");
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        shoppingCart.setId(shoppingCartId);
        shoppingCart.setProducts(getProductFromShoppingCart(shoppingCartId));
        return shoppingCart;
    }

    private List<Product> getProductFromShoppingCart(Long id) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.name, p.product_id, p.price "
                + "FROM shopping_carts_products AS scp INNER JOIN product AS p "
                + "ON scp.product_id = p.product_id WHERE scp.cart_id = ?;";
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
        }
    }

    private boolean deleteProductFromShoppingCart(ShoppingCart shoppingcart) {
        String sql = "DELETE FROM shopping_carts_products WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, shoppingcart.getId());
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete product from shopping cart in DB", e);
        }
    }

    private void addProductToShoppingCart(ShoppingCart shoppingcart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO shopping_carts_products(cart_id, product_id) VALUES (?,?);";
            for (Product product : shoppingcart.getProducts()) {
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, shoppingcart.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();

            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add product to shopping cart in DB", e);
        }
    }
}
