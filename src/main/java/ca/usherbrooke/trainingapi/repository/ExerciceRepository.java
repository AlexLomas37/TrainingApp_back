package ca.usherbrooke.trainingapi.repository;

import ca.usherbrooke.trainingapi.model.Exercice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour accéder aux données des exercices.
 */
public interface ExerciceRepository extends JpaRepository<Exercice, Integer> {

    /**
     * Retourne la liste des exercices associés à un entraînement.
     *
     * @param trainingId l'identifiant de l'entraînement
     * @return la liste des exercices
     */
    List<Exercice> findByTrainingId(int trainingId);
}
