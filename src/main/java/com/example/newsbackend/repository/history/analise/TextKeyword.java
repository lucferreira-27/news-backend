package com.example.newsbackend.repository.history.analise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TextKeyword {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private double relevance;
    private String text;
    @ManyToOne
    private WatsonAnaliseResult watsonAnaliseResult;
    private int count;

    public WatsonAnaliseResult getWatsonAnaliseResult() {
        return watsonAnaliseResult;
    }

    public void setWatsonAnaliseResult(WatsonAnaliseResult watsonAnaliseResult) {
        this.watsonAnaliseResult = watsonAnaliseResult;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getRelevance() {
        return relevance;
    }

    public void setRelevance(double relevance) {
        this.relevance = relevance;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
