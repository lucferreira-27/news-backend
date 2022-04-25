package com.example.newsbackend.service.nlu.watson;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import org.springframework.stereotype.Service;

@Service
public class WatsonAuth {
    public IamAuthenticator getIamAuthenticator(String apikey){
        IamAuthenticator authenticator = new IamAuthenticator
                .Builder()
                .apikey(apikey)
                .build();
        return authenticator;
    }
}
