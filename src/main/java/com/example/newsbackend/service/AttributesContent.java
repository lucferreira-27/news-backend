package com.example.newsbackend.service;

import com.example.newsbackend.repository.page.PageContent;
import com.example.newsbackend.repository.page.PageHeadline;
import com.example.newsbackend.service.scrape.stable.ParseValues;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class AttributesContent {
    private static final String URL_ATTRIBUTE = "href";
    private static final String TITLE_ATTRIBUTE = "text";

    public static PageHeadline contentToHeadline(ParseValues parseValues) {
        try {
            JsonNode jsonNode = parseValues.getJsonValues();

            if (isNotValid(jsonNode)) {
                throw new AttributesContentException("JsonNode from parseValues does not have title or/and url");
            }

            String title = jsonNode.path(TITLE_ATTRIBUTE).asText();
            String url = jsonNode.path(URL_ATTRIBUTE).asText();
            return new PageHeadline(url, title);
        } catch (JsonProcessingException e) {
            throw new AttributesContentException("Error while getting json values from parse values", e);
        }
    }
    private static boolean isNotValid(JsonNode jsonNode) {
        return jsonNode.path(TITLE_ATTRIBUTE).isMissingNode() || jsonNode.path(URL_ATTRIBUTE).isMissingNode();
    }

}
