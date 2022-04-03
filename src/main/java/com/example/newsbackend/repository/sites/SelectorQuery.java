package com.example.newsbackend.repository.sites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SelectorQuery {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private SiteConfiguration siteConfiguration;

    private String selector;
    private String attribute;

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

    public String getSelector() {
        return selector;
    }

    public void setSelector(String query) {
        this.selector = query;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
