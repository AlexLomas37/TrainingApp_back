package ca.usherbrooke.trainingapi.repository;

import ca.usherbrooke.trainingapi.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Integer> {

    Iterable<TrainingSession> findByTrainingId(int training_id);

    @Query("SELECT ts FROM TrainingSession ts WHERE ts.training.id = :trainingId AND ts.start BETWEEN :startDate AND :endDate")
    Iterable<TrainingSession> findByDateTrainingSessionBetweenAndTrainingId(LocalDateTime startDate, LocalDateTime endDate, int trainingId);
}
