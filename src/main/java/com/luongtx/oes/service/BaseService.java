package com.luongtx.oes.service;

import java.util.List;

public interface BaseService<T> {
    List<T> findAll();
    T findById(Integer id);
    T getById(Integer id);
    void add(T t);
    T deleteById(Integer id);
    void update(T t);
}
