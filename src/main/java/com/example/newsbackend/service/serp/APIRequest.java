package com.example.newsbackend.service.serp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface APIRequest {
    public APIResponse getResponse(RequestParameters requestParameters) throws IOException;
}
