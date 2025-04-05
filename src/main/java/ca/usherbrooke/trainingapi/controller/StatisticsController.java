package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.Services.Strategies.Exercice.StatisticsExerciceInterface;
import ca.usherbrooke.trainingapi.Services.Strategies.Training.StatisticsTrainingMatrixFactory;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.StatisticType;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.repository.ExerciceRepository;
import ca.usherbrooke.trainingapi.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private ExerciceRepository exerciceRepository;
    @Autowired
    private StatisticsTrainingMatrixFactory statisticsTrainingService;
    @Autowired
    private StatisticsExerciceInterface statisticsExerciceService;

    /**
     * Retourne les statistiques d'un entraînement sous forme de matrice.
     *
     * @param training L'id de l'entraînement
     * @param startDate La date de début de la période
     * @param endDate La date de fin de la période
     * @return une carte contenant les dates et un booléen indiquant si l'entraînement a été effectué ce jour-là
     */
    @GetMapping("/trainings/matrix")
    public Object getStatisticsTrainingMatrix(@RequestParam Training training, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        Training train = trainingRepository
                .findById(training.getId())
                .orElseThrow(() -> new IllegalArgumentException("Aucun entraînement trouvé avec cet ID: " + training.getId()));
        return statisticsTrainingService.retournerStatistiques(train, startDate, endDate).toString();
    }

    /**
     * Retourne les nbTime dernières statistiques d'un exercice donné.
     *
     * @param exercice L'id de l'exercice
     * @param nbTime Le nombre de fois que l'exercice a été effectué trié par date décroissante
     * @return une carte contenant les dates et les statistiques d'accruacy
     */
    @GetMapping("/exercices/curve/nbTimes")
    public Map<LocalDate, Map<StatisticType, String>> getStatisticsExerciceCurveNbTime(@RequestParam Exercice exercice, @RequestParam int nbTime) {
        Exercice exo = exerciceRepository
                .findById(exercice.getId())
                .orElseThrow(() -> new IllegalArgumentException("Aucun exercice trouvé avec cet ID: " + exercice.getId()));
        return (Map<LocalDate, Map<StatisticType, String>>) statisticsExerciceService.retournerStatistiques(exo, nbTime);
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
    public Map<LocalDate, Map<StatisticType, String>> getStatisticsExerciceCurveDates(@RequestParam Exercice exercice, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        Exercice exo = exerciceRepository
                .findById(exercice.getId())
                .orElseThrow(() -> new IllegalArgumentException("Aucun exercice trouvé avec cet ID: " + exercice.getId()));
        return (Map<LocalDate, Map<StatisticType, String>>) statisticsExerciceService.retournerStatistiques(exo, startDate, endDate);
    }


}
