package com.psalles.multiworkJ17.krosmaga;

import com.psalles.multiworkJ17.commons.client.BaseHttpClient;
import com.psalles.multiworkJ17.exceptions.BusinessException;
import com.psalles.multiworkJ17.exceptions.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class KMService {
    final BaseHttpClient httpClient;
    final PlayerRepository playerRepository;


    public KMService(BaseHttpClient httpClient,
                     PlayerRepository playerRepository) {
        this.httpClient = httpClient;
        this.playerRepository = playerRepository;
    }

    // appelle ankama pour avoir un SID et skip la premiere requete qui part sur un redirect. sus
    public String getSID() {
        String SID = this.httpClient.getAnkamaSID(getAuthHeaders(null));
        this.httpClient.makeCall(HttpMethod.GET, "https://www.krosmaga.com/fr/communaute/ladder/eternel?search=toto", String.class, null, getAuthHeaders(SID));
        return SID;
    }


    // throw 400 error if unknown or save valid true on player.
    public void checkUsernameValidity(String playerId, String username) {
        Player player = playerRepository.findByUuidOrderByLastUpdated(playerId).orElseThrow(() -> new ResourceNotFoundException("no"));
        if (!player.getName().equals(username) || !player.isValidUsername()) {
            checkUsernameValidity(username);
            player.setValidUsername(true);
            playerRepository.save(player);
        }
    }

    private void checkUsernameValidity(String username) throws BusinessException {
        String SID = getCachedSID();
        String webpage = this.httpClient.makeCall(HttpMethod.GET, "https://www.krosmaga.com/fr/communaute/ladder/eternel?search=" + username, String.class, null, getAuthHeaders(SID));
        if (webpage.contains("<strong>0</strong>")) {
            throw new BusinessException("Pseudo invalide");
        }
    }


    // Gestion du cache du SID. Suppression toutes les 12h
    @Cacheable(value = "SID")
    public String getCachedSID() {
        return getSID();
    }

    @CacheEvict("SID")
    public void deleteSID() {
        // empty method
    }

    private HttpHeaders getAuthHeaders(String SID) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
        headers.add("host", "www.krosmaga.com");
        if (SID != null) headers.add("Cookie", "SID=" + SID + ";LANG=fr");
        return headers;
    }

}
