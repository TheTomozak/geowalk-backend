package com.example.geowalk.models.entities;

import javax.persistence.Entity;

@Entity
public class SwearWord extends EntityBase {

    private String value;

    public SwearWord() {
    }

    public SwearWord(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
