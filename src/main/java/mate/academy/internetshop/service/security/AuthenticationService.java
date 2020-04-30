package mate.academy.internetshop.service.security;

import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;
}
