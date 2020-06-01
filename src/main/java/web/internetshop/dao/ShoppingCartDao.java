package web.internetshop.dao;

import java.util.Optional;
import web.internetshop.model.ShoppingCart;

public interface ShoppingCartDao extends InterfaceDao<ShoppingCart, Long> {
    public Optional<ShoppingCart> getByUserId(Long id);

}
