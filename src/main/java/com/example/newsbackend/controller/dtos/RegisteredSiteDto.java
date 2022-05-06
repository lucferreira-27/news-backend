package com.example.newsbackend.controller.dtos;

import com.example.newsbackend.entity.sites.SelectorQuery;
import com.example.newsbackend.entity.sites.SiteConfiguration;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class RegisteredSiteDto {

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
        private RegisteredSiteDto registeredSiteDto;

        public Builder(){
            registeredSiteDto = new RegisteredSiteDto();
        }

        public Builder name(String name){
            registeredSiteDto.name = name;
            return this;
        }

        public Builder domain(String domain){
            registeredSiteDto.domain = domain;
            return this;
        }

        public Builder description(String description){
            registeredSiteDto.description = description;
            return this;
        }

        public Builder logo(String logo){
            registeredSiteDto.logo = logo;
            return this;
        }

        public Builder keywords(List<String> keywords){
            registeredSiteDto.keywords = keywords;
            return this;
        }

        public Builder language(String language){
            registeredSiteDto.language = language;
            return this;
        }

        public Builder country(String country){
            registeredSiteDto.country = country;
            return this;
        }

        public Builder url(String url){
            registeredSiteDto.url = url;
            return this;
        }

        public Builder scrapingType(SiteConfiguration.ScrapingType scrapingType){
            registeredSiteDto.scrapingType = scrapingType;
            return this;
        }

        public Builder selectorQueries(List<SelectorQuery> selectorQueries){
            registeredSiteDto.selectorQueries = selectorQueries;
            return this;
        }

        public RegisteredSiteDto build(){
            return registeredSiteDto;
        }
    }


}
