package com.performancemeasurements.performance_measurement.Service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.performancemeasurements.performance_measurement.DAO.CyclistRepository;
import com.performancemeasurements.performance_measurement.DAO.IncrementalTestRepository;
import com.performancemeasurements.performance_measurement.DTO.IncrementalTestDTO;
import com.performancemeasurements.performance_measurement.entities.Cyclist;
import com.performancemeasurements.performance_measurement.entities.IncrementalTest;;

/**
 * Servicio para manejar la logica de negocio asociada a las pruebas de esfuerzo
 * (test incrementales)
 * IncrementalTestService
 */
@Service
public class IncrementalTestService {

    private IncrementalTestRepository incrementalTestRepository;
    private CyclistRepository cyclistRepository;

    public static final Logger LOGGER = LoggerFactory.getLogger(IncrementalTestService.class);

    public IncrementalTestService(IncrementalTestRepository incrementalTestRepository,
            CyclistRepository cyclistRepository) {
        this.cyclistRepository = cyclistRepository;
        this.incrementalTestRepository = incrementalTestRepository;
    }

    /**
     * Buscar todos los test incrementales asociados a un ciclista por su Id
     * 
     * @param cyclistId Id del ciclista para el que queremos buscar los test
     *                  incrementales
     * @return Una lista de objetos IncrementalTestDTO que representan los test del
     *         ciclista
     */
    public List<IncrementalTestDTO> findAllTestByCyclistId(int cyclistId) {
        if (!cyclistRepository.existsById(cyclistId)) {
            LOGGER.warn("El ciclista {} no existe con ese ID en la base de datos.", cyclistId);
            return null;
        }

        List<IncrementalTest> listTest = incrementalTestRepository.findAllTestsByCyclistId(cyclistId);
        LOGGER.info("Encontrados {} test incrementales para el ciclista {}", listTest.size(), cyclistId);

        // Convertir la lista de Training a una lista de TrainingDTO
        return listTest.stream()
                .map(test -> new IncrementalTestDTO(
                        test.getId(),
                        test.getDate(),
                        test.getVo2max(),
                        test.getVt1(),
                        test.getVt2()))
                .toList();
    }

    /**
     * Busca uno o varios test incrementales por una fecha determinada y para un
     * ciclista concreto.
     * 
     * @param cyclistId Id del ciclista del que queremos buscar sus test.
     * @param date      Fecha por la cual queremos filtrar el test
     * @return Lista de test asociados a un ciclista para una fecha determinada
     */
    public List<IncrementalTestDTO> findTestsByCyclistIdAndDate(int cyclistId,
            String date) {
        if (cyclistRepository.existsById(cyclistId)) {

            List<IncrementalTest> listTests = incrementalTestRepository
                    .findByCyclistIdAndDate(cyclistId, date);
            return listTests.stream()
                    .map(test -> new IncrementalTestDTO(
                            test.getId(), test.getDate(), test.getVo2max(), test.getVt1(), test.getVt2()))
                    .toList();
        } else {
            LOGGER.warn("El ciclista {} no existe con ese ID en la base de datos.", cyclistId);
            return null;
        }
    }

    /**
     * Crear un nuevo test incremental para un ciclista determinado
     * 
     * @param cyclistId Id del ciclista en el que queremos crear el test incremental
     * @param test      Objeto que contiene los datos del test incremental realizado
     * @return El objeto IncrementalTestDTO con los datos del test guardado en la
     *         base de datos
     */
    public IncrementalTestDTO createIncrementalTest(int cyclistId, IncrementalTestDTO test) {
        if (test != null) {
            Optional<Cyclist> cyclist = cyclistRepository.findById(cyclistId);

            if (cyclist.isPresent()) {

                // Instancio un objeto de la entidad de test incremental y modifico el ciclista
                // asociado para guardarlo en la base de datos
                IncrementalTest testEntity = new IncrementalTest(test.getDate(), test.getVo2max(), test.getVt1(),
                        test.getVt2());
                testEntity.setCyclist(cyclist.get());

                IncrementalTest newTest = incrementalTestRepository.save(testEntity);
                LOGGER.info("Se ha creado un nuevo test incremental para el ciclista con ID {}: {}", cyclistId,
                        newTest);

                return new IncrementalTestDTO(
                        newTest.getId(),
                        newTest.getDate(),
                        newTest.getVo2max(),
                        newTest.getVt1(),
                        newTest.getVt2());
            } else {
                LOGGER.warn("No se encontró un ciclista con el ID {}. No se ha creado el test incremental.",
                        cyclistId);
            }

        } else {
            LOGGER.warn(
                    "El test proporcionado es nulo. No se puede crear un test incremental para el ciclista con ID {}.",
                    cyclistId);

        }

        return null;
    }

