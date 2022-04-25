package com.example.newsbackend.repository.page;

import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.service.serp.NewsResultPage;

public class PageBody implements PageContent{
    private String textContent;
    private NewsResultPage newsResultPage;
    private RegisteredSite registeredSite;
    public PageBody(NewsResultPage pageHeadline,
                    String textContent,
                    RegisteredSite registeredSite) {
        this.textContent = textContent;
        this.newsResultPage = pageHeadline;
        this.registeredSite = registeredSite;
    }


    @Override
    public String getTextContent() {
        return textContent;
    }

    public NewsResultPage getNewsResultPage() {
        return newsResultPage;
    }

    public RegisteredSite getRegisteredSite() {
        return registeredSite;
    }
}
