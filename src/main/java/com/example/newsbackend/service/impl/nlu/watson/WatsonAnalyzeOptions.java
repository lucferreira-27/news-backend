package com.example.newsbackend.service.nlu.watson;

public class WatsonAnalyzeOptions {
    private String text;
    private Integer conceptsLimit;
    private Integer entitiesLimit;
    private Integer keywordsLimit;
    private Boolean entitiesSentiment;
    private Boolean entitiesEmotion;
    private Boolean keywordsSentiment;
    private Boolean keywordsEmotion;
    private Boolean conceptsSentiment ;
    private Boolean conceptsEmotion;


    private WatsonAnalyzeOptions(String text,Integer conceptsLimit, Integer entitiesLimit, Integer keywordsLimit, Boolean entitiesSentiment, Boolean entitiesEmotion, Boolean keywordsSentiment, Boolean keywordsEmotion, Boolean conceptsSentiment, Boolean conceptsEmotion) {
        this.text = text;
        this.conceptsLimit = conceptsLimit;
        this.entitiesLimit = entitiesLimit;
        this.keywordsLimit = keywordsLimit;
        this.entitiesSentiment = entitiesSentiment;
        this.entitiesEmotion = entitiesEmotion;
        this.keywordsSentiment = keywordsSentiment;
        this.keywordsEmotion = keywordsEmotion;
        this.conceptsSentiment = conceptsSentiment;
        this.conceptsEmotion = conceptsEmotion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getConceptsLimit() {
        return conceptsLimit;
    }


    public Integer getEntitiesLimit() {
        return entitiesLimit;
    }


    public Integer getKeywordsLimit() {
        return keywordsLimit;
    }

    public Boolean isEntitiesSentiment() {
        return entitiesSentiment;
    }


    public Boolean isEntitiesEmotion() {
        return entitiesEmotion;
    }


    public Boolean isKeywordsSentiment() {
        return keywordsSentiment;
    }


    public Boolean isKeywordsEmotion() {
        return keywordsEmotion;
    }

    public static class Builder {
        private String text;
        private Integer conceptsLimit = 1;
        private Integer entitiesLimit = 1;
        private Integer keywordsLimit = 1;
        private Boolean entitiesSentiment = false;
        private Boolean entitiesEmotion = false;
        private Boolean keywordsSentiment = false;
        private Boolean keywordsEmotion = false;
        private Boolean conceptsSentiment = false;
        private Boolean conceptsEmotion = false;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder conceptsLimit(Integer conceptsLimit) {
            this.conceptsLimit = conceptsLimit;
            return this;
        }

        public Builder entitiesLimit(Integer entitiesLimit) {
            this.entitiesLimit = entitiesLimit;
            return this;
        }

        public Builder keywordsLimit(Integer keywordsLimit) {
            this.keywordsLimit = keywordsLimit;
            return this;
        }

        public Builder entitiesSentiment(Boolean entitiesSentiment) {
            this.entitiesSentiment = entitiesSentiment;
            return this;
        }

        public Builder entitiesEmotion(Boolean entitiesEmotion) {
            this.entitiesEmotion = entitiesEmotion;
            return this;
        }

        public Builder keywordsSentiment(Boolean keywordsSentiment) {
            this.keywordsSentiment = keywordsSentiment;
            return this;
        }

        public Builder keywordsEmotion(Boolean keywordsEmotion) {
            this.keywordsEmotion = keywordsEmotion;
            return this;
        }

        public Builder conceptsSentiment(Boolean conceptsSentiment) {
            this.conceptsSentiment = conceptsSentiment;
            return this;
        }

        public Builder conceptsEmotion(Boolean conceptsEmotion) {
            this.conceptsEmotion = conceptsEmotion;
            return this;
        }

        public WatsonAnalyzeOptions build() {
            if(text == null || text.isEmpty()) {
                throw new IllegalArgumentException("Text is required");
            }
            return new WatsonAnalyzeOptions(
                    text,
                    conceptsLimit,
                    entitiesLimit,
                    keywordsLimit,
                    entitiesSentiment,
                    entitiesEmotion,
                    keywordsSentiment,
                    keywordsEmotion,
                    conceptsSentiment,
                    conceptsEmotion);
        }
    }

}


