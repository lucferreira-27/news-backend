package com.example.newsbackend.service.scrape.stable;

import com.example.newsbackend.service.scrape.dynamic.FirefoxSeleniumBrowser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
@Service
public class DownloadPage {
    public InputStream getPageInputStream(URL url) throws IOException {

        URLConnection conn = url.openConnection();
        conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

        return conn.getInputStream();

    }
}
