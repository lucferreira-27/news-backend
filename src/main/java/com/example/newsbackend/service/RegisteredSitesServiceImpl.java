package com.example.newsbackend.service;

import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.repository.sites.RegisteredSiteForm;
import com.example.newsbackend.repository.sites.RegisteredSiteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegisteredSitesServiceImpl implements RegisteredSitesService {

    private final RegisteredSiteRepository registeredSiteRepository;

    public RegisteredSitesServiceImpl(RegisteredSiteRepository registeredSiteRepository) {
        this.registeredSiteRepository = registeredSiteRepository;
    }


    @Override
    public RegisteredSite save(RegisteredSiteForm siteForm) {
        RegisteredSite site = RegisteredSite.fromRegisteredSiteForm(siteForm);
        return save(site);
    }

    @Override
    public RegisteredSite save(RegisteredSite site) {
        if(registeredSiteRepository.existsByUrl(site.getUrl())) {
            throw new ResourceAlreadyExistsException("Site with url \"" + site.getUrl() + "\" already exists");
        }
        return registeredSiteRepository.save(site);
    }

    @Override
    public RegisteredSite findById(Long id) {
        Optional<RegisteredSite> optional = registeredSiteRepository.findById(id);
        RegisteredSite site = optional.orElseThrow(() -> new ResourceNotFoundException("Site with ID \""+id+"\" not found"));
        return site;
    }

    @Override
    public RegisteredSite findByUrl(String url) {
        Optional<RegisteredSite> optional = registeredSiteRepository.findByUrl(url);
        RegisteredSite site = optional.orElseThrow(() -> new ResourceNotFoundException("Site with URL \""+url+"\" not found"));
        return site;
    }

    @Override
    public List<RegisteredSite> findAll() {
        List<RegisteredSite> registeredSites = registeredSiteRepository.findAll();
        if(registeredSites.isEmpty()) {
            throw new ResourceNotFoundException("No registered sites found");
        }
        return registeredSites;
    }

    @Override
    public void deleteById(Long id) {
        if(!existsById(id)) {
            throw new ResourceNotFoundException("Registered site with id \"" + id +"\" not found");
        }
        registeredSiteRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        registeredSiteRepository.deleteAll();
    }

    @Override
    public boolean existsById(Long id) {
        return registeredSiteRepository.existsById(id);
    }
}
