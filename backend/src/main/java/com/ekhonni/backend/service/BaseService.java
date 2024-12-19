package com.ekhonni.backend.service;

import com.ekhonni.backend.exception.EntityNotFoundException;
import com.ekhonni.backend.repository.BaseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Asif Iqbal
 * Date: 12/16/24
 */

public class BaseService<T, ID> {
    private BaseRepository<T, ID> repository;
    /**
     * ==================================================
     * Non soft-deleted (Default)
     * ==================================================
     * Entity, Projection, Pageable Entity and Projection
     */
    public T get(ID id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(EntityNotFoundException::new);
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
     * Soft deleted
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
     * ============================
     * All including soft deleted
     * ============================
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
    public void softDelete(ID id) {
        repository.softDelete(id);
    }

    public void softDelete(List<ID> ids) {
        repository.softDelete(ids);
    }

    public void delete(ID id) {     // delete permanently
        repository.deleteById(id);
    }

    /**
     * =============================================
     * Restore soft deleted data
     * =============================================
     * Non parameterized method is for restoring all
     */
    public void restore(ID id) {
        repository.restore(id);
    }

    public void restore(List<ID> ids) {
        repository.restore(ids);
    }

    public void restore() {
        repository.restore();
    }

    /**
     * =====================
     * Update
     * =====================
     */
    @Modifying
    @Transactional
    public <D> D update(ID id, D dto) {
        T entity = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        BeanUtils.copyProperties(dto, entity);
        repository.save(entity);
        return dto;
    }

}
