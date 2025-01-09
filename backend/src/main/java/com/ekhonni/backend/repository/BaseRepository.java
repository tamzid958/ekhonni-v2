package com.ekhonni.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Author: Md Jahid Hasan
 * Date: 12/8/24
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    /**
     * =============================
     * Soft Delete Operations
     * =============================
     */
    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deletedAt=CURRENT_TIMESTAMP() WHERE e.id = :id")
    void softDelete(ID id);

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deletedAt=CURRENT_TIMESTAMP() WHERE e.id IN :ids")
    void softDeleteSelected(List<ID> ids);

    /**
     * =============================
     * Restore Operations
     * =============================
     */
    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deletedAt=NULL WHERE e.id = :id")
    void restore(ID id);

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deletedAt=NULL WHERE e.id IN :ids")
    void restoreSelected(List<ID> ids);

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.deletedAt=NULL WHERE e.deletedAt IS NOT NULL")
    void restoreAll();

    /**
     * ============================================
     * Non soft deleted (Default)
     * ============================================
     */
    // Entity
    Optional<T> findByIdAndDeletedAtIsNull(ID id);
    List<T> findAllByDeletedAtIsNull();
    Page<T> findAllByDeletedAtIsNull(Pageable pageable);

    // Projection
    <P> List<P> findAllByDeletedAtIsNull(Class<P> projection);
    <P> Optional<P> findByIdAndDeletedAtIsNull(ID id, Class<P> projection);
    <P> Page<P> findAllByDeletedAtIsNull(Class<P> projection, Pageable pageable);

    // Helper
    long countByDeletedAtIsNull();

    /**
     * ========================================
     * Soft deleted
     * ========================================
     */
    // Entity
    Optional<T> findByIdAndDeletedAtIsNotNull(ID id);
    List<T> findAllByDeletedAtIsNotNull();
    Page<T> findAllByDeletedAtIsNotNull(Pageable pageable);

    // Projection
    <P> List<P> findAllByDeletedAtIsNotNull(Class<P> projection);
    <P> Optional<P> findByIdAndDeletedAtIsNotNull(ID id, Class<P> projection);
    <P> Page<P> findAllByDeletedAtIsNotNull(Class<P> projection, Pageable pageable);

    // Helper
    long countByDeletedAtIsNotNull();

    /**
     * ===============================================
     * All including soft-deleted
     * ===============================================
     * Methods for entity is provided by JpaRepository
     */
    <P> Optional<P> findById(ID id, Class<P> projection);
    <P> List<P> findBy(Class<P> projection);
    <P> Page<P> findBy(Class<P> projection, Pageable pageable);

}
