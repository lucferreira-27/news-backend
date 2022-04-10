package com.example.newsbackend.service.serp;

import org.springframework.stereotype.Service;

@Service
public class URLCustom {
    public String buildParametersURL(String baseUrl,RequestParameters requestParameters) {
        StringBuilder url = new StringBuilder(baseUrl);
        url.append("?");
        url.append(requestParameters.getParameters());
        return url.toString();
    }
}
