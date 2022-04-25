package com.example.newsbackend.repository.history.analise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TextConcept {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @ManyToOne
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
