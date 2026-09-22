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
@Table(name="entrenamiento")
public class Entrenamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "tiempo_sesion")
    private LocalTime tiempoSesion; //en horas:minutos:segundos
    @Column(name = "potencia_media")
    private int potenciaMedia;
    @Column(name = "NP")
    private int NP;
    @JsonIgnore
    @JoinColumn(name = "ciclista_Id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Ciclista ciclista;

    public Entrenamiento(LocalDate fecha, LocalTime tiempoSesion, int potenciaMedia, int np) {
        this.fecha = fecha;
        this.tiempoSesion = tiempoSesion;
        this.potenciaMedia = potenciaMedia;
        this.NP = np;
    }
    public static Entrenamiento of(LocalDate fecha, LocalTime tiempoSesion, int pmedia, int np){
        return new Entrenamiento(fecha, tiempoSesion, pmedia, np);
    }
    @Override
    public String toString() {
        return "Entrenamiento [id=" + id + ", fecha=" + fecha + ", tiempoSesion=" + tiempoSesion + ", potenciaMedia="
                + potenciaMedia + ", NP=" + NP + "]";
    }

    
}
