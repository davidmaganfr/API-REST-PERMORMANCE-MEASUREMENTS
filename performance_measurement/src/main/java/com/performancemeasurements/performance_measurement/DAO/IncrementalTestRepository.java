package com.performancemeasurements.performance_measurement.DAO;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.performancemeasurements.performance_measurement.entities.IncrementalTest;

public interface IncrementalTestRepository extends JpaRepository<IncrementalTest, Integer>{
    List<IncrementalTest> findByDate(LocalDate date);
    List<IncrementalTest> findByCyclistId(int cyclistId);
    List<IncrementalTest> findByCyclistIdAndDate(int cyclistId, String date);
}
