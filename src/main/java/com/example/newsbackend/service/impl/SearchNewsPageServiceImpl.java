package com.example.newsbackend.service.impl;

import com.example.newsbackend.exception.NoneSearchResultException;
import com.example.newsbackend.exception.ScaleAPIException;
import com.example.newsbackend.service.SearchNewsPageService;
import com.example.newsbackend.service.impl.serp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SearchNewsPageServiceImpl implements SearchNewsPageService {


    private final ScaleAPIRequestServiceImpl scaleAPIRequestServiceImpl;
    @Value("${scale.apikey}")
    private  String apikey;
    private static final String SEARCH_TYPE = "news";


    @Autowired
    public SearchNewsPageServiceImpl(ScaleAPIRequestServiceImpl scaleAPIRequestServiceImpl) {
        this.scaleAPIRequestServiceImpl = scaleAPIRequestServiceImpl;
    }


    @Override
    public List<NewsResultPage> search(Map<String, String> params) throws ScaleAPIException {

        RequestParameters requestParameters = buildRequestParameters(params);
        APIResponse response = scaleAPIRequestServiceImpl.getResponse(requestParameters);
        if(response.getNewsResultPages().size() == 0){
            throw new NoneSearchResultException("No results found for query \"" + requestParameters.getQuery() + "\"");
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
        QUERY("q"), // Search query (required) Ex: q="trump"
        LANGUAGE("lang"), // Search results language (optional) (default: English) Ex: lang=en
        SORT_BY("sortBy"), // Sort search by [relevance, date] (optional) (default: relevance) Ex: sortBy=date
        PAGE("page"), // Total pages to retrieve (optional) (default: 1) Ex: page=2
        COUNTRY("country"), // Search in country (optional)  (default: US) Ex: country=US
        LOCATION("location"); // Search location (optional) (default: None) Ex: location=New+York,New+York,United+States

        private String value;

        SearchParameters(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
