package com.example.newsbackend.repository.storage;
import com.example.newsbackend.repository.storage.analise.ContentAnaliseResult;
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
    private String statusMessage;
    private long analysisTime;
    private Double finalScore;
    @OneToOne
    private ContentAnaliseResult contentAnaliseResult;


    public StorageStatus getStatus() {
        return status;
    }
    public void setStatus(StorageStatus status) {
        this.status = status;
    }
    public void setStatus(StorageStatus status, String message) {
        this.status = status;
        this.statusMessage = message;
    }

    public String getStatusMessage() {
        return statusMessage;
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

    public void setAnalysisTime(long analysisTime) {
        this.analysisTime = analysisTime;
    }

    public long getAnalysisTime() {
        return analysisTime;
    }
}
