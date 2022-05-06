package com.example.newsbackend.repository.page;

import com.example.newsbackend.entity.RegisteredSite;
import com.example.newsbackend.service.impl.serp.NewsResultPage;

public class PageBody {
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
