package web.internetshop.service.security;

import web.internetshop.exception.AuthenticationException;
import web.internetshop.model.User;

public interface AuthenticationService {
    public User login(String login, String password) throws AuthenticationException;
}
