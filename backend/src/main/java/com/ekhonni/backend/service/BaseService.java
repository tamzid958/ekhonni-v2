package com.ekhonni.backend.service;

import com.ekhonni.backend.exception.EntityNotFoundException;
import com.ekhonni.backend.repository.BaseRepository;
import org.springframework.beans.BeanUtils;
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
     * =======================================
     * By default return non soft deleted data
     * =======================================
     */
    public T get(ID id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException(getEntityName()));
    }

    public List<T> getAll() {
        return repository.findAllByDeletedAtIsNull();
    }

    public <P> P get(ID id, Class<P> projection) {
        return repository.findByIdAndDeletedAtIsNull(id, projection)
                .orElseThrow(() -> new EntityNotFoundException(getEntityName()));
    }

    public <P> List<P> getAll(Class<P> projection) {
        return repository.findAllByDeletedAtIsNull(projection);
    }

    /**
     * =================
     * Soft deleted data
     * =================
     */
    public T getDeleted(ID id) {
        return repository.findByIdAndDeletedAtIsNotNull(id)
                .orElseThrow(() -> new EntityNotFoundException(getEntityName()));
    }

    public List<T> getAllDeleted() {
        return repository.findAllByDeletedAtIsNotNull();
    }

    public <P> P getDeleted(ID id, Class<P> projection) {
        return repository.findByIdAndDeletedAtIsNotNull(id, projection)
                .orElseThrow(() -> new EntityNotFoundException(getEntityName()));

    }

    public <P> List<P> getAllDeleted(Class<P> projection) {
        return repository.findAllByDeletedAtIsNotNull(projection);
    }

    /**
     * ===============================
     * All including soft deleted data
     * ===============================
     */
    public T getIncludingDeleted(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getEntityName()));
    }

    public List<T> getAllIncludingDeleted() {
        return repository.findAll();
    }

    public <P> P getIncludingDeleted(ID id, Class<P> projection) {
        return repository.findById(id, projection)
                .orElseThrow(() -> new EntityNotFoundException(getEntityName()));

    }

    public <P> List<P> getAllIncludingDeleted(Class<P> projection) {
        return repository.findBy(projection);
    }

    /**
     * ======
     * Delete
     * ======
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
     * ======
     * Update
     * ======
     */
    @Modifying
    @Transactional
    public <D> D update(ID id, D dto) {
        T entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getEntityName()));
        BeanUtils.copyProperties(dto, entity);
        repository.save(entity);
        return dto;
    }

    private String getEntityName() {
        return repository.getClass().getGenericSuperclass().getTypeName()
                .split("<")[1].split(">")[0];
    }
}
