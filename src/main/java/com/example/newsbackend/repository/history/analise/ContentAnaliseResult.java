package com.example.newsbackend.repository.history.analise;

import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class ContentAnaliseResult  {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
