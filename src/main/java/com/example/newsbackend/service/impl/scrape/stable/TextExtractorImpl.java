package com.example.newsbackend.service.impl.scrape.stable;

import com.example.newsbackend.service.scrape.TextExtractorService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
public class TextExtractorImpl implements TextExtractorService {
    public String extractTextFromInputStream(InputStream inputStream) throws IOException {

        String html = getStringFromInputStream(inputStream);

        return html;
    }

    private String getStringFromInputStream(InputStream inputStream) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            return readString(reader);
        } catch (IOException e) {
           throw e;
        }
    }

    private String readString(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();

        int c = 0;
        while ((c = reader.read()) != -1) {
            sb.append((char) c);
        }
        return sb.toString();
    }
}
