package ca.usherbrooke.trainingapi.Services.Factories;

import ca.usherbrooke.trainingapi.Services.ExerciceService;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsExerciceInterface;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsTrainingInterface;
import ca.usherbrooke.trainingapi.Services.TrainingService;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StatisticsFactory {

    @Autowired
    private StatisticsTrainingInterface statisticsTrainingInterface;
    @Autowired
    private StatisticsExerciceInterface statisticsExerciceInterface;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private ExerciceService exerciceService;

    /**
     * Retourne les statistiques d'un objet (entraînement ou exercice) pour une période donnée ou un nombre de fois.
     *
     * @param objectToHaveStats Training ou Exercice
     * @param id L'id de l'objet
     * @param startDate La date de début de la période
     * @param endDate La date de fin de la période
     * @param nbTime Le nombre de fois que l'objet a été effectué
     * @return les statistiques de l'objet
     */
    public Object getStatistics(String objectToHaveStats, int id, String startDate, String endDate, int nbTime) {
        switch (objectToHaveStats) {
            case "training":
                Training training = trainingService.getTrainingById(id);
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);
                return statisticsTrainingInterface.retournerStatistiques(training, start, end);
            case "exercice":
                Exercice exercice = exerciceService.getExerciceById(id);
                if (nbTime > 0) {
                    return statisticsExerciceInterface.retournerStatistiques(exercice, nbTime);
                } else {
                    LocalDate startExo = LocalDate.parse(startDate);
                    LocalDate endExo = LocalDate.parse(endDate);
                    return statisticsExerciceInterface.retournerStatistiques(exercice, startExo, endExo);
                }
            default:
                throw new IllegalArgumentException("Type de statistiques non reconnu");
        }
    }
}
