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
        String sql = String.format("SELECT * FROM shopping_cart WHERE shopping_cart_id=%d;", element);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
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
            ShoppingCart shoppingCart;
            while (resultSet.next()) {
                shoppingCart = getShoppingCartFromResultSet(resultSet);
                shoppingCarts.add(shoppingCart);
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
        String sql = "DELETE FROM shopping_cart WHERE shopping_cart_id=" + element + ";";
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
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

    private List<Product> getProductFromShoppingCart(Long id) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_id, p.name, p.price FROM shopping_cart_prodcts as scp "
                + "RIGHT JOIN product as p on scp.product_id = p.product_id "
                + "WHERE scp.product_id = " + id + ";";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
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
            throw new DataProcessingException("Can`t grt product from shopping cart", e);
        }
    }

    private boolean deleteProductFromShoppingCart(ShoppingCart shoppingcart) {
        String sql = "DELETE FROM shopping_cart_products as scp " +
                "JOIN shopping_cart as sc on scp.prudct_id = sc.product_id " +
                "WHERE sc.shopping_cart_id=" + shoppingcart.getId()+ ";";
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
                String sql = "INSERT INTO shopping_cart_product(cart_id, product_id) VALUES (?,?);";
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
