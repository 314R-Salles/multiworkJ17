package com.psalles.multiworkJ17.krosmaga.dtos.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Playerdto {
    private Long bddId;
    private String uuid; // A ne renvoyer qu'à l'user concerné, sinon faille de sécurité.
    private String name;
    private Integer d1;
    private Integer d2;
    private Integer d3;
    private Integer ban;

}
