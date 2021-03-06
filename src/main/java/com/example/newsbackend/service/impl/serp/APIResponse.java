package com.example.newsbackend.service.impl.serp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(as = ScaleAPIResponse.class)
public interface APIResponse {
    RequestInfo getRequestInfo();
    List<NewsResultPage> getNewsResultPages();
}
