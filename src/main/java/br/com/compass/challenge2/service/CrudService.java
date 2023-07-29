package br.com.compass.challenge2.service;

import java.util.List;

public interface CrudService<T> {

    T save(T entity);

    List<T> findAll();

    T findById(Long id);

    T update(T entity);

    T deleteById(Long id);
}
