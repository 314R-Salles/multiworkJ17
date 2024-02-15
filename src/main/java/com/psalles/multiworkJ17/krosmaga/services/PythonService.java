package com.psalles.multiworkJ17.krosmaga.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Slf4j
public class PythonService {

    public void runPython(String name1, String d1, String d2, String d3banned, String name2, String d12, String d22, String d32banned, String out) {
        ProcessBuilder pb = new ProcessBuilder("/usr/bin/python3", "/project/multiworkJ17/src/main/resources/draft.py", name1, d1, d2, d3banned, name2, d12, d22, d32banned, out);
        try {
            Process p = pb.start();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = bfr.readLine()) != null) {
                log.info("Python Output: " + line);
            }
        } catch (IOException e) {
            // pas de rethrow pour pas planter le reste du fonctionnement
            log.error("FUCK", e);
        }
    }

}
