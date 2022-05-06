package com.example.newsbackend.service.scrape;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface DownloadPageService {
    public InputStream getPageInputStream(URL url) throws IOException;
}
