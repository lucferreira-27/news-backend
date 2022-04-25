package newsbackend;

import com.example.newsbackend.NewsBackendApplication;
import com.example.newsbackend.service.nlu.watson.WatsonAnalyze;
import com.example.newsbackend.service.nlu.watson.WatsonAnalyzeOptions;
import com.example.newsbackend.service.serp.HTTPRequest;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= NewsBackendApplication.class)
@AutoConfigureMockMvc
public class NewsBackendApiTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private HTTPRequest mockHttpRequest;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnStatusOkWhenGetAllNews() throws Exception {
        //Given
        String thirdPartyResponse = readTextFromFile("test_int_scale_serp_success.json");

        // When
        when(mockHttpRequest.sendRequest(any(URL.class))).thenReturn(thirdPartyResponse);
        // Then
        var response = mvc.perform(get("/search")
                        .param("q", "test")
                        .accept("application/json"))
                .andExpect(status().isOk()).andReturn();
        System.out.println(response.getResponse());
    }

    private String readTextFromFile(String name) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(name).getFile());
        String result = Files.readString(Paths.get(file.getAbsolutePath()));
        return result;
    }

}
