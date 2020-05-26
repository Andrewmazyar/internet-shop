package web.internetshop.dao;

import java.util.Optional;
import web.internetshop.model.User;

public interface UserDao extends InterfaceDao<User, Long> {

    Optional<User> findByLogin(String login);
}
