package com.performancemeasurements.performance_measurement.entities;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@EqualsAndHashCode(of = {"id"})
@Entity 
@Table(name = "bioimpedance")
public class Bioimpedance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "weight_kg")
    private double weight;
    @Column(name = "height")
    private double height;
    @Column(name = "percentage_fat")
    private double percentageFat;
    @Column(name = "percentage_muscle")
    private double percentageMuscle;
    @Column(name = "percentage_water")
    private double percentageWater;
    @JoinColumn(name = "cyclist_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Cyclist cyclist;
    
    @Override
    public String toString() {
        return "Bioimpedance [id=" + id + ", date=" + date + ", weight=" + weight + ", height=" + height
                + ", percentageFat=" + percentageFat + ", percentageMuscle=" + percentageMuscle + ", percentageWater="
                + percentageWater + ", cyclist=" + cyclist + "]";
    }
    
    


}
