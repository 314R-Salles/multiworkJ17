package com.psalles.multiworkJ17.krosmaga.dtos.entities;


import com.psalles.multiworkJ17.krosmaga.dtos.entities.Playerdto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {
    private String room;
    private List<Playerdto> player = new ArrayList<>();
    private LocalDateTime lastUpdated;
    private boolean picksDone;
    private boolean bansDone;

    @Override
    public String toString() {
        return "Room{" +
                "room='" + room + '\'' +
                ", player=" + player +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
