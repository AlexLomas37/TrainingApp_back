package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.components.StatisticsFactoryProvider;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.StatisticMetric;
import ca.usherbrooke.trainingapi.model.StatisticType;
import ca.usherbrooke.trainingapi.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsFactoryProvider statisticsFactory;

    /**
     * Retourne les statistiques d'un entraînement sous forme de matrice.
     *
     * @param training L'id de l'entraînement
     * @param startDate La date de début de la période
     * @param endDate La date de fin de la période
     * @return une carte contenant les dates et un booléen indiquant si l'entraînement a été effectué ce jour-là
     */
    @GetMapping("/trainings/matrix")
    public Map<LocalDate, Boolean> getStatisticsTrainingMatrix(@RequestParam Training training, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return (Map<LocalDate, Boolean>) statisticsFactory.getStatisticsStrategy(training, StatisticType.HEATMAP).retournerStatistiques(training, startDate, endDate, 0);
    }

    /**
     * Retourne les nbTime dernières statistiques d'un exercice donné.
     *
     * @param exercice L'id de l'exercice
     * @param nbTime Le nombre de fois que l'exercice a été effectué trié par date décroissante
     * @return une carte contenant les dates et les statistiques d'accruacy
     */
    @GetMapping("/exercices/curve/nbTimes")
    public Map<LocalDate, Map<StatisticMetric, String>> getStatisticsExerciceCurveNbTime(@RequestParam Exercice exercice, @RequestParam int nbTime) {
        return (Map<LocalDate, Map<StatisticMetric, String>>) statisticsFactory.getStatisticsStrategy(exercice, StatisticType.CURVE).retournerStatistiques(exercice, null, null, nbTime);
    }

    /**
     * Retourne les statistiques d'un exercice pour une période donnée sous forme de Map dans le but d'afficher des graphes.
     *
     * @param exercice L'id de l'exercice
     * @param startDate La date de début de la période
     * @param endDate La date de fin de la période
     * @return une carte contenant les dates et les statistiques d'un exercice
     */
    @GetMapping("/exercices/curve/dates")
    public Map<LocalDate, Map<StatisticMetric, String>> getStatisticsExerciceCurveDates(@RequestParam Exercice exercice, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return (Map<LocalDate, Map<StatisticMetric, String>>) statisticsFactory.getStatisticsStrategy(exercice, StatisticType.CURVE).retournerStatistiques(exercice, startDate, endDate, 0);
    }

    /**
     * Retourne les statistiques d'un exercice pour une période donnée sous forme de Map dans le but d'afficher des graphes.
     *
     * @param exercice L'id de l'exercice
     * @param startDate La date de début de la période
     * @param endDate La date de fin de la période
     * @return une carte contenant les dates et les statistiques d'un exercice
     */
    @GetMapping("/exercices/avg/dates")
    public Map<StatisticMetric, Float> getStatisticsExerciceAVG(@RequestParam Exercice exercice, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return (Map<StatisticMetric, Float>) statisticsFactory.getStatisticsStrategy(exercice, StatisticType.AVERAGE).retournerStatistiques(exercice, startDate, endDate, 0);
    }

    /**
     * Retourne les nbTime dernières statistiques d'un exercice donné.
     *
     * @param exercice L'id de l'exercice
     * @param nbTime Le nombre de fois que l'exercice a été effectué trié par date décroissante
     * @return une carte contenant les dates et les statistiques d'un exercice
     */
    @GetMapping("/exercices/avg/nbTimes")
    public Map<StatisticMetric, Float> getStatisticsExerciceAVGNbTime(@RequestParam Exercice exercice, @RequestParam int nbTime) {
        return (Map<StatisticMetric, Float>) statisticsFactory.getStatisticsStrategy(exercice, StatisticType.AVERAGE).retournerStatistiques(exercice, null, null, nbTime);
    }


}
