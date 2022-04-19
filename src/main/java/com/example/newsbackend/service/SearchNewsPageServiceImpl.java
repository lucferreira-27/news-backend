package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import com.example.newsbackend.service.serp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchNewsPageServiceImpl implements SearchNewsPageService {


    private final ScaleAPIRequest scaleAPIRequest;
    @Value("${scale.apikey}")
    private  String apikey;
    private static final String SEARCH_TYPE = "news";


    @Autowired
    public SearchNewsPageServiceImpl(ScaleAPIRequest scaleAPIRequest) {
        this.scaleAPIRequest = scaleAPIRequest;
    }


    @Override
    public List<NewsResultPage> search(Map<String, String> params) throws  ScaleAPIException {

        RequestParameters requestParameters = buildRequestParameters(params);
        APIResponse response = scaleAPIRequest.getResponse(requestParameters);
        if(response.getNewsResultPages().size() == 0){
            throw new ScaleAPIException("No results found for query \"" + requestParameters.getQuery() + "\"");
        };
        return response.getNewsResultPages();
    }
    private RequestParameters buildRequestParameters(Map<String, String> params){
        return new RequestParameters.Builder()
                .addApiKey(apikey)
                .addQuery(params.get(SearchParameters.QUERY.getValue()))
                .addGoogleUILanguage(params.get(SearchParameters.LANGUAGE.getValue()))
                .addSortBy(params.get(SearchParameters.SORT_BY.getValue()))
                .addPage(params.get(SearchParameters.PAGE.getValue()))
                .addGoogleCountry(params.get(SearchParameters.COUNTRY.getValue()))
                .addLocation(params.get(SearchParameters.LOCATION.getValue()))
                .addSearchType(SEARCH_TYPE)
                .build();
    }
    public enum SearchParameters{
        QUERY("q"), // Search query
        LANGUAGE("lang"), // Search results language
        SORT_BY("sortBy"), // Sort search by
        PAGE("page"), // Total pages
        COUNTRY("country"), // Search in country
        LOCATION("location"); // Search location

        private String value;

        SearchParameters(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
