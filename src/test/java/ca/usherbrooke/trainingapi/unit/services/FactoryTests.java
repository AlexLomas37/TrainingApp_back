package ca.usherbrooke.trainingapi.unit.services;

import ca.usherbrooke.trainingapi.Services.Factories.StatisticsExerciceFactoryImpl;
import ca.usherbrooke.trainingapi.Services.Factories.StatisticsTrainingFactoryImpl;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsExerciceAVGStrategy;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsExerciceCurveStrategy;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsTrainingHeatmapStrategy;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsTrainingTotalStrategy;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.StatisticType;
import ca.usherbrooke.trainingapi.model.Training;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FactoryTests {

    @Autowired
    private StatisticsTrainingFactoryImpl statisticsTrainingFactory;
    @Autowired
    private StatisticsExerciceFactoryImpl statisticsExerciceFactory;
    private static Training training;
    private static Exercice exercice;

    @BeforeAll
    static void setUp() {
        exercice = mock(Exercice.class);
        when(exercice.getId()).thenReturn(1);
        when(exercice.getName()).thenReturn("Test Exercice");
        when(exercice.getDescription()).thenReturn("Test Description");

        training = mock(Training.class);
        when(training.getId()).thenReturn(1);
        when(training.getName()).thenReturn("Test Training");
        when(training.getDescription()).thenReturn("Test Description");
    }

    /**
     * Test pour vérifier que la méthode getStatisticsStrategy() de StatisticsTrainingFactoryImpl retourne la bonne instance de stratégie de calcul.
     */
    @Test
    void testStatisticsTrainingFactory() {
        assertEquals(StatisticsTrainingHeatmapStrategy.class, statisticsTrainingFactory.getStatisticsStrategy(StatisticType.HEATMAP).getClass());
        assertEquals(StatisticsTrainingTotalStrategy.class, statisticsTrainingFactory.getStatisticsStrategy(StatisticType.TOTAL).getClass());
    }

    /**
     * Test pour vérifier que la méthode getStatisticsStrategy() de StatisticsExerciceFactoryImpl retourne la bonne instance de stratégie de calcul.
     */
    @Test
    void testStatisticsExerciceFactory() {
        assertEquals(StatisticsExerciceCurveStrategy.class, statisticsExerciceFactory.getStatisticsStrategy(StatisticType.CURVE).getClass());
        assertEquals(StatisticsExerciceAVGStrategy.class, statisticsExerciceFactory.getStatisticsStrategy(StatisticType.AVERAGE).getClass());
    }

    /**
     * Test pour vérifier que la méthode getStatisticsStrategy() de StatisticsTrainingFactoryImpl et StatisticsExerciceFactoryImpl lève une exception IllegalArgumentException si le type de statistique est invalide.
     */
    @Test
    void testStatisticsFactoryWithBadStatisticsType() {
        assertThrows(IllegalArgumentException.class, () -> {
            statisticsExerciceFactory.getStatisticsStrategy(StatisticType.HEATMAP);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            statisticsTrainingFactory.getStatisticsStrategy(StatisticType.CURVE);
        });
    }
}
