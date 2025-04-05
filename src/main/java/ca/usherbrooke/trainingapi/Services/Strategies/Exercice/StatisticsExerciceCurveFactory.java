package ca.usherbrooke.trainingapi.Services.Strategies.Exercice;

import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.ExerciceSession;
import ca.usherbrooke.trainingapi.model.StatisticType;
import ca.usherbrooke.trainingapi.repository.ExerciceSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsExerciceCurveFactory implements StatisticsExerciceInterface {

    @Autowired
    private ExerciceSessionRepository exerciceSessionRepository;

    /**
     * Retourne les statistiques d'un exercice pour une période donnée sous forme de Map dans le but d'afficher des graphes.
     *
     * @param exercice L'id de l'exercice
     * @param nbTime Le nombre de fois que l'exercice a été effectué trié par date decreissante
     * @return une carte contenant les dates et les statistiques d'un exercice
     */
    @Override
    public Map<LocalDate, Map<StatisticType, String>> retournerStatistiques(Exercice exercice, int nbTime) {
        if (nbTime <= 0) {
            throw new IllegalArgumentException("Le nombre de temps doit être positif.");
        }
        Map<LocalDate, Map<StatisticType, String>> statistiques = new HashMap<>();

        List<ExerciceSession> exerciceSessions = (List<ExerciceSession>)exerciceSessionRepository
                .findByExerciceIdAndLastNbTime(exercice.getId(), nbTime);
        if (exerciceSessions.isEmpty()) {
            System.out.println("Aucune session d'exercice trouvée pour cette période.");
        } else {

            Map<StatisticType, String> allStatisticsMap = new HashMap<>();

            for (ExerciceSession session : exerciceSessions) {
                LocalDate dateSession = session.getStart().toLocalDate();
                for (Map.Entry<StatisticType, String> entry : session.getStatisticsMap().entrySet()) {
                    StatisticType type = entry.getKey();
                    String value = entry.getValue();
                    allStatisticsMap.merge(type, value, (oldValue, newValue) -> oldValue + " ; " + newValue);
                }
                statistiques.put(dateSession, allStatisticsMap);
            }
        }

        return statistiques;
    }

    /**
     * Retourne les statistiques d'un exercice pour une période donnée sous forme de Map dans le but d'afficher des graphes.
     *
     * @param exercice L'id de l'exercice
     * @param dateDebut La date de début de la période
     * @param dateFin La date de fin de la période
     * @return une carte contenant les dates et les statistiques d'un exercice
     */
    @Override
    public Map<LocalDate, Map<StatisticType, String>> retournerStatistiques(Exercice exercice, LocalDate dateDebut, LocalDate dateFin) {
        if(dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("Date de début et de fin ne peuvent pas être nulles");
        }
        if(dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("La date de fin ne peut pas être avant la date de début.");
        }
        if(dateDebut.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de début ne peut pas être dans le futur.");
        }

        Map<LocalDate, Map<StatisticType, String>> statistiques = new HashMap<>();

        LocalDateTime dateDebutDateTime = dateDebut.atStartOfDay();
        LocalDateTime dateFinDateTime = dateFin.atStartOfDay().plusDays(1).minusSeconds(1);

        List<ExerciceSession> exerciceSessions = (List<ExerciceSession>)exerciceSessionRepository
                .findByExerciceIdAndDates(exercice.getId(), dateDebutDateTime, dateFinDateTime);
        if (exerciceSessions.isEmpty()) {
            System.out.println("Aucune session d'exercice trouvée pour cette période.");
        } else {

            Map<StatisticType, String> allStatisticsMap = new HashMap<>();

            for (ExerciceSession session : exerciceSessions) {
                LocalDate dateSession = session.getStart().toLocalDate();
                for (Map.Entry<StatisticType, String> entry : session.getStatisticsMap().entrySet()) {
                    StatisticType type = entry.getKey();
                    String value = entry.getValue();
                    allStatisticsMap.merge(type, value, (oldValue, newValue) -> oldValue + " ; " + newValue);
                }
                statistiques.put(dateSession, allStatisticsMap);
            }
            System.out.println("Statistiques pour l'exercice " + exercice.getId() + " entre " + dateDebut + " et " + dateFin + ":");
        }

        return statistiques;
    }
}
