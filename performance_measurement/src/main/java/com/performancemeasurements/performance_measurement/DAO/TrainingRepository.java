package com.performancemeasurements.performance_measurement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.performancemeasurements.performance_measurement.entities.Training;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface TrainingRepository extends JpaRepository<Training, Integer>{
    List<Training> findByDate(LocalDate fecha);
    List<Training> findByCyclistId(int cyclistId);
    Optional<Training> findByIdAndCyclistId(int trainingId, int cyclistId);
    List<Training> findByCyclistIdAndDate(int cyclistId, LocalDate date);
}
