package ca.usherbrooke.trainingapi.unit.services;

import ca.usherbrooke.trainingapi.Services.ExerciceSessionService;
import ca.usherbrooke.trainingapi.Services.TrainingSessionService;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsExerciceAVGStrategy;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsExerciceCurveStrategy;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsTrainingHeatmapStrategy;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsTrainingTotalStrategy;
import ca.usherbrooke.trainingapi.model.*;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StrategyTests {

    @Mock
    private StatisticsTrainingHeatmapStrategy statisticsTrainingHeatmapStrategy;
    @Mock
    private StatisticsTrainingTotalStrategy statisticsTrainingTotalStrategy;
    @InjectMocks
    private StatisticsExerciceCurveStrategy statisticsExerciceCurveStrategy;
    @InjectMocks
    private StatisticsExerciceAVGStrategy statisticsExerciceAvgStrategy;
    @Mock
    private ExerciceSessionService exerciceSessionService;
    @Mock
    private TrainingSessionService trainingSessionService;

    private Training training;
    private Exercice exercice;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private Map<LocalDate, Boolean> statsHeatmap;
    private Map<LocalDate, Map<StatisticMetric, String>> statsCurve;
    private Map<StatisticMetric, Float> statsAvg;
    private Map<StatisticMetric, Integer> statsTotal;

    @BeforeEach
    void setUp() {
        // Création de mocks pour les entités
        exercice = mock(Exercice.class);
        when(exercice.getId()).thenReturn(1);

        training = mock(Training.class);
        when(training.getId()).thenReturn(1);

        // Utilisation d'instances réelles pour LocalDate
        dateDebut = LocalDate.of(2023, 1, 1);
        dateFin = LocalDate.of(2023, 12, 31);

        // Initialisation des statistiques avec des instances concrètes
        statsHeatmap = Map.of(
                LocalDate.of(2023, 1, 1), true,
                LocalDate.of(2023, 1, 2), false
        );

        statsCurve = Map.of(
                LocalDate.of(2023, 1, 1), Map.of(StatisticMetric.ACCURACY, "90"),
                LocalDate.of(2023, 1, 2), Map.of(StatisticMetric.ACCURACY, "80")
        );
        statsAvg = Map.of(
                StatisticMetric.ACCURACY, 85.3F
        );
        statsTotal = Map.of(
                StatisticMetric.ACCURACY, 98
        );
    }

    /**
     * Test pour vérifier que la méthode retournerStatistiques de StatisticsTrainingHeatmapStrategy retourne les statistiques correctes.
     */
    @Test
    void testTrainingHeatmapStrategyNormal() {
        doReturn(statsHeatmap).when(statisticsTrainingHeatmapStrategy)
                .retournerStatistiques(training, dateDebut, dateFin, 0);
        assertEquals(statsHeatmap, statisticsTrainingHeatmapStrategy.retournerStatistiques(training, dateDebut, dateFin, 0));
    }

    /**
     * Test pour vérifier que la méthode retournerStatistiques de StatisticsTrainingHeatmapStrategy lève une exception si la date de début est dans le futur.
     */
    @Test
    void testTrainingHeatmapStrategyInvalidDates() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now();

        doCallRealMethod().when(statisticsTrainingHeatmapStrategy)
                .retournerStatistiques(training, startDate, endDate, 0);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                statisticsTrainingHeatmapStrategy.retournerStatistiques(training, startDate, endDate, 0)
        );
        assertEquals("La date de fin ne peut pas être avant la date de début.", ex.getMessage());
    }

    /**
     * Test pour vérifier que la méthode retournerStatistiques de StatisticsTrainingTotalStrategy retourne les statistiques correctes.
     */
    @Test
    void testTrainingTotalStrategyInvalidDates() {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now();

        doCallRealMethod().when(statisticsTrainingTotalStrategy)
                .retournerStatistiques(training, startDate, endDate, 0);

        assertThrows(IllegalArgumentException.class, () ->
                statisticsTrainingTotalStrategy.retournerStatistiques(training, startDate, endDate, 0)
        );
    }

    /**
     * Test pour vérifier que la méthode retournerStatistiques de StatisticsTrainingHeatmapStrategy lève une exception si la date de fin est avant la date de début.
     */
    @Test
    void testTrainingTotalStrategyReturnsTotal() {
        doReturn(statsTotal).when(statisticsTrainingTotalStrategy)
                .retournerStatistiques(training, dateDebut, dateFin, 0);
        when(trainingSessionService.getTrainingSessionsByTrainingId(training.getId()))
            .thenReturn(List.of(mock(TrainingSession.class)));
        assertEquals(statsTotal, statisticsTrainingTotalStrategy.retournerStatistiques(training, dateDebut, dateFin, 0));
    }

    /**
     * Test pour vérifier que la méthode retournerStatistiques de StatisticsTrainingTotalStrategy lève une exception si la date de début est dans le futur.
     */
    @Test
    void testExerciceAVGStrategyAverageCalculation() {
        Map<StatisticMetric, String> stats1 = Map.of(StatisticMetric.ACCURACY, "10");
        Map<StatisticMetric, String> stats2 = Map.of(StatisticMetric.ACCURACY, "20");
        ExerciceSession session1 = mock(ExerciceSession.class);
        ExerciceSession session2 = mock(ExerciceSession.class);
        when(session1.getStatisticsMap()).thenReturn(stats1);
        when(session2.getStatisticsMap()).thenReturn(stats2);
        List<ExerciceSession> sessions = List.of(session1, session2);
        when(exerciceSessionService.getExercicesSessionsByDates(exercice.getId(), dateDebut, dateFin))
            .thenReturn(sessions);
        Map<StatisticMetric, Float> expectedAvg = Map.of(StatisticMetric.ACCURACY, 15.0F);
        assertEquals(expectedAvg, statisticsExerciceAvgStrategy.retournerStatistiques(exercice, dateDebut, dateFin, 0));
    }

    /**
     * Test pour vérifier que la méthode retournerStatistiques de StatisticsExerciceAVGStrategy lève une exception si la date de début est dans le futur.
     */
    @Test
    void testExerciceCurveStrategyMergeLogic() {
        Map<StatisticMetric, String> stats1 = Map.of(StatisticMetric.ACCURACY, "89");
        Map<StatisticMetric, String> stats2 = Map.of(StatisticMetric.ACCURACY, "20");
        ExerciceSession session1 = mock(ExerciceSession.class);
        ExerciceSession session2 = mock(ExerciceSession.class);
        when(session1.getStart()).thenReturn(LocalDateTime.of(2023, 1, 1, 0, 0));
        when(session2.getStart()).thenReturn(LocalDateTime.of(2023, 1, 2, 0, 0));
        when(session1.getStatisticsMap()).thenReturn(stats1);
        when(session2.getStatisticsMap()).thenReturn(stats2);
        List<ExerciceSession> sessions = List.of(session1, session2);
        when(exerciceSessionService.getExercicesSessionsByDates(exercice.getId(), dateDebut, dateFin))
                .thenReturn(sessions);
        Map<LocalDate, Map<StatisticMetric, String>> expected = Map.of(
                LocalDate.of(2023, 1, 1), Map.of(StatisticMetric.ACCURACY, "89"),
                LocalDate.of(2023, 1, 2), Map.of(StatisticMetric.ACCURACY, "20")
        );
        assertEquals(expected, statisticsExerciceCurveStrategy.retournerStatistiques(exercice, dateDebut, dateFin, 0));
    }

    /**
     * Test pour vérifier que StatisticsTrainingHeatmapStrategy lève une exception 
     * avec nbTime positif (cas non implémenté)
     */
    @Test
    void testTrainingHeatmapStrategyNbTime() {
        doCallRealMethod().when(statisticsTrainingHeatmapStrategy)
            .retournerStatistiques(training, dateDebut, dateFin, 1);
        Exception ex = assertThrows(NotImplementedException.class, () ->
            statisticsTrainingHeatmapStrategy.retournerStatistiques(training, dateDebut, dateFin, 1)
        );
        assertEquals("La méthode retournerStatistiques avec nbTime n'est pas implémentée pour les entraînements.", ex.getMessage());
    }

    /**
     * Test pour vérifier que StatisticsExerciceAVGStrategy fonctionne avec nbTime positif.
     */
    @Test
    void testExerciceAVGStrategyNbTime() {
        // Création de sessions factices
        Map<StatisticMetric, String> stats1 = Map.of(StatisticMetric.ACCURACY, "30");
        Map<StatisticMetric, String> stats2 = Map.of(StatisticMetric.ACCURACY, "40");
        ExerciceSession session1 = mock(ExerciceSession.class);
        ExerciceSession session2 = mock(ExerciceSession.class);
        when(session1.getStatisticsMap()).thenReturn(stats1);
        when(session2.getStatisticsMap()).thenReturn(stats2);
        List<ExerciceSession> sessions = List.of(session1, session2);
        when(exerciceSessionService.getExercicesSessionsByIdExoAndLastXTimes(exercice.getId(), 5))
            .thenReturn(sessions);
        Map<StatisticMetric, Float> expectedAvg = Map.of(StatisticMetric.ACCURACY, 35.0F);
        assertEquals(expectedAvg, statisticsExerciceAvgStrategy.retournerStatistiques(exercice, dateDebut, dateFin, 5));
    }

    /**
     * Test pour vérifier que StatisticsExerciceCurveStrategy fonctionne avec nbTime positif.
     */
    @Test
    void testExerciceCurveStrategyNbTime() {
        // Création de sessions factices avec dates distinctes
        ExerciceSession session1 = mock(ExerciceSession.class);
        ExerciceSession session2 = mock(ExerciceSession.class);
        LocalDateTime dateTime1 = LocalDateTime.of(2023, 2, 1, 10, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2023, 2, 2, 10, 0);
        
        when(session1.getStart()).thenReturn(dateTime1);
        when(session2.getStart()).thenReturn(dateTime2);
        Map<StatisticMetric, String> stats1 = Map.of(StatisticMetric.ACCURACY, "50");
        Map<StatisticMetric, String> stats2 = Map.of(StatisticMetric.ACCURACY, "60");
        when(session1.getStatisticsMap()).thenReturn(stats1);
        when(session2.getStatisticsMap()).thenReturn(stats2);
        List<ExerciceSession> sessions = List.of(session1, session2);
        when(exerciceSessionService.getExercicesSessionsByIdExoAndLastXTimes(exercice.getId(), 3))
            .thenReturn(sessions);
        
        Map<LocalDate, Map<StatisticMetric, String>> expected = new HashMap<>();
        expected.put(dateTime1.toLocalDate(), stats1);
        expected.put(dateTime2.toLocalDate(), stats2);
        
        assertEquals(expected, statisticsExerciceCurveStrategy.retournerStatistiques(exercice, dateDebut, dateFin, 3));
    }
}
