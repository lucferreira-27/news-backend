package com.example.newsbackend.service.serp;

import com.example.newsbackend.service.impl.serp.APIResponse;
import com.example.newsbackend.service.impl.serp.RequestParameters;

import java.io.IOException;

public interface APIRequestService {
    public APIResponse getResponse(RequestParameters requestParameters) throws IOException;
}
