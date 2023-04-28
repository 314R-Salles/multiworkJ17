package com.psalles.multiworkJ17.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class History {
    @Id
    @Column
    private Integer id;
    @Column
    private Integer roomId;
    @Column(columnDefinition="TEXT")
    private String tour;

}
