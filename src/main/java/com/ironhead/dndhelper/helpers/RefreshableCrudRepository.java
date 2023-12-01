package com.ironhead.dndhelper.helpers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

@NoRepositoryBean
public interface RefreshableCrudRepository<T, ID> extends CrudRepository<T, ID> {
  void refresh(T t);
  void refresh(Collection<T> c);
  void flush();
}
