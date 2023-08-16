package com.psalles.multiworkJ17.krosmaga;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@Table(name = "KM_Player")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bddId;

    @ManyToOne
    private Room roomId;

    @Column
    private String uuid;
    @Column
    private String name;
    @Column
    private Integer d1;
    @Column
    private Integer d2;
    @Column
    private Integer d3;
    @Column
    private Integer ban;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(bddId, player.bddId) && Objects.equals(roomId, player.roomId) && Objects.equals(uuid, player.uuid) && Objects.equals(name, player.name) && Objects.equals(d1, player.d1) && Objects.equals(d2, player.d2) && Objects.equals(d3, player.d3) && Objects.equals(ban, player.ban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bddId, roomId, uuid, name, d1, d2, d3, ban);
    }

    @Override
    public String toString() {
        return "Player{" +
                "bddId=" + bddId +
                ", roomId=" + roomId.getRoom() +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", d1=" + d1 +
                ", d2=" + d2 +
                ", d3=" + d3 +
                ", ban=" + ban +
                '}';
    }
}
