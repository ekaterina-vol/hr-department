package com.github.ekaterina_vol.hr_department.infrastructure.services;

import java.util.List;
import java.util.Optional;

public interface Service<T, ID> {
    T create(final T entity);

    T findById(final ID id);

    List<T> findAll();

    T update(final T entity);

    boolean deleteById(final ID id);
}
