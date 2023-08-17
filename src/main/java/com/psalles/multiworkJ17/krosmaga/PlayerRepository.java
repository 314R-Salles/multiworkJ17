package com.psalles.multiworkJ17.krosmaga;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, String> {

    @Query(value = "SELECT * FROM km_player player LEFT JOIN km_room room on player.room_id = room.room WHERE player.uuid =?1 ORDER BY room.last_updated DESC LIMIT 1",
            nativeQuery = true)
    Optional<Player> findByUuidOrderByLastUpdated(String uuid);
}
