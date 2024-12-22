package com.ekhonni.backend;
import com.ekhonni.backend.exception.EntityNotFoundException;
import com.ekhonni.backend.repository.BaseRepository;
import com.ekhonni.backend.service.BaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseServiceTest {

    @Mock
    private BaseRepository<TestEntity, Long> repository;

    @InjectMocks
    private BaseService<TestEntity, Long> baseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGet_WhenEntityExists_ShouldReturnEntity() {
        // Arrange
        TestEntity entity = new TestEntity(1L, "Test Name");
        when(repository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(entity));

        // Act
        TestEntity result = baseService.get(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).findByIdAndDeletedAtIsNull(1L);
    }

    @Test
    void testGet_WhenEntityDoesNotExist_ShouldThrowException() {
        // Arrange
        when(repository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> baseService.get(1L));
        verify(repository, times(1)).findByIdAndDeletedAtIsNull(1L);
    }

    @Test
    void testGetAll_ShouldReturnNonDeletedEntities() {
        // Arrange
        List<TestEntity> entities = Arrays.asList(new TestEntity(1L, "Test 1"), new TestEntity(2L, "Test 2"));
        when(repository.findAllByDeletedAtIsNull()).thenReturn(entities);

        // Act
        List<TestEntity> result = baseService.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAllByDeletedAtIsNull();
    }

    @Test
    void testGetAll_WithPagination_ShouldReturnPagedResults() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 2);
        List<TestEntity> entities = Arrays.asList(new TestEntity(1L, "Test 1"), new TestEntity(2L, "Test 2"));
        Page<TestEntity> page = new PageImpl<>(entities, pageable, entities.size());
        when(repository.findAllByDeletedAtIsNull(pageable)).thenReturn(page);

        // Act
        Page<TestEntity> result = baseService.getAll(pageable);

        // Assert
        assertEquals(2, result.getContent().size());
        verify(repository, times(1)).findAllByDeletedAtIsNull(pageable);
    }

    @Test
    void testSoftDelete_ShouldCallRepositorySoftDeletePermanently() {
        // Act
        baseService.softDelete(1L);

        // Assert
        verify(repository, times(1)).softDelete(1L);
    }

    @Test
    void testRestore_ShouldCallRepositoryRestore() {
        // Act
        baseService.restore(1L);

        // Assert
        verify(repository, times(1)).restore(1L);
    }

    @Test
    void testUpdate_ShouldUpdateAndReturnDto() {
        // Arrange
        TestEntity existingEntity = new TestEntity(1L, "Old Name");
        TestDto updateDto = new TestDto("New Name");

        when(repository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(TestEntity.class))).thenReturn(existingEntity);

        // Act
        TestDto result = baseService.update(1L, updateDto);

        // Assert
        assertEquals("New Name", result.getName());
        verify(repository, times(1)).save(existingEntity);
    }
}

