package com.example.newsbackend.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class StorageResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne(cascade = CascadeType.PERSIST)
    private RegisteredSite registeredSite;
    private StorageStatus status;
    private String statusMessage;
    private long analysisTime;
    @OneToOne(cascade = CascadeType.PERSIST)
    private ContentAnaliseResult contentAnaliseResult;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    private SearchHistory searchHistory;
    @Embedded
    private SearchInfoResult searchInfo;
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

    public RegisteredSite getRegisteredSite() {
        return registeredSite;
    }

    public void setRegisteredSite(RegisteredSite registeredSite) {
        this.registeredSite = registeredSite;
    }

    public void setAnalysisTime(long analysisTime) {
        this.analysisTime = analysisTime;
    }

    public long getAnalysisTime() {
        return analysisTime;
    }

    public SearchInfoResult getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfoResult searchInfo) {
        this.searchInfo = searchInfo;
    }

    public SearchHistory getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(SearchHistory searchHistory) {
        this.searchHistory = searchHistory;
    }
}
