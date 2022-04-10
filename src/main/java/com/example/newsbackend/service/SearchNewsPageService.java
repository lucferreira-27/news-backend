package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageContent;
import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.service.scrape.ScrapingException;
import com.example.newsbackend.service.serp.NewsResultPage;
import com.example.newsbackend.service.serp.ScaleAPIException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchNewsPageService {
    public List<NewsResultPage> search(Map<String,String> params) throws ScaleAPIException;
}
