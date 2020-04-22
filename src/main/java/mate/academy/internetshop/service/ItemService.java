package mate.academy.internetshop.service;

import mate.academy.internetshop.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item create(Item item);

    Optional<Item> get(Long id);

    List<Item> getAll();

    List<Item> getAllAvailable();

    Item update(Item item);

    boolean delete(Long id);

}

