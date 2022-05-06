package com.example.newsbackend.service.impl.serp;

public class RequestInfo {

    private Boolean success;
    private Integer creditsUsed;
    private Integer creditsUsedThisRequest;
    private Integer creditsRemaining;
    private String creditsResetAt;
    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public Integer getCreditsUsed() {
        return creditsUsed;
    }
    public void setCreditsUsed(Integer creditsUsed) {
        this.creditsUsed = creditsUsed;
    }
    public Integer getCreditsUsedThisRequest() {
        return creditsUsedThisRequest;
    }
    public void setCreditsUsedThisRequest(Integer creditsUsedThisRequest) {
        this.creditsUsedThisRequest = creditsUsedThisRequest;
    }
    public Integer getCreditsRemaining() {
        return creditsRemaining;
    }
    public void setCreditsRemaining(Integer creditsRemaining) {
        this.creditsRemaining = creditsRemaining;
    }
    public String getCreditsResetAt() {
        return creditsResetAt;
    }
    public void setCreditsResetAt(String creditsResetAt) {
        this.creditsResetAt = creditsResetAt;
    }

}
