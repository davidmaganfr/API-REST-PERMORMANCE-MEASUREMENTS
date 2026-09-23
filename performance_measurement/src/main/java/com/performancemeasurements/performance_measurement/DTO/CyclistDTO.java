package com.performancemeasurements.performance_measurement.DTO;

import lombok.Data;

@Data
public class CyclistDTO {
    private String fullname;
    private int age;
    private String category;

    public CyclistDTO(String fullname, int age) {
        this.fullname = fullname;
        this.age = age;
        this.category = calculateCategory(age);
    }

    public static String calculateCategory(int age) {
        if (age < 15) {
            return "Cadete";
        } else if (age >= 15 && age < 18) {
            return "Junior";
        } else if (age >= 18 && age < 23) {
            return "Sub23";
        } else if (age >= 23 && age < 30) {
            return "Elite";
        } else if (age >= 30 && age < 40) {
            return "Master 30";
        } else if (age >= 40 && age < 50) {
            return "Master 40";
        } else if (age >= 50 && age < 60) {
            return "Master 50";
        } else if (age >= 60) {
            return "Veterano";
        }

        return "Unknown";
    }
}
