package com.example.newsbackend.service.serp;

import java.util.Map;

public class RequestParameters {
    private final String apiKey;
    private final String q;
    private final String searchType;
    private final String location;
    private final String timePeriod;
    private final String sortBy;
    private final String device;
    private final String page;
    private final String gl;
    private final String hl;
    private final boolean includeHtml;
    private final Map<String,String> defaultValues = Map.of(
            "page", "1",
            "gl", "us",
            "hl", "en",
            "include_html", "false",
            "device", "desktop",
            "sort_by", "date"
    );
    private RequestParameters(String apiKey, String q, String searchType, String location, String timePeriod, String sortBy, String device, String page, String gl, String hl, boolean includeHtml) {
        this.apiKey = apiKey;
        this.q = q;
        this.searchType = searchType;
        this.location = location == null ? defaultValues.get("location") : location;
        this.timePeriod = timePeriod == null ? defaultValues.get("time_period") : timePeriod;
        this.gl = gl == null ? defaultValues.get("gl") : gl;
        this.hl = hl == null ? defaultValues.get("hl") : hl;
        this.sortBy = sortBy;
        this.device = device;
        this.page = page;
        this.includeHtml = includeHtml;
    }



    public String getParameters() {

        return   parameterValue("api_key=", apiKey)
                +  parameterValue("&q=", q)
                +  parameterValue("&search_type=", searchType)
                +  parameterValue("&location=", location)
                +  parameterValue("&time_period=", timePeriod)
                +  parameterValue("&sort_by=", sortBy)
                +  parameterValue("&device=", device)
                +  parameterValue("&page=", page)
                +  parameterValue("&gl=", gl)
                +  parameterValue("&hl=", hl)
                +  "&include_html=" + includeHtml;

    }
    private String parameterValue(String text, String value) {
        return value == null ? "" : text + value;
    }


    public static class Builder {

        private String apiKey;
        private String q;
        private String searchType;
        private String location;
        private String timePeriod;
        private String sortBy;
        private String device;
        private String page;
        private String gl;
        private String hl;
        private boolean includeHtml;

        public RequestParameters build() {
            if (this.apiKey == null || this.searchType == null || this.q == null) {
                throw new IllegalArgumentException("apiKey, searchType and q are mandatory");
            }
            return new RequestParameters(this.apiKey, this.q, this.searchType, this.location, this.timePeriod, this.sortBy, this.device, this.page, this.gl, this.hl, this.includeHtml);
        }

        public Builder addApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder addQuery(String q) {
            this.q = q;
            return this;
        }

        public Builder addSearchType(String searchType) {
            this.searchType = searchType;
            return this;
        }

        public Builder addLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder addTimePeriod(String timePeriod) {
            this.timePeriod = timePeriod;
            return this;
        }


        public Builder addSortBy(String sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public Builder addDevice(String device) {
            this.device = device;
            return this;
        }

        public Builder addPage(String page) {
            this.page = page;
            return this;
        }

        public Builder addGoogleCountry(String gl) {
            this.gl = gl;
            return this;
        }

        public Builder addGoogleUILanguage(String hl) {
            this.hl = hl;
            return this;
        }

        public Builder addIncludeHtml(boolean includeHtml) {
            this.includeHtml = includeHtml;
            return this;
        }

    }

}

