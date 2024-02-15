package com.psalles.multiworkJ17.krosmaga.repositories;


import com.psalles.multiworkJ17.krosmaga.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<Player, String> {

    @Query(value = "SELECT * FROM km_player player LEFT JOIN km_room room on player.room_id = room.room WHERE player.uuid =?1 ORDER BY room.last_updated DESC LIMIT 1",
            nativeQuery = true)
    Player findByUuidOrderByLastUpdated(String uuid);
}
