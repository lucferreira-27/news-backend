package com.example.newsbackend.repository.history;



import com.example.newsbackend.repository.history.ContentAnaliseResult;

import javax.persistence.Entity;

@Entity
public class WatsonAnaliseResult extends ContentAnaliseResult {
    private String text;
    private String intent;
    private String entities;
    private String sentiment;
    private String confidence;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getEntities() {
        return entities;
    }

    public void setEntities(String entities) {
        this.entities = entities;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}
