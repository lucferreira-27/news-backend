package com.example.newsbackend.service;

import com.example.newsbackend.exception.ResourceAlreadyExistsException;
import com.example.newsbackend.exception.ResourceNotFoundException;
import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.controller.dtos.RegisteredSiteDto;
import com.example.newsbackend.repository.sites.RegisteredSiteRepository;
import com.example.newsbackend.entity.sites.SiteConfiguration;
import com.example.newsbackend.service.impl.RegisteredSitesServiceImpl;
import com.example.newsbackend.util.RegisteredSiteDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
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
    @Spy
    private RegisteredSiteDtoMapper mockRegisteredSiteDtoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registeredSitesServiceUnderTest = new RegisteredSitesServiceImpl(
                mockRegisteredSiteRepository,
                mockRegisteredSiteDtoMapper);
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
        registeredSitesServiceUnderTest.saveSite(registeredSite);

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
        assertThrows(ResourceAlreadyExistsException.class, () -> registeredSitesServiceUnderTest.saveSite(registeredSite));

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
        RegisteredSite result = registeredSitesServiceUnderTest.findSiteById(1L);
        assertThat(result).isEqualTo(registeredSite);

        //Verify
        verify(mockRegisteredSiteRepository, times(1)).findById(1L);
    }

    @Test
    void if_When_FindById_RegisteredSite_Not_Found_Should_Throw_ResourceNotFoundException() {

        //When
        when(mockRegisteredSiteRepository.findById(1L)).thenReturn(Optional.empty());

        //Then
        assertThrows(ResourceNotFoundException.class, () -> registeredSitesServiceUnderTest.findSiteById(1L));

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
        RegisteredSite result = registeredSitesServiceUnderTest.findSiteByUrl(registeredSite.getUrl());

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
        assertThrows(ResourceNotFoundException.class, () -> registeredSitesServiceUnderTest.findSiteByUrl(registeredSite.getUrl()));

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
        List<RegisteredSite> result = registeredSitesServiceUnderTest.findAllSites();

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
        assertThrows(ResourceNotFoundException.class, () -> registeredSitesServiceUnderTest.findAllSites());

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
        registeredSitesServiceUnderTest.deleteSiteById(registeredSite.getId());

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
        assertThrows(ResourceNotFoundException.class, () -> registeredSitesServiceUnderTest.deleteSiteById(registeredSite.getId()));

        //Verify
        verify(mockRegisteredSiteRepository, never()).deleteById(registeredSite.getId());
    }

    @Test
    void when_DeleteAll_Should_Delete_All_RegisteredSites_From_Repository() {

        //When
        doNothing().when(mockRegisteredSiteRepository).deleteAll();

        //Then
        registeredSitesServiceUnderTest.deleteAllSites();

        //Verify
        verify(mockRegisteredSiteRepository, times(1)).deleteAll();
    }

    @Test
    void when_UpdateSite_Should_Update_Site_Given_Id_In_Repository() {
        //Given
        RegisteredSite findRegisteredSite = new RegisteredSite();
        SiteConfiguration siteConfiguration = new SiteConfiguration();
        siteConfiguration.setDescription("before description");
        findRegisteredSite.setSiteConfiguration(siteConfiguration);
        findRegisteredSite.setUrl("beforeUpdate.com");
        findRegisteredSite.setId(1L);
        RegisteredSiteDto registeredSiteDto = new RegisteredSiteDto.Builder()
                .description("after description")
                .url("afterUpdate.com")
                .build();

        //When
        when(mockRegisteredSiteRepository.findById(anyLong())).thenReturn(Optional.of(findRegisteredSite));
        when(mockRegisteredSiteRepository.save(any(RegisteredSite.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(mockRegisteredSiteDtoMapper.updateWithNullAsNoChange(
                any(RegisteredSiteDto.class),
                any(RegisteredSite.class))
        ).thenReturn(RegisteredSite.fromRegisteredSiteDto(registeredSiteDto));


        //Then
        RegisteredSite result = registeredSitesServiceUnderTest.updateSite(1L, registeredSiteDto);

        //Verify
        assertThat(result.getUrl()).isEqualTo(registeredSiteDto.getUrl());
        assertThat(result.getSiteConfiguration().getDescription()).isEqualTo(registeredSiteDto.getDescription());
        verify(mockRegisteredSiteRepository, times(1)).save(any(RegisteredSite.class));
        verify(mockRegisteredSiteRepository, times(1)).findById(anyLong());

    }
}