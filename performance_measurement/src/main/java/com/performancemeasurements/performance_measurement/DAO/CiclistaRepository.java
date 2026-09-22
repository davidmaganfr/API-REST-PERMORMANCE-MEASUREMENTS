package com.performancemeasurements.performance_measurement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.performancemeasurements.performance_measurement.entities.Ciclista;
import java.util.List;


public interface CiclistaRepository extends JpaRepository<Ciclista, Integer> {
   List<Ciclista> findByNombre(String nombre);
   List<Ciclista> findByApellido(String apellido);
}
