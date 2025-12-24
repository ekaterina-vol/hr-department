package com.github.ekaterina_vol.hr_department.domain.repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    Optional<T> create(final T entity);

    Optional<T> findById(final ID id);

    List<T> findAll();

    Optional<T> update(final T entity);

    boolean deleteById(final ID id);
}
