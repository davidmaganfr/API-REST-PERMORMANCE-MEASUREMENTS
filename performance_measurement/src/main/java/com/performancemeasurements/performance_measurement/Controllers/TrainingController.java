package com.performancemeasurements.performance_measurement.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.performancemeasurements.performance_measurement.DAO.CyclistRepository;
import com.performancemeasurements.performance_measurement.DAO.TrainingRepository;
import com.performancemeasurements.performance_measurement.DTO.TrainingDTO;
import com.performancemeasurements.performance_measurement.entities.Cyclist;
import com.performancemeasurements.performance_measurement.entities.Training;

@RestController
@RequestMapping("/Cyclists/{cyclistId}/trainings")
public class TrainingController {

        private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);

        @Autowired
        private TrainingRepository trainingsRepository;
        @Autowired
        private CyclistRepository cyclistRepository;

        @GetMapping
        public List<TrainingDTO> findByCyclistId(
                        @PathVariable int cyclistId) {

                if (!cyclistRepository.existsById(cyclistId)) {
                        logger.warn("El ciclista {} no existe con ese ID", cyclistId);
                        throw new RuntimeException("The cyclist does not exist with id: " + cyclistId);
                }
                List<Training> trainings = trainingsRepository.findByCyclistId(cyclistId);
                logger.debug("Encontrados {} entrenamientos para el ciclista {}", trainings.size(), cyclistId);

                // Convertir la lista de Training a una lista de TrainingDTO
                return trainings.stream()
                                .map(tr -> new TrainingDTO(
                                                tr.getDate(),
                                                tr.getTotalTimeSesion(),
                                                tr.getAvgPower(),
                                                tr.getNP()))
                                .toList();
        }

        @GetMapping("/date/{date}")
        public List<Training> findByDate(
                        @PathVariable int cyclistId,
                        @PathVariable String date) {

                if (cyclistRepository.existsById(cyclistId)) {
                        LocalDate localDate = LocalDate.parse(
                                        date,
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        return trainingsRepository
                                        .findByCyclistIdAndDate(cyclistId, localDate);
                }

                return null;

        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public TrainingDTO create(
                        @PathVariable int cyclistId,
                        @RequestBody Training training) {
                if (training != null) {
                        Optional<Cyclist> cyclist = cyclistRepository.findById(cyclistId);

                        training.setCyclist(cyclist.get());
                        Training newTraining = trainingsRepository.save(training);

                        return new TrainingDTO(
                                        newTraining.getDate(),
                                        newTraining.getTotalTimeSesion(),
                                        newTraining.getAvgPower(),
                                        newTraining.getNP());
                }

                return null;

        }

        @PutMapping("/{trainingId}")
        public Training update(
                        @PathVariable int cyclistId,
                        @PathVariable int trainingId,
                        @RequestBody Training newTraining) {

                Training training = trainingsRepository
                                .findByIdAndCyclistId(trainingId, cyclistId)
                                .orElseThrow(() -> new RuntimeException("The training does not exist for the cyclist"));

                training.setDate(newTraining.getDate());
                training.setTotalTimeSesion(newTraining.getTotalTimeSesion());
                training.setAvgPower(newTraining.getAvgPower());
                training.setNP(newTraining.getNP());

                return trainingsRepository.save(training);
        }

        @DeleteMapping("/{trainingId}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete(
                        @PathVariable int cyclistId,
                        @PathVariable int trainingId) {

                Training training = trainingsRepository
                                .findByIdAndCyclistId(trainingId, cyclistId)
                                .orElseThrow(() -> new RuntimeException("The training does not exist for the cyclist"));

                if (training != null) {
                        trainingsRepository.delete(training);
                }
        }

}
