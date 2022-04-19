package com.example.newsbackend.repository.page;

import com.example.newsbackend.service.serp.NewsResultPage;

public class PageBody implements PageContent{
    private String textContent;
    private NewsResultPage newsResultPage;
    public PageBody(NewsResultPage pageHeadline, String textContent) {
        this.textContent = textContent;
        this.newsResultPage = pageHeadline;
    }


    @Override
    public String getTextContent() {
        return textContent;
    }

    public NewsResultPage getNewsResultPage() {
        return newsResultPage;
    }
}
