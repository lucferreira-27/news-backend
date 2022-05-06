package com.example.newsbackend.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class SiteConfiguration {
    private String name;
    private String domain;
    private String description;
    private String logo;
    private String keywords;
    private String language;
    private String country;
    @Enumerated(EnumType.ORDINAL)
    private ScrapingType scrapingType = ScrapingType.STATIC;
    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SelectorQuery> selectorQueries = new ArrayList<>();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String siteCountry) {
        this.country = siteCountry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String siteDescription) {
        this.description = siteDescription;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String siteDomain) {
        this.domain = siteDomain;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String siteLanguage) {
        this.language = siteLanguage;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String siteLogo) {
        this.logo = siteLogo;
    }

    public List<String> getKeywords() {
        return Arrays.asList(keywords.split(", "));
    }

    public void setKeywords(List<String> siteKeywords) {
        this.keywords = siteKeywords.stream().collect(Collectors.joining(", "));
    }

    public String getName() {
        return name;
    }

    public void setName(String siteName) {
        this.name = siteName;
    }

    public List<SelectorQuery> getSelectorQueries() {
        return selectorQueries;
    }

    public void setSelectorQueries(List<SelectorQuery> scrapeQueries) {
        this.selectorQueries = scrapeQueries;
    }

    public ScrapingType getScrapingType() {
        return scrapingType;
    }

    public void setScrapingType(ScrapingType scrapingType) {
        this.scrapingType = scrapingType;
    }

    public enum ScrapingType {
        DYNAMIC, STATIC
    }
}
