package com.example.newsbackend.repository.storage;

import javax.persistence.Embeddable;

@Embeddable
public class SearchInfoResult {
    private String headline;
    private String url;
    private String snippet;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeadline() {
        return headline;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
