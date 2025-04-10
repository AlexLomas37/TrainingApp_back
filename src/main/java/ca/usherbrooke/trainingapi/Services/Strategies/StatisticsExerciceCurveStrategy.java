package ca.usherbrooke.trainingapi.Services.Strategies;

import ca.usherbrooke.trainingapi.Services.ExerciceSessionService;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.ExerciceSession;
import ca.usherbrooke.trainingapi.model.StatisticMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsExerciceCurveStrategy implements StatisticsStrategyInterface {

    @Autowired
    private ExerciceSessionService exerciceSessionService;

    /**
     * Cette méthode permet de créer une carte contenant les statistiques d'un exercice.
     *
     * @param session La session d'exercice
     * @return une carte contenant les statistiques d'un exercice
     */
    private Map<StatisticMetric, String> setStatisticsMap(ExerciceSession session) {
        Map<StatisticMetric, String> statisticsMap = new HashMap<>();
        for (Map.Entry<StatisticMetric, String> entry : session.getStatisticsMap().entrySet()) {
            StatisticMetric type = entry.getKey();
            String value = entry.getValue();
            statisticsMap.merge(type, value, (oldValue, newValue) -> oldValue + " ; " + newValue);
        }
        return statisticsMap;
    }

    /**
     * Retourne les statistiques d'un exercice pour une période donnée sous forme de Map dans le but d'afficher des graphes.
     *
     * @param exercice L'id de l'exercice
     * @param nbTime Le nombre de fois que l'exercice a été effectué trié par date decreissante
     * @return une carte contenant les dates et les statistiques d'un exercice
     */
    public Map<LocalDate, Map<StatisticMetric, String>> retournerStatistiques(Exercice exercice, int nbTime) {
        if (nbTime <= 0) {
            throw new IllegalArgumentException("Le nombre de temps doit être positif.");
        }
        Map<LocalDate, Map<StatisticMetric, String>> statistiques = new HashMap<>();

        List<ExerciceSession> exerciceSessions = exerciceSessionService.getExercicesSessionsByIdExoAndLastXTimes(exercice.getId(), nbTime);
        if (exerciceSessions.isEmpty()) {
            System.out.println("Aucune session d'exercice trouvée pour cette période.");
        } else {
            for (ExerciceSession session : exerciceSessions) {
                LocalDate dateSession = session.getStart().toLocalDate();
                Map<StatisticMetric, String> allStatisticsMap = setStatisticsMap(session);
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
    public Map<LocalDate, Map<StatisticMetric, String>> retournerStatistiques(Exercice exercice, LocalDate dateDebut, LocalDate dateFin) {
        if(dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("Date de début et de fin ne peuvent pas être nulles");
        }
        if(dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("La date de fin ne peut pas être avant la date de début.");
        }
        if(dateDebut.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de début ne peut pas être dans le futur.");
        }

        Map<LocalDate, Map<StatisticMetric, String>> statistiques = new HashMap<>();
        List<ExerciceSession> exerciceSessions = exerciceSessionService.getExercicesSessionsByDates(exercice.getId(), dateDebut, dateFin);
        if (exerciceSessions.isEmpty()) {
            System.out.println("Aucune session d'exercice trouvée pour cette période.");
        } else {
            for (ExerciceSession session : exerciceSessions) {
                LocalDate dateSession = session.getStart().toLocalDate();
                Map<StatisticMetric, String> allStatisticsMap = setStatisticsMap(session);
                statistiques.put(dateSession, allStatisticsMap);
            }
        }

        return statistiques;
    }

    /**
     * Retourne les statistiques d'un exercice pour une période donnée sous forme de Map dans le but d'afficher des graphes.
     *
     * @param objectToHaveStats L'objet pour lequel on veut obtenir des statistiques.
     * @param startDate La date de début de la période
     * @param endDate La date de fin de la période
     * @param nbTime Le nombre de fois que l'exercice a été effectué trié par date décroissante
     * @return une carte contenant les dates et les statistiques d'un exercice
     */
    @Override
    public Object retournerStatistiques(Object objectToHaveStats, LocalDate startDate, LocalDate endDate, int nbTime) {
        if(objectToHaveStats instanceof Exercice) {
            Exercice exercice = (Exercice) objectToHaveStats;
            if (nbTime > 0) {
                return retournerStatistiques(exercice, nbTime);
            } else {
                return retournerStatistiques(exercice, startDate, endDate);
            }
        } else {
            throw new IllegalArgumentException("L'objet doit être de type Exercice.");
        }
    }
}
