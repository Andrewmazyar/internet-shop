package mate.academy.internetshop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class UserJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String sql = String.format("SELECT * FROM users WHERE login=%s;", login);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
            return Optional.ofNullable(user);
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
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add user to DB", e);
        }
    }

    @Override
    public Optional<User> get(Long element) {
        String sql = String.format("SELECT * FROM users WHERE user_id=%d;", element);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            while (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive user from DB by id ", e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = String.format("SELECT * FROM users;");
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            User user;
            while (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive user from DB by login ", e);
        }
    }

    @Override
    public User update(User element) {
        String sql = "UPDATE users SET name=?, login=?, password=? WHERE user_id="
                + element.getId() + ";";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, element.getUserName());
            statement.setString(2, element.getLogin());
            statement.setString(3, element.getPassword());
            return get(element.getId()).get();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update user in DB", e);
        }
    }

    @Override
    public boolean delete(Long element) {
        String sql = "DELETE FROM users WHERE user_id=" + element + ";";
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
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
}
