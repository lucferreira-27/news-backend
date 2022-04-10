package com.example.newsbackend.repository.sites;

import javax.persistence.*;
import java.util.List;

@Entity
public class SiteConfiguration {
    private String siteName;
    private String siteDomain;
    private String siteDescription;
    private String siteLogo;
    private String siteKeywords;
    private String siteLanguage;
    private String siteCountry;
    @Enumerated(EnumType.ORDINAL)
    private DefaultScrapingType scrapingType = DefaultScrapingType.STATIC;
    @OneToMany(cascade=CascadeType.ALL)
    private List<SelectorQuery> scrapeQueries;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteCountry() {
        return siteCountry;
    }

    public void setSiteCountry(String siteCountry) {
        this.siteCountry = siteCountry;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public String getSiteDomain() {
        return siteDomain;
    }

    public void setSiteDomain(String siteDomain) {
        this.siteDomain = siteDomain;
    }

    public String getSiteLanguage() {
        return siteLanguage;
    }

    public void setSiteLanguage(String siteLanguage) {
        this.siteLanguage = siteLanguage;
    }

    public String getSiteLogo() {
        return siteLogo;
    }

    public void setSiteLogo(String siteLogo) {
        this.siteLogo = siteLogo;
    }

    public String getSiteKeywords() {
        return siteKeywords;
    }

    public void setSiteKeywords(String siteKeywords) {
        this.siteKeywords = siteKeywords;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public List<SelectorQuery> getScrapeQueries() {
        return scrapeQueries;
    }

    public void setScrapeQueries(List<SelectorQuery> scrapeQueries) {
        this.scrapeQueries = scrapeQueries;
    }

    public DefaultScrapingType getScrapingType() {
        return scrapingType;
    }

    public void setScrapingType(DefaultScrapingType scrapingType) {
        this.scrapingType = scrapingType;
    }

    public enum DefaultScrapingType {
        DYNAMIC, STATIC
    }
}
