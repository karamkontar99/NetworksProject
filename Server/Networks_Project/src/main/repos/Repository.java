package main.repos;

import java.util.List;

public interface Repository<T> {

    void insert(T t);

    void delete(String id);

    void update(T t);

    List<T> findAll();

    T find(String id);

}
