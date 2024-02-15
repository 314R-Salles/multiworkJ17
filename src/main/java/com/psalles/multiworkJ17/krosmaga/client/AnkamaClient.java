package com.psalles.multiworkJ17.krosmaga.client;

import com.psalles.multiworkJ17.commons.client.BaseHttpClient;
import com.psalles.multiworkJ17.commons.exceptions.BusinessException;
import com.psalles.multiworkJ17.krosmaga.services.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnkamaClient {

    private final BaseHttpClient httpClient;

    public AnkamaClient(BaseHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    // appelle ankama pour avoir un SID et skip la premiere requete qui part sur un redirect. sus
    public String getSID() {
        log.info("Appel Ankama SID");
        String SID = this.httpClient.getAnkamaSID(getAuthHeaders(null));
        this.httpClient.makeCall(HttpMethod.GET, "https://www.krosmaga.com/fr/communaute/ladder/eternel?search=toto", String.class, null, getAuthHeaders(SID));
        return SID;
    }


    public void checkUsernameValidity(String username, String SID) throws BusinessException {
        String webpage = this.httpClient.makeCall(HttpMethod.GET, "https://www.krosmaga.com/fr/communaute/ladder/eternel?search=" + username, String.class, null, getAuthHeaders(SID));
        if (webpage.contains("<strong>0</strong>")) {
            throw new BusinessException("Pseudo invalide");
        }
    }


    private HttpHeaders getAuthHeaders(String SID) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
        headers.add("host", "www.krosmaga.com");
        if (SID != null) headers.add("Cookie", "SID=" + SID + ";LANG=fr");
        return headers;
    }
}
