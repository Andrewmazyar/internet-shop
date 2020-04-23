package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class ShoppingDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        Storage.shoppingCarts
                .stream()
                .filter(s -> s.getId().equals(shoppingCart.getId()))
                .forEach(s -> s.getProducts().add(product));
        return shoppingCart;
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        return Storage.shoppingCarts
                .removeIf(s -> s.getId().equals(product.getId()));
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        Storage.shoppingCarts.clear();
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shoppingCarts
                .stream()
                .filter(s -> s.getUser().getId().equals(userId))
                .findFirst();
    }

    @Override
    public List<Product> getAllProducts(ShoppingCart shoppingCart) {
        return Storage.shoppingCarts
                .stream()
                .filter(s -> s.getId().equals(shoppingCart.getId()))
                .flatMap(c -> c.getProducts().stream())
                .collect(Collectors.toList());
    }
}
