package com.performancemeasurements.performance_measurement.entities;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "coach")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "emailAddress")
    private String emailAddress;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "coach")
    private List<Cyclist> listCyclist;

    public Coach(String name, String lastName, String emailAddress) {
        this.name = name;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Coach [id=" + id + ", name=" + name + ", lastName=" + lastName
                + ", emailAddress=" + emailAddress + "]";
    }

}
