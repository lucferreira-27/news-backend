package com.example.newsbackend.repository.storage.analise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TextEntity extends AbstractTextEmotion {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private WatsonAnaliseResult watsonAnaliseResult;
    private String type;
    private String text;
    private double relevance;
    private double confidence;
    private int count;
    private double sentimentScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public WatsonAnaliseResult getWatsonAnaliseResult() {
        return watsonAnaliseResult;
    }

    public void setWatsonAnaliseResult(WatsonAnaliseResult watsonAnaliseResult) {
        this.watsonAnaliseResult = watsonAnaliseResult;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }


}
