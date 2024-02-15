package com.psalles.multiworkJ17.krosmaga.dtos.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NameUpdateRequest {
    private String roomId;
    private String name;
}
