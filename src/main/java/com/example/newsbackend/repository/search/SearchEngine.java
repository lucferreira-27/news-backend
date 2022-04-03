package com.example.newsbackend.repository.search;

import com.example.newsbackend.repository.sites.SiteConfiguration;

import javax.persistence.*;

@Entity
public class SearchEngine {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String queryUrl;
    private boolean active;
    @OneToOne
    private SiteConfiguration siteConfiguration;
    @OneToOne (cascade = CascadeType.ALL)
    private SearchFilters searchFilters;
    public String getName() {
        return name;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SiteConfiguration getSiteConfiguration() {
        return siteConfiguration;
    }
    public void setSiteConfiguration(SiteConfiguration siteConfiguration) {
        this.siteConfiguration = siteConfiguration;
    }

    public SearchFilters getSearchFilters() {
        return searchFilters;
    }

    public void setSearchFilters(SearchFilters searchFilters) {
        this.searchFilters = searchFilters;
    }
}
