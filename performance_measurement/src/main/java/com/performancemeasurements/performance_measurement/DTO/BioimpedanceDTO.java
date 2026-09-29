package com.performancemeasurements.performance_measurement.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BioimpedanceDTO {
    private int id;
    private LocalDate date;
    private double weight;
    private double height;
    private double percentageFat;
    private double percentageMuscle;
    private double percentageWater;
}