    /**
     * Actualiza un test incremental asociado a un ciclista concreto
     * 
     * @param cyclistId         Id del ciclista en el que queremos actualizar el
     *                          test incremental
     * @param incrementalTestId Id del test incremental que queremos actualizar
     * @param newTest           Objeto referente al test con los datos que queremos
     *                          modificar
     * @return Objeto IncrementalTestDTO con los datos modificados en la base de
     *         datos
     */
    public IncrementalTestDTO updateTest(int cyclistId, int incrementalTestId, IncrementalTestDTO newTest) {
        Optional<IncrementalTest> optinalTest = incrementalTestRepository.findByIdAndCyclistId(incrementalTestId,
                cyclistId);

        if (optinalTest.isPresent()) {
            IncrementalTest filteredTest = optinalTest.get();
            filteredTest.setDate(newTest.getDate());
            filteredTest.setVo2max(newTest.getVo2max());
            filteredTest.setVt1(newTest.getVt1());
            filteredTest.setVt2(newTest.getVt2());

            IncrementalTest updatedTest = incrementalTestRepository.save(filteredTest);

            LOGGER.info("Se ha actualizado el test incremental con ID {} para el ciclista con ID {}: {}",
                    incrementalTestId, cyclistId, updatedTest);

            return new IncrementalTestDTO(
                    updatedTest.getId(),
                    updatedTest.getDate(),
                    updatedTest.getVo2max(),
                    updatedTest.getVt1(),
                    updatedTest.getVt2());
                    
        } else {
            LOGGER.warn("El test incremental con ID {} no existe para el ciclista con ID {}.", incrementalTestId,
                    cyclistId);
            return null;
        }
    }

    /**
     * Borra un test a través del id del test incremental asociado a un ciclista
     * 
     * @param cyclistId         Id del ciclista asociado al test que queremos borrar
     * @param incrementalTestId Id del test que queremos eliminar de la base de
     *                          datos
     */
    public void deleteIncrementalTest(int cyclistId, int incrementalTestId) {
        Optional<IncrementalTest> optionalTests = incrementalTestRepository.findByIdAndCyclistId(incrementalTestId,
                cyclistId);

        if (optionalTests.isPresent()) {
            incrementalTestRepository.delete(optionalTests.get());

            LOGGER.info("Se ha eliminado el test incremental con ID {} para el ciclista con ID {}.", incrementalTestId,
                    cyclistId);
        } else {
            LOGGER.warn("El test incremental con ID {} no existe para el ciclista con ID {}. No se puede eliminar.",
                    incrementalTestId, cyclistId);
        }
    }

    /**
     * Borra todos los test incrementales que contiene un ciclista
     * 
     * @param cyclistId Id del ciclista desde el que queremos borrar todos los test
     */
    public void deleteAllTestsFromCyclist(int cyclistId) {
        List<IncrementalTest> listTests = incrementalTestRepository.findAllTestsByCyclistId(cyclistId);
        if (!listTests.isEmpty()) {
            incrementalTestRepository.deleteAll(listTests);
            LOGGER.info("Se han eliminado todos los test incrementales para el ciclista con ID {}.", cyclistId);
        } else {
            LOGGER.warn(
                    "No se encontraron test incrementales para el ciclista con ID {}. No se eliminó ningún test incremental.",
                    cyclistId);
        }
    }

}
