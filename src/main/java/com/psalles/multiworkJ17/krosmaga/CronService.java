package com.psalles.multiworkJ17.krosmaga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronService {
    private final KMService kmService;

    @Autowired
    public CronService(KMService kmService) {
        this.kmService = kmService;
    }

    @Scheduled(fixedDelay = 12 * 60 * 60 * 1000)
    public void cacheUpdate() {
        kmService.deleteSID();
    }
}
