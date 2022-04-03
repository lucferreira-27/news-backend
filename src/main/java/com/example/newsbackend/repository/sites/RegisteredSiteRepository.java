package com.example.newsbackend.repository.sites;

import org.springframework.data.repository.CrudRepository;

public interface RegisteredSiteRepository extends CrudRepository<RegisteredSite, Long> {
    Iterable<RegisteredSite> findByUrl(String url);

}
