package mate.academy.internetshop.service;

import java.util.List;

public interface GenericService<E, K> {
    E create(E element);

    E get(K element);

    List<E> getAll();

    E update(E element);

    boolean delete(K element);
}
