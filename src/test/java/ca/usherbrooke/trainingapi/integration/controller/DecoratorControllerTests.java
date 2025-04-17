package ca.usherbrooke.trainingapi.integration.controller;

import ca.usherbrooke.trainingapi.Services.Decorators.ObjectWithStatsDecoratorImpl;
import ca.usherbrooke.trainingapi.Services.ExerciceService;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsStrategyInterface;
import ca.usherbrooke.trainingapi.Services.TrainingService;
import ca.usherbrooke.trainingapi.components.StatisticsFactoryProvider;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.model.StatisticType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class DecoratorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ObjectWithStatsDecoratorImpl exerciceDecorator;

    @MockBean
    private ExerciceService exerciceService;

    @MockBean
    private TrainingService trainingService;

    @MockBean
    private StatisticsFactoryProvider statisticsFactory;

    /**
     * Teste la méthode getExerciceDecorator() de la classe DecoratorController.
     *
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetExerciceDecorator() throws Exception {
        // Création d'un exercice test
        Exercice testExercice = new Exercice();
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 2);
        int nbTimes = 5;

        // Stub du service
        when(exerciceService.getExerciceById(1)).thenReturn(testExercice);

        // Création et configuration des mocks pour les stratégies
        StatisticsStrategyInterface strategyCurve = Mockito.mock(StatisticsStrategyInterface.class);
        StatisticsStrategyInterface strategyAvg = Mockito.mock(StatisticsStrategyInterface.class);
        when(strategyCurve.retournerStatistiques(eq(testExercice), eq(startDate), eq(endDate), eq(nbTimes)))
                .thenReturn("curve_data");
        when(strategyAvg.retournerStatistiques(eq(testExercice), eq(startDate), eq(endDate), eq(nbTimes)))
                .thenReturn("avg_data");

        when(statisticsFactory.getStatisticsStrategy(eq(testExercice), eq(StatisticType.CURVE)))
                .thenReturn(strategyCurve);
        when(statisticsFactory.getStatisticsStrategy(eq(testExercice), eq(StatisticType.AVERAGE)))
                .thenReturn(strategyAvg);

        // Stub du décorateur
        when(exerciceDecorator.retournerObjetDecore(eq(testExercice), any(Map.class))).thenAnswer(invocation -> {
            Object obj = invocation.getArgument(0);
            Map<String, Object> stats = invocation.getArgument(1);
            Map<String, Object> result = new HashMap<>();
            result.put("exercice", obj);
            result.putAll(stats);
            return result;
        });

        // Exécution de la requête et vérification du résultat
        mockMvc.perform(get("/decorators/exercices/1")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .param("nbTimes", String.valueOf(nbTimes)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exercice").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statistiques-curve").value("curve_data"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statistiques-avg").value("avg_data"));
    }

    /**
     * Teste la méthode getTrainingDecorator() de la classe DecoratorController.
     *
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetTrainingDecorator() throws Exception {
        // Création d'un training test
        Training testTraining = new Training();
        LocalDate startDate = LocalDate.of(2023, 2, 1);
        LocalDate endDate = LocalDate.of(2023, 2, 2);
        int nbTimes = 3;

        // Stub du service
        when(trainingService.getTrainingById(1)).thenReturn(testTraining);

        // Création et configuration du mock pour la stratégie HEATMAP
        StatisticsStrategyInterface strategyHeatmap = Mockito.mock(StatisticsStrategyInterface.class);
        when(strategyHeatmap.retournerStatistiques(eq(testTraining), eq(startDate), eq(endDate), eq(nbTimes)))
                .thenReturn("heatmap_data");

        when(statisticsFactory.getStatisticsStrategy(eq(testTraining), eq(StatisticType.HEATMAP)))
                .thenReturn(strategyHeatmap);

        // Stub du décorateur
        when(exerciceDecorator.retournerObjetDecore(eq(testTraining), any(Map.class))).thenAnswer(invocation -> {
            Object obj = invocation.getArgument(0);
            Map<String, Object> stats = invocation.getArgument(1);
            Map<String, Object> result = new HashMap<>();
            result.put("training", obj);
            result.putAll(stats);
            return result;
        });

        // Exécution de la requête et vérification du résultat
        mockMvc.perform(get("/decorators/trainings/1")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .param("nbTimes", String.valueOf(nbTimes)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.training").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statistiques-heatmap").value("heatmap_data"));
    }
}

