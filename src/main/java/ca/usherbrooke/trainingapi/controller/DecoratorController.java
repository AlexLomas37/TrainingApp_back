package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.Services.Decorators.ObjectWithStatsDecoratorImpl;
import ca.usherbrooke.trainingapi.Services.ExerciceService;
import ca.usherbrooke.trainingapi.Services.TrainingService;
import ca.usherbrooke.trainingapi.components.StatisticsFactoryProvider;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.StatisticType;
import ca.usherbrooke.trainingapi.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/decorators")
public class DecoratorController {

    @Autowired
    private ObjectWithStatsDecoratorImpl exerciceDecorator;
    @Autowired
    private ExerciceService exerciceService;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private StatisticsFactoryProvider statisticsFactory;

    /**
     * Récupère les statistiques d'un exercice et les décore avec des informations supplémentaires.
     *
     * @param id L'identifiant de l'exercice.
     * @param startDate La date de début pour les statistiques.
     * @param endDate La date de fin pour les statistiques.
     * @param nbTimes Le nombre de fois que l'exercice a été effectué si > 0. Les dates ne sont pâs prises en compte dans ce cas la.
     * @return Un objet contenant les statistiques décorées de l'exercice.
     */
    @GetMapping("/exercices/{id}")
    public Map<String, Object> getExerciceDecorator(@PathVariable int id, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam int nbTimes) {
        Exercice exercice = exerciceService.getExerciceById(id);
        Map<String, Object> stats = new HashMap<>();
        stats.put("statistiques-curve", statisticsFactory.getStatisticsStrategy(exercice, StatisticType.CURVE).retournerStatistiques(exercice, startDate, endDate, nbTimes));
        stats.put("statistiques-avg", statisticsFactory.getStatisticsStrategy(exercice, StatisticType.AVERAGE).retournerStatistiques(exercice, startDate, endDate, nbTimes));
        return (Map<String, Object>) exerciceDecorator.retournerObjetDecore(exercice, stats);
    }

    /**
     * Récupère les statistiques d'un entraînement et les décore avec des informations supplémentaires.
     *
     * @param id L'identifiant de l'entraînement.
     * @param startDate La date de début pour les statistiques.
     * @param endDate La date de fin pour les statistiques.
     * @param nbTimes Le nombre de fois que l'entraînement a été effectué si > 0. Les dates ne sont pâs prises en compte dans ce cas la.
     * @return Un objet contenant les statistiques décorées de l'entraînement.
     */
    @GetMapping("/trainings/{id}")
    public Map<String, Object> getTrainingDecorator(@PathVariable int id, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam int nbTimes) {
        Training training = trainingService.getTrainingById(id);
        Map<String, Object> stats = new HashMap<>();
        stats.put("statistiques-heatmap", statisticsFactory.getStatisticsStrategy(training, StatisticType.HEATMAP).retournerStatistiques(training, startDate, endDate, nbTimes));
        return (Map<String, Object>) exerciceDecorator.retournerObjetDecore(training, stats);
    }

}
