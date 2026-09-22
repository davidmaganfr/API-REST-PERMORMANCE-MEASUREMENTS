package com.performancemeasurements.performance_measurement.DAO;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.performancemeasurements.performance_measurement.entities.PruebaEsfuerzo;

public interface PruebaEsfuerzoRepository extends JpaRepository<PruebaEsfuerzo, Integer>{
    List<PruebaEsfuerzo> findByFecha(LocalDate fecha);
    List<PruebaEsfuerzo> findByCiclistaId(int ciclistaId);
    List<PruebaEsfuerzo> findByCiclistaIdAndDate(int ciclistaId, String fecha);
}
