package com.example.newsbackend.repository.storage.analise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonProperty("document")
    private void unpackNameFromNestedObject(Map<String, String> document) {
        this.label = document.get("label");
        var tempScore = document.get("score");
        this.score = tempScore != null ? Double.parseDouble(tempScore) : null;

    }

}
