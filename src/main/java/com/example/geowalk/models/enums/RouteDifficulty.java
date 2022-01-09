package com.example.geowalk.models.enums;

import java.util.List;

public enum RouteDifficulty {
    EASY, MEDIUM, HARD;

    public static List<String> getAllValues() {
        return List.of("EASY", "MEDIUM", "HARD");
    }
}
