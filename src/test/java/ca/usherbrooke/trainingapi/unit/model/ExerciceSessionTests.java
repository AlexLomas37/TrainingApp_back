package ca.usherbrooke.trainingapi.unit.model;

import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.ExerciceSession;
import ca.usherbrooke.trainingapi.model.StatisticMetric;
import ca.usherbrooke.trainingapi.model.TrainingSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests pour la classe ExerciceSession.
 */
public class ExerciceSessionTests {
    private ExerciceSession session;
    private Exercice exercice;
    private TrainingSession trainingSession;

    /**
     * Initialisation avant chaque test.
     */
    @BeforeEach
    public void setup() {
        session = new ExerciceSession();
        exercice = mock(Exercice.class);
        trainingSession = mock(TrainingSession.class);
        session.setExercice(exercice);
        session.setTrainingSession(trainingSession);
    }

    /**
     * Test de l'état initial de la session d'exercice.
     */
    @Test
    public void testInitialState() {
        assertNull(session.getStart());
        assertNull(session.getEnd());
        assertNotNull(session.getStatisticsMap());
        assertTrue(session.getStatisticsMap().isEmpty());
    }

    /**
     * Test de récupération des dates
     */
    @Test
    public void testSetAndGetDates() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);
        session.setStart(start);
        session.setEnd(end);
        assertEquals(start, session.getStart());
        assertEquals(end, session.getEnd());
    }

    /**
     * Test de récupération de l'exercice
     */
    @Test
    public void testSetAndGetExercice() {
        Exercice newExercice = mock(Exercice.class);
        session.setExercice(newExercice);
        assertEquals(newExercice, session.getExercice());
    }

    /**
     * Test de récupération de la session d'entraînement
     */
    @Test
    public void testSetAndGetTrainingSession() {
        TrainingSession newTrainingSession = mock(TrainingSession.class);
        session.setTrainingSession(newTrainingSession);
        assertEquals(newTrainingSession, session.getTrainingSession());
    }

    /**
     * Test de récupération de la carte des statistiques
     */
    @Test
    public void testAddStatistic() {
        StatisticMetric metric = mock(StatisticMetric.class);
        String value = "10";
        session.getStatisticsMap().put(metric, value);
        assertEquals(value, session.getStatisticsMap().get(metric));
    }

    /**
     * Test de la suppression d'une statistique
     */
    @Test
    public void testRemoveStatistic() {
        StatisticMetric metric = mock(StatisticMetric.class);
        String value = "10";
        session.getStatisticsMap().put(metric, value);
        session.getStatisticsMap().remove(metric);
        assertFalse(session.getStatisticsMap().containsKey(metric));
    }

    /**
     * Test du constructeur avec paramètres
     */
    @Test
    public void testConstructorWithParameters() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(45);
        Map<StatisticMetric, String> stats = new HashMap<>();
        stats.put(mock(StatisticMetric.class), "value");
        ExerciceSession paramSession = new ExerciceSession(exercice, start, end, stats, trainingSession);

        assertEquals(exercice, paramSession.getExercice());
        assertEquals(start, paramSession.getStart());
        assertEquals(end, paramSession.getEnd());
        assertEquals(stats, paramSession.getStatisticsMap());
        assertEquals(trainingSession, paramSession.getTrainingSession());
    }
}
