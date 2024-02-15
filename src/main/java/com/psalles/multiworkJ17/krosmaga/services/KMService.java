package com.psalles.multiworkJ17.krosmaga.services;

import com.psalles.multiworkJ17.krosmaga.client.AnkamaClient;
import com.psalles.multiworkJ17.krosmaga.dtos.entities.Playerdto;
import com.psalles.multiworkJ17.krosmaga.dtos.entities.RoomDto;
import com.psalles.multiworkJ17.krosmaga.entities.Player;
import com.psalles.multiworkJ17.krosmaga.entities.Room;
import com.psalles.multiworkJ17.krosmaga.repositories.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KMService {

    private final PlayerRepository playerRepository;
    private final CacheService cacheService;
    private final AnkamaClient ankamaClient;


    public KMService(PlayerRepository playerRepository,
                     AnkamaClient ankamaClient,
                     CacheService cacheService
    ) {
        this.playerRepository = playerRepository;
        this.ankamaClient = ankamaClient;
        this.cacheService = cacheService;
    }


    public String getName(String playerId, String defaultValue) {
        Player p = playerRepository.findByUuidOrderByLastUpdated(playerId);
        if (p == null) {
            return defaultValue;
        } else {
            String potentialName = p.getName();
            if (potentialName.equals("Joueur 1") || potentialName.equals("Joueur 2")) {
                return defaultValue;
            } else {
                return potentialName;
            }
        }

    }


    public void checkUsernameValidity(String playerId, String username) {
        Player player = playerRepository.findByUuidOrderByLastUpdated(playerId);
        log.info("Comparaison du pseudo {} avec celui enregistré {}", username, player.getName());
        if (!player.getName().equalsIgnoreCase(username) || !player.isValidUsername() || Arrays.asList("Joueur 1", "Joueur 2").contains(username)) {
            String SID = cacheService.getCachedSID();
            ankamaClient.checkUsernameValidity(username, SID);
            player.setValidUsername(true);
            playerRepository.save(player);
        }
    }


    public RoomDto mapRoomWithoutBans(Room room, String playerId) {
        List<Playerdto> playersDto = room.getPlayer().stream()
                .map(player -> {
                    Playerdto.PlayerdtoBuilder builder = Playerdto.builder()
                            .bddId(player.getBddId())
                            .name(player.getName())
                            .d1(player.getD1())
                            .d2(player.getD2())
                            .d3(player.getD3());
                    if (player.getUuid().equals(playerId)) {
                        builder.ban(player.getBan())
                                .uuid(player.getUuid());
                    }
                    return builder.build();
                }).collect(Collectors.toList());

        return RoomDto.builder()
                .player(playersDto)
                .room(room.getRoom())
                .lastUpdated(room.getLastUpdated())
                .picksDone(true)
                .bansDone(false)
                .build();
    }

    public RoomDto mapRoomWithBans(Room room, String playerId) {
        List<Playerdto> playersDto = room.getPlayer().stream()
                .map(player -> {
                    Playerdto.PlayerdtoBuilder builder = Playerdto.builder()
                            .bddId(player.getBddId())
                            .name(player.getName())
                            .d1(player.getD1())
                            .d2(player.getD2())
                            .d3(player.getD3())
                            .ban(player.getBan());
                    if (player.getUuid().equals(playerId)) {
                        builder.uuid(player.getUuid());
                    }
                    return builder.build();
                }).collect(Collectors.toList());

        return RoomDto.builder()
                .player(playersDto)
                .room(room.getRoom())
                .lastUpdated(room.getLastUpdated())
                .picksDone(true)
                .bansDone(false)
                .build();
    }

    public RoomDto mapPartialRoom(Room room, String playerId) {
        List<Playerdto> playersDto = room.getPlayer().stream()
                .map(player -> {
                    Playerdto.PlayerdtoBuilder builder = Playerdto.builder()
                            .bddId(player.getBddId())
                            .name(player.getName());
                    if (player.getUuid() != null && player.getUuid().equals(playerId)) {
                        builder.uuid(player.getUuid())
                                .d1(player.getD1())
                                .d2(player.getD2())
                                .d3(player.getD3())
                                .ban(player.getBan());
                    }
                    return builder.build();
                }).collect(Collectors.toList());

        return RoomDto.builder()
                .player(playersDto)
                .room(room.getRoom())
                .lastUpdated(room.getLastUpdated())
                .picksDone(false)
                .bansDone(false)
                .build();
    }

    public RoomDto mapToSpecRoom(Room room) {
        List<Playerdto> playersDto = room.getPlayer().stream()
                .map(player -> {
                    Playerdto.PlayerdtoBuilder builder = Playerdto.builder()
                            .name(player.getName());
                    return builder.build();
                }).collect(Collectors.toList());

        return RoomDto.builder()
                .player(playersDto)
                .room(room.getRoom())
                .lastUpdated(room.getLastUpdated())
                .picksDone(false)
                .bansDone(false)
                .build();
    }
}
