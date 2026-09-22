package com.performancemeasurements.performance_measurement.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.performancemeasurements.performance_measurement.DAO.CiclistaRepository;
import com.performancemeasurements.performance_measurement.DAO.EntrenamientoRepository;
import com.performancemeasurements.performance_measurement.entities.Ciclista;
import com.performancemeasurements.performance_measurement.entities.Entrenamiento;

@RestController
@RequestMapping("/ciclistas/{ciclistaId}/entrenamientos")
public class EntrenamientoController {

    @Autowired
    private EntrenamientoRepository entrenamientoRepository;

    @Autowired
    private CiclistaRepository ciclistaRepository;

    @GetMapping
    public List<Entrenamiento> findByCiclista(
            @PathVariable int ciclistaId) {

        obtenerCiclista(ciclistaId);

        return entrenamientoRepository.findByCiclistaId(ciclistaId);
    }

    @GetMapping("/{entrenamientoId}")
    public Entrenamiento findById(
            @PathVariable int ciclistaId,
            @PathVariable int entrenamientoId) {

        obtenerCiclista(ciclistaId);

        return entrenamientoRepository
                .findByIdAndCiclistaId(entrenamientoId, ciclistaId)
                .orElseThrow(() ->
                        new RuntimeException("No existe ese entrenamiento para el ciclista"));
    }

    @GetMapping("/fecha/{fecha}")
    public List<Entrenamiento> findByFecha(
            @PathVariable int ciclistaId,
            @PathVariable String fecha) {

        obtenerCiclista(ciclistaId);

        LocalDate fechaLocal = LocalDate.parse(
                fecha,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );

        return entrenamientoRepository
                .findByCiclistaIdAndFecha(ciclistaId, fechaLocal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entrenamiento create(
            @PathVariable int ciclistaId,
            @RequestBody Entrenamiento entrenamiento) {

        Ciclista ciclista = obtenerCiclista(ciclistaId);

        entrenamiento.setCiclista(ciclista);

        return entrenamientoRepository.save(entrenamiento);
    }

    @PutMapping("/{entrenamientoId}")
    public Entrenamiento update(
            @PathVariable int ciclistaId,
            @PathVariable int entrenamientoId,
            @RequestBody Entrenamiento nuevosDatos) {

        Entrenamiento entrenamiento = entrenamientoRepository
                .findByIdAndCiclistaId(entrenamientoId, ciclistaId)
                .orElseThrow(() ->
                        new RuntimeException("No existe ese entrenamiento para el ciclista"));

        entrenamiento.setFecha(nuevosDatos.getFecha());
        entrenamiento.setTiempoSesion(nuevosDatos.getTiempoSesion());
        entrenamiento.setPotenciaMedia(nuevosDatos.getPotenciaMedia());
        entrenamiento.setNP(nuevosDatos.getNP());

        return entrenamientoRepository.save(entrenamiento);
    }

    @DeleteMapping("/{entrenamientoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable int ciclistaId,
            @PathVariable int entrenamientoId) {

        Entrenamiento entrenamiento = entrenamientoRepository
                .findByIdAndCiclistaId(entrenamientoId, ciclistaId)
                .orElseThrow(() ->
                        new RuntimeException("No existe ese entrenamiento para el ciclista"));

        entrenamientoRepository.delete(entrenamiento);
    }

    private Ciclista obtenerCiclista(int ciclistaId) {
        return ciclistaRepository.findById(ciclistaId)
                .orElseThrow(() ->
                        new RuntimeException("No existe el ciclista con id: " + ciclistaId));
    }
}
