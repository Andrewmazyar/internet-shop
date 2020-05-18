package mate.academy.internetshop.service.security.impl;

import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.service.security.AuthenticationService;
import mate.academy.internetshop.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User userFromDb = userService.findByLogin(login).orElseThrow(
                () -> new AuthenticationException("Incorrect password or login"));
        if (HashUtil.hashPassword(password, userFromDb.getSalt()).equals(userFromDb.getPassword()))  {
            return userFromDb;
        }
        throw new AuthenticationException("Incorrect password or login");
    }
}
