package com.example.newsbackend.service.impl.nlu.watson;

import com.example.newsbackend.service.nlu.AuthService;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import org.springframework.stereotype.Service;

@Service
public class WatsonAuthServiceImpl implements AuthService {
    public IamAuthenticator getIamAuthenticator(String apikey){
        IamAuthenticator authenticator = new IamAuthenticator
                .Builder()
                .apikey(apikey)
                .build();
        return authenticator;
    }
}
