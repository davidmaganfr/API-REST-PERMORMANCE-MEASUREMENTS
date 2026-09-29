package com.performancemeasurements.performance_measurement.Controllers;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.performancemeasurements.performance_measurement.DTO.BioimpedanceDTO;
import com.performancemeasurements.performance_measurement.Service.BioimpedanceService;

@RestController
@RequestMapping("/cyclists/{cyclistId}/bioimpedances")
public class BioimpedanceController {

    private final BioimpedanceService bioimpedanceService;

    public BioimpedanceController(BioimpedanceService bioimpedanceService) {
        this.bioimpedanceService = bioimpedanceService;
    }

    @GetMapping
    public List<BioimpedanceDTO> findAllByCyclistId(@PathVariable int cyclistId) {
        return bioimpedanceService.findAllBioimpedancesByCyclistId(cyclistId);
    }

    @GetMapping("/date/{date}")
    public List<BioimpedanceDTO> findByDate(
            @PathVariable int cyclistId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return bioimpedanceService.findByCyclistIdAndDate(cyclistId, date);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BioimpedanceDTO create(
            @PathVariable int cyclistId,
            @RequestBody BioimpedanceDTO bioimpedanceDTO) {
        return bioimpedanceService.createBioimpedance(cyclistId, bioimpedanceDTO);
    }

    @PutMapping("/{bioimpedanceId}")
    public BioimpedanceDTO update(
            @PathVariable int cyclistId,
            @PathVariable int bioimpedanceId,
            @RequestBody BioimpedanceDTO bioimpedanceDTO) {
        return bioimpedanceService.updateBioimpedance(cyclistId, bioimpedanceId, bioimpedanceDTO);
    }

    @DeleteMapping("/{bioimpedanceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable int cyclistId,
            @PathVariable int bioimpedanceId) {
        bioimpedanceService.deleteBioimpedance(cyclistId, bioimpedanceId);
    }

    @DeleteMapping("/delete-all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(@PathVariable int cyclistId) {
        bioimpedanceService.deleteAllByCyclistId(cyclistId);
    }
}