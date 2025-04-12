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
     * Retourne une carte contenant les statistiques par date pour un ensemble de sessions d'exercice.
     * @param sessions La liste des sessions d'exercice
     * @return une carte contenant les dates et les statistiques d'un exercice
     */
    private Map<LocalDate, Map<StatisticMetric, String>> setStatisticsMap(List<ExerciceSession> sessions) {
        Map<LocalDate, Map<StatisticMetric, String>> statisticsMap = new HashMap<>();
        // Parcours de chaque sesion d'exercice
        for (ExerciceSession session : sessions) {
            LocalDate dateSession = session.getStart().toLocalDate();

            Map<StatisticMetric, String> statsMapByExercice = new HashMap<>();
            // Parcours de chaque statistique de la session d'exercice
            for (Map.Entry<StatisticMetric, String> entry : session.getStatisticsMap().entrySet()) {
                StatisticMetric type = entry.getKey();
                String value = entry.getValue();
                // Inversion de l'ordre des valeurs lors du merge
                statsMapByExercice.merge(type, value, (oldValue, newValue) -> newValue + " ; " + oldValue);
            }
            // Ajout de la date et des statistiques à la carte
            statisticsMap.merge(dateSession, statsMapByExercice, (existingStats, newStats) -> {
                newStats.forEach((key, value) ->
                        // Inversion de l'ordre des valeurs lors du merge
                        existingStats.merge(key, value, (oldValue, newValue) -> newValue + " ; " + oldValue)
                );
                return existingStats;
            });
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

        List<ExerciceSession> exerciceSessions = exerciceSessionService.getExercicesSessionsByIdExoAndLastXTimes(exercice.getId(), nbTime);
        return setStatisticsMap(exerciceSessions);
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
        return setStatisticsMap(exerciceSessions);
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
        if(objectToHaveStats instanceof Exercice exercice) {
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
