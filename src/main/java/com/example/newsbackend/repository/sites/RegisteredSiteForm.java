package com.example.newsbackend.repository.sites;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class RegisteredSiteForm {

    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "domain is required")
    private String domain;
    private String description;
    private String logo;
    private List<String> keywords = new ArrayList<>();
    private String language;
    private String country;
    @NotBlank(message = "url is required")
    private String url;
    private SiteConfiguration.ScrapingType scrapingType;
    private List<SelectorQuery> selectorQueries = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }

    public String getDescription() {
        return description;
    }

    public String getLogo() {
        return logo;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getUrl() {
        return url;
    }

    public SiteConfiguration.ScrapingType getScrapingType() {
        return scrapingType;
    }

    public List<SelectorQuery> getSelectorQueries() {
        return selectorQueries;
    }

    public static class Builder{
        private RegisteredSiteForm registeredSiteForm;

        public Builder(){
            registeredSiteForm = new RegisteredSiteForm();
        }

        public Builder name(String name){
            registeredSiteForm.name = name;
            return this;
        }

        public Builder domain(String domain){
            registeredSiteForm.domain = domain;
            return this;
        }

        public Builder description(String description){
            registeredSiteForm.description = description;
            return this;
        }

        public Builder logo(String logo){
            registeredSiteForm.logo = logo;
            return this;
        }

        public Builder keywords(List<String> keywords){
            registeredSiteForm.keywords = keywords;
            return this;
        }

        public Builder language(String language){
            registeredSiteForm.language = language;
            return this;
        }

        public Builder country(String country){
            registeredSiteForm.country = country;
            return this;
        }

        public Builder url(String url){
            registeredSiteForm.url = url;
            return this;
        }

        public Builder scrapingType(SiteConfiguration.ScrapingType scrapingType){
            registeredSiteForm.scrapingType = scrapingType;
            return this;
        }

        public Builder selectorQueries(List<SelectorQuery> selectorQueries){
            registeredSiteForm.selectorQueries = selectorQueries;
            return this;
        }

        public RegisteredSiteForm build(){
            return registeredSiteForm;
        }
    }


}
