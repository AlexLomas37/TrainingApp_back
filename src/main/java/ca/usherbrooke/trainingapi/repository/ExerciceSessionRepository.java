package ca.usherbrooke.trainingapi.repository;

import ca.usherbrooke.trainingapi.model.ExerciceSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciceSessionRepository extends JpaRepository<ExerciceSession, Integer> {

    Iterable<ExerciceSession> findByExerciceId(int idExo);
}
