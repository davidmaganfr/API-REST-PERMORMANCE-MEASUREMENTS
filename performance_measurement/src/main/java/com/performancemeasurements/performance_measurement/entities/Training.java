package com.performancemeasurements.performance_measurement.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(of={"id"})
@Entity
@Table(name="training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "time_sesion")
    private LocalTime totalTimeSesion; //en horas:minutos:segundos
    @Column(name = "avg_power")
    private int avgPower;
    @Column(name = "NP")
    private int NP;
    @JoinColumn(name = "cyclist_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Cyclist cyclist;

    public Training(LocalDate date, LocalTime totalTimeSesion, int avgPower, int NP) {
        this.date = date;
        this.totalTimeSesion = totalTimeSesion;
        this.avgPower = avgPower;
        this.NP = NP;
    }
    public static Training of(LocalDate date, LocalTime totalTimeSesion, int avgPower, int NP){
        return new Training(date, totalTimeSesion, avgPower, NP);
    }
    @Override
    public String toString() {
        return "Entrenamiento [id=" + id + ", date=" + date + ", totalTimeSesion=" + totalTimeSesion + ", avgPower="
                + avgPower + ", NP=" + NP + "]";
    }

    
}
