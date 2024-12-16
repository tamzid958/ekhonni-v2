package com.ekhonni.backend.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Author: Md Jahid Hasan
 * Date: 12/8/24
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deletedAt=CURRENT_TIMESTAMP() WHERE e.id = :id")
    void softDelete(ID id);

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deletedAt=CURRENT_TIMESTAMP() WHERE e.id IN :ids")
    void softDelete(List<ID> ids);

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deletedAt=NULL WHERE e.id = :id")
    void restore(ID id);

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deletedAt=NULL WHERE e.id IN :ids")
    void restore(List<ID> ids);

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deletedAt=NULL WHERE e.deletedAt IS NOT NULL")
    void restore();

    // Soft-deleted and non soft-deleted
    <P> Optional<P> findById(ID id, Class<P> projection);
    <P> List<P> findBy(Class<P> projection);

    // Non soft-deleted
    Optional<T> findByIdAndDeletedAtIsNull(ID id);
    List<T> findAllByDeletedAtIsNull();
    <P> List<P> findAllByDeletedAtIsNull(Class<P> projection);
    <P> Optional<P> findByIdAndDeletedAtIsNull(ID id, Class<P> projection);
    long countByDeletedAtIsNull();

    // Soft-deleted
    Optional<T> findByIdAndDeletedAtIsNotNull(ID id);
    List<T> findAllByDeletedAtIsNotNull();
    <P> List<P> findAllByDeletedAtIsNotNull(Class<P> projection);
    <P> Optional<P> findByIdAndDeletedAtIsNotNull(ID id, Class<P> projection);
    long countByDeletedAtIsNotNull();

}
