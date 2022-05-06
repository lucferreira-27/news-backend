package com.example.newsbackend.service.impl.scrape.dynamic;

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

class AbstractSeleniumBrowserTest {
    @Mock
    private RemoteWebDriver mockDriver;

    private AbstractSeleniumBrowser abstractSeleniumBrowserUnderTest;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);


        abstractSeleniumBrowserUnderTest = new AbstractSeleniumBrowser() {
            @Override
            protected RemoteWebDriver initRemoteWebDriver(String binaryPath, String driverPath) {
                return null;
            }
        };
    }

    @Test
    void when_Init_Should_InitializeRemoteWebDriver() {
        // Given
        AbstractSeleniumBrowser spyAbstractSeleniumBrowser = spy(abstractSeleniumBrowserUnderTest);

        final EmuBrowserConfiguration configuration = new EmuBrowserConfiguration("browserPath", "browserName",
                "browserVersion", "browserDriver");

        // When
        when(spyAbstractSeleniumBrowser.initRemoteWebDriver("browserPath", "browserDriver")).thenReturn(mockDriver);
        // Then
        spyAbstractSeleniumBrowser.init(configuration);

        // Verify
        verify(spyAbstractSeleniumBrowser, times(1)).initRemoteWebDriver("browserPath", "browserDriver");
    }

    @Test
    void when_Reinit_Should_Reinitialize_Driver() {


        //Given
        AbstractSeleniumBrowser spyAbstractSeleniumBrowser = spy(abstractSeleniumBrowserUnderTest);

        // When
        doReturn(true).when(spyAbstractSeleniumBrowser).isInitialized();
        doNothing().when(spyAbstractSeleniumBrowser).init(any());
        //Then
        spyAbstractSeleniumBrowser.reinit();

        // Verify
        verify(spyAbstractSeleniumBrowser, times(1)).init(any());
    }
    @Test
    void when_Reinitialize_Driver_If_Is_Not_Initialized_Should_Throw_IllegalStateException() {


        //Given
        AbstractSeleniumBrowser spyAbstractSeleniumBrowser = spy(abstractSeleniumBrowserUnderTest);

        // When
        doReturn(false).when(spyAbstractSeleniumBrowser).isInitialized();
        //Then
        assertThatThrownBy(() -> spyAbstractSeleniumBrowser.reinit()).isInstanceOf(IllegalStateException.class);

        // Verify
        verify(spyAbstractSeleniumBrowser, never()).init(any());
    }
    @Test
    void when_IsInitialized_Should_Return_True() {

        // When
        when(mockDriver.toString()).thenReturn("mockDriver.toString()");
        ReflectionTestUtils.setField(abstractSeleniumBrowserUnderTest, "driver", mockDriver);

        // Then
        final boolean result = abstractSeleniumBrowserUnderTest.isInitialized();

        // Verify the results
        assertThat(result).isTrue();
    }
    @Test
    void when_IsInitialized_Should_Return_False() {

        // When
        when(mockDriver.toString()).thenReturn("(null)");
        ReflectionTestUtils.setField(abstractSeleniumBrowserUnderTest, "driver", mockDriver);

        // Then
        final boolean result = abstractSeleniumBrowserUnderTest.isInitialized();

        // Verify the results
        assertThat(result).isFalse();
    }
    @Test
    void when_Navigate_Should_DriverNavigateTo_Given_Url() {
        //Given
        WebDriver.Navigation mockNavigation = mock(WebDriver.Navigation.class);

        //When
        ReflectionTestUtils.setField(abstractSeleniumBrowserUnderTest, "driver", mockDriver);
        when(mockDriver.navigate()).thenReturn(mockNavigation);
        doNothing().when(mockNavigation).to(anyString());

        //Then
        abstractSeleniumBrowserUnderTest.navigate("url");

        // Verify
        verify(mockNavigation, times(1)).to("url");
    }
    @Test
    void when_Navigate_If_Is_Not_Initialized_Should_Throw_IllegalStateException() {
        //Given
        AbstractSeleniumBrowser spyAbstractSeleniumBrowser = spy(abstractSeleniumBrowserUnderTest);
        WebDriver.Navigation mockNavigation = mock(WebDriver.Navigation.class);

        //When
        ReflectionTestUtils.setField(abstractSeleniumBrowserUnderTest, "driver", mockDriver);
        when(mockDriver.navigate()).thenReturn(mockNavigation);
        doReturn(false).when(spyAbstractSeleniumBrowser).isInitialized();

        //Then
        assertThatThrownBy(() -> spyAbstractSeleniumBrowser.navigate(anyString())).isInstanceOf(IllegalStateException.class);

        // Verify
        verify(mockNavigation,never()).to("url");
    }
    @Test
    void when_RetrieveContent_If_Driver_Is_Not_Initialized_Should_Throw_IllegalStateException() {
        //Given
        AbstractSeleniumBrowser spyAbstractSeleniumBrowser = spy(abstractSeleniumBrowserUnderTest);

        // When
        ReflectionTestUtils.setField(abstractSeleniumBrowserUnderTest, "driver", mockDriver);
        doReturn(false).when(spyAbstractSeleniumBrowser).isInitialized();
        //Then
        assertThatThrownBy(() -> spyAbstractSeleniumBrowser.retrieveContent()).isInstanceOf(IllegalStateException.class);

        // Verify
        verify(mockDriver, never()).getPageSource();

    }
    @Test
    void when_RetrieveContent_Should_Return_PageSource() {
        //Given
        final String expectedContent = "expectedContent";
        AbstractSeleniumBrowser spyAbstractSeleniumBrowser = spy(abstractSeleniumBrowserUnderTest);

        //When
        ReflectionTestUtils.setField(abstractSeleniumBrowserUnderTest, "driver", mockDriver);
        when(mockDriver.getPageSource()).thenReturn(expectedContent);
        doReturn(true).when(spyAbstractSeleniumBrowser).isInitialized();

        final String result = abstractSeleniumBrowserUnderTest.retrieveContent();

        // Verify the results
        assertThat(result).isEqualTo(expectedContent);
        verify(mockDriver, times(1)).getPageSource();

    }
    @Test
    void when_Close_Should_WebDriver_Quit() {
        //Given
        AbstractSeleniumBrowser spyAbstractSeleniumBrowser = spy(abstractSeleniumBrowserUnderTest);

        //When
        ReflectionTestUtils.setField(abstractSeleniumBrowserUnderTest, "driver", mockDriver);
        doNothing().when(mockDriver).quit();
        doReturn(true).when(spyAbstractSeleniumBrowser).isInitialized();

        abstractSeleniumBrowserUnderTest.close();

        // Verify the results
        verify(mockDriver, times(1)).quit();
    }

    @Test
    void when_Destroy_Should_WebDriver_Close() {
        //Given
        AbstractSeleniumBrowser spyAbstractSeleniumBrowser = spy(abstractSeleniumBrowserUnderTest);

        //When
        ReflectionTestUtils.setField(abstractSeleniumBrowserUnderTest, "driver", mockDriver);
        doNothing().when(mockDriver).close();
        doReturn(true).when(spyAbstractSeleniumBrowser).isInitialized();

        abstractSeleniumBrowserUnderTest.destroy();

        // Verify the results
        verify(mockDriver, times(1)).close();
    }
}
