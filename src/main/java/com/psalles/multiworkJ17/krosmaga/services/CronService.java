package com.psalles.multiworkJ17.krosmaga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronService {
    private final CacheService cacheService;

    @Autowired
    public CronService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Scheduled(fixedDelay = 12 * 60 * 60 * 1000)
    public void cacheUpdate() {
        cacheService.deleteSID();
    }
}
