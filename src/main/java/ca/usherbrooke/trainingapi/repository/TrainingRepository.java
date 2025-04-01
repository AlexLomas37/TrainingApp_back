package ca.usherbrooke.trainingapi.repository;

import ca.usherbrooke.trainingapi.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour accéder aux données des entraînements.
 */
public interface TrainingRepository extends JpaRepository<Training, Integer> {

    /**
     * Retourne la liste des entraînements filtrés par identifiant de discipline.
     *
     * @param disciplineId l'identifiant de la discipline
     * @return la liste des entraînements
     */
    List<Training> findByDisciplineId(int disciplineId);
}
