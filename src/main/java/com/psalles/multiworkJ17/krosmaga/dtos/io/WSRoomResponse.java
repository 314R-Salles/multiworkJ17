package com.psalles.multiworkJ17.krosmaga.dtos.io;


import com.psalles.multiworkJ17.krosmaga.dtos.entities.RoomDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WSRoomResponse {
    private RoomDto room;
    private String action;

    @Override
    public String toString() {
        return "WSRoomResponse{" +
                "room='" + room + '\'' +
                ", action=" + action +
                '}';
    }
}
