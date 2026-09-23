package com.performancemeasurements.performance_measurement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.performancemeasurements.performance_measurement.entities.Cyclist;
import java.util.List;


public interface CyclistRepository extends JpaRepository<Cyclist, Integer> {
   List<Cyclist> findByFullname(String fullname);
}
