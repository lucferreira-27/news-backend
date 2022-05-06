package com.example.newsbackend.service;

import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.repository.sites.RegisteredSiteDto;
import com.example.newsbackend.repository.sites.RegisteredSiteRepository;
import com.example.newsbackend.util.RegisteredSiteDtoMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegisteredSitesServiceImpl implements RegisteredSitesService {

    private final RegisteredSiteRepository registeredSiteRepository;
    private final RegisteredSiteDtoMapper registeredSiteDtoMapper;
    public RegisteredSitesServiceImpl(RegisteredSiteRepository registeredSiteRepository,
                                      RegisteredSiteDtoMapper  registeredSiteDtoMapper) {
        this.registeredSiteRepository = registeredSiteRepository;
        this.registeredSiteDtoMapper = registeredSiteDtoMapper;
    }


    @Override
    public RegisteredSite saveSite(RegisteredSiteDto siteDto) {
        RegisteredSite site = RegisteredSite.fromRegisteredSiteDto(siteDto);
        return saveSite(site);
    }

    @Override
    public RegisteredSite updateSite(Long id,RegisteredSiteDto site) {
        RegisteredSite registeredSite = findSiteById(id);
        RegisteredSite updatedSite = registeredSiteDtoMapper.updateWithNullAsNoChange(site,registeredSite);
        return saveSite(updatedSite);
    }


    @Override
    public RegisteredSite saveSite(RegisteredSite site) {
        if(registeredSiteRepository.existsByUrl(site.getUrl())) {
            throw new ResourceAlreadyExistsException("Site with url \"" + site.getUrl() + "\" already exists");
        }
        return registeredSiteRepository.save(site);
    }

    @Override
    public RegisteredSite findSiteById(Long id) {
        Optional<RegisteredSite> optional = registeredSiteRepository.findById(id);
        RegisteredSite site = optional.orElseThrow(() -> new ResourceNotFoundException("Site with ID \""+id+"\" not found"));
        return site;
    }

    @Override
    public RegisteredSite findSiteByUrl(String url) {
        Optional<RegisteredSite> optional = registeredSiteRepository.findByUrl(url);
        RegisteredSite site = optional.orElseThrow(() -> new ResourceNotFoundException("Site with URL \""+url+"\" not found"));
        return site;
    }

    @Override
    public List<RegisteredSite> findAllSites() {
        List<RegisteredSite> registeredSites = registeredSiteRepository.findAll();
        if(registeredSites.isEmpty()) {
            throw new ResourceNotFoundException("No registered sites found");
        }
        return registeredSites;
    }

    @Override
    public void deleteSiteById(Long id) {
        if(!siteExistsById(id)) {
            throw new ResourceNotFoundException("Registered site with id \"" + id +"\" not found");
        }
        registeredSiteRepository.deleteById(id);
    }

    @Override
    public void deleteAllSites() {
        registeredSiteRepository.deleteAll();
    }

    @Override
    public boolean siteExistsById(Long id) {
        return registeredSiteRepository.existsById(id);
    }
}
