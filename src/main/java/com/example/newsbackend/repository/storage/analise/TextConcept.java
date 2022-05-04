package com.example.newsbackend.repository.storage.analise;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextConcept extends AbstractTextEmotion {
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
    @ManyToOne
    @JsonIgnore
    private WatsonAnaliseResult watsonAnaliseResult;
    private String text;
    private double relevance;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getRelevance() {
        return relevance;
    }

    public void setRelevance(double relevance) {
        this.relevance = relevance;
    }

    public void setWatsonAnaliseResult(WatsonAnaliseResult watsonAnaliseResult) {
        this.watsonAnaliseResult = watsonAnaliseResult;
    }
}
