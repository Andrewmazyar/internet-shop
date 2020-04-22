package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ItemDaoImpl implements ItemDao {
    @Override
    public Item create(Item item) {
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Storage.items
                .stream()
                .filter(item -> item.getId() == id)
                .findFirst();
    }

    @Override
    public List<Item> getAll() {
        return Storage.items;
    }

    @Override
    public Item update(Item item) {
        IntStream.range(0, Storage.items.size())
                .filter(ind -> Storage.items.get(ind).getId() == (item.getId()))
                .findFirst()
                .ifPresent(ind -> Storage.items.set(ind, item));
        return item;
    }

    @Override
    public boolean delete(Long id) {
        Item item = Storage.items
                .stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
        return Storage.items.remove(item);
    }
}
