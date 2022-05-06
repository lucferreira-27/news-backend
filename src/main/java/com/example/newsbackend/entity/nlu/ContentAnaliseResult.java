package com.example.newsbackend.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.util.List;

@Entity
public abstract class ContentAnaliseResult  {
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

    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);

    }
    public abstract List<TextConcept> getConcepts();

    public abstract List<TextEntity> getEntities();

    public abstract  List<TextKeyword> getKeywords();

    public abstract TextGlobalSentiment getSentiment();

    public abstract String getLanguage();
}
