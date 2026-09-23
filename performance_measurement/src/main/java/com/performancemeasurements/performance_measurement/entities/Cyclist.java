package com.performancemeasurements.performance_measurement.entities;

import java.util.ArrayList;
import java.util.List;

import com.performancemeasurements.performance_measurement.DTO.CyclistDTO;

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
@Table(name = "cyclist")
public class Cyclist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "fullname")
    private String fullname;
    @Column (name = "age")
    private int age;
    @Column (name = "category")
    private String category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cyclist")
    private List<IncrementalTest> listIncrementalTests;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cyclist")
    private List<Training> listTrainings;


    public Cyclist(String fullname, int age) {
        this.fullname = fullname;
        this.age = age;
        this.category = CyclistDTO.calculateCategory(age);
    }

    public static Cyclist of(String fullname, int age) {
        return new Cyclist(fullname, age);
    }

    public void addPruebaEsfuerzo(IncrementalTest incrementalTest) {
        if (listIncrementalTests == null) {
            listIncrementalTests = new ArrayList<IncrementalTest>();
        }
        listIncrementalTests.add(incrementalTest);
        incrementalTest.setCyclist(this);
    }

    public void addEntrenamiento(Training training) {
        if (listTrainings == null) {
            listTrainings = new ArrayList<Training>();
        }
        listTrainings.add(training);
        training.setCyclist(this);
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
