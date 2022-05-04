package com.example.newsbackend.repository.sites;

import javax.persistence.*;

@Entity
public class RegisteredSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
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
    public void setUrl(String url) {
        this.url = url;
    }
    public static RegisteredSite fromRegisteredSiteForm(RegisteredSiteForm form) {
        RegisteredSite registeredSite = new RegisteredSite();
        registeredSite.setUrl(form.getUrl());
        SiteConfiguration siteConfiguration = new SiteConfiguration();
        siteConfiguration.setName(form.getName());
        siteConfiguration.setDomain(form.getDomain());
        siteConfiguration.setDescription(form.getDescription());
        siteConfiguration.setLogo(form.getLogo());
        siteConfiguration.setKeywords(form.getKeywords());
        siteConfiguration.setLanguage(form.getLanguage());
        siteConfiguration.setCountry(form.getCountry());
        siteConfiguration.setScrapingType(form.getScrapingType());
        siteConfiguration.setSelectorQueries(form.getSelectorQueries());
        registeredSite.setSiteConfiguration(siteConfiguration);

        return registeredSite;
    }

}
