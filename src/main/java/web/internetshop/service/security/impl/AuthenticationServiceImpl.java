package web.internetshop.service.security.impl;

import web.internetshop.exception.AuthenticationException;
import web.internetshop.lib.Inject;
import web.internetshop.lib.Service;
import web.internetshop.model.User;
import web.internetshop.service.UserService;
import web.internetshop.service.security.AuthenticationService;
import web.internetshop.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User userFromDb = userService.findByLogin(login).orElseThrow(
                () -> new AuthenticationException("Incorrect password or login"));
        if (HashUtil.hashPassword(password, userFromDb.getSalt())
                .equals(userFromDb.getPassword())) {
            return userFromDb;
        }
        throw new AuthenticationException("Incorrect password or login");
    }
}
