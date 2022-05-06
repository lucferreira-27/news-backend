package com.example.newsbackend.service.scrape;

import com.example.newsbackend.entity.sites.SelectorQuery;
import com.example.newsbackend.service.impl.scrape.stable.ParseValues;

import java.util.List;

public interface HTMLParserService {
    public List<ParseValues> parse(String html, List<SelectorQuery> scrapeQueries);
}
