package com.psalles.multiworkJ17.krosmaga;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> {

//    Room findTopRoomByPlayer1OrderByLastUpdated(String id);

}
