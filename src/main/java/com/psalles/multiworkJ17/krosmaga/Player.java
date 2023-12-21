package com.psalles.multiworkJ17.krosmaga;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "km_player")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bddId;

    @ManyToOne
    @JoinColumn(name = "roomId")
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

    @Column
    private Boolean validUsername = false;

    public boolean isValidUsername() {
        return validUsername != null ? validUsername : false;
    }

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
                ", valid=" + validUsername +
                '}';
    }
}
