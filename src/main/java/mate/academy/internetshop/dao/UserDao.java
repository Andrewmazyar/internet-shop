package mate.academy.internetshop.dao;

import java.util.Optional;
import mate.academy.internetshop.model.User;

public interface UserDao extends InterfaceDao<User, Long> {

    Optional<User> findByLogin(String login);
}
