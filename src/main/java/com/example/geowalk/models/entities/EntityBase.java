package com.example.geowalk.models.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }
}
