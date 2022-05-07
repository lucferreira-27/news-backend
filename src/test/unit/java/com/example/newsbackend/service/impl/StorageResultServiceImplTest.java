package com.example.newsbackend.service.impl;

import com.example.newsbackend.entity.search.StorageResult;
import com.example.newsbackend.repository.storage.StorageResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StorageResultServiceImplTest {

    private StorageResultServiceImpl storageResultResultService;

    @Mock
    private StorageResultRepository mockStorageResultRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        storageResultResultService = new StorageResultServiceImpl(mockStorageResultRepository);
    }

    @Test
    void when_FindById_Should_Return_StorageResult_With_Given_Id() {
        //Given
        final StorageResult storageResult = new StorageResult();
        storageResult.setId(1L);
        //When
        when(mockStorageResultRepository.findByIdAndSearchHistoryId(1L,1L)).thenReturn(Optional.of(storageResult));
        //Then
        StorageResult result = storageResultResultService.findById(1L,1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(mockStorageResultRepository,times(1)).findByIdAndSearchHistoryId(1L,1L);

    }
    @Test
    void when_FindAll_Should_Return_List_Of_StorageResult() {
        //Given
        final List<StorageResult> storageResults = List.of(new StorageResult(),new StorageResult(),new StorageResult());
        //When
        when(mockStorageResultRepository.findAll()).thenReturn(storageResults);
        //Then
        List<StorageResult> result = storageResultResultService.findAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(storageResults.size());
        verify(mockStorageResultRepository,times(1)).findAll();
    }

    @Test
    void findBySearchHistoryId() {
        //Given
        final List<StorageResult> storageResults = List.of(new StorageResult(),new StorageResult(),new StorageResult());
        //When
        when(mockStorageResultRepository.findAllBySearchHistoryId(1L)).thenReturn(storageResults);
        //Then
        List<StorageResult> result = storageResultResultService.findBySearchHistoryId(1L);
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(storageResults.size());
        verify(mockStorageResultRepository,times(1)).findAllBySearchHistoryId(1L);
    }

    @Test
    void when_DeleteById_Should_Delete_StorageResult_With_Given_Id_And_SearchHistoryId() {
        //Given
        final StorageResult storageResult = new StorageResult();
        storageResult.setId(1L);
        //When
        when(mockStorageResultRepository.findByIdAndSearchHistoryId(1L,1L))
                .thenReturn(Optional.of(storageResult));

        doNothing().when(mockStorageResultRepository).delete(storageResult);
        //Then
        storageResultResultService.deleteById(1L,1L);
        verify(mockStorageResultRepository,times(1)).findByIdAndSearchHistoryId(1L,1L);
        verify(mockStorageResultRepository,times(1)).delete(storageResult);
    }

    @Test
    void when_DeleteById_Should_Delete_StorageResult_With_Given_SearchHistoryId() {
        //Given
        final StorageResult storageResult = new StorageResult();
        storageResult.setId(1L);
        //When
        when(mockStorageResultRepository.findAllBySearchHistoryId(1L))
                .thenReturn(List.of(storageResult));
        doNothing().when(mockStorageResultRepository).deleteAll(List.of(storageResult));

        //Then
        storageResultResultService.deleteAllBySearchHistoryId(1L);
        verify(mockStorageResultRepository,times(1)).findAllBySearchHistoryId(1L);
        verify(mockStorageResultRepository,times(1)).deleteAll(List.of(storageResult));

    }
}