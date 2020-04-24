package mate.academy.internetshop.dao;

import java.util.Optional;
import mate.academy.internetshop.model.ShoppingCart;

public interface ShoppingCartDao extends InterfaceDao<ShoppingCart, Long> {
    Optional<ShoppingCart> getByUser(Long id);
}
