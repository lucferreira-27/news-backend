package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageBody;
import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.service.serp.NewsResultPage;

public class ParseNewsServiceImpl implements ParseNewsService {
    @Override
    public RegisteredSite validateNewsSite(String url) {
        return null;
    }

    @Override
    public PageBody getNewsPageBody(NewsResultPage searchResult, RegisteredSite registeredSite) {
        return null;
    }
}
