package com.example.newsbackend.service.nlu;

public interface AnalyzeOptions {

    public String getText();

    public void setText(String text);

    public Integer getConceptsLimit();


    public Integer getEntitiesLimit();


    public Integer getKeywordsLimit();

    public Boolean isEntitiesSentiment();


    public Boolean isEntitiesEmotion();


    public Boolean isKeywordsSentiment();


    public Boolean isKeywordsEmotion();
}
