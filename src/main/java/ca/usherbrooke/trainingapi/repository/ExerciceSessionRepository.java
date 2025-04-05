package ca.usherbrooke.trainingapi.repository;

import ca.usherbrooke.trainingapi.model.ExerciceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ExerciceSessionRepository extends JpaRepository<ExerciceSession, Integer> {

    @Query("SELECT es FROM ExerciceSession es WHERE es.exercice.id = :idExercice")
    Iterable<ExerciceSession> findByExerciceId(int exerciceId);

    @Query("SELECT es FROM ExerciceSession es WHERE es.exercice.id = :exerciceId AND es.start BETWEEN :startDate AND :endDate")
    Iterable<ExerciceSession> findByExerciceIdAndDates(int exerciceId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT es FROM ExerciceSession es WHERE es.exercice.id = :exerciceId ORDER BY es.start DESC LIMIT :nb")
    Iterable<ExerciceSession> findByExerciceIdAndLastNbTime(int exerciceId, int nb);
}
