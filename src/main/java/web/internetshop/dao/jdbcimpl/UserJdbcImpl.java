package web.internetshop.dao.jdbcimpl;

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
import web.internetshop.dao.UserDao;
import web.internetshop.exception.DataProcessingException;
import web.internetshop.lib.Dao;
import web.internetshop.model.Role;
import web.internetshop.model.User;
import web.internetshop.util.ConnectionUtil;

@Dao
public class UserJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesForUser(user.getId()));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("can`t receive user from DB by login ", e);
        }
    }

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users(name, login, password, salt) VALUES (?, ?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            insertUsersRoles(user, connection);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add user to DB", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String sql = "SELECT * FROM users WHERE user_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
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
    public User update(User user) {
        String sql = "UPDATE users SET name=?, login=?, password=?, salt=? WHERE user_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.setLong(5, user.getId());
            return get(user.getId()).get();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update user in DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM users WHERE user_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
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
        byte[] salt = resultSet.getBytes("salt");
        User user = new User(name, login1, password);
        user.setId(userId);
        user.setSalt(salt);
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

    private void insertUsersRoles(User user, Connection connection) throws SQLException {
        String selectRoleIdQuery = "SELECT role_id FROM roles WHERE role_name = ?";
        String insertUsersRolesQuery = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?);";
        PreparedStatement selectStatement = connection.prepareStatement(selectRoleIdQuery);
        PreparedStatement insertStatement = connection.prepareStatement(insertUsersRolesQuery);
        for (Role role : user.getRoles()) {
            selectStatement.setString(1, role.getRoleName().name());
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();
            insertStatement.setLong(1, user.getId());
            insertStatement.setLong(2, resultSet.getLong("role_id"));
            insertStatement.executeUpdate();
        }
    }
}
