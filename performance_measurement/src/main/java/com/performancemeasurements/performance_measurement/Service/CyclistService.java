package com.performancemeasurements.performance_measurement.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.performancemeasurements.performance_measurement.DAO.CyclistRepository;
import com.performancemeasurements.performance_measurement.DTO.CyclistDTO;
import com.performancemeasurements.performance_measurement.entities.Cyclist;

/**
 * Servicio para manejar la lógica de negocio relacionada con los ciclistas en
 * la base de datos.
 * CyclistService
 */
@Service
public class CyclistService {

    private CyclistRepository cyclistRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CyclistService.class);

    public CyclistService(CyclistRepository cyclistRepository) {
        this.cyclistRepository = cyclistRepository;
    }

    /**
     * Metodo para obtener todos los ciclistas de la base de datos y convertirlos a
     * DTOs
     */
    public List<CyclistDTO> findAllCyclists() {

        List<Cyclist> cyclists = cyclistRepository.findAll();

        if (!cyclists.isEmpty()) {
            List<CyclistDTO> cyclistDTOs = cyclists.stream()
                    .map(cycl -> new CyclistDTO(cycl.getFullname(), cycl.getAge()))
                    .toList();
            return cyclistDTOs;
        } else {
            LOGGER.warn("No se encontraron ciclistas en la base de datos");
            return null;
        }
    }

    /**
     * Metodo para obtener un ciclista por id y convertirlo a DTO
     * 
     * @param id Id del ciclista a buscar en la base de datos
     * @return El objeto CyclistDTO encontrado por el id especificado
     */
    public CyclistDTO findCyclistById(int id) {
        return cyclistRepository.findById(id)
                .map(cyclist -> new CyclistDTO(cyclist.getFullname(), cyclist.getAge())).orElse(null);
    }

    /**
     * Metodo para obtener una lista de ciclistas por nombre completo y convertirlos
     * a DTOs. En condiciones normales,
     * solo debería haber un ciclista con un nombre completo, pero se devuelve una
     * lista por asegurar esta situación.
     * 
     * @param fullname Nombre completo del ciclista a buscar en la base de datos
     * @return Lista de objetos CyclistDTO encontrados con el nombre completo
     *         especificado
     */
    public List<CyclistDTO> findCyclistByFullname(String fullname) {
        List<Cyclist> cyclists = cyclistRepository.findByFullname(fullname);
        if (cyclists == null) {
            LOGGER.warn("No se encontraron ciclistas con el nombre: {}", fullname);
            return null;
        } else {
            List<CyclistDTO> cyclistDTOs = cyclists.stream()
                    .map(cycl -> new CyclistDTO(cycl.getFullname(), cycl.getAge()))
                    .toList();

            return cyclistDTOs;
        }
    }

    /**
     * Metodo para crear un nuevo ciclista en la base de datos
     * 
     * @param cyclist Entidad con los datos del ciclista a crear en la base de datos
     * @return El objeto CyclistDTO creado en la base de datos
     */
    public CyclistDTO createCyclist(CyclistDTO cyclist) {
        if (cyclist == null) {
            LOGGER.warn("El ciclista proporcionado viene como nulo y no tiene datos");
            return null;
        }

        Cyclist newCyclist = cyclistRepository.save(new Cyclist(cyclist.getFullname(), cyclist.getAge()));
        LOGGER.info("Ciclista creado con éxito: {}", newCyclist.getFullname());

        CyclistDTO cyclistCreated = new CyclistDTO(
                newCyclist.getFullname(),
                newCyclist.getAge());

        cyclistCreated.setId(newCyclist.getId());

        return cyclistCreated;

    }

    /**
     * Metodo para verificar si un ciclista existe en la base de datos por su id
     * 
     * @param id Id del ciclista a verificar en la base de datos
     * @return True si el ciclista existe, False si no existe
     */
    public boolean existsCyclistById(int id) {
        return cyclistRepository.existsById(id);
    }

    /**
     * Metodo para actualizar un ciclista en la base de datos por su id
     * 
     * @param cyclist Entidad con los nuevos datos del ciclista a actualizar en la
     *                base de datos
     * @param id      Id del ciclista a actualizar en la base de datos
     * @return El objeto CyclistDTO con los datos del ciclista actualizado en la
     *         base de datos, o null si no se pudo actualizar
     */
    public CyclistDTO updateCyclistById(CyclistDTO cyclist, int idCyclist) {
        if (cyclist == null) {
            LOGGER.warn("El ciclista proporcionado viene como nulo y no tiene datos");
            return null;
        }

        Optional<Cyclist> optionalCyclist = cyclistRepository.findById(idCyclist);

        if (optionalCyclist.isEmpty()) {
            LOGGER.warn("No se encontró un ciclista con el id: {}. No se ha actualizado ningún ciclista",
                    idCyclist);
            return null;
        }

        Cyclist filteredCyclist = optionalCyclist.get();

        filteredCyclist.setFullname(cyclist.getFullname());
        filteredCyclist.setAge(cyclist.getAge());
        filteredCyclist.setCategory(CyclistDTO.calculateCategory(cyclist.getAge()));

        Cyclist updatedCyclist = cyclistRepository.save(filteredCyclist);

        LOGGER.info("Ciclista actualizado con éxito: {}", updatedCyclist.getFullname());

        CyclistDTO updatedCyclistDTO = new CyclistDTO(
                updatedCyclist.getFullname(),
                updatedCyclist.getAge());

        updatedCyclistDTO.setId(updatedCyclist.getId());

        return updatedCyclistDTO;

    }

    /**
     * Metodo para eliminar un ciclista de la base de datos por su id
     * 
     * @param id Id del ciclista a eliminar en la base de datos
     */
    public void deleteCyclistById(int id) {
        if (!cyclistRepository.existsById(id)) {
            LOGGER.warn("No se encontró un ciclista con el id: {}. No se ha eliminado ningun ciclista", id);
            return;
        }

        cyclistRepository.deleteById(id);
        LOGGER.info("Ciclista con id: {} eliminado con éxito", id);
    }

    /**
     * Metodo para eliminar todos los ciclistas de la base de datos
     */
    public void deleteAllCyclists() {
        cyclistRepository.deleteAll();
        LOGGER.info("Todos los ciclistas han sido eliminados de la base de datos con éxito");
    }
}