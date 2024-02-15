package com.psalles.multiworkJ17.krosmaga.dtos.io;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PickGodRequest {
    private String roomId;
    private String name;
    private List<Integer> picks;
}


