package com.example.newsbackend.repository.storage.analise;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class WatsonAnaliseResult extends    ContentAnaliseResult {

    public String language;
    @OneToMany(mappedBy = "watsonAnaliseResult",cascade= CascadeType.PERSIST)
    private List<TextConcept> concepts = new ArrayList<>();
    @OneToMany(mappedBy = "watsonAnaliseResult",cascade= CascadeType.PERSIST)
    private List<TextEntity> entities = new ArrayList<>();
    @OneToMany(mappedBy = "watsonAnaliseResult",cascade= CascadeType.PERSIST)
    private List<TextKeyword> keywords = new ArrayList<>();
    @Embedded
    public TextGlobalSentiment sentiment;

    public static WatsonAnaliseResult fromJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json,  WatsonAnaliseResult.class);
    }
    @JsonProperty("sentiment")
    public TextGlobalSentiment getSentiment() {
        return sentiment;
    }
    @JsonProperty("sentiment")
    public void setSentiment(TextGlobalSentiment sentiment) {
        this.sentiment = sentiment;
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
