package com.example.newsbackend.repository.page;

public class PageHeadline implements PageContent {
    private String title;
    private String url;

    public PageHeadline(String url, String title) {
        this.title = title;
        this.url = url;
    }

    @Override
    public String getTextContent() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
