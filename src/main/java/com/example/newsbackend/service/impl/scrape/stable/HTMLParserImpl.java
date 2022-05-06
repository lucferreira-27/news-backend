package com.example.newsbackend.service.impl.scrape.stable;

import com.example.newsbackend.entity.sites.SelectorQuery;
import com.example.newsbackend.exception.SelectorQueryException;
import com.example.newsbackend.service.scrape.HTMLParserService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class HTMLParserImpl implements HTMLParserService {


    public List<ParseValues> parse(String html, List<SelectorQuery> scrapeQueries) {
        Document document = createDocument(html);
        List<ParseValues> parseValues = new ArrayList<>();

        for (SelectorQuery selectorQuery : scrapeQueries) {
            List<String> values = selectValue(document, selectorQuery);
            if (parseValues.size() < values.size()) {
                maximizeParseValuesList(parseValues, values.size());
            }
            for (int i = 0; i < values.size(); i++) {
                parseValues.get(i).addValue(selectorQuery.getAttribute(),values.get(i));
            }
        }


        return parseValues;
    }

    private void maximizeParseValuesList(List<ParseValues> parseValues, int size) {
        IntStream.range(parseValues.size(), size)
                .mapToObj(i -> new ParseValues())
                .forEach(parseValues::add);
    }

    private Document createDocument(String html) {
        if(html == null || html.isEmpty()) {
            throw new IllegalArgumentException("Html is null or empty");
        }
        return Jsoup.parse(html);
    }

    private List<String> selectValue(Document document, SelectorQuery selectorQuery) {
        String query = selectorQuery.getSelector();
        Elements elements = document.select(query);
        if(elements.isEmpty()) {
            throw new SelectorQueryException("No elements found for selector: " + query);
        }
        List<String> values = elements.stream()
                .map(element -> getAttributeValue(element, selectorQuery.getAttribute()))
                .collect(Collectors.toList());
        return values;
    }

    private String getAttributeValue(Element element, String attribute) {

        if (attribute.equals("text")) {
            return getInnerText(element);
        }
        String text = element.attr(attribute);
        return text;
    }

    private String getInnerText(Element element) {
        return element.text();
    }


}
