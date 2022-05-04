package com.example.newsbackend.service;

import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.repository.sites.RegisteredSiteRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class PageValidator {
    private final RegisteredSiteRepository registeredSiteRepository;

    public PageValidator(RegisteredSiteRepository registeredSiteRepository) {
        this.registeredSiteRepository = registeredSiteRepository;
    }

    public RegisteredSite validatePage(String url) throws PageValidatorException {
        String urlDomain = url.substring(0, url.indexOf("/", 8));
        RegisteredSite registeredSite = getRegisteredSiteByURL(urlDomain)
                .orElseThrow(() -> new PageValidatorException("No registered site found for url: " + url));

        return registeredSite;
    }

    private Optional<RegisteredSite> getRegisteredSiteByURL(String url) {
        Iterator<RegisteredSite> iterator = registeredSiteRepository.findByUrlContaining(url).iterator();
        if (!iterator.hasNext()) {
            return Optional.empty();
        }

        return Optional.of(iterator.next());

    }

}
