package com.example.newsbackend.repository.storage.analise;

import javax.persistence.*;

@Embeddable
public class TextGlobalSentiment {
    private String label;
    private double score;

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }

}
