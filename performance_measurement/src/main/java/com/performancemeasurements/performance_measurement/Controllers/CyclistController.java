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
import com.performancemeasurements.performance_measurement.DTO.CyclistDTO;
import com.performancemeasurements.performance_measurement.Service.CyclistService;
import com.performancemeasurements.performance_measurement.entities.Cyclist;


@RestController
@RequestMapping("/cyclists")
public class CyclistController {

    @Autowired
    private CyclistService cyclistService;

    /**
     * Find all cyclists of the database
     * 
     * @return The list of all cyclists in the database
     */
    @GetMapping("/all")
    public List<CyclistDTO> findAll() {
        return cyclistService.findAllCyclists();
    }

    /**
     * Controlador para obtener un ciclista por id y convertirlo a DTO
     * 
     * @param id Id del ciclista a buscar en la base de datos
     * @return El objeto CyclistDTO encontrado por el id especificado
     */
    @GetMapping("/find/{id}")
    public CyclistDTO findById(@PathVariable int id) {
        return cyclistService.findCyclistById(id);
    }

    /**
     * Controlador para obtener una lista de ciclistas por nombre completo y 
     * convertirlos a DTOs. Por ejemplo: fullname = "David Magan"
     * 
     * @param fullname Nombre completo del ciclista a buscar en la base de datos
     * @return Lista de objetos CyclistDTO encontrados con el nombre completo especificado
     */
    @GetMapping("/find/{fullname}")
    public List<CyclistDTO> findByName(@PathVariable String fullname) {
        return cyclistService.findCyclistByFullname(fullname);
    }

    /**
     * Controlador para crear un nuevo ciclista en la base de datos
     * 
     * @param cyclist Objeto con los datos del ciclista a crear en la base de datos
     * @return El objeto CyclistDTO creado en la base de datos
     */
    @PostMapping("/create")
    public CyclistDTO create(@RequestBody Cyclist cyclist) {
        return cyclistService.createCyclist(cyclist);
    }

    /**
     * Controlador para actualizar un ciclista existente en la base de datos por Id
     * 
     * @param cyclist Entidad del ciclista con los datos actualizados
     * @param id      Id del ciclista a actualizar en la base de datos
     * @return El objeto CyclistDTO con los datos del ciclista actualizado en la base de datos
     * 
     */
    @PutMapping("/update/{id}")
    public CyclistDTO update(@RequestBody Cyclist cyclist, @PathVariable int id) {
        return cyclistService.updateCyclistById(cyclist, id);
    }

    /**
     * Controlador para eliminar un ciclista existente en la base de datos por Id
     * 
     * @param id Id del ciclista a eliminar en la base de datos
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        cyclistService.deleteCyclistById(id);
    }

    /** Controlador para eliminar todos los ciclistas de la base de datos */
    @DeleteMapping("/delete/all")
    public void deleteAll() {
        cyclistService.deleteAllCyclists();
    }
}
