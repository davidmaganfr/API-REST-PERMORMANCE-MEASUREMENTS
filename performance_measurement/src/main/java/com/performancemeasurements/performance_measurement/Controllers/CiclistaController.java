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
import com.performancemeasurements.performance_measurement.DAO.CiclistaRepository;
import com.performancemeasurements.performance_measurement.entities.Ciclista;


@RestController
@RequestMapping("/ciclistas")
public class CiclistaController {

    @Autowired
    private CiclistaRepository ciclistaRepository;

     @GetMapping("/all")
    public List<Ciclista> findAll(){
        return ciclistaRepository.findAll();
    }

    @GetMapping("/find/{id}")
    public Ciclista findById(@PathVariable int id){
        var ciclista = ciclistaRepository.findById(id);
        return ciclista.get();
    }

    @GetMapping("/find/{nombre}")
    public List<Ciclista> findByName(@PathVariable String nombre){
        var ciclista = ciclistaRepository.findByNombre(nombre);
        if(ciclista == null){
            throw new RuntimeException("Not found");
        }
        return ciclista;
    }

    @GetMapping("/find/{apellido}")
    public List<Ciclista> findByApellido(@PathVariable String apellido){
        var ciclista = ciclistaRepository.findByApellido(apellido);
        if(ciclista == null){
            throw new RuntimeException("Not found");
        }
        return ciclista;
    }

    @PostMapping("/create")
    public Ciclista create(@RequestBody Ciclista ciclista){
        ciclistaRepository.save(ciclista);
        return ciclista;
    }

    @PutMapping("/update/{id}")
    public void update(@RequestBody Ciclista ciclista, @PathVariable int id){
        ciclista.setId(id);
        if(!ciclistaRepository.existsById(id)){
            throw new RuntimeException("Not found");
        }
        ciclistaRepository.save(ciclista);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        ciclistaRepository.deleteById(id);
    }

    @DeleteMapping("/delete/all")
    public void deleteAll(){
        ciclistaRepository.deleteAll();
    }
}
