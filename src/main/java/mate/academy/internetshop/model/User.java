package mate.academy.internetshop.model;

public class User {
    private static Long idGenerator = 0L;
    private Long id;
    private String userName;
    private String login;
    private String password;

    public User(String userName,String login, String password) {
        id = ++idGenerator;
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

}
