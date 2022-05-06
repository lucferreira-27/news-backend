package com.example.newsbackend.service.impl.serp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsResultPage {

    private String title;
    private String link;
    private String domain;
    private String source;
    private String snippet;

    public NewsResultPage() {

    }
    public NewsResultPage(String title, String link, String domain, String source, String snippet) {
        this.title = title;
        this.link = link;
        this.domain = domain;
        this.source = source;
        this.snippet = snippet;
    }
    public NewsResultPage(String title, String link) {
        this.title = title;
        this.link = link;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String toString() {
        return "NewsResultPage [title=" + title + ", link=" + link + ", domain=" + domain + ", source=" + source + ", snippet=" + snippet + "]";
    }
}
