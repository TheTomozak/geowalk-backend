package com.example.geowalk.models.dto.responses;

public class TagResDto {

    private Long id;
    private String name;
    private Long occurrenceNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOccurrenceNumber() {
        return occurrenceNumber;
    }

    public void setOccurrenceNumber(Long occurrenceNumber) {
        this.occurrenceNumber = occurrenceNumber;
    }
}
