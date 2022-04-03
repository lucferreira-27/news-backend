package com.example.newsbackend.service.scrape.dynamic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class FirefoxSeleniumBrowserTest {

    private FirefoxSeleniumBrowser firefoxSeleniumBrowserUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        firefoxSeleniumBrowserUnderTest = new FirefoxSeleniumBrowser();
    }


    @Test
    void when_InitRemoteWebDriver_Should_CreateFirefoxDriver(){
        //Given
        FirefoxSeleniumBrowser spyFirefoxSeleniumBrowser = spy(firefoxSeleniumBrowserUnderTest);
        FirefoxDriver mockFirefoxDriver = mock(FirefoxDriver.class);

        //When
        doReturn(mockFirefoxDriver)
                .when(spyFirefoxSeleniumBrowser)
                .createFirefoxDriver(any(FirefoxOptions.class));


        //Then
        spyFirefoxSeleniumBrowser.initRemoteWebDriver("binary", "executable");

        //Verify
        verify(spyFirefoxSeleniumBrowser, times(1)).createFirefoxDriver(any(FirefoxOptions.class));

    }


}
