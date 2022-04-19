package com.example.newsbackend.service.serp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScaleAPIResponse implements APIResponse{
    @JsonProperty("request_info")
    private RequestInfo requestInfo;
    @JsonProperty("news_results")
    private List<NewsResultPage> newsResultPages = new ArrayList<>();

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    public List<NewsResultPage> getNewsResultPages() {
        return newsResultPages;
    }

    public void setNewsResultPages(List<NewsResultPage> newsResultPages) {
        this.newsResultPages = newsResultPages;
    }
}
