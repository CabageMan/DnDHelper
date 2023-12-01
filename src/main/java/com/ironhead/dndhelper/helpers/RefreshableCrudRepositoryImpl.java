package com.ironhead.dndhelper.helpers;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@NoRepositoryBean
public class RefreshableCrudRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements RefreshableCrudRepository<T, ID> {

  private final EntityManager entityManager;

  public RefreshableCrudRepositoryImpl(
          JpaEntityInformation entityInformation,
          EntityManager entityManager
  ) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public void refresh(T t) {
    entityManager.refresh(t);
  }

  @Override
  @Transactional
  public void refresh(Collection<T> c) {
    for (T t: c) {
      entityManager.refresh(t);
    }
  }

  @Override
  @Transactional
  public void flush() {
    entityManager.flush();
  }
}
