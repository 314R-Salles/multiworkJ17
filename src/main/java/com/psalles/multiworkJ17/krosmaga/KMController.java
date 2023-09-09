package com.psalles.multiworkJ17.krosmaga;

import com.psalles.multiworkJ17.exceptions.ResourceNotFoundException;
import com.psalles.multiworkJ17.mappers.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@RestController
@RequestMapping("/km")
public class KMController {

    final GenericMapper mapper;
    final RoomRepository roomRepository;
    final PlayerRepository playerRepository;

    @Autowired
    public KMController(GenericMapper mapper,
                        RoomRepository roomRepository,
                        PlayerRepository playerRepository
    ) {
        this.mapper = mapper;
        this.roomRepository = roomRepository;
        this.playerRepository = playerRepository;
    }


    public String getName(String playerId, String defaultValue) {
        Optional<Player> p = playerRepository.findByUuidOrderByLastUpdated(playerId);
        if (p.isEmpty()) {
            return defaultValue;
        } else {
            String potentialName = p.get().getName();
            if (potentialName.equals("Joueur 1") || potentialName.equals("Joueur 2")) {
                return defaultValue;
            } else {
                return potentialName;
            }
        }

    }

    // le joueur crée une room (id généré dans le front)
    // l'id servira dans l'url. Pour y accéder + refresh
    @PostMapping("/room/{roomId}")
    public String generate(@RequestHeader("km_token") String playerId, @PathVariable String roomId) {
        Room room = Room.builder().room(roomId).lastUpdated(LocalDateTime.now()).build();
        roomRepository.save(room);
        Player player = Player.builder().roomId(room).name(getName(playerId, "Joueur 1")).uuid(playerId).build();
        playerRepository.save(player);
        room.setPlayer(singletonList(player));
        roomRepository.save(room);
        return roomId;
    }

    //    https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    // charger la room entière pas que les dieux
    @GetMapping("room/{roomId}")
    public RoomDto getRoom(@RequestHeader("km_token") String playerId, @PathVariable String roomId) {

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room introuvable"));
        List<Player> players = room.getPlayer();

        int registeredPlayers = players.size();
        boolean onlyOnePlayer = registeredPlayers == 1;
        boolean allPlayersRegistered = registeredPlayers == 2;

        boolean picksDone = allPlayersRegistered && players.get(0).getD1() != null && players.get(1).getD1() != null;
        boolean bansDone = allPlayersRegistered && players.get(0).getBan() != null && players.get(1).getBan() != null;

        Optional<Player> playerInRoom = players.stream().filter(player -> player.getUuid().equals(playerId)).findAny();

        // pas encore fait les picks
        if (!picksDone) {
            if (playerInRoom.isPresent()) {
                // Le joueur qui interroge est dans la room, on affiche ses données + le nom du potentiel J2
                return mapPartialRoom(room, playerId);
            } else {
                // le joueur ne fait pas partie de la room
                if (onlyOnePlayer) {
                    // on avait qu'un joueur, donc on enregistre ce nouveau joueur
                    Optional<Player> p = playerRepository.findByUuidOrderByLastUpdated(playerId);
                    Player newPlayer = Player.builder().roomId(room).name(getName(playerId, "Joueur 2")).uuid(playerId).build();
                    playerRepository.save(newPlayer);
                    room.getPlayer().add(newPlayer);
                    roomRepository.save(room); // ligne inutile? pareil la suivante? // Non il faut mettre à jour le lastUpdated au passage.
                    Room updatedRoom = roomRepository.findById(roomId).get();
                    // on renvoie les données de la room + le nom du J1
                    return mapPartialRoom(updatedRoom, playerId);
                } else {
                    // On a un joueur 3 qui ne fait pas partie de la room, on renvoie que les noms des joueurs.
                    return mapToSpecRoom(room);
                }
            }
        } else if (!bansDone) {
            // les pick sont faits :
            // les bans ne sont pas validés des 2 cotés, on affiche les dieux + le ban du joueur qui fait l'appel.
            return mapRoomWithoutBans(room, playerId);
        } else {
            // on a toutes les infos, tout le monde charge tout.
            return mapRoomWithBans(room, playerId);
        }
    }

