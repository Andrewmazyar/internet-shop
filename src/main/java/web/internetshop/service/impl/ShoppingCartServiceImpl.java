package web.internetshop.service.impl;

import java.util.List;
import web.internetshop.dao.ShoppingCartDao;
import web.internetshop.lib.Inject;
import web.internetshop.lib.Service;
import web.internetshop.model.Product;
import web.internetshop.model.ShoppingCart;
import web.internetshop.service.ShoppingCartService;
import web.internetshop.service.UserService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Inject
    private UserService userService;

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        if (shoppingCart.getId() == null) {
            shoppingCartDao.create(shoppingCart);
        }
        shoppingCart.getProducts().add(product);
        shoppingCartDao.update(shoppingCart);
        return shoppingCart;
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        return shoppingCart.getProducts().remove(product);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().clear();
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getAll().stream()
                .filter(shoppingCart -> shoppingCart.getUserId().equals(userId))
                .findFirst()
                .orElse(shoppingCartDao.create(new ShoppingCart(userId)));
    }

    @Override
    public List<Product> getAllProducts(ShoppingCart shoppingCart) {
        return shoppingCartDao.get(shoppingCart.getId())
                .orElse(shoppingCart)
                .getProducts();
    }

    @Override
    public ShoppingCart create(ShoppingCart element) {
        return shoppingCartDao.create(element);
    }

    @Override
    public ShoppingCart get(Long element) {
        return shoppingCartDao.get(element).get();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return shoppingCartDao.getAll();
    }

    @Override
    public ShoppingCart update(ShoppingCart element) {
        return shoppingCartDao.update(element);
    }

    @Override
    public boolean delete(Long element) {
        return shoppingCartDao.delete(element);
    }
}
