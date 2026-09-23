package com.performancemeasurements.performance_measurement.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.performancemeasurements.performance_measurement.DAO.CyclistRepository;
import com.performancemeasurements.performance_measurement.DTO.CyclistDTO;
import com.performancemeasurements.performance_measurement.entities.Cyclist;

/**
 * Controller for managing cyclist resources
 * CyclistController
 */
@RestController
@RequestMapping("/cyclists")
public class CyclistController {

    @Autowired
    private CyclistRepository cyclistRepository;

    /**
     * Find all cyclists of the database
     * 
     * @return The list of all cyclists in the database
     */
    @GetMapping("/all")
    public List<Cyclist> findAll() {
        return cyclistRepository.findAll();
    }

    /**
     * Find a cyclist by id
     * 
     * @param id Id of the cyclist to find in the database
     * @return The cyclist object DTO found by the specified id
     */
    @GetMapping("/find/{id}")
    public CyclistDTO findById(@PathVariable int id) {
        Optional<Cyclist> cyclist = cyclistRepository.findById(id);
        if (!cyclist.isEmpty()) {
            CyclistDTO cyclistDTO = new CyclistDTO(cyclist.get().getFullname(), cyclist.get().getAge());
            return cyclistDTO;
        } else {
            throw new RuntimeException("Cyclist not found");
        }
    }

    /**
     * Find a cyclist by fullname. For example, David Magan Fernandez
     * 
     * @param fullname Fullname of the cyclist to find in the database
     * @return The list of cyclists with the specified fullname
     */
    @GetMapping("/find/{fullname}")
    public List<CyclistDTO> findByName(@PathVariable String fullname) {
        List<Cyclist> cyclists = cyclistRepository.findByFullname(fullname);
        if (cyclists == null) {
            throw new RuntimeException("Cyclist not found");
        } else {
            // Create a list of CyclistDTO with fuctional programming
            List<CyclistDTO> cyclistDTOs = cyclists.stream()
                    .map(cyclist -> new CyclistDTO(cyclist.getFullname(), cyclist.getAge()))
                    .toList();
            return cyclistDTOs;
        }
    }

    /**
     * Create a new cyclist
     * 
     * @param cyclist Object with the data of the cyclist to create in the database
     * @return The created cyclist object DTO
     */
    @PostMapping("/create")
    public CyclistDTO create(@RequestBody Cyclist cyclist) {
        if (cyclist != null) {
            cyclistRepository.save(cyclist);
            CyclistDTO cyclistDTO = new CyclistDTO(cyclist.getFullname(), cyclist.getAge());

            return cyclistDTO;
        }

        return null;

    }

    /**
     * Update a cyclist by id
     * 
     * @param cyclist Object with the new data of the cyclist to update in the
     *                database
     * @param id      Id of the cyclist to update in the database
     * @return The updated cyclist DTO object
     * 
     */
    @PutMapping("/update/{id}")
    public CyclistDTO update(@RequestBody Cyclist cyclist, @PathVariable int id) {
        if (cyclist != null) {
            cyclist.setId(id);
            if (!cyclistRepository.existsById(id)) {
                throw new RuntimeException("Cyclist not found");
            }
            var cyclistUpdated = cyclistRepository.save(cyclist);
            return new CyclistDTO(cyclistUpdated.getFullname(), cyclistUpdated.getAge());
        }
        return null;
    }

    /**
     * Delete a cyclist by id
     * 
     * @param id Id of the cyclist to delete in the database
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        cyclistRepository.deleteById(id);
    }

    /** Delete all cyclists */
    @DeleteMapping("/delete/all")
    public void deleteAll() {
        cyclistRepository.deleteAll();
    }
}
