package com.performancemeasurements.performance_measurement.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class IncrementalTestDTO {
    private int id;
    private LocalDate date;
    private String vo2max;
    private String vt1;
    private String vt2;
}
