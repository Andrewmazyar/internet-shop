package web.internetshop.dao;

import java.util.List;
import java.util.Optional;

public interface InterfaceDao<E,K> {
    E create(E element);

    Optional<E> get(K element);

    List<E> getAll();

    E update(E element);

    boolean delete(K element);
}
