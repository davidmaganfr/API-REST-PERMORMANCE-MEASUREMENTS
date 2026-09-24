package com.performancemeasurements.performance_measurement.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.performancemeasurements.performance_measurement.DAO.CyclistRepository;
import com.performancemeasurements.performance_measurement.DAO.TrainingRepository;
import com.performancemeasurements.performance_measurement.DTO.TrainingDTO;
import com.performancemeasurements.performance_measurement.entities.Cyclist;
import com.performancemeasurements.performance_measurement.entities.Training;

@Service 
public class TrainingService {

    private TrainingRepository trainingRepository;
    private CyclistRepository cyclistRepository;

    public static final Logger LOGGER = LoggerFactory.getLogger(TrainingService.class);

    public TrainingService(TrainingRepository trainingRepository, CyclistRepository cyclistRepository) {
        this.trainingRepository = trainingRepository;
        this.cyclistRepository = cyclistRepository;
    }

    /**
     * Obtiene todos los entrenamientos de un ciclista específico y devuelve una lista de objetos TrainingDTO.
     * @param cyclistId El ID del ciclista para el cual se desean obtener los entrenamientos.
     * @return Una lista de objetos TrainingDTO que representan los entrenamientos del ciclista.
     */
    public List<TrainingDTO> findAllTrainingsByCyclistId(int cyclistId) {

                if (!cyclistRepository.existsById(cyclistId)) {
                        LOGGER.warn("El ciclista {} no existe con ese ID en la base de datos.", cyclistId);
                }
                List<Training> trainings = trainingRepository.findTrainingsByCyclistId(cyclistId);
                LOGGER.info("Encontrados {} entrenamientos para el ciclista {}", trainings.size(), cyclistId);

                // Convertir la lista de Training a una lista de TrainingDTO
                return trainings.stream()
                                .map(tr -> new TrainingDTO(
                                                tr.getDate(),
                                                tr.getTotalTimeSesion(),
                                                tr.getAvgPower(),
                                                tr.getNP()))
                                .toList();
    }

    /**
     * Obtiene todos los entrenamientos de un ciclista específico en una fecha determinada y devuelve una lista
     *  de objetos TrainingDTO.
     * @param cyclistId El ID del ciclista para el cual se desean obtener los entrenamientos.
     * @param date La fecha en la que se desean obtener los entrenamientos (formato: "yyyy-MM-dd").
     * @return Una lista de objetos TrainingDTO que representan los entrenamientos del ciclista en la fecha 
     * especificada.
     */
    public List<TrainingDTO> findTrainingsByCyclistIdAndDate(int cyclistId, String date) {

        if (cyclistRepository.existsById(cyclistId)) {
                        LocalDate localDate = LocalDate.parse(
                                        date,
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        List<Training> trainings = trainingRepository
                                        .findTrainingsByCyclistIdAndDate(cyclistId, localDate);
                        return trainings.stream()
                                        .map(tr -> new TrainingDTO(
                                                        tr.getDate(),
                                                        tr.getTotalTimeSesion(),
                                                        tr.getAvgPower(),
                                                        tr.getNP()))
                                        .toList();
        } else {
            LOGGER.warn("El ciclista {} no existe con ese ID en la base de datos.", cyclistId);
            return null;
        }
    }

    /**
     * Crea un nuevo entrenamiento para un ciclista específico.
     *  Se le pasa el id del ciclista al que queremos asociar el entrenamiento.
     * @param cyclistId El ID del ciclista para el cual se desea crear el entrenamiento.
     * @param training El objeto Training que se desea crear.
     * @return El objeto TrainingDTO que representa el entrenamiento creado.
     */
    public TrainingDTO createTraining(int cyclistId, Training training) {
        if (training != null) {
                Optional<Cyclist> cyclist = cyclistRepository.findById(cyclistId);

                training.setCyclist(cyclist.get());
                Training newTraining = trainingRepository.save(training);

                LOGGER.info("Se ha creado un nuevo entrenamiento para el ciclista con ID {}: {}", cyclistId, newTraining);

                return new TrainingDTO(
                                newTraining.getDate(),
                                newTraining.getTotalTimeSesion(),
                                newTraining.getAvgPower(),
                                newTraining.getNP());
        } else {
            LOGGER.warn("El entrenamiento proporcionado es nulo. No se puede crear un entrenamiento para el ciclista con ID {}.", cyclistId);
        }

        return null;
    }

    /**
     * Actualiza un entrenamiento existente para un ciclista específico con un id concreto.
     * @param cyclistId Id del ciclista al que pertenece el entrenamiento.
     * @param trainingId Id del entrenamiento que se desea actualizar.
     * @param newTraining El objeto Training que contiene los nuevos datos del entrenamiento.
     * @return El objeto TrainingDTO que representa el entrenamiento actualizado, o null si no se encontró el entrenamiento.
     */
    public TrainingDTO updateTraining(int cyclistId, int trainingId, Training newTraining) {

        Optional<Training> optionalTraining = trainingRepository.findByIdAndCyclistId(trainingId, cyclistId);

        if (optionalTraining.isPresent()) {
            Training filteredTraining = optionalTraining.get();
            filteredTraining.setDate(newTraining.getDate());
            filteredTraining.setTotalTimeSesion(newTraining.getTotalTimeSesion());
            filteredTraining.setAvgPower(newTraining.getAvgPower());
            filteredTraining.setNP(newTraining.getNP());

            Training updatedTraining = trainingRepository.save(filteredTraining);

            LOGGER.info("Se ha actualizado el entrenamiento con ID {} para el ciclista con ID {}: {}", trainingId, cyclistId, updatedTraining);

            return new TrainingDTO(
                    updatedTraining.getDate(),
                    updatedTraining.getTotalTimeSesion(),
                    updatedTraining.getAvgPower(),
                    updatedTraining.getNP());
        } else {
            LOGGER.warn("El entrenamiento con ID {} no existe para el ciclista con ID {}.", trainingId, cyclistId);
            return null;
        }
    }

    /**
     * Elimina un entrenamiento existente para un ciclista específico con un id concreto.
     * @param cyclistId Id del ciclista al que pertenece el entrenamiento.
     * @param trainingId Id del entrenamiento que se desea eliminar.
     */
    public void deleteTraining(int cyclistId, int trainingId) {
        Optional<Training> optionalTraining = trainingRepository.findByIdAndCyclistId(trainingId, cyclistId);

        if (optionalTraining.isPresent()) {
            trainingRepository.delete(optionalTraining.get());

            LOGGER.info("Se ha eliminado el entrenamiento con ID {} para el ciclista con ID {}.", trainingId, cyclistId);
        } else {
            LOGGER.warn("El entrenamiento con ID {} no existe para el ciclista con ID {}. No se puede eliminar.", trainingId, cyclistId);
        }
    }

    /**
     * Elimina todos los entrenamientos de un ciclista específico.
     * @param cyclistId Id del ciclista al que pertenecen los entrenamientos.
     */
    public void deleteAllTrainingsByCyclistId(int cyclistId) {
        List<Training> trainings = trainingRepository.findTrainingsByCyclistId(cyclistId);
        if (!trainings.isEmpty()) {
            trainingRepository.deleteAll(trainings);
            LOGGER.info("Se han eliminado todos los entrenamientos para el ciclista con ID {}.", cyclistId);
        } else {
            LOGGER.warn("No se encontraron entrenamientos para el ciclista con ID {}. No se eliminó ningún entrenamiento.", cyclistId);
        }
    }
}
