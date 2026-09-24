package com.performancemeasurements.performance_measurement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.performancemeasurements.performance_measurement.entities.Training;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


public interface TrainingRepository extends JpaRepository<Training, Integer>{
    List<Training> findTrainingsByDate(LocalDate fecha);
    List<Training> findTrainingsByCyclistId(int cyclistId);
    Optional<Training> findByIdAndCyclistId(int trainingId, int cyclistId);
    List<Training> findTrainingsByCyclistIdAndDate(int cyclistId, LocalDate date);
}
