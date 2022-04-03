package com.example.newsbackend.service.scrape.dynamic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class SeleniumBrowserTest {
    @Mock
    private RemoteWebDriver mockDriver;

    private SeleniumBrowser seleniumBrowserUnderTest;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);


        seleniumBrowserUnderTest = new SeleniumBrowser() {
            @Override
            protected RemoteWebDriver initRemoteWebDriver(String binaryPath, String driverPath) {
                return null;
            }
        };
    }

    @Test
    void when_Init_Should_InitializeRemoteWebDriver() {
        // Given
        SeleniumBrowser spySeleniumBrowser = spy(seleniumBrowserUnderTest);

        final EmuBrowserConfiguration configuration = new EmuBrowserConfiguration("browserPath", "browserName",
                "browserVersion", "browserDriver");

        // When
        when(spySeleniumBrowser.initRemoteWebDriver("browserPath", "browserDriver")).thenReturn(mockDriver);
        // Then
        spySeleniumBrowser.init(configuration);

        // Verify
        verify(spySeleniumBrowser, times(1)).initRemoteWebDriver("browserPath", "browserDriver");
    }

    @Test
    void when_Reinit_Should_Reinitialize_Driver() {


        //Given
        SeleniumBrowser spySeleniumBrowser = spy(seleniumBrowserUnderTest);

        // When
        doReturn(true).when(spySeleniumBrowser).isInitialized();
        doNothing().when(spySeleniumBrowser).init(any());
        //Then
        spySeleniumBrowser.reinit();

        // Verify
        verify(spySeleniumBrowser, times(1)).init(any());
    }
    @Test
    void when_Reinitialize_Driver_If_Is_Not_Initialized_Should_Throw_IllegalStateException() {


        //Given
        SeleniumBrowser spySeleniumBrowser = spy(seleniumBrowserUnderTest);

        // When
        doReturn(false).when(spySeleniumBrowser).isInitialized();
        //Then
        assertThatThrownBy(() -> spySeleniumBrowser.reinit()).isInstanceOf(IllegalStateException.class);

        // Verify
        verify(spySeleniumBrowser, never()).init(any());
    }
    @Test
    void when_IsInitialized_Should_Return_True() {

        // When
        when(mockDriver.toString()).thenReturn("mockDriver.toString()");
        ReflectionTestUtils.setField(seleniumBrowserUnderTest, "driver", mockDriver);

        // Then
        final boolean result = seleniumBrowserUnderTest.isInitialized();

        // Verify the results
        assertThat(result).isTrue();
    }
    @Test
    void when_IsInitialized_Should_Return_False() {

        // When
        when(mockDriver.toString()).thenReturn("(null)");
        ReflectionTestUtils.setField(seleniumBrowserUnderTest, "driver", mockDriver);

        // Then
        final boolean result = seleniumBrowserUnderTest.isInitialized();

        // Verify the results
        assertThat(result).isFalse();
    }
    @Test
    void when_Navigate_Should_DriverNavigateTo_Given_Url() {
        //Given
        WebDriver.Navigation mockNavigation = mock(WebDriver.Navigation.class);

        //When
        ReflectionTestUtils.setField(seleniumBrowserUnderTest, "driver", mockDriver);
        when(mockDriver.navigate()).thenReturn(mockNavigation);
        doNothing().when(mockNavigation).to(anyString());

        //Then
        seleniumBrowserUnderTest.navigate("url");

        // Verify
        verify(mockNavigation, times(1)).to("url");
    }
    @Test
    void when_Navigate_If_Is_Not_Initialized_Should_Throw_IllegalStateException() {
        //Given
        SeleniumBrowser spySeleniumBrowser = spy(seleniumBrowserUnderTest);
        WebDriver.Navigation mockNavigation = mock(WebDriver.Navigation.class);

        //When
        ReflectionTestUtils.setField(seleniumBrowserUnderTest, "driver", mockDriver);
        when(mockDriver.navigate()).thenReturn(mockNavigation);
        doReturn(false).when(spySeleniumBrowser).isInitialized();

        //Then
        assertThatThrownBy(() -> spySeleniumBrowser.navigate(anyString())).isInstanceOf(IllegalStateException.class);

        // Verify
        verify(mockNavigation,never()).to("url");
    }
    @Test
    void when_RetrieveContent_If_Driver_Is_Not_Initialized_Should_Throw_IllegalStateException() {
        //Given
        SeleniumBrowser spySeleniumBrowser = spy(seleniumBrowserUnderTest);

        // When
        ReflectionTestUtils.setField(seleniumBrowserUnderTest, "driver", mockDriver);
        doReturn(false).when(spySeleniumBrowser).isInitialized();
        //Then
        assertThatThrownBy(() -> spySeleniumBrowser.retrieveContent()).isInstanceOf(IllegalStateException.class);

        // Verify
        verify(mockDriver, never()).getPageSource();

    }
    @Test
    void when_RetrieveContent_Should_Return_PageSource() {
        //Given
        final String expectedContent = "expectedContent";
        SeleniumBrowser spySeleniumBrowser = spy(seleniumBrowserUnderTest);

        //When
        ReflectionTestUtils.setField(seleniumBrowserUnderTest, "driver", mockDriver);
        when(mockDriver.getPageSource()).thenReturn(expectedContent);
        doReturn(true).when(spySeleniumBrowser).isInitialized();

        final String result = seleniumBrowserUnderTest.retrieveContent();

        // Verify the results
        assertThat(result).isEqualTo(expectedContent);
        verify(mockDriver, times(1)).getPageSource();

    }
    @Test
    void when_Close_Should_WebDriver_Quit() {
        //Given
        SeleniumBrowser spySeleniumBrowser = spy(seleniumBrowserUnderTest);

        //When
        ReflectionTestUtils.setField(seleniumBrowserUnderTest, "driver", mockDriver);
        doNothing().when(mockDriver).quit();
        doReturn(true).when(spySeleniumBrowser).isInitialized();

        seleniumBrowserUnderTest.close();

        // Verify the results
        verify(mockDriver, times(1)).quit();
    }

    @Test
    void when_Destroy_Should_WebDriver_Close() {
        //Given
        SeleniumBrowser spySeleniumBrowser = spy(seleniumBrowserUnderTest);

        //When
        ReflectionTestUtils.setField(seleniumBrowserUnderTest, "driver", mockDriver);
        doNothing().when(mockDriver).close();
        doReturn(true).when(spySeleniumBrowser).isInitialized();

        seleniumBrowserUnderTest.destroy();

        // Verify the results
        verify(mockDriver, times(1)).close();
    }
}
