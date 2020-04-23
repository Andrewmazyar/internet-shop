package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        return Storage.addToUser(user);
    }

    @Override
    public Optional<User> get(Long id) {
        return Storage.users
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User update(User user) {
        IntStream.range(0, Storage.users.size())
                .filter(u -> user.getId().equals(Storage.users.get(u).getId()))
                .forEach(i -> Storage.users.set(i, user));
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.users.removeIf(user -> user.getId().equals(id));
    }
}
