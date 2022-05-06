package com.example.newsbackend.entity.nlu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class TextKeyword extends AbstractTextEmotion {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private double relevance;
    private String text;
    @ManyToOne
    @JsonIgnore
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
