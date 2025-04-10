package ca.usherbrooke.trainingapi.Services.Strategies;

import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.model.TrainingSession;
import ca.usherbrooke.trainingapi.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsTrainingHeatmapStrategy implements StatisticsStrategyInterface {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    /**
     * Retourne les jours ou un entraînement à été effectué pour une période donnée.
     *
     * @param training L'id de l'entraînement
     * @param dateDebut La date de début de la période
     * @param dateFin La date de fin de la période
     * @return une carte contenant les dates et un booléen indiquant si l'entraînement a été effectué ce jour-là
     */
    private Map<LocalDate, Boolean> retournerStatistiques(Training training, LocalDate dateDebut, LocalDate dateFin) {
        if(dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("La date de fin ne peut pas être avant la date de début.");
        }
        if(dateDebut.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de début ne peut pas être dans le futur.");
        }

        Map<LocalDate, Boolean> statistiques = new HashMap<>();

        LocalDateTime dateDebutDateTime = dateDebut.atStartOfDay();
        LocalDateTime dateFinDateTime = dateFin.atStartOfDay().plusDays(1).minusSeconds(1);

        List<TrainingSession> trainingSessions = (List<TrainingSession>) trainingSessionRepository
                .findByDateTrainingSessionBetweenAndTrainingId(dateDebutDateTime, dateFinDateTime, training.getId());
        if (trainingSessions.isEmpty()) {
            System.out.println("Aucune session d'entraînement trouvée pour cette période.");
        } else {
            /*LocalDate currentDate = dateDebut;
            while (!currentDate.isAfter(dateFin)) {
                statistiques.put(currentDate, false);
                currentDate = currentDate.plusDays(1);
            }*/
            for (TrainingSession session : trainingSessions) {
                LocalDate dateSession = session.getStart().toLocalDate();
                statistiques.put(dateSession, true);
            }
            System.out.println("Statistiques pour l'entraînement " + training.getId() + " entre " + dateDebut + " et " + dateFin + ":");
        }
        return statistiques;
    }

    /**
     * Retourne les jours ou un entraînement à été effectué pour une période donnée.
     *
     * @param objectToHaveStats L'objet pour lequel on veut obtenir des statistiques.
     * @param startDate La date de début de la période
     * @param endDate La date de fin de la période
     * @param nbTime Le nombre de fois que l'entraînement a été effectué trié par date décroissante
     * @return une carte contenant les dates et un booléen indiquant si l'entraînement a été effectué ce jour-là
     */
    @Override
    public Object retournerStatistiques(Object objectToHaveStats, LocalDate startDate, LocalDate endDate, int nbTime) {
        if(objectToHaveStats instanceof Training) {
            Training training = (Training) objectToHaveStats;
            return retournerStatistiques(training, startDate, endDate);
        } else {
            throw new IllegalArgumentException("L'objet doit être de type Entraînement.");
        }
    }
}
