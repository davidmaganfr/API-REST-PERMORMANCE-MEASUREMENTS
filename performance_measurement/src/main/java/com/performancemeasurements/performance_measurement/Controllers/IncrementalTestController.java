package com.performancemeasurements.performance_measurement.Controllers;

import com.performancemeasurements.performance_measurement.PerformanceMeasurementApplication;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.performancemeasurements.performance_measurement.DAO.CyclistRepository;
import com.performancemeasurements.performance_measurement.DAO.IncrementalTestRepository;
import com.performancemeasurements.performance_measurement.DTO.IncrementalTestDTO;
import com.performancemeasurements.performance_measurement.entities.Cyclist;
import com.performancemeasurements.performance_measurement.entities.IncrementalTest;

@RestController
@RequestMapping("/cyclists/{cyclistId}/incremental-tests")
public class IncrementalTestController {

        @Autowired
        private IncrementalTestRepository incrementalTestRepository;

        @Autowired
        private CyclistRepository cyclistRepository;

        @GetMapping
        public List<IncrementalTestDTO> findByCyclist(
                        @PathVariable("cyclistId") int cyclistId) {

                if (cyclistRepository.existsById(cyclistId)) {
                        List<IncrementalTest> incrementalTests = incrementalTestRepository.findByCyclistId(cyclistId);
                        return incrementalTests.stream()
                                        .map(test -> new IncrementalTestDTO(
                                                        test.getDate(),
                                                        test.getVo2max(),
                                                        test.getVt1(),
                                                        test.getVt2()))
                                        .toList();

                } else {
                        throw new RuntimeException("The cyclist with id: " + cyclistId + " does not exist");
                }
        }

        @GetMapping("/date/{date}")
        public List<IncrementalTestDTO> findByDate(
                        @PathVariable("cyclistId") int cyclistId,
                        @PathVariable("date") String date) {

                if (cyclistRepository.existsById(cyclistId)) {
                        List<IncrementalTest> tests = incrementalTestRepository.findByCyclistIdAndDate(cyclistId, date);
                        return tests.stream()
                                        .map(test -> new IncrementalTestDTO(
                                                        test.getDate(),
                                                        test.getVo2max(),
                                                        test.getVt1(),
                                                        test.getVt2()))
                                        .toList();
                }

                return null;

        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public IncrementalTestDTO create(
                        @PathVariable("cyclistId") int cyclistId,
                        @RequestBody IncrementalTest incrementalTest) {

                if (cyclistRepository.existsById(cyclistId)) {
                        Optional<Cyclist> cyclist = cyclistRepository.findById(cyclistId);
                        incrementalTest.setCyclist(cyclist.get());

                        IncrementalTest testCreated = incrementalTestRepository.save(incrementalTest);

                        return testCreated != null ? new IncrementalTestDTO(
                                        testCreated.getDate(),
                                        testCreated.getVo2max(),
                                        testCreated.getVt1(),
                                        testCreated.getVt2()) : null;
                } else {
                        throw new RuntimeException("The cyclist with id: " + cyclistId + " does not exist");
                }
        }

        @PutMapping("/{incrementalId}")
        public IncrementalTestDTO update(
                        @PathVariable("cyclistId") int cyclistId,
                        @PathVariable("incrementalId") int incrementalId,
                        @RequestBody IncrementalTest incrementalTest) {

                IncrementalTest filteredTest = incrementalTestRepository
                                .findById(incrementalId)
                                .filter(test -> test.getCyclist().getId() == cyclistId)
                                .orElseThrow(() -> new RuntimeException(
                                                "No existe esa prueba para el ciclista"));

                filteredTest.setDate(incrementalTest.getDate());
                filteredTest.setVo2max(incrementalTest.getVo2max());
                filteredTest.setVt1(incrementalTest.getVt1());
                filteredTest.setVt2(incrementalTest.getVt2());

                IncrementalTest updatedTest = incrementalTestRepository.save(filteredTest);

                return updatedTest != null ? new IncrementalTestDTO(
                                updatedTest.getDate(),
                                updatedTest.getVo2max(),
                                updatedTest.getVt1(),
                                updatedTest.getVt2()) : null;
        }

        @DeleteMapping("/{incrementalId}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete(
                @PathVariable("cyclistId") int cyclistId,
                @PathVariable("incrementalId") int incrementalId) {

                Optional<IncrementalTest> filteredTest = incrementalTestRepository
                        .findById(incrementalId)
                        .filter(test ->
                                test.getCyclist().getId() == cyclistId);
                
                if (filteredTest.isPresent()) {
                        incrementalTestRepository.delete(filteredTest.get());
                }
                
        }

}
