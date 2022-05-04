package com.example.newsbackend.service;

import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.repository.sites.RegisteredSiteRepository;
import com.example.newsbackend.repository.storage.SearchHistory;
import com.example.newsbackend.repository.storage.StorageResult;
import com.example.newsbackend.service.serp.NewsResultPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisteredSitesServiceTest {
    private RegisteredSitesService registeredSitesServiceUnderTest;
    @Mock
    private RegisteredSiteRepository mockRegisteredSiteRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registeredSitesServiceUnderTest = new RegisteredSitesServiceImpl(mockRegisteredSiteRepository);
    }

    @Test
    void when_Save_Should_Save_RegisteredSite_In_Repository() {
        //Given
        RegisteredSite registeredSite = new RegisteredSite();
        registeredSite.setUrl("http://www.example.com");

        //When
        when(mockRegisteredSiteRepository.existsByUrl(registeredSite.getUrl())).thenReturn(false);
        when(mockRegisteredSiteRepository.save(any(RegisteredSite.class))).thenReturn(registeredSite);

        //Then
        registeredSitesServiceUnderTest.save(registeredSite);

        //Then
        verify(mockRegisteredSiteRepository, times(1)).save(registeredSite);
        verify(mockRegisteredSiteRepository, times(1)).existsByUrl(registeredSite.getUrl());

    }
    @Test
    void if_UrlExist_In_Repository_Should_Throw_ResourceAlreadyExistsException() {
        //Given
        RegisteredSite registeredSite = new RegisteredSite();
        registeredSite.setUrl("http://www.example.com");

        //When
        when(mockRegisteredSiteRepository.existsByUrl(registeredSite.getUrl())).thenReturn(true);

        //Then
        assertThrows(ResourceAlreadyExistsException.class, () -> registeredSitesServiceUnderTest.save(registeredSite));

        //Verify
        verify(mockRegisteredSiteRepository, times(1)).existsByUrl(registeredSite.getUrl());
    }



    @Test
    void when_FindById_Should_Return_RegisteredSite_Given_Id() {
        //Given
        RegisteredSite registeredSite = new RegisteredSite();

        //When
        when(mockRegisteredSiteRepository.findById(1L)).thenReturn(Optional.of(registeredSite));

        //Then
        RegisteredSite result = registeredSitesServiceUnderTest.findById(1L);
        assertThat(result).isEqualTo(registeredSite);

        //Verify
        verify(mockRegisteredSiteRepository, times(1)).findById(1L);
    }
    @Test
    void if_When_FindById_RegisteredSite_Not_Found_Should_Throw_ResourceNotFoundException() {

        //When
        when(mockRegisteredSiteRepository.findById(1L)).thenReturn(Optional.empty());

        //Then
        assertThrows(ResourceNotFoundException.class, () -> registeredSitesServiceUnderTest.findById(1L));

        //Verify
        verify(mockRegisteredSiteRepository, times(1)).findById(1L);
    }

    @Test
    void when_FindByUrl_Should_Return_RegisteredSite_From_Repository() {
        //Given
        RegisteredSite registeredSite = new RegisteredSite();
        registeredSite.setUrl("http://www.example.com");

        //When
        when(mockRegisteredSiteRepository.findByUrl(registeredSite.getUrl())).thenReturn(Optional.of(registeredSite));

        //Then
        RegisteredSite result = registeredSitesServiceUnderTest.findByUrl(registeredSite.getUrl());

        //Then
        assertThat(result).isEqualTo(registeredSite);
        verify(mockRegisteredSiteRepository, times(1)).findByUrl(registeredSite.getUrl());
    }

    @Test
    void if_When_FindByUrl_RegisteredSite_Not_Found_Should_Throw_ResourceNotFoundException() {
        //Given
        RegisteredSite registeredSite = new RegisteredSite();
        registeredSite.setUrl("http://www.example.com");

        //When
        when(mockRegisteredSiteRepository.findByUrl(registeredSite.getUrl())).thenReturn(Optional.empty());

        //Then
        assertThrows(ResourceNotFoundException.class, () -> registeredSitesServiceUnderTest.findByUrl(registeredSite.getUrl()));

        //Verify
        verify(mockRegisteredSiteRepository, times(1)).findByUrl(registeredSite.getUrl());
    }
    @Test
    void when_FindAll_Should_Return_All_RegisteredSites_From_Repository() {
        //Given
        RegisteredSite registeredSite = new RegisteredSite();
        List<RegisteredSite> registeredSites = new ArrayList<>();
        registeredSites.add(registeredSite);

        //When
        when(mockRegisteredSiteRepository.findAll()).thenReturn(registeredSites);

        //Then
        List<RegisteredSite> result = registeredSitesServiceUnderTest.findAll();

        //Then
        assertThat(result).isEqualTo(registeredSites);
        verify(mockRegisteredSiteRepository, times(1)).findAll();
    }
    @Test
    void if_When_FindAll_RegisteredSites_Not_Found_Should_Throw_ResourceNotFoundException() {
        //Given
        List<RegisteredSite> registeredSites = new ArrayList<>();

        //When
        when(mockRegisteredSiteRepository.findAll()).thenReturn(registeredSites);

        //Then
        assertThrows(ResourceNotFoundException.class, () -> registeredSitesServiceUnderTest.findAll());

        //Verify
        verify(mockRegisteredSiteRepository, times(1)).findAll();
    }
    @Test
    void when_DeleteById_Should_Delete_RegisteredSite_From_Repository() {
        //Given
        RegisteredSite registeredSite = new RegisteredSite();
        registeredSite.setId(1L);

        //When
        when(mockRegisteredSiteRepository.existsById(registeredSite.getId())).thenReturn(true);
        doNothing().when(mockRegisteredSiteRepository).deleteById(registeredSite.getId());

        //Then
        registeredSitesServiceUnderTest.deleteById(registeredSite.getId());

        //Verify
        verify(mockRegisteredSiteRepository, times(1)).deleteById(registeredSite.getId());
    }
    @Test
    void if_When_DeleteById_RegisteredSite_Not_Found_Should_Throw_ResourceNotFoundException() {
        //Given
        RegisteredSite registeredSite = new RegisteredSite();
        registeredSite.setId(1L);

        //When
        doThrow(ResourceNotFoundException.class).when(mockRegisteredSiteRepository).deleteById(registeredSite.getId());

        //Then
        assertThrows(ResourceNotFoundException.class, () -> registeredSitesServiceUnderTest.deleteById(registeredSite.getId()));

        //Verify
        verify(mockRegisteredSiteRepository, never()).deleteById(registeredSite.getId());
    }

    @Test
    void when_DeleteAll_Should_Delete_All_RegisteredSites_From_Repository() {

        //When
        doNothing().when(mockRegisteredSiteRepository).deleteAll();

        //Then
        registeredSitesServiceUnderTest.deleteAll();

        //Verify
        verify(mockRegisteredSiteRepository, times(1)).deleteAll();
    }
}