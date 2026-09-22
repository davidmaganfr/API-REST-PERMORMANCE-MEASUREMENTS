package com.performancemeasurements.performance_measurement.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "ciclista")
public class Ciclista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Column (name = "edad")
    private int edad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciclista")
    private List<PruebaEsfuerzo> listaPruebasEsfuerzo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciclista")
    private List<Entrenamiento> listaEntrenamientos;


    public Ciclista(String nombreCompleto, int edad) {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
    }

    public static Ciclista of(String nombreCompleto, int edad) {
        return new Ciclista(nombreCompleto, edad);
    }

    public void addPruebaEsfuerzo(PruebaEsfuerzo prueba) {
        if (listaPruebasEsfuerzo == null) {
            listaPruebasEsfuerzo = new ArrayList<PruebaEsfuerzo>();
        }
        listaPruebasEsfuerzo.add(prueba);
        prueba.setCiclista(this);
    }

    public void addEntrenamiento(Entrenamiento entto) {
        if (listaEntrenamientos == null) {
            listaEntrenamientos = new ArrayList<Entrenamiento>();
        }
        listaEntrenamientos.add(entto);
        entto.setCiclista(this);
    }

    // public Impedancia findImpedancia(int dia, int mes, int año) {
    //     var fechaToLocalDate = LocalDate.of(año, mes, dia);
    //     return listaImpedancias.stream()
    //             .filter(impedancia -> impedancia.getFecha().isEqual(fechaToLocalDate))
    //             .findFirst()
    //             .get();
    // }

    // public Entrenamiento findEntrenamiento(int dia, int mes, int año){
    //     var fechaToLocalDate = LocalDate.of(año, mes, dia);
    //     return listaEntrenamientos.stream()
    //             .filter(antro -> antro.getFecha().isEqual(fechaToLocalDate))
    //             .findFirst()
    //             .get();
    // }
}
