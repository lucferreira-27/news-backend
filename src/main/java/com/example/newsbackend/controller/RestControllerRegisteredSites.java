package com.example.newsbackend.controller;

import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.controller.dtos.RegisteredSiteDto;
import com.example.newsbackend.exception.BadRequestException;
import com.example.newsbackend.service.RegisteredSitesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sites")
public class RestControllerRegisteredSites {

    private final RegisteredSitesService registeredSitesService;

    public RestControllerRegisteredSites(RegisteredSitesService registeredSitesService) {
        this.registeredSitesService = registeredSitesService;
    }

    @PostMapping("/add")
    public ResponseEntity<RegisteredSite> addSite(@RequestBody @Validated RegisteredSiteDto registeredSite, Errors errors) {

        if (errors.hasErrors()) {
            String msg = errors.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(","));
            throw new BadRequestException(msg);
        }

        RegisteredSite newRegisteredSite = registeredSitesService.saveSite(registeredSite);
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
                .body(registeredSitesService.findAllSites());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<RegisteredSite> getSiteById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(registeredSitesService.findSiteById(id));
    }

    @GetMapping("/find/filter")
    public ResponseEntity<RegisteredSite> getSiteByFilterURL(@RequestParam String url) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(registeredSitesService.findSiteByUrl(url));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RegisteredSite> updateSite(@PathVariable Long id, @RequestBody RegisteredSiteDto registeredSiteDto) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(registeredSitesService.updateSite(id,registeredSiteDto));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RegisteredSite> deleteSite(@PathVariable Long id) {
        registeredSitesService.deleteSiteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<RegisteredSite> deleteAllSites() {
        registeredSitesService.deleteAllSites();
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
