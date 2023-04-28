package com.psalles.multiworkJ17.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnDto {

    private String id;
    private String roomId;

    private String mot;
    private Boolean transpose;
    private Integer c1;
    private Integer c2;
    private List<String> pioche;
    private List<String> lettresReposees;
    private List<Joker> jokers;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Joker {
        private String c1;
        private String c2;
        private String lettre;

    }

}
