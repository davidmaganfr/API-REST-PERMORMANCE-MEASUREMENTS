package com.performancemeasurements.performance_measurement.Controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.performancemeasurements.performance_measurement.DAO.CyclistRepository;
import com.performancemeasurements.performance_measurement.DAO.TrainingRepository;
import com.performancemeasurements.performance_measurement.DTO.TrainingDTO;
import com.performancemeasurements.performance_measurement.Service.TrainingService;
import com.performancemeasurements.performance_measurement.entities.Cyclist;
import com.performancemeasurements.performance_measurement.entities.Training;

@RestController
@RequestMapping("/Cyclists/{cyclistId}/trainings")
public class TrainingController {


        @Autowired
        private TrainingRepository trainingsRepository;
        @Autowired
        private CyclistRepository cyclistRepository;
        @Autowired 
        private TrainingService trainingService;

        /**
         * Controlador que obtiene todos los entrenamientos de un ciclista específico y 
         * devuelve una lista de objetos TrainingDTO.
         * @param cyclistId El ID del ciclista para el cual se desean obtener los entrenamientos.
         * @return Una lista de objetos TrainingDTO que representan los entrenamientos del ciclista.
         */
        @GetMapping
        public List<TrainingDTO> findAllTrainings(
                        @PathVariable int cyclistId) {

                return trainingService.findAllTrainingsByCyclistId(cyclistId);
        }

        /**
         * Controlador para buscar un entrenamiento de un ciclista filtrando por fecha.
         * @param cyclistId Id del ciclista en el que queremos buscar el entrenamiento
         * @param date Fecha para filtrar el entrenamiento
         * @return Lista de entrenamientos de un ciclista para la fecha dada
         */
        @GetMapping("/date/{date}")
        public List<TrainingDTO> findByDate(
                        @PathVariable int cyclistId,
                        @PathVariable String date) {

                return trainingService.findTrainingsByCyclistIdAndDate(cyclistId, date);

        }

        /**
         * Controlador para crear un nuevo entrenamiento dentro de un ciclista
         * @param cyclistId Id del ciclista al que queremos asociar ese entrenamiento
         * @param training Objeto con los datos del entrenamiento que queremos registrar
         * @return Objeto TrainingDTO con los datos del entrenamiento recien guardado
         */
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

        /**
         * Controlador para actualizar los datos de un entrenamiento asociado a un ciclista
         * @param cyclistId Id del ciclista en el que queremos actualizar el entrenamiento
         * @param trainingId Id del entrenamiento que queremos actualizar
         * @param newTraining Objeto Training para actualizar la tabla de entrenamientos
         * @return Objeto TrainingDTO con los datos del entrenamiento actualizado
         */
        @PutMapping("/{trainingId}")
        public TrainingDTO update(
                        @PathVariable int cyclistId,
                        @PathVariable int trainingId,
                        @RequestBody Training newTraining) {

                return trainingService.updateTraining(cyclistId, trainingId, newTraining);
        }

        /**
         * Controlador para eliminar un entrenamiento de un ciclista en base al id del ciclista y del entrenamiento
         * @param cyclistId Id del ciclista desde el que queremos borrar el entrenamiento
         * @param trainingId Id del entrenamiento que queremos eliminar
         */
        @DeleteMapping("/{trainingId}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete(
                        @PathVariable int cyclistId,
                        @PathVariable int trainingId) {

                trainingService.deleteTraining(cyclistId, trainingId);
        }

        /**
         * Controlador para borrar todos los entrenamientos de un ciclista.
         * @param cyclistId Id del ciclista del que se quieren borrar los entrenamientos.
         */
        @DeleteMapping("/delete-all")
        public void deleteAll(@PathVariable int cyclistId) {

                trainingService.deleteAllTrainingsByCyclistId(cyclistId);
        }

}
