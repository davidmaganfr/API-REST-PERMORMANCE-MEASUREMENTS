package com.performancemeasurements.performance_measurement.DTO;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class TrainingDTO {
    private LocalDate date;
    private LocalTime totalTimeSesion; //en horas:minutos:segundos
    private int avgPower;
    private int NP;

}
