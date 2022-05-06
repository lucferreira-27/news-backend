package com.example.newsbackend.service.impl.scrape.dynamic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class FirefoxAbstractSeleniumBrowserTest {

    private FirefoxImplSeleniumBrowser firefoxSeleniumBrowserImplUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        firefoxSeleniumBrowserImplUnderTest = new FirefoxImplSeleniumBrowser();
    }


    @Test
    void when_InitRemoteWebDriver_Should_CreateFirefoxDriver(){
        //Given
        FirefoxImplSeleniumBrowser spyFirefoxSeleniumBrowserImpl = spy(firefoxSeleniumBrowserImplUnderTest);
        FirefoxDriver mockFirefoxDriver = mock(FirefoxDriver.class);

        //When
        doReturn(mockFirefoxDriver)
                .when(spyFirefoxSeleniumBrowserImpl)
                .createFirefoxDriver(any(FirefoxOptions.class));


        //Then
        spyFirefoxSeleniumBrowserImpl.initRemoteWebDriver("binary", "executable");

        //Verify
        verify(spyFirefoxSeleniumBrowserImpl, times(1)).createFirefoxDriver(any(FirefoxOptions.class));

    }


}
