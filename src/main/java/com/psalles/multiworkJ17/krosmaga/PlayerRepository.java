package com.psalles.multiworkJ17.krosmaga;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, String> {

    @Query(value = "SELECT * FROM KM_Player player LEFT JOIN KM_Room room on player.room_id_room = room.room WHERE player.uuid =?1 ORDER BY room.last_updated DESC LIMIT 1",
            nativeQuery = true)
    Optional<Player> findByUuidOrderByLastUpdated(String uuid);
}
