package ca.usherbrooke.trainingapi.components;

import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsExerciceCurveStrategy;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsTrainingHeatmapStrategy;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsStrategyInterface;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.StatisticType;
import ca.usherbrooke.trainingapi.model.Training;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class StatisticsFactoryProviderTests {

    @Autowired
    private StatisticsFactoryProvider statisticsFactoryProvider;
    @Autowired
    private StatisticsTrainingHeatmapStrategy statisticsTrainingHeatmapStrategy;
    @Autowired
    private StatisticsExerciceCurveStrategy statisticsExerciceCurveStrategy;

    private Training training;
    private Exercice exercice;
    private Object invalidObject;

    @BeforeEach
    void setup() {
        training = mock(Training.class);
        exercice = mock(Exercice.class);
        invalidObject = mock(Object.class);
    }

    /**
     * Test de la méthode getStatisticsStrategy pour vérifier que le provider retourne la bonne stratégie pour un objet Training.
     */
    @Test
    void testGetStatisticsStrategyForTraining() {
        StatisticsStrategyInterface strategyFromProvider = 
                statisticsFactoryProvider.getStatisticsStrategy(training, StatisticType.HEATMAP);
        assertEquals(statisticsTrainingHeatmapStrategy, strategyFromProvider, "Le provider doit retourner la stratégie HEATMAP de la factory Training");
    }

    /**
     * Test de la méthode getStatisticsStrategy pour vérifier que le provider retourne la bonne stratégie pour un objet Exercice.
     */
    @Test
    void testGetStatisticsStrategyForExercice() {
        StatisticsStrategyInterface strategyFromProvider = 
                statisticsFactoryProvider.getStatisticsStrategy(exercice, StatisticType.CURVE);
        assertEquals(statisticsExerciceCurveStrategy, strategyFromProvider, "Le provider doit retourner la stratégie CURVE de la factory Exercice");
    }

    /**
     * Test de la méthode getStatisticsStrategy pour vérifier que le provider retourne une IllegalArgumentException pour un objet invalide.
     */
    @Test
    void testGetStatisticsStrategyWithInvalidObject() {
        assertThrows(IllegalArgumentException.class, () ->
            statisticsFactoryProvider.getStatisticsStrategy(invalidObject, StatisticType.CURVE),
            "Un objet invalide doit générer une IllegalArgumentException"
        );
    }
}
