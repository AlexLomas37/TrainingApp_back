package ca.usherbrooke.trainingapi.repository;

import ca.usherbrooke.trainingapi.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour accéder aux données des disciplines.
 */
public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {

    /**
     * Retourne la liste des disciplines filtrées par nom.
     *
     * @param name le nom de la discipline
     * @return la liste des disciplines correspondantes
     */
    List<Discipline> findByName(String name);
}
