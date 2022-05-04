package com.example.newsbackend.service.serp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

@Service
public class ScaleAPIRequest implements APIRequest {
    public final String BASE_URL = "https://api.scaleserp.com/search";
    private final HTTPRequest httpRequest;
    private final URLCustom urlCustom;
    public ScaleAPIRequest(HTTPRequest httpRequest, URLCustom urlCustom) {
        this.httpRequest = httpRequest;
        this.urlCustom = urlCustom;
    }

    @Override
    public APIResponse getResponse(RequestParameters requestParameters) throws ScaleAPIException {
        try{
            URL requestUrl = urlCustom.buildParametersURL(BASE_URL,requestParameters);

            String response = httpRequest.sendRequest(requestUrl);
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
