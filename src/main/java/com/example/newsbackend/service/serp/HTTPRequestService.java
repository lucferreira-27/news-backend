package com.example.newsbackend.service.serp;

import java.io.IOException;
import java.net.URL;

public interface HTTPRequestService {
    public String sendRequest(URL url) throws IOException;
}
