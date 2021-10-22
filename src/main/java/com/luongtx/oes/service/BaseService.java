package com.luongtx.oes.service;

import java.util.List;

public interface BaseService<T> {
    List<T> findAll();

    T findById(Long id);

    void add(T t);

    void update(T t);
}
