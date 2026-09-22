package com.performancemeasurements.performance_measurement.Controllers;

import com.performancemeasurements.performance_measurement.PerformanceMeasurementApplication;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.performancemeasurements.performance_measurement.DAO.CiclistaRepository;
import com.performancemeasurements.performance_measurement.DAO.PruebaEsfuerzoRepository;
import com.performancemeasurements.performance_measurement.entities.Ciclista;
import com.performancemeasurements.performance_measurement.entities.PruebaEsfuerzo;

@RestController
@RequestMapping("/ciclistas/{ciclistaId}/testincremental")
public class PruebaEsfuerzoController {

    private final PerformanceMeasurementApplication performanceMeasurementApplication;

    @Autowired
    private PruebaEsfuerzoRepository pruebaEsfuerzoRepository;

    @Autowired
    private CiclistaRepository ciclistaRepository;

    PruebaEsfuerzoController(PerformanceMeasurementApplication performanceMeasurementApplication) {
        this.performanceMeasurementApplication = performanceMeasurementApplication;
    }

    @GetMapping
    public List<PruebaEsfuerzo> findByCiclista(
            @PathVariable("ciclistaId") int ciclistaId) {

        obtenerCiclista(ciclistaId);

        return pruebaEsfuerzoRepository.findByCiclistaId(ciclistaId);
    }

    @GetMapping("/{pruebaId}")
    public PruebaEsfuerzo findById(
            @PathVariable("ciclistaId") int ciclistaId,
            @PathVariable("pruebaId") int pruebaId) {

        obtenerCiclista(ciclistaId);

        return pruebaEsfuerzoRepository
                .findById(pruebaId)
                .filter(prueba -> prueba.getCiclista().getId() == ciclistaId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No existe esa prueba para el ciclista"));
    }

    @GetMapping("/fecha/{date}")
    public List<PruebaEsfuerzo> findByDate(
            @PathVariable("ciclistaId") int ciclistaId,
            @PathVariable("date") String date) {

        obtenerCiclista(ciclistaId);

        return pruebaEsfuerzoRepository
                .findByCiclistaIdAndDate(ciclistaId, date);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PruebaEsfuerzo create(
            @PathVariable("ciclistaId") int ciclistaId,
            @RequestBody PruebaEsfuerzo pruebaEsfuerzo) {

        Ciclista ciclista = obtenerCiclista(ciclistaId);

        pruebaEsfuerzo.setCiclista(ciclista);

        return pruebaEsfuerzoRepository.save(pruebaEsfuerzo);
    }

    @PutMapping("/{pruebaId}")
    public PruebaEsfuerzo update(
            @PathVariable("ciclistaId") int ciclistaId,
            @PathVariable("pruebaId") int pruebaId,
            @RequestBody PruebaEsfuerzo pruebaEsfuerzo) {

        PruebaEsfuerzo pruebaFiltrada = pruebaEsfuerzoRepository
                .findById(pruebaId)
                .filter(pruebaEncontrada ->
                        pruebaEncontrada.getCiclista().getId() == ciclistaId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No existe esa prueba para el ciclista"));

        pruebaFiltrada.setDate(pruebaEsfuerzo.getDate());
        pruebaFiltrada.setVo2max(pruebaEsfuerzo.getVo2max());
        pruebaFiltrada.setVt1(pruebaEsfuerzo.getVt1());
        pruebaFiltrada.setVt2(pruebaEsfuerzo.getVt2());

        return pruebaFiltrada != null ? pruebaEsfuerzoRepository.save(pruebaFiltrada) : null;
    }

    @DeleteMapping("/{pruebaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("ciclistaId") int ciclistaId,
            @PathVariable("pruebaId") int pruebaId) {

        PruebaEsfuerzo pruebaFiltrada = pruebaEsfuerzoRepository
                .findById(pruebaId)
                .filter(test ->
                        test.getCiclista().getId() == ciclistaId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No existe esa prueba para el ciclista"));
        
        if (pruebaFiltrada != null) {
            pruebaEsfuerzoRepository.delete(pruebaFiltrada);
        }  
    }

    private Ciclista obtenerCiclista(int ciclistaId) {
        return ciclistaRepository.findById(ciclistaId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No existe el ciclista con id: " + ciclistaId));
    }
}
