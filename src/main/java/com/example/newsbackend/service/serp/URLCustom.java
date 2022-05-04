package com.example.newsbackend.service.serp;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class URLCustom {
    public URL buildParametersURL(String baseUrl,RequestParameters requestParameters) throws MalformedURLException {
        StringBuilder url = new StringBuilder(baseUrl);
        url.append("?");
        url.append(requestParameters.getParameters());
        String appendUrl = url.toString().replace(" ", "+");

        return new URL(appendUrl);
    }
}
