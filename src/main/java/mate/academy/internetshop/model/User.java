package mate.academy.internetshop.model;

import java.util.Set;

public class User {
    private Long id;
    private String userName;
    private String login;
    private String password;
    private Set<Role> roles;

    public User(String userName,String login, String password) {
        this.userName = userName;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "User{name:" + userName
                + ", login: " + login
                + ", password: " + password
                + "}";
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
