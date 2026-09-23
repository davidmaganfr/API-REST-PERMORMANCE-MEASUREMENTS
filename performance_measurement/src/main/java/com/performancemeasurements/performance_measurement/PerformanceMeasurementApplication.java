package com.performancemeasurements.performance_measurement;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.performancemeasurements.performance_measurement.DAO.CyclistRepository;
import com.performancemeasurements.performance_measurement.DAO.TrainingRepository;
import com.performancemeasurements.performance_measurement.entities.Cyclist;
import com.performancemeasurements.performance_measurement.entities.Training;

@SpringBootApplication
public class PerformanceMeasurementApplication {

	public static void main(String[] args) {
		
		var context= SpringApplication.run(PerformanceMeasurementApplication.class, args);
		var repoEnnto =context.getBean(TrainingRepository.class);
		var repoDep = context.getBean(CyclistRepository.class);

		//DEPORTISTAS
		// var deportista1 = Ciclista.of("David", "Magan");
		var deportista2 = Cyclist.of("Jesus Lopez", 34);
		var deportista3 = Cyclist.of("Javier Noya", 28);

		//ENTRENAMIENTOS
		// var entrenamiento1 = Entrenamiento.of(LocalDate.of(2022, 11, 10), LocalTime.of(2,56,32), 220, 250);
		var entrenamiento2 = Training.of(LocalDate.of(2022, 12, 10), LocalTime.of(1,32,32), 200, 290);
		var entrenamiento3 = Training.of(LocalDate.of(2022, 10, 14), LocalTime.of(4,30,30), 200, 235);

		//Añadir entrenamientos y impedancias al deportista1
		// deportista1.addEntrenamiento(entrenamiento1);
		// deportista1.addEntrenamiento(entrenamiento2);
		// deportista1.addEntrenamiento(entrenamiento3);
		// deportista1.addImpedancia(impedancia1);
		// deportista1.addImpedancia(impedancia2);
		// deportista1.addImpedancia(impedancia3);

		repoDep.save(deportista2);
		repoEnnto.save(entrenamiento2);
		


	}

}
