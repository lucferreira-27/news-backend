package com.example.newsbackend.service;

import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.repository.sites.RegisteredSiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class PageValidatorTest {

    private PageValidator pageValidatorUnderTest;
    @Mock
    private RegisteredSiteRepository mockRegisteredSiteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pageValidatorUnderTest = new PageValidator(mockRegisteredSiteRepository);
    }

    @Test
    void when_Validate_Page_Should_Return_RegisteredSite() throws PageValidatorException {
        // Given
        final RegisteredSite expectResult = new RegisteredSite();
        final String testUrl = "https://www.google.com/search?q=sql+list+all+tables&client=firefox-b-d&sxsrf=APq-WBu1o9h-yRkEW4jnbCV4076rF1yEDA%3A1650325411746&ei=o_ddYtmSLc2J4dUP37aH2AU&oq=sql+list+all&gs_lcp=Cgdnd3Mtd2l6EAMYADIFCAAQywEyBQgAEIAEMgUIABDLATIFCAAQywEyBQgAEMsBMgUIABDLATIFCAAQywEyBQgAEMsBMgUIABDLATIFCAAQywE6BwgAEEcQsAM6BwgAELADEEM6BAgjECc6BAgAEEM6CAguEIAEELEDOggIABCABBCxAzoICC4QsQMQgwE6BwgjEOoCECc6CwguEIAEELEDENQCOgcIABCxAxBDOgcIIxCxAhAnOgcIABAKEMsBOgcIABCxAxAKSgQIQRgASgQIRhgAUJMJWOglYOgpaAVwAXgAgAHMAYgB-g2SAQYwLjEyLjGYAQCgAQGwAQrIAQrAAQE&sclient=gws-wiz";

        // When
        when(mockRegisteredSiteRepository.findByUrl(testUrl)).thenReturn(List.of(expectResult));

        // Then
        final RegisteredSite result = pageValidatorUnderTest.validatePage(testUrl);

        // Verify
        assertThat(result).isEqualTo(expectResult);
        verify(mockRegisteredSiteRepository, times(1)).findByUrl(testUrl);

    }


    @Test
    void if_RegisteredSite_Is_Not_Found_Should_Throw_PageValidatorException() {

        // Given
        final String testUrl = "https://www.google.com";

        // When
        when(mockRegisteredSiteRepository.findByUrl(testUrl)).thenReturn(Collections.emptyList());

        // Then
        assertThatThrownBy(() -> pageValidatorUnderTest.validatePage(testUrl))
                .isInstanceOf(PageValidatorException.class)
                .hasMessage("No registered site found for url: " + testUrl);
        //Verify
        verify(mockRegisteredSiteRepository, times(1)).findByUrl(testUrl);
        verifyNoMoreInteractions(mockRegisteredSiteRepository);


    }

}
