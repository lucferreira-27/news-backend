package com.example.newsbackend.repository.history;
import com.example.newsbackend.repository.sites.RegisteredSite;

import javax.persistence.*;

@Entity
public class StorageResult {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    private RegisteredSite registeredSite;
    private StorageStatus status;
    private Double finalScore;
    @OneToOne
    private ContentAnaliseResult contentAnaliseResult;

    @ManyToOne
    private ResultHistory resultHistory;

    public StorageStatus getStatus() {
        return status;
    }
    public void setStatus(StorageStatus status) {
        this.status = status;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public ContentAnaliseResult getContentAnaliseResult() {
        return contentAnaliseResult;
    }

    public void setContentAnaliseResult(ContentAnaliseResult contentAnaliseResult) {
        this.contentAnaliseResult = contentAnaliseResult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegisteredSite getTargetSite() {
        return registeredSite;
    }

    public void setTargetSite(RegisteredSite registeredSite) {
        this.registeredSite = registeredSite;
    }

    public ResultHistory getResultHistory() {
        return resultHistory;
    }

    public void setResultHistory(ResultHistory resultHistory) {
        this.resultHistory = resultHistory;
    }
}
