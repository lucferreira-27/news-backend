package com.example.newsbackend.util;

import com.example.newsbackend.entity.sites.RegisteredSite;
import com.example.newsbackend.controller.dtos.RegisteredSiteDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface RegisteredSiteDtoMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "domain",target ="siteConfiguration.domain")
    @Mapping(source = "name",target ="siteConfiguration.name")
    @Mapping(source = "description",target ="siteConfiguration.description")
    @Mapping(source = "logo",target ="siteConfiguration.logo")
    @Mapping(source = "language",target ="siteConfiguration.language")
    @Mapping(source = "country",target ="siteConfiguration.country")
    @Mapping(source = "scrapingType",target ="siteConfiguration.scrapingType")
    @Mapping(source = "keywords",target ="siteConfiguration.keywords")
    RegisteredSite updateWithNullAsNoChange(RegisteredSiteDto siteDto, @MappingTarget RegisteredSite site);

}
