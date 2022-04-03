package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageContent;
import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.repository.search.SearchEngine;
import com.example.newsbackend.service.scrape.ScrapingException;

import java.util.List;

public interface SearchNewsPageService {
    public List<PageHeadline> search(String keyword) throws ScrapingException;
}
