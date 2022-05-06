package com.example.newsbackend.service;

import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.controller.dtos.RegisteredSiteDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RegisteredSitesService {
    RegisteredSite saveSite(RegisteredSiteDto site);
    RegisteredSite updateSite(Long id,RegisteredSiteDto site);
    RegisteredSite saveSite(RegisteredSite site);
    RegisteredSite findSiteById(Long id);
    RegisteredSite findSiteByUrl(String url);
    List<RegisteredSite> findAllSites();
    void deleteSiteById(Long id);
    void deleteAllSites();
    boolean siteExistsById(Long id);
}
