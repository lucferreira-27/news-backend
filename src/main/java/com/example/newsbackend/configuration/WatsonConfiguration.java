package com.example.newsbackend.configuration;

import com.example.newsbackend.service.nlu.watson.WatsonAuth;
import com.ibm.watson.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WatsonConfiguration {

        @Value("${watson.nlu.version}")
        private String version;
        @Value("${watson.nlu.apikey}")
        private String apikey;
        @Autowired
        private WatsonAuth auth;
        @Bean
        public NaturalLanguageUnderstanding naturalLanguageUnderstanding() {
            return new NaturalLanguageUnderstanding(version,auth.getIamAuthenticator(apikey));
        }



}
