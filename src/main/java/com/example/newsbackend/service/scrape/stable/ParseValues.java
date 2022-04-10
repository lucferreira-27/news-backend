package com.example.newsbackend.service.scrape.stable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ParseValues {

    private ObjectMapper mapper = new ObjectMapper();
    private List<String> values = new ArrayList<>();

    public ParseValues() {
    }

    public ParseValues(List<String> values) {
        this.values = values;
    }


    public void addValue(String attribute, String value) {
        String json = String.format("{\"%s\":\"%s\"}",attribute,value);
        values.add(json);
    }

    public List<String> getValues() {
        return values;
    }

    public String getConcatenatedJsonValues() {
        return values.stream().reduce((a, b) -> a.replaceFirst("\\}","") + ", " + b.replaceFirst("\\{","")).get();
    }
    public JsonNode getJsonValues() throws JsonProcessingException {
        JsonNode node = mapper.readTree(getConcatenatedJsonValues());
        return node;
    }

    @Override
    public String toString() {
        return "ParseValues{" +
                "mapper=" + mapper +
                ", values=" + values +
                '}';
    }
}



