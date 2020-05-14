package mate.academy.internetshop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class ShoppingCartJdbcImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart element) {
        String sql = "INSERT INTO shopping_cart(user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, element.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                element.setId(resultSet.getLong(1));
            }
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add shopping cart to DB", e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long element) {
        String sql = "SELECT * FROM shopping_cart WHERE shopping_cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, element);
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
    public ShoppingCart update(ShoppingCart element) {
        deleteProductFromShoppingCart(element);
        addProductToShoppingCart(element);
        return element;
    }

    @Override
    public boolean delete(Long element) {
        String sql = "DELETE FROM shopping_cart WHERE shopping_cart_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, element);
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
        String sql = "SELECT product.name, product.product_id, product.price "
                + "FROM shopping_carts_products INNER JOIN product "
                + "ON shopping_carts_products.product_id = product.product_id "
                + "WHERE shopping_carts_products.cart_id = ?;";
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
        String sql = "DELETE FROM shopping_carts_products "
                + "WHERE cart_id = " + shoppingcart.getId() + ";";
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete product from shopping cart in DB", e);
        }
    }

    private void addProductToShoppingCart(ShoppingCart shoppingcart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Product product : shoppingcart.getProducts()) {
                String sql = "INSERT INTO shopping_carts_products(cart_id, product_id) "
                        + "VALUES (?,?);";
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
