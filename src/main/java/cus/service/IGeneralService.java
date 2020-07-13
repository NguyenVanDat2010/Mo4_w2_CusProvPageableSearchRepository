package cus.service;

import java.util.List;

public interface IGeneralService<T> {
    Iterable<T> findAll();

    T findById(Long id);

    void save(T model);

    void remove(Long id);
}
