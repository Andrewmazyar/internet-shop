package mate.academy.internetshop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class UserJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesForUser(user.getId()));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive user from DB by login ", e);
        }
    }

    @Override
    public User create(User element) {
        String sql = "INSERT INTO users(name, login, password) VALUES (?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getUserName());
            statement.setString(2, element.getLogin());
            statement.setString(3, element.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                element.setId(resultSet.getLong(1));
            }
            String query = "INSERT INTO user_roles(user_id, role_id) VALUES("
                    + element.getId() + ", 2);";
            PreparedStatement statement1 = connection.prepareStatement(query);
            statement1.executeUpdate();
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add user to DB", e);
        }
    }

    @Override
    public Optional<User> get(Long element) {
        String sql = "SELECT * FROM users WHERE user_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, element);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesForUser(user.getId()));
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive user from DB by id ", e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            User user;
            while (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesForUser(user.getId()));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive all user from DB ", e);
        }
    }

    @Override
    public User update(User element) {
        String sql = "UPDATE users SET name=?, login=?, password=? WHERE user_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, element.getUserName());
            statement.setString(2, element.getLogin());
            statement.setString(3, element.getPassword());
            statement.setLong(4, element.getId());
            return get(element.getId()).get();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update user in DB", e);
        }
    }

    @Override
    public boolean delete(Long element) {
        String sql = "DELETE FROM users WHERE user_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, element);
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete user from DB", e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login1 = resultSet.getString("login");
        String password = resultSet.getString("password");
        User user = new User(name, login1, password);
        user.setId(userId);
        return user;
    }

    private Set<Role> getRolesForUser(Long userId) throws SQLException {
        String url = "SELECT role_name FROM user_roles "
                + "JOIN roles USING (role_id)"
                + "WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(url);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> roles = new HashSet<>();
            while (resultSet.next()) {
                roles.add(Role.of(resultSet.getString("role_name")));
            }
            return roles;
        }
    }
}
