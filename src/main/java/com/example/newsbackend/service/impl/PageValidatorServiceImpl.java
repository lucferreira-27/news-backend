package com.example.newsbackend.service.impl;

import com.example.newsbackend.exception.PageValidatorException;
import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.repository.sites.RegisteredSiteRepository;
import com.example.newsbackend.service.PageValidatorService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service
public class PageValidatorServiceImpl implements PageValidatorService {
    private final RegisteredSiteRepository registeredSiteRepository;

    public PageValidatorServiceImpl(RegisteredSiteRepository registeredSiteRepository) {
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
