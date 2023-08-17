package com.psalles.multiworkJ17.krosmaga;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "km_room")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @Column
    private String room;
    @Column
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "roomId"
    )
    @JsonIgnore
    private List<Player> player = new ArrayList<>();
    @Column
    private LocalDateTime lastUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room1 = (Room) o;
        return Objects.equals(room, room1.room) && Objects.equals(player, room1.player) && Objects.equals(lastUpdated, room1.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, player, lastUpdated);
    }

    @Override
    public String toString() {
        return "Room{" +
                "room='" + room + '\'' +
                ", player=" + player +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
