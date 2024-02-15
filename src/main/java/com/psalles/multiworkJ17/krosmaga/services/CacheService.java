package com.psalles.multiworkJ17.krosmaga.services;

import com.psalles.multiworkJ17.krosmaga.client.AnkamaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

    private final AnkamaClient ankamaClient;

    public CacheService(AnkamaClient ankamaClient) {
        this.ankamaClient = ankamaClient;
    }

    // Gestion du cache du SID. Suppression toutes les 12h
    @Cacheable(value = "SID")
    public String getCachedSID() {
        log.info("Cacheable SID");
        return ankamaClient.getSID();
    }


    @CacheEvict("SID")
    public void deleteSID() {
        // empty method
    }


}
