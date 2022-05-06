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

class SeleniumBrowserAbstractTest {
    @Mock
    private RemoteWebDriver mockDriver;

    private SeleniumBrowserAbstract seleniumBrowserAbstractUnderTest;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);


        seleniumBrowserAbstractUnderTest = new SeleniumBrowserAbstract() {
            @Override
            protected RemoteWebDriver initRemoteWebDriver(String binaryPath, String driverPath) {
                return null;
            }
        };
    }

    @Test
    void when_Init_Should_InitializeRemoteWebDriver() {
        // Given
        SeleniumBrowserAbstract spySeleniumBrowserAbstract = spy(seleniumBrowserAbstractUnderTest);

        final EmuBrowserConfiguration configuration = new EmuBrowserConfiguration("browserPath", "browserName",
                "browserVersion", "browserDriver");

        // When
        when(spySeleniumBrowserAbstract.initRemoteWebDriver("browserPath", "browserDriver")).thenReturn(mockDriver);
        // Then
        spySeleniumBrowserAbstract.init(configuration);

        // Verify
        verify(spySeleniumBrowserAbstract, times(1)).initRemoteWebDriver("browserPath", "browserDriver");
    }

    @Test
    void when_Reinit_Should_Reinitialize_Driver() {


        //Given
        SeleniumBrowserAbstract spySeleniumBrowserAbstract = spy(seleniumBrowserAbstractUnderTest);

        // When
        doReturn(true).when(spySeleniumBrowserAbstract).isInitialized();
        doNothing().when(spySeleniumBrowserAbstract).init(any());
        //Then
        spySeleniumBrowserAbstract.reinit();

        // Verify
        verify(spySeleniumBrowserAbstract, times(1)).init(any());
    }
    @Test
    void when_Reinitialize_Driver_If_Is_Not_Initialized_Should_Throw_IllegalStateException() {


        //Given
        SeleniumBrowserAbstract spySeleniumBrowserAbstract = spy(seleniumBrowserAbstractUnderTest);

        // When
        doReturn(false).when(spySeleniumBrowserAbstract).isInitialized();
        //Then
        assertThatThrownBy(() -> spySeleniumBrowserAbstract.reinit()).isInstanceOf(IllegalStateException.class);

        // Verify
        verify(spySeleniumBrowserAbstract, never()).init(any());
    }
    @Test
    void when_IsInitialized_Should_Return_True() {

        // When
        when(mockDriver.toString()).thenReturn("mockDriver.toString()");
        ReflectionTestUtils.setField(seleniumBrowserAbstractUnderTest, "driver", mockDriver);

        // Then
        final boolean result = seleniumBrowserAbstractUnderTest.isInitialized();

        // Verify the results
        assertThat(result).isTrue();
    }
    @Test
    void when_IsInitialized_Should_Return_False() {

        // When
        when(mockDriver.toString()).thenReturn("(null)");
        ReflectionTestUtils.setField(seleniumBrowserAbstractUnderTest, "driver", mockDriver);

        // Then
        final boolean result = seleniumBrowserAbstractUnderTest.isInitialized();

        // Verify the results
        assertThat(result).isFalse();
    }
    @Test
    void when_Navigate_Should_DriverNavigateTo_Given_Url() {
        //Given
        WebDriver.Navigation mockNavigation = mock(WebDriver.Navigation.class);

        //When
        ReflectionTestUtils.setField(seleniumBrowserAbstractUnderTest, "driver", mockDriver);
        when(mockDriver.navigate()).thenReturn(mockNavigation);
        doNothing().when(mockNavigation).to(anyString());

        //Then
        seleniumBrowserAbstractUnderTest.navigate("url");

        // Verify
        verify(mockNavigation, times(1)).to("url");
    }
    @Test
    void when_Navigate_If_Is_Not_Initialized_Should_Throw_IllegalStateException() {
        //Given
        SeleniumBrowserAbstract spySeleniumBrowserAbstract = spy(seleniumBrowserAbstractUnderTest);
        WebDriver.Navigation mockNavigation = mock(WebDriver.Navigation.class);

        //When
        ReflectionTestUtils.setField(seleniumBrowserAbstractUnderTest, "driver", mockDriver);
        when(mockDriver.navigate()).thenReturn(mockNavigation);
        doReturn(false).when(spySeleniumBrowserAbstract).isInitialized();

        //Then
        assertThatThrownBy(() -> spySeleniumBrowserAbstract.navigate(anyString())).isInstanceOf(IllegalStateException.class);

        // Verify
        verify(mockNavigation,never()).to("url");
    }
    @Test
    void when_RetrieveContent_If_Driver_Is_Not_Initialized_Should_Throw_IllegalStateException() {
        //Given
        SeleniumBrowserAbstract spySeleniumBrowserAbstract = spy(seleniumBrowserAbstractUnderTest);

        // When
        ReflectionTestUtils.setField(seleniumBrowserAbstractUnderTest, "driver", mockDriver);
        doReturn(false).when(spySeleniumBrowserAbstract).isInitialized();
        //Then
        assertThatThrownBy(() -> spySeleniumBrowserAbstract.retrieveContent()).isInstanceOf(IllegalStateException.class);

        // Verify
        verify(mockDriver, never()).getPageSource();

    }
    @Test
    void when_RetrieveContent_Should_Return_PageSource() {
        //Given
        final String expectedContent = "expectedContent";
        SeleniumBrowserAbstract spySeleniumBrowserAbstract = spy(seleniumBrowserAbstractUnderTest);

        //When
        ReflectionTestUtils.setField(seleniumBrowserAbstractUnderTest, "driver", mockDriver);
        when(mockDriver.getPageSource()).thenReturn(expectedContent);
        doReturn(true).when(spySeleniumBrowserAbstract).isInitialized();

        final String result = seleniumBrowserAbstractUnderTest.retrieveContent();

        // Verify the results
        assertThat(result).isEqualTo(expectedContent);
        verify(mockDriver, times(1)).getPageSource();

    }
    @Test
    void when_Close_Should_WebDriver_Quit() {
        //Given
        SeleniumBrowserAbstract spySeleniumBrowserAbstract = spy(seleniumBrowserAbstractUnderTest);

        //When
        ReflectionTestUtils.setField(seleniumBrowserAbstractUnderTest, "driver", mockDriver);
        doNothing().when(mockDriver).quit();
        doReturn(true).when(spySeleniumBrowserAbstract).isInitialized();

        seleniumBrowserAbstractUnderTest.close();

        // Verify the results
        verify(mockDriver, times(1)).quit();
    }

    @Test
    void when_Destroy_Should_WebDriver_Close() {
        //Given
        SeleniumBrowserAbstract spySeleniumBrowserAbstract = spy(seleniumBrowserAbstractUnderTest);

        //When
        ReflectionTestUtils.setField(seleniumBrowserAbstractUnderTest, "driver", mockDriver);
        doNothing().when(mockDriver).close();
        doReturn(true).when(spySeleniumBrowserAbstract).isInitialized();

        seleniumBrowserAbstractUnderTest.destroy();

        // Verify the results
        verify(mockDriver, times(1)).close();
    }
}
