package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<E, K> {
    E create(E element);

    Optional<E> get(K element);

    List<E> getAll();

    E update(E element);

    boolean delete(K element);
}
