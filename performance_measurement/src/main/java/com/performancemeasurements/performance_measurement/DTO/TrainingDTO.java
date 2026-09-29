package com.performancemeasurements.performance_measurement.DTO;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class TrainingDTO {
    private int id;
    private LocalDate date;
    private LocalTime totalTimeSesion;
    private int avgPower;
    private int NP;
    private int maximumPower;
    private int heartRate;
    private int avgHeartRate;
    private int maximumHeartRate;

}
