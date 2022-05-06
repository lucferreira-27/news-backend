package com.example.newsbackend.service.impl.serp;

import com.example.newsbackend.exception.ScaleAPIException;
import com.example.newsbackend.service.serp.APIRequestService;
import com.example.newsbackend.util.URLCustom;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class ScaleAPIRequestServiceImpl implements APIRequestService {
    public final String BASE_URL = "https://api.scaleserp.com/search";
    private final HTTPRequestServiceImpl httpRequestServiceImpl;
    private final URLCustom urlCustom;
    public ScaleAPIRequestServiceImpl(HTTPRequestServiceImpl httpRequestServiceImpl, URLCustom urlCustom) {
        this.httpRequestServiceImpl = httpRequestServiceImpl;
        this.urlCustom = urlCustom;
    }

    @Override
    public APIResponse getResponse(RequestParameters requestParameters) throws ScaleAPIException {
        try{
            URL requestUrl = urlCustom.buildParametersURL(BASE_URL,requestParameters);

            String response = httpRequestServiceImpl.sendRequest(requestUrl);
            return jsonParse(response);
        }catch (Exception e){
            throw new ScaleAPIException(e.getMessage(),e);
        }

    }
    private APIResponse jsonParse(String value) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        APIResponse  response = mapper.readValue(value,  APIResponse.class);

        return response;
    }
}
