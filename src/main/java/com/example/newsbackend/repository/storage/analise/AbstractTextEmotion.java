package com.example.newsbackend.repository.storage.analise;

import javax.persistence.Entity;

@Entity
public abstract class AbstractTextEmotion {
    private double joy;
    private double anger;
    private double disgust;
    private double fear;
    private double sadness;

}
