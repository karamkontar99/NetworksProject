package main.repos;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    void insert(T t);

    void delete(UUID id);

    void update(T t);

    List<T> findAll();

    T find(UUID id);

}
