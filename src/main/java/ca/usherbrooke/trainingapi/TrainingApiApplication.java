package ca.usherbrooke.trainingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application Training API.
 * Point d'entrée de l'application Spring Boot.
 */
@SpringBootApplication
public class TrainingApiApplication {

    /**
     * Méthode principale qui démarre l'application.
     *
     * @param args les arguments de la ligne de commande
     */
	public static void main(String[] args) {
		SpringApplication.run(TrainingApiApplication.class, args);
	}

}
