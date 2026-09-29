package com.performancemeasurements.performance_measurement.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.performancemeasurements.performance_measurement.DAO.BioimpedanceRepository;
import com.performancemeasurements.performance_measurement.DAO.CyclistRepository;
import com.performancemeasurements.performance_measurement.DTO.BioimpedanceDTO;
import com.performancemeasurements.performance_measurement.entities.Bioimpedance;
import com.performancemeasurements.performance_measurement.entities.Cyclist;

/**
 * Servicio para controlar toda la logica de negocio relacionada con las
 * bioimpedancias realizadas a los ciclistas
 * BioimpedanceService
 */
@Service
public class BioimpedanceService {

    private final BioimpedanceRepository bioimpedanceRepository;
    private final CyclistRepository cyclistRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BioimpedanceService.class);

    public BioimpedanceService(BioimpedanceRepository bioimpedanceRepository,
            CyclistRepository cyclistRepository) {
        this.bioimpedanceRepository = bioimpedanceRepository;
        this.cyclistRepository = cyclistRepository;
    }

    /**
    * Busca todas las bioimpedancias asociadas a un ciclista.
     * 
    * @param cyclistId Id del ciclista
    * @return Lista de bioimpedancias; vacía si el ciclista existe pero no tiene
    *         registros, o null si el ciclista no existe
     */
    public List<BioimpedanceDTO> findAllBioimpedancesByCyclistId(int cyclistId) {
        if (!cyclistRepository.existsById(cyclistId)) {
            LOGGER.warn("El ciclista {} no existe con ese ID en la base de datos.", cyclistId);
            return null;
        }

        List<Bioimpedance> allBioimpedances = bioimpedanceRepository.findAllBioimpedancesByCyclistId(cyclistId);
        LOGGER.info("Encontradas {} bioimpedancias para el ciclista {}", allBioimpedances.size(), cyclistId);

        return allBioimpedances
                .stream()
                .map(bio -> new BioimpedanceDTO(bio.getId(), bio.getDate(), bio.getWeight(), bio.getHeight(),
                        bio.getPercentageFat(), bio.getPercentageMuscle(), bio.getPercentageWater()))
                .toList();
    }

    /**
     * Busca las bioimpedancias de un ciclista realizadas en una fecha determinada.
     *
     * @param cyclistId Id del ciclista
     * @param date      Fecha de realización de la bioimpedancia
     * @return Lista de bioimpedancias encontradas, vacía si no hay coincidencias,
     *         o null si el ciclista no existe
     */
    public List<BioimpedanceDTO> findByCyclistIdAndDate(int cyclistId, LocalDate date) {
        if (!cyclistRepository.existsById(cyclistId)) {
            LOGGER.warn("El ciclista {} no existe con ese ID en la base de datos.", cyclistId);
            return null;
        }

        return bioimpedanceRepository.findByCyclistIdAndDate(cyclistId, date)
                .stream()
                .map(bio -> new BioimpedanceDTO(
                        bio.getId(),
                        bio.getDate(),
                        bio.getWeight(),
                        bio.getHeight(),
                        bio.getPercentageFat(),
                        bio.getPercentageMuscle(),
                        bio.getPercentageWater()))
                .toList();
    }

    /**
     * Crea una bioimpedancia y la asocia al ciclista indicado.
     *
     * @param cyclistId        Id del ciclista al que se asociará la bioimpedancia
     * @param bioimpedanceDTO  Objeto DTO con los datos de la bioimpedancia que se va a crear
     * @return DTO del registro creado, o null si el DTO es nulo o el ciclista
     *         no existe
     */
    public BioimpedanceDTO createBioimpedance(int cyclistId, BioimpedanceDTO bioimpedanceDTO) {
        if (bioimpedanceDTO == null) {
            LOGGER.warn("Los datos de bioimpedancia son nulos para el ciclista {}.", cyclistId);
            return null;
        }

        Optional<Cyclist> optionalCyclist = cyclistRepository.findById(cyclistId);
        if (optionalCyclist.isEmpty()) {
            LOGGER.warn("El ciclista {} no existe con ese ID en la base de datos.", cyclistId);
            return null;
        }

        Bioimpedance newBioimpedance = new Bioimpedance();
        newBioimpedance.setDate(bioimpedanceDTO.getDate());
        newBioimpedance.setWeight(bioimpedanceDTO.getWeight());
        newBioimpedance.setHeight(bioimpedanceDTO.getHeight());
        newBioimpedance.setPercentageFat(bioimpedanceDTO.getPercentageFat());
        newBioimpedance.setPercentageMuscle(bioimpedanceDTO.getPercentageMuscle());
        newBioimpedance.setPercentageWater(bioimpedanceDTO.getPercentageWater());
        newBioimpedance.setCyclist(optionalCyclist.get());

        Bioimpedance savedBioimpedance = bioimpedanceRepository.save(newBioimpedance);
        LOGGER.info("Bioimpedancia creada para el ciclista {}: {}", cyclistId, savedBioimpedance);

        return new BioimpedanceDTO(
                savedBioimpedance.getId(),
                savedBioimpedance.getDate(),
                savedBioimpedance.getWeight(),
                savedBioimpedance.getHeight(),
                savedBioimpedance.getPercentageFat(),
                savedBioimpedance.getPercentageMuscle(),
                savedBioimpedance.getPercentageWater());
    }

    /**
     * Actualiza una bioimpedancia perteneciente al ciclista concreto.
     *
     * @param cyclistId        Id del ciclista propietario del registro
     * @param bioimpedanceId   Id de la bioimpedancia que se actualizará
     * @param bioimpedanceDTO Objeto con nuevos datos de la bioimpedancia
     * @return DTO del registro actualizado, o null si el DTO es nulo o no se
     *         encuentra el registro para ese ciclista
     */
    public BioimpedanceDTO updateBioimpedance(int cyclistId, int bioimpedanceId,
            BioimpedanceDTO bioimpedanceDTO) {
        if (bioimpedanceDTO == null) {
            LOGGER.warn("Los datos de bioimpedancia son nulos para el registro {}.", bioimpedanceId);
            return null;
        }

        Optional<Bioimpedance> optionalBioimpedance = bioimpedanceRepository.findByIdAndCyclistId(bioimpedanceId,
                cyclistId);
        if (optionalBioimpedance.isEmpty()) {
            LOGGER.warn("La bioimpedancia {} no existe para el ciclista {}.", bioimpedanceId, cyclistId);
            return null;
        }

        Bioimpedance filteredBioimpedance = optionalBioimpedance.get();
        filteredBioimpedance.setDate(bioimpedanceDTO.getDate());
        filteredBioimpedance.setWeight(bioimpedanceDTO.getWeight());
        filteredBioimpedance.setHeight(bioimpedanceDTO.getHeight());
        filteredBioimpedance.setPercentageFat(bioimpedanceDTO.getPercentageFat());
        filteredBioimpedance.setPercentageMuscle(bioimpedanceDTO.getPercentageMuscle());
        filteredBioimpedance.setPercentageWater(bioimpedanceDTO.getPercentageWater());

        Bioimpedance updatedBioimpedance = bioimpedanceRepository.save(filteredBioimpedance);
        LOGGER.info("Bioimpedancia {} actualizada para el ciclista {}.", bioimpedanceId, cyclistId);
        
        return new BioimpedanceDTO(
                updatedBioimpedance.getId(),
                updatedBioimpedance.getDate(),
                updatedBioimpedance.getWeight(),
                updatedBioimpedance.getHeight(),
                updatedBioimpedance.getPercentageFat(),
                updatedBioimpedance.getPercentageMuscle(),
                updatedBioimpedance.getPercentageWater());
    }

    /**
     * Elimina una bioimpedancia de la base de datos asociada a un ciclista.
     *
     * @param cyclistId      Id del ciclista 
     * @param bioimpedanceId Id de la bioimpedancia que se eliminará
     */
    public void deleteBioimpedance(int cyclistId, int bioimpedanceId) {
        Optional<Bioimpedance> optionalBioimpedance = bioimpedanceRepository.findByIdAndCyclistId(bioimpedanceId,
                cyclistId);

        if (optionalBioimpedance.isPresent()) {
            bioimpedanceRepository.delete(optionalBioimpedance.get());
            LOGGER.info("Bioimpedancia {} eliminada para el ciclista {}.", bioimpedanceId, cyclistId);
        } else {
            LOGGER.warn("La bioimpedancia {} no existe para el ciclista {}.", bioimpedanceId, cyclistId);
        }
    }

    /**
     * Elimina todas las bioimpedancias asociadas al ciclista concreto.
     *
     * @param cyclistId Id del ciclista cuyos registros se eliminarán
     */
    public void deleteAllByCyclistId(int cyclistId) {
        List<Bioimpedance> bioimpedances = bioimpedanceRepository.findAllBioimpedancesByCyclistId(cyclistId);

        if (!bioimpedances.isEmpty()) {
            bioimpedanceRepository.deleteAll(bioimpedances);
            LOGGER.info("Bioimpedancias eliminadas para el ciclista {}.", cyclistId);
        } else {
            LOGGER.warn("No se encontraron bioimpedancias para el ciclista {}.", cyclistId);
        }
    }

}