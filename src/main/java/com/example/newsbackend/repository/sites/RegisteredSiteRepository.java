package com.example.newsbackend.repository.sites;

import com.example.newsbackend.entity.sites.RegisteredSite;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RegisteredSiteRepository extends CrudRepository<RegisteredSite, Long> {
    Optional<RegisteredSite> findByUrl(String url);
    Iterable<RegisteredSite> findByUrlContaining(String url);
    List<RegisteredSite> findAll();
    boolean existsByUrl(String url);


}
