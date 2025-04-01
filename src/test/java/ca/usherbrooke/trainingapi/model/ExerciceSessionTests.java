package ca.usherbrooke.trainingapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExerciceSessionTests {
    private ExerciceSession session;
    private Exercice exercice;
    private TrainingSession trainingSession;

    @BeforeEach
    public void setup() {
        session = new ExerciceSession();
        session.setStatistics(new ArrayList<>());
        // Mocks pour les entités associées
        exercice = mock(Exercice.class);
        trainingSession = mock(TrainingSession.class);
        session.setExercice(exercice);
        session.setTrainingSession(trainingSession);
    }

    @Test
    public void testInitialState() {
        assertNotNull(session.getStatistics());
        assertNull(session.getStart());
        assertNull(session.getEnd());
    }
    
    @Test
    public void testSetAndGetDates() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);
        session.setStart(start);
        session.setEnd(end);
        assertEquals(start, session.getStart());
        assertEquals(end, session.getEnd());
    }
    
    @Test
    public void testAddStatistic() {
        ExerciceStatistic statistic = mock(ExerciceStatistic.class);
        session.addStatistic(statistic);
        verify(statistic).setExerciceSession(session);
        assertTrue(session.getStatistics().contains(statistic));
    }
    
    @Test
    public void testRemoveStatistic() {
        ExerciceStatistic statistic = mock(ExerciceStatistic.class);
        // Ajoute d'abord puis supprime
        session.addStatistic(statistic);
        session.removeStatistic(statistic);
        // Vérifier le rappel de suppression
        verify(statistic).setExerciceSession(null);
        assertFalse(session.getStatistics().contains(statistic));
    }
    
    @Test
    public void testConstructorWithParameters() {
        // Utilisation du constructeur avec paramètres pour vérifier les affectations correctes.
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(45);
        ExerciceStatistic statistic = mock(ExerciceStatistic.class);
        ArrayList<ExerciceStatistic> stats = new ArrayList<>();
        stats.add(statistic);
        ExerciceSession paramSession = new ExerciceSession(exercice, start, end, stats);
        assertEquals(exercice, paramSession.getExercice());
        assertEquals(start, paramSession.getStart());
        assertEquals(end, paramSession.getEnd());
        assertEquals(stats, paramSession.getStatistics());
    }
}
