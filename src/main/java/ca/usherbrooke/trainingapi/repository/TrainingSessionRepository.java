package ca.usherbrooke.trainingapi.repository;

import ca.usherbrooke.trainingapi.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Integer> {

    Iterable<TrainingSession> findByTrainingId(int training_id);
}
