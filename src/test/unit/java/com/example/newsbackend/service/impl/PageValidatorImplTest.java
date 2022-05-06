package com.example.newsbackend.service;

import com.example.newsbackend.exception.PageValidatorException;
import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.repository.sites.RegisteredSiteRepository;
import com.example.newsbackend.service.impl.PageValidatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class PageValidatorImplTest {

    private PageValidatorServiceImpl pageValidatorServiceImplUnderTest;
    @Mock
    private RegisteredSiteRepository mockRegisteredSiteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pageValidatorServiceImplUnderTest = new PageValidatorServiceImpl(mockRegisteredSiteRepository);
    }

    @Test
    void when_Validate_Page_Should_Return_RegisteredSite() throws PageValidatorException {
        // Given
        final RegisteredSite expectResult = new RegisteredSite();
        final String testUrl = "https://www.google.com/?query=test";
        final String validateTestUrl = "https://www.google.com";

        // When
        when(mockRegisteredSiteRepository.findByUrlContaining(validateTestUrl)).thenReturn(List.of(expectResult));

        // Then
        final RegisteredSite result = pageValidatorServiceImplUnderTest.validatePage(testUrl);

        // Verify
        assertThat(result).isEqualTo(expectResult);
        verify(mockRegisteredSiteRepository, times(1)).findByUrlContaining(validateTestUrl);

    }


    @Test
    void if_RegisteredSite_Is_Not_Found_Should_Throw_PageValidatorException() {

        // Given
        final String testUrl = "https://www.google.com/?query=test";
        final String validateTestUrl = "https://www.google.com";

        // When
        when(mockRegisteredSiteRepository.findByUrl(testUrl)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> pageValidatorServiceImplUnderTest.validatePage(testUrl))
                .isInstanceOf(PageValidatorException.class)
                .hasMessage("No registered site found for url: " + testUrl);
        //Verify
        verify(mockRegisteredSiteRepository, times(1)).findByUrlContaining(validateTestUrl);
        verifyNoMoreInteractions(mockRegisteredSiteRepository);


    }

}
