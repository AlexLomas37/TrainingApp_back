package ca.usherbrooke.trainingapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExerciceSessionTests {
    private ExerciceSession session;
    private Exercice exercice;
    private TrainingSession trainingSession;

    @BeforeEach
    public void setup() {
        session = new ExerciceSession();
        exercice = mock(Exercice.class);
        trainingSession = mock(TrainingSession.class);
        session.setExercice(exercice);
        session.setTrainingSession(trainingSession);
    }

    @Test
    public void testInitialState() {
        assertNull(session.getStart());
        assertNull(session.getEnd());
        assertNotNull(session.getStatisticsMap());
        assertTrue(session.getStatisticsMap().isEmpty());
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
    public void testSetAndGetExercice() {
        Exercice newExercice = mock(Exercice.class);
        session.setExercice(newExercice);
        assertEquals(newExercice, session.getExercice());
    }

    @Test
    public void testSetAndGetTrainingSession() {
        TrainingSession newTrainingSession = mock(TrainingSession.class);
        session.setTrainingSession(newTrainingSession);
        assertEquals(newTrainingSession, session.getTrainingSession());
    }

    @Test
    public void testAddStatistic() {
        StatisticMetric metric = mock(StatisticMetric.class);
        String value = "10";
        session.getStatisticsMap().put(metric, value);
        assertEquals(value, session.getStatisticsMap().get(metric));
    }

    @Test
    public void testRemoveStatistic() {
        StatisticMetric metric = mock(StatisticMetric.class);
        String value = "10";
        session.getStatisticsMap().put(metric, value);
        session.getStatisticsMap().remove(metric);
        assertFalse(session.getStatisticsMap().containsKey(metric));
    }

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
