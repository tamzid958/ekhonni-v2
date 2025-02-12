package com.ekhonni.backend.service;

import com.ekhonni.backend.exception.EntityNotFoundException;
import com.ekhonni.backend.repository.BaseRepository;
import com.ekhonni.backend.util.BeanUtilHelper;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Author: Asif Iqbal
 * Date: 12/16/24
 */


@NoArgsConstructor
public abstract class BaseService<T, ID> {

    private BaseRepository<T, ID> repository;

    public BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    /**
     * ==================================================
     * Non soft-deleted (Default)
     * ==================================================
     * Entity, Projection, Pageable Entity and Projection
     */
    public Optional<T> get(ID id) {
        return repository.findByIdAndDeletedAtIsNull(id);
    }

    public <P> P get(ID id, Class<P> projection) {
        return repository.findByIdAndDeletedAtIsNull(id, projection)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<T> getAll() {
        return repository.findAllByDeletedAtIsNull();
    }

    public <P> List<P> getAll(Class<P> projection) {
        return repository.findAllByDeletedAtIsNull(projection);
    }

    public Page<T> getAll(Pageable pageable) {
        return repository.findAllByDeletedAtIsNull(pageable);
    }

    public <P> Page<P> getAll(Class<P> projection, Pageable pageable) {
        return repository.findAllByDeletedAtIsNull(projection, pageable);
    }


    /**
     * =========================
     *        Soft deleted
     * =========================
     */
    public T getDeleted(ID id) {
        return repository.findByIdAndDeletedAtIsNotNull(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public <P> P getDeleted(ID id, Class<P> projection) {
        return repository.findByIdAndDeletedAtIsNotNull(id, projection)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<T> getAllDeleted() {
        return repository.findAllByDeletedAtIsNotNull();
    }

    public <P> List<P> getAllDeleted(Class<P> projection) {
        return repository.findAllByDeletedAtIsNotNull(projection);
    }

    public Page<T> getAllDeleted(Pageable pageable) {
        return repository.findAllByDeletedAtIsNotNull(pageable);
    }

    public <P> Page<P> getAllDeleted(Class<P> projection, Pageable pageable) {
        return repository.findAllByDeletedAtIsNotNull(projection, pageable);
    }

    /**
     * ==================================
     * All including soft deleted
     * ==================================
     */
    public T getIncludingDeleted(ID id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public <P> P getIncludingDeleted(ID id, Class<P> projection) {
        return repository.findById(id, projection)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<T> getAllIncludingDeleted() {
        return repository.findAll();
    }

    public <P> List<P> getAllIncludingDeleted(Class<P> projection) {
        return repository.findBy(projection);
    }

    public Page<T> getAllIncludingDeleted(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public <P> Page<P> getAllIncludingDeleted(Class<P> projection, Pageable pageable) {
        return repository.findBy(projection, pageable);
    }

    /**
     * ==========================
     * Soft Delete
     * ==========================
     */
    @Transactional
    public void softDelete(ID id) {
        repository.softDelete(id);
    }

    @Transactional
    public void softDelete(List<ID> ids) {
        repository.softDeleteSelected(ids);
    }

    @Transactional
    public void deletePermanently(ID id) {
        repository.deleteById(id);
    }

    /**
     * =============================================
     * Restore soft deleted data
     * =============================================
     * Non parameterized method is for restoring all
     */
    @Transactional
    public void restore(ID id) {
        repository.restore(id);
    }

    @Transactional
    public void restore(List<ID> ids) {
        repository.restoreSelected(ids);
    }

    @Transactional
    public void restoreAll() {
        repository.restoreAll();
    }

    /**
     * =====================
     * Update
     * =====================
     */
    @Transactional
    public <D> D update(ID id, D dto) {
        T entity = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        BeanUtils.copyProperties(dto, entity, BeanUtilHelper.getBlankPropertyNames(dto));
        return dto;
    }

}
