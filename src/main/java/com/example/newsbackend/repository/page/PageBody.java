package com.example.newsbackend.repository.page;

public class PageBody implements PageContent{
    private String textContent;
    private PageHeadline pageHeadline;
    public PageBody(PageHeadline pageHeadline,String textContent) {
        this.textContent = textContent;
        this.pageHeadline = pageHeadline;
    }


    @Override
    public String getTextContent() {
        return textContent;
    }
    public PageHeadline getPageHeadline() {
        return pageHeadline;
    }
}
