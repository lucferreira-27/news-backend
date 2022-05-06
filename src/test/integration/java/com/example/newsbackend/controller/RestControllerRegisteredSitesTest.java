package com.example.newsbackend.controller;

import com.example.newsbackend.NewsBackendApplication;
import com.example.newsbackend.controller.dtos.RegisteredSiteDto;
import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.entity.sites.SelectorQuery;
import com.example.newsbackend.entity.sites.SiteConfiguration;
import com.example.newsbackend.repository.sites.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewsBackendApplication.class)
@AutoConfigureMockMvc
class RestControllerRegisteredSitesTest {
    @Autowired
    private MockMvc mvc;
    @SpyBean
    private RegisteredSiteRepository registeredSiteRepository;
    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_Add_Should_Return_As_Response_StatusCreated_And_Redirect() throws Exception {
        //Given
        SelectorQuery selectorQuery = new SelectorQuery();
        selectorQuery.setSelector(".article-title");
        selectorQuery.setAttribute("text");

        RegisteredSiteDto registeredSiteDto = new RegisteredSiteDto.Builder()
                .name("test")
                .url("http://test/test.com")
                .domain("test.com")
                .description("A test site")
                .language("en")
                .logo("http://test/test.com/logo.png")
                .country("US")
                .keywords(List.of("test","test2"))
                .scrapingType(SiteConfiguration.ScrapingType.STATIC)
                .selectorQueries(List.of(selectorQuery))
                .build();

        int totalRegisteredSites = registeredSiteRepository.findAll().size();

        //Verify
        mvc.perform(post("/api/v1/sites/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(registeredSiteDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("/api/v1/sites/find/**"))
                        .andReturn();
        assertThat( registeredSiteRepository.findAll().size()).isEqualTo(totalRegisteredSites + 1);
    }
    @Test
    void when_Add_If_Form_Not_Valid_Should_Return_As_Response_StatusBadRequest() throws Exception {
        //Given
        SelectorQuery selectorQuery = new SelectorQuery();
        selectorQuery.setSelector("");
        selectorQuery.setAttribute("");

        RegisteredSiteDto registeredSiteDto = new RegisteredSiteDto.Builder()
                .name("")
                .url("")
                .domain("")
                .description("A test site")
                .language("en")
                .logo("http://test.com/logo.png")
                .country("US")
                .keywords(List.of("test","test2"))
                .scrapingType(SiteConfiguration.ScrapingType.STATIC)
                .selectorQueries(List.of(selectorQuery))
                .build();

        //Verify
        mvc.perform(post("/api/v1/sites/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(registeredSiteDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",
                        containsString("domain is required")))
                .andExpect(jsonPath("$.message",
                        containsString("name is required")))
                .andExpect(jsonPath("$.message",
                        containsString("url is required")))
                .andReturn();
    }
    @Test
    void when_Add_If_Url_Exist_Should_Return_As_Response_StatusConflict() throws Exception {
        //Given
        SelectorQuery selectorQuery = new SelectorQuery();
        selectorQuery.setSelector(".article-title");
        selectorQuery.setAttribute("text");

        RegisteredSiteDto registeredSiteDto = new RegisteredSiteDto.Builder()
                .name("test")
                .url("http://test.com")
                .domain("test.com")
                .description("A test site")
                .language("en")
                .logo("http://test.com/logo.png")
                .country("US")
                .keywords(List.of("test","test2"))
                .scrapingType(SiteConfiguration.ScrapingType.STATIC)
                .selectorQueries(List.of(selectorQuery))
                .build();

        registeredSiteRepository.save(RegisteredSite.fromRegisteredSiteDto(registeredSiteDto));

        //Verify
        mvc.perform(post("/api/v1/sites/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(registeredSiteDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message")
                        .value("Site with url \"" + registeredSiteDto.getUrl() + "\" already exists"))
                .andReturn();
    }

    @Test
    void when_Find_Should_Return_As_Response_StatusOK_With_RegisteredSite_Given_Id() throws Exception {
        //Given
        RegisteredSite registeredSite = registeredSiteRepository.findAll().get(0);
        //Verify
        mvc.perform(get("/api/v1/sites/find/"+registeredSite.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(registeredSite.getId()))
                .andExpect(jsonPath("$.url")
                        .value(registeredSite.getUrl()))
                .andExpect(jsonPath("$.siteConfiguration.domain")
                        .value(registeredSite.getSiteConfiguration().getDomain()))
                .andExpect(jsonPath("$.siteConfiguration.language")
                        .value(registeredSite.getSiteConfiguration().getLanguage()))
                .andExpect(jsonPath("$.siteConfiguration.logo")
                        .value(registeredSite.getSiteConfiguration().getLogo()))
                .andExpect(jsonPath("$.siteConfiguration.country")
                        .value(registeredSite.getSiteConfiguration().getCountry()))
                .andExpect(jsonPath("$.siteConfiguration.keywords.length()")
                        .value(registeredSite.getSiteConfiguration().getKeywords().size()))
                .andExpect(jsonPath("$.siteConfiguration.scrapingType")
                        .value(registeredSite.getSiteConfiguration().getScrapingType().name()))
                .andExpect(jsonPath("$.siteConfiguration.selectorQueries[0].selector")
                        .value(registeredSite.getSiteConfiguration().getSelectorQueries().get(0).getSelector()))
                .andExpect(jsonPath("$.siteConfiguration.selectorQueries[0].attribute")
                        .value(registeredSite.getSiteConfiguration().getSelectorQueries().get(0).getAttribute()));


    }
    @Test
    void when_Find_If_No_Resource_Found_Should_Return_As_Response_StatusNotFound_Given_Id() throws Exception {

        //Verify
         mvc.perform(get("/api/v1/sites/find/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Site with ID \"0\" not found"));

    }
    @Test
    void when_Find_Should_Return_As_Response_StatusOK_With_RegisteredSite_Given_Url() throws Exception {
        //Given
        RegisteredSite registeredSite = registeredSiteRepository.findAll().get(0);
        //Verify
        mvc.perform(get("/api/v1/sites/find/filter/?url=" + registeredSite.getUrl()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(registeredSite.getId()))
                .andExpect(jsonPath("$.url")
                        .value(registeredSite.getUrl()))
                .andExpect(jsonPath("$.siteConfiguration.domain")
                        .value(registeredSite.getSiteConfiguration().getDomain()))
                .andExpect(jsonPath("$.siteConfiguration.language")
                        .value(registeredSite.getSiteConfiguration().getLanguage()))
                .andExpect(jsonPath("$.siteConfiguration.logo")
                        .value(registeredSite.getSiteConfiguration().getLogo()))
                .andExpect(jsonPath("$.siteConfiguration.country")
                        .value(registeredSite.getSiteConfiguration().getCountry()))
                .andExpect(jsonPath("$.siteConfiguration.keywords.length()")
                        .value(registeredSite.getSiteConfiguration().getKeywords().size()))
                .andExpect(jsonPath("$.siteConfiguration.scrapingType")
                        .value(registeredSite.getSiteConfiguration().getScrapingType().name()))
                .andExpect(jsonPath("$.siteConfiguration.selectorQueries[0].selector")
                        .value(registeredSite.getSiteConfiguration().getSelectorQueries().get(0).getSelector()));
    }
    @Test
    void when_Find_If_No_Resource_Should_Return_As_Response_StatusNotFound_Given_Url() throws Exception {

        //Verify
        mvc.perform(get("/api/v1/sites/find/filter/?url=www.test.com"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Site with URL \"www.test.com\" not found"));

    }

    @Test
    void when_DeleteById_Should_Return_As_Response_StatusOk_Given_Id() throws Exception {

        //When
        doNothing().when(registeredSiteRepository).deleteById(anyLong());

        //Given
        RegisteredSite registeredSite = registeredSiteRepository.findAll().get(0);
        //Verify
        mvc.perform(delete("/api/v1/sites/delete/" + registeredSite.getId()))
                .andExpect(status().isOk());
    }
    @Test
    void when_DeleteById_If_No_Resource_Should_Return_As_Response_StatusNotFound_Given_Id() throws Exception {



        //Verify
        mvc.perform(delete("/api/v1/sites/delete/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Registered site with id \"0\" not found"));
    }

    @Test
    void when_DeleteAll_Should_Return_As_Response_StatusOk() throws Exception {

        //When
        doNothing().when(registeredSiteRepository).deleteAll();
        //Verify
        mvc.perform(delete("/api/v1/sites/delete/all"))
                .andExpect(status().isOk());
    }

    @Test
    void when_Update_Should_Return_As_Response_StatusOk() throws Exception {
        //Given
        RegisteredSite registeredSite = registeredSiteRepository.findAll().get(1);
        RegisteredSiteDto registeredSiteDto = new RegisteredSiteDto.Builder()
                .language("en")
                .domain("test.com")
                .logo("www.test.com")
                .country("Poland")
                .url("www.updated.com")
                .build();
        //Verify
        mvc.perform(put("/api/v1/sites/update/" + registeredSite.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(registeredSiteDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(registeredSite.getId()))
                .andExpect(jsonPath("$.url")
                        .value(registeredSiteDto.getUrl()))
                .andExpect(jsonPath("$.siteConfiguration.domain")
                        .value(registeredSiteDto.getDomain()))
                .andExpect(jsonPath("$.siteConfiguration.language")
                        .value(registeredSiteDto.getLanguage()))
                .andExpect(jsonPath("$.siteConfiguration.logo")
                        .value(registeredSiteDto.getLogo()))
                .andExpect(jsonPath("$.siteConfiguration.country")
                        .value(registeredSiteDto.getCountry()))
                .andExpect(jsonPath("$.siteConfiguration.keywords.length()")
                        .value(registeredSite.getSiteConfiguration().getKeywords().size()))
                .andExpect(jsonPath("$.siteConfiguration.scrapingType")
                        .value(registeredSite.getSiteConfiguration().getScrapingType().name()))
                .andExpect(jsonPath("$.siteConfiguration.selectorQueries[0].selector")
                        .value(registeredSite.getSiteConfiguration().getSelectorQueries().get(0).getSelector()));;


        RegisteredSite updatedRegisteredSite = registeredSiteRepository.findAll().get(1);
        assertThat(updatedRegisteredSite.getSiteConfiguration().getLanguage()).isEqualTo(registeredSiteDto.getLanguage());
        assertThat(updatedRegisteredSite.getSiteConfiguration().getDomain()).isEqualTo(registeredSiteDto.getDomain());
        assertThat(updatedRegisteredSite.getSiteConfiguration().getLogo()).isEqualTo(registeredSiteDto.getLogo());
        assertThat(updatedRegisteredSite.getSiteConfiguration().getCountry()).isEqualTo(registeredSiteDto.getCountry());
        assertThat(updatedRegisteredSite.getUrl()).isEqualTo(registeredSiteDto.getUrl());

    }


    private <T> String toJson(T titleDto) throws JsonProcessingException {

        return new ObjectMapper().writeValueAsString(titleDto);

    }

}