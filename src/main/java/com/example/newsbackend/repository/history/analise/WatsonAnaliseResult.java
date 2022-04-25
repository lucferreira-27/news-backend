package com.example.newsbackend.repository.history.analise;



import com.example.newsbackend.service.serp.APIResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class WatsonAnaliseResult extends ContentAnaliseResult {

    public String language;
    @OneToMany(mappedBy = "watsonAnaliseResult")
    private List<TextConcept> concepts = new ArrayList<>();
    @OneToMany(mappedBy = "watsonAnaliseResult")
    private List<TextEntity> entities = new ArrayList<>();
    @OneToMany(mappedBy = "watsonAnaliseResult")
    private List<TextKeyword> keywords = new ArrayList<>();
    @Embedded
    public TextGlobalSentiment sentiment;

    public static WatsonAnaliseResult fromJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json,  WatsonAnaliseResult.class);
    }

    public TextGlobalSentiment getGlobalSentiment() {
        return sentiment;
    }

    public List<TextConcept> getConcepts() {
        return concepts;
    }

    public List<TextEntity> getEntities() {
        return entities;
    }

    public List<TextKeyword> getKeywords() {
        return keywords;
    }

    public String getLanguage() {
        return language;
    }
}
