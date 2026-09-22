package com.performancemeasurements.performance_measurement.entities;

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
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "prueba_esfuerzo")
public class PruebaEsfuerzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date")
    private String date;
    @Column(name = "vo2max")
    private String vo2max;
    @Column (name = "vt1")
    private String vt1;
    @Column(name = "vt2")
    private String vt2;
    @JsonIgnore
    @JoinColumn(name = "ciclista_Id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Ciclista ciclista;

    public PruebaEsfuerzo(String date, String vo2max, String vt1, String vt2) {
        this.date = date;
        this.vo2max = vo2max;
        this.vt1 = vt1;
        this.vt2 = vt2;
    }

    @Override
    public String toString() {
        return "PruebaEsfuerzo [id=" + id + ", date=" + date + ", vo2max=" + vo2max + ", vt1=" + vt1 + ", vt2=" + vt2
                + "]";
    }
}