    @GetMapping("meta/{roomId}")
    public RoomDto getRoomForCrawlers(@PathVariable String roomId) {

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room introuvable"));
        List<Player> players = room.getPlayer();

        int registeredPlayers = players.size();
        boolean onlyOnePlayer = registeredPlayers == 1;
        boolean allPlayersRegistered = registeredPlayers == 2;

        boolean picksDone = allPlayersRegistered && players.get(0).getD1() != null && players.get(1).getD1() != null;
        boolean bansDone = allPlayersRegistered && players.get(0).getBan() != null && players.get(1).getBan() != null;

        // pas encore fait les picks
        if (!picksDone) {
            if (onlyOnePlayer) {
                Player newPlayer = Player.builder().name("Joueur 2").build();
                room.getPlayer().add(newPlayer);
                return mapPartialRoom(room, null);
            } else {
                return mapToSpecRoom(room);
            }
        } else if (!bansDone) {
            return mapRoomWithoutBans(room, null);
        } else {
            return mapRoomWithBans(room, null);
        }
    }


    //        // le joueur pick ses dieux
    @PostMapping("/gods")
    public RoomDto pickGods(@RequestHeader("km_token") String playerId, @RequestBody PickGodRequest request) {
        Room room = roomRepository.findById(request.getRoomId()).get();
        room.setLastUpdated(LocalDateTime.now());
        Player player = room.getPlayer().stream().filter(p -> p.getUuid().equals(playerId)).findFirst().get();
        player.setD1(request.getPicks().get(0));
        player.setD2(request.getPicks().get(1));
        player.setD3(request.getPicks().get(2));
        player.setName(request.getName());
        playerRepository.save(player);
        roomRepository.save(room);

        return getRoom(playerId, request.getRoomId());
    }

    //        // le joueur pick ses dieux
    @PostMapping("/ban")
    public RoomDto pickGods(@RequestHeader("km_token") String playerId, @RequestBody BanGodRequest request) {
        Room room = roomRepository.findById(request.getRoomId()).get();
        room.setLastUpdated(LocalDateTime.now());
        Player player = room.getPlayer().stream().filter(p -> p.getUuid().equals(playerId)).findFirst().get();
        player.setBan(request.getBan());
        player.setName(request.getName());
        playerRepository.save(player);
        roomRepository.save(room);
        return getRoom(playerId, request.getRoomId());
    }

    @PostMapping("/username")
    public void saveUsername(@RequestHeader("km_token") String playerId, @RequestBody NameUpdateRequest request) {
        Room room = roomRepository.findById(request.getRoomId()).get();
        room.setLastUpdated(LocalDateTime.now());
        Player player = room.getPlayer().stream().filter(p -> p.getUuid().equals(playerId)).findFirst().get();
        player.setName(request.getName());
        playerRepository.save(player);
        roomRepository.save(room);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PickGodRequest {
        private String roomId;
        private String name;
        private List<Integer> picks;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BanGodRequest {
        private String roomId;
        private String name;
        private Integer ban;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class NameUpdateRequest {
        private String roomId;
        private String name;
    }


    private RoomDto mapRoomWithoutBans(Room room, String playerId) {
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

    private RoomDto mapRoomWithBans(Room room, String playerId) {
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

    private RoomDto mapPartialRoom(Room room, String playerId) {
        List<Playerdto> playersDto = room.getPlayer().stream()
                .map(player -> {
                    Playerdto.PlayerdtoBuilder builder = Playerdto.builder()
                            .bddId(player.getBddId())
                            .name(player.getName());
                    if (player.getUuid().equals(playerId)) {
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

    private RoomDto mapToSpecRoom(Room room) {
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
