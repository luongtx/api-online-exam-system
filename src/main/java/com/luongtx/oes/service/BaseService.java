package com.luongtx.oes.service;

import java.util.List;

public interface BaseService<T> {
    List<T> findAll();

    T findById(Long id);

    T getById(Long id);

    void add(T t);

    T deleteById(Long id);

    void update(T t);
}
