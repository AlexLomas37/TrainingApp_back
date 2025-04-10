package ca.usherbrooke.trainingapi.Services.Strategies;

import ca.usherbrooke.trainingapi.Services.TrainingSessionService;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.model.TrainingSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticsTrainingTotalStrategy implements StatisticsStrategyInterface{

    @Autowired
    private TrainingSessionService trainingSessionService;

    /**
     * Retourne le nombre total de sessions d'entraînement pour un entraînement donné.
     * (La gestio des dates et du nombre de fois n'est pas nécessaire ici)
     *
     * @param objectToHaveStats L'entraînement pour lequel on veut les statistiques
     * @param startDate La date de début de la période
     * @param endDate La date de fin de la période
     * @param nbTime Le nombre de fois que l'entraînement a été effectué
     * @return Le nombre total de sessions d'entraînement
     */
    @Override
    public Object retournerStatistiques(Object objectToHaveStats, LocalDate startDate, LocalDate endDate, int nbTime) {
        int totalNbSessions = 0;
        if(objectToHaveStats instanceof Training training) {
            List<TrainingSession> trainingSessions = (List<TrainingSession>) trainingSessionService
                    .getTrainingSessionsByTrainingId(training.getId());
            if (trainingSessions.isEmpty()) {
                throw new IllegalArgumentException("Aucune session d'entraînement trouvée pour cette période.");
            }
            totalNbSessions = trainingSessions.size();
        }
        return totalNbSessions;
    }
}
