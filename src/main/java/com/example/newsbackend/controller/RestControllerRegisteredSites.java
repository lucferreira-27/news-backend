package com.example.newsbackend.controller;

import com.example.newsbackend.repository.sites.RegisteredSite;
import com.example.newsbackend.repository.sites.RegisteredSiteForm;
import com.example.newsbackend.service.BadRequestException;
import com.example.newsbackend.service.RegisteredSitesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sites")
public class RestControllerRegisteredSites {

    private final RegisteredSitesService registeredSitesService;

    public RestControllerRegisteredSites(RegisteredSitesService registeredSitesService) {
        this.registeredSitesService = registeredSitesService;
    }

    @PostMapping("/add")
    public ResponseEntity<RegisteredSite> addSite(@RequestBody @Validated RegisteredSiteForm registeredSite, Errors errors) {

        if(errors.hasErrors()) {
           String msg = errors.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(","));
            throw new BadRequestException(msg);
        }

        RegisteredSite newRegisteredSite = registeredSitesService.save(registeredSite);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromPath("/api/v1/sites/find/{id}")
                        .buildAndExpand(newRegisteredSite.getId())
                        .toUri())
                .build();
    }

    @GetMapping("/find/all")
    public ResponseEntity<Iterable<RegisteredSite>> getAllSites() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(registeredSitesService.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<RegisteredSite> getSiteById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(registeredSitesService.findById(id));
    }

    @GetMapping("/find/filter")
    public ResponseEntity<RegisteredSite> getSiteByFilterURL(@RequestParam String url) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(registeredSitesService.findByUrl(url));
    }

    @PutMapping("/update")
    public ResponseEntity<RegisteredSite> updateSite(RegisteredSite registeredSite) {
        if (registeredSitesService.existsById(registeredSite.getId())) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(registeredSitesService.save(registeredSite));
        }
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromPath("/api/v1/sites/find/{id}")
                        .buildAndExpand(registeredSite.getId())
                        .toUri())
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RegisteredSite> deleteSite(@PathVariable Long id) {
        registeredSitesService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<RegisteredSite> deleteAllSites() {
        registeredSitesService.deleteAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
