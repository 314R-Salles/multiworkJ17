package com.psalles.multiworkJ17.controllers;

import com.psalles.multiworkJ17.entities.History;
import com.psalles.multiworkJ17.mappers.GenericMapper;
import com.psalles.multiworkJ17.models.TurnDto;
import com.psalles.multiworkJ17.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@RestController
@RequestMapping("/scrabble")
public class ScrabbleController {

    final GenericMapper mapper;
    final HistoryRepository repo;



    @Autowired
    public ScrabbleController(GenericMapper mapper, HistoryRepository repo) {
        this.mapper = mapper;
        this.repo = repo;
    }

    // TEMP MOCK
    @GetMapping("")
    public List generate(String id) {
        TurnDto tour = TurnDto.builder().c1(7).c2(5).transpose(false).mot("carotte").jokers(emptyList()).pioche(List.of("a", "z", "e", "r", "t", "y", "u"))
                .lettresReposees(emptyList()).build();

        History a = new History();
        a.setId(1);
        a.setRoomId(1);
        a.setTour(mapper.formatRequestToJson(tour));
        repo.save(a);
        return emptyList();
    }

    // TEMP MOCK
    @GetMapping("/turns")
    public List getTurns(String id) {
        List<History> list = repo.findAll();
        return list.stream().map(History::getTour).map(a-> mapper.parseObjectFromJson(a, TurnDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/rooms")
    public List list(String id) {
        return emptyList();
    }

    @PostMapping("/rooms")
    public Integer create(String name, String pwd) {
        return 0; // room Id?
    }

    @PutMapping("/rooms")
    public Integer join(String playerUuid, String name, String pwd) {
        return 0; // room Id?
    }

}
