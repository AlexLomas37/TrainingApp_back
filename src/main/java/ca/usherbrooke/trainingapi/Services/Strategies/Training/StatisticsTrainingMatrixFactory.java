package ca.usherbrooke.trainingapi.Services.Strategies.Training;

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
public class StatisticsTrainingMatrixFactory implements StatisticsTrainingInterface {

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
    @Override
    public Map<LocalDate, Boolean> retournerStatistiques(Training training, LocalDate dateDebut, LocalDate dateFin) {
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
            LocalDate currentDate = dateDebut;
            while (!currentDate.isAfter(dateFin)) {
                statistiques.put(currentDate, false);
                currentDate = currentDate.plusDays(1);
            }
            for (TrainingSession session : trainingSessions) {
                LocalDate dateSession = session.getStart().toLocalDate();
                if (statistiques.containsKey(dateSession)) {
                    statistiques.put(dateSession, true);
                }
            }
            System.out.println("Statistiques pour l'entraînement " + training.getId() + " entre " + dateDebut + " et " + dateFin + ":");
        }
        return statistiques;
    }
}
