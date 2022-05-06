package com.example.newsbackend.service.nlu;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;

public interface AuthService {
    public IamAuthenticator getIamAuthenticator(String apikey);
}
