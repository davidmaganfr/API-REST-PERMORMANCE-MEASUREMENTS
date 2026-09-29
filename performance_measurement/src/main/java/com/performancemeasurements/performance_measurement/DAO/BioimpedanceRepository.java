package com.performancemeasurements.performance_measurement.DAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.performancemeasurements.performance_measurement.entities.Bioimpedance;

public interface BioimpedanceRepository extends JpaRepository<Bioimpedance, Integer> {
    List<Bioimpedance> findAllBioimpedancesByCyclistId(int cyclistId);
    Optional<Bioimpedance> findByIdAndCyclistId(int bioimpedanceId, int cyclistId);
    List<Bioimpedance> findByCyclistIdAndDate(int cyclistId, LocalDate date);
}