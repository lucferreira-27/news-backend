package com.example.newsbackend.service;

import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.repository.sites.RegisteredSiteForm;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RegisteredSitesService {
    RegisteredSite save(RegisteredSiteForm site);
    RegisteredSite save(RegisteredSite site);
    RegisteredSite findById(Long id);
    RegisteredSite findByUrl(String url);
    List<RegisteredSite> findAll();
    void deleteById(Long id);
    void deleteAll();
    boolean existsById(Long id);
}
