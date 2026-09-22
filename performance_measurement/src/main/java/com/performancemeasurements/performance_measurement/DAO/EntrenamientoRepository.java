package com.performancemeasurements.performance_measurement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.performancemeasurements.performance_measurement.entities.Entrenamiento;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface EntrenamientoRepository extends JpaRepository<Entrenamiento, Integer>{
    List<Entrenamiento> findByFecha(LocalDate fecha);
    List<Entrenamiento> findByCiclistaId(int ciclistaId);
    Optional<Entrenamiento> findByIdAndCiclistaId(int entrenamientoId, int ciclistaId);
    List<Entrenamiento> findByCiclistaIdAndFecha(int ciclistaId, LocalDate fecha);
}
