package com.performancemeasurements.performance_measurement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class IncrementalTestDTO {
    private String date;
    private String vo2max;
    private String vt1;
    private String vt2;
}
