package com.example.newsbackend.service;

import com.example.newsbackend.exception.PageValidatorException;
import com.example.newsbackend.entity.sites.RegisteredSite;

public interface PageValidatorService {
    public RegisteredSite validatePage(String url) throws PageValidatorException;
}
