package com.performancemeasurements.performance_measurement.Controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.performancemeasurements.performance_measurement.DTO.IncrementalTestDTO;
import com.performancemeasurements.performance_measurement.Service.IncrementalTestService;

@RestController
@RequestMapping("/cyclists/{cyclistId}/incremental-tests")
public class IncrementalTestController {

        private final IncrementalTestService incrementalTestService;

        IncrementalTestController(IncrementalTestService incrementalTestService) {
                this.incrementalTestService = incrementalTestService;
        }

        /**
         * Controlador para buscar todos los test incrementales de un ciclista
         * @param cyclistId Id de ciclista en la base de datos 
         * @return Una lista con los datos de todos los test incrementales asociados a un ciclista determinado o Null si no existen
         */
        @GetMapping
        public List<IncrementalTestDTO> findAllIncrementalTest(
                        @PathVariable("cyclistId") int cyclistId) {

                return incrementalTestService.findAllTestByCyclistId(cyclistId);
        }

        /**
         * Controlador para buscar un test incremental asociado a un ciclista filtrando por la fecha de realización
         * @param cyclistId Id del ciclista en el que queremos buscar un test
         * @param date Fecha por la que queremos filtrar el/los tests
         * @return Una lista de todos los test realizados para un ciclista en la fecha pasada por parametro
         */
        @GetMapping("/date/{date}")
        public List<IncrementalTestDTO> findByDate(
                        @PathVariable("cyclistId") int cyclistId,
                        @PathVariable("date") String date) {

                return incrementalTestService.findTestsByCyclistIdAndDate(cyclistId, date);
        }

        /**
         * Controlador para crear un nuevo test incremental asociado a un ciclista
         * @param cyclistId Id del ciclista en el que queremos crear el test
         * @param incrementalTest Objeto que contiene la informacion para crear el test en la base de datos
         * @return Objeto IncrementalTestDTO con la informacion guardada en la base de datos
         */
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public IncrementalTestDTO createTest(
                        @PathVariable("cyclistId") int cyclistId,
                        @RequestBody IncrementalTestDTO incrementalTest) {

                return incrementalTestService.createIncrementalTest(cyclistId, incrementalTest);
        }

        /**
         * Controlador para actualizar los datos de un test incremental en la base de datos
         * @param cyclistId Id del ciclista que contiene el test incremental a actualizar
         * @param incrementalId Id del test que queremos modificar
         * @param incrementalTest Objeto con los datos del test que queremos modificar
         * @return Objeto IncrementalTestDTO con los datos del test recien modificado en la base de datos
         */
        @PutMapping("/{incrementalId}")
        public IncrementalTestDTO updateTest(
                        @PathVariable("cyclistId") int cyclistId,
                        @PathVariable("incrementalId") int incrementalId,
                        @RequestBody IncrementalTestDTO incrementalTest) {

                return incrementalTestService.updateTest(cyclistId, incrementalId, incrementalTest);
        }

        /**
         * Controlador para borrar un test incremental de un ciclista
         * @param cyclistId Id del ciclista que contiene el test que deseamos borrar
         * @param incrementalId Id del test que queremos eliminar de la base de datos
         */
        @DeleteMapping("/{incrementalId}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteTest(
                        @PathVariable("cyclistId") int cyclistId,
                        @PathVariable("incrementalId") int incrementalId) {

                incrementalTestService.deleteIncrementalTest(cyclistId, incrementalId);

        }

        /**
         * Controlador para eliminar todos los test incrementales asociados a un ciclista
         * @param cyclistId Id del ciclista del que queremos eliminar todos los test
         */
        @DeleteMapping("/delete-all")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteAll(@PathVariable int cyclistId) {

                incrementalTestService.deleteAllTestsFromCyclist(cyclistId);
        }

}
