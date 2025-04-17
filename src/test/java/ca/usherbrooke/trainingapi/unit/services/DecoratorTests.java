package ca.usherbrooke.trainingapi.unit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.usherbrooke.trainingapi.Services.Decorators.ObjectWithStatsDecoratorImpl;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.Training;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class DecoratorTests {

    @Autowired
    private ObjectWithStatsDecoratorImpl addStatsDecorator;

    private Training training;
    private Exercice exercice;
    private Map<String, Object> stats;

    @BeforeEach
    void setUp() {
        exercice = mock(Exercice.class);
        training = mock(Training.class);
        when(exercice.getId()).thenReturn(1);
        when(training.getId()).thenReturn(1);

        stats = new HashMap<>();
        stats.put("statistiques-curve", "curve stats");
        stats.put("statistiques-avg", "avg stats");
    }

    /**
     * Teste la méthode retournerObjetDecore de la classe AddStatisticsDecoratorImpl
     */
    @Test
    void testTrainingDecoratorValid() {
        Map<String, Object> decorated = (Map<String, Object>) addStatsDecorator.retournerObjetDecore(training, stats);
        assertTrue(decorated.containsKey("training"));
        assertTrue(decorated.containsKey("statistiques-curve"));
        assertTrue(decorated.containsKey("statistiques-avg"));
    }

    /**
     * Teste la méthode retournerObjetDecore de la classe AddStatisticsDecoratorImpl
     */
    @Test
    void testTrainingDecoratorInvalidInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                addStatsDecorator.retournerObjetDecore(null, stats)
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                addStatsDecorator.retournerObjetDecore(exercice, null)
        );
    }

    /**
     * Teste la méthode retournerObjetDecore de la classe AddStatisticsDecoratorImpl
     */
    @Test
    void testExerciceDecoratorValid() {
        Map<String, Object> decorated = (Map<String, Object>) addStatsDecorator.retournerObjetDecore(exercice, stats);
        assertTrue(decorated.containsKey("exercice"));
        assertTrue(decorated.containsKey("statistiques-curve"));
        assertTrue(decorated.containsKey("statistiques-avg"));
    }

    /**
     * Teste la méthode retournerObjetDecore de la classe AddStatisticsDecoratorImpl
     */
    @Test
    void testExerciceDecoratorInvalidInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                addStatsDecorator.retournerObjetDecore(null, stats)
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                addStatsDecorator.retournerObjetDecore(training, null)
        );
    }
}