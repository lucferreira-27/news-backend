package com.example.newsbackend.repository.sites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class RegisteredSite implements Site{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    private SiteConfiguration siteConfiguration;
    @Column(name = "url", nullable = false)
    private String url;

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

    public String getUrl() {
        return url;
    }
}
