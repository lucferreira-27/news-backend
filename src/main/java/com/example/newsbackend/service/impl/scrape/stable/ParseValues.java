package com.example.newsbackend.service.impl.scrape.stable;

import java.util.HashMap;
import java.util.Map;

public class ParseValues {

    private Map<String,String> values = new HashMap<>();

    public ParseValues() {
    }

    public ParseValues(Map<String,String> values) {
        this.values = values;
    }

    public void addValue(String attribute, String value) {
       values.put(attribute,value);
    }

    public Map<String, String> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "ParseValues{" +
                "values=" + values +
                '}';
    }
}



