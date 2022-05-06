package com.example.newsbackend.service;

import com.example.newsbackend.service.impl.serp.NewsResultPage;
import com.example.newsbackend.exception.ScaleAPIException;

import java.util.List;
import java.util.Map;

public interface SearchNewsPageService {
    public List<NewsResultPage> search(Map<String,String> params) throws ScaleAPIException;
}
