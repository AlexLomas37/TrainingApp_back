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
public class StatisticsExerciceAVGStrategy implements StatisticsStrategyInterface {

    @Autowired
    private ExerciceSessionService exerciceSessionService;

    /**
     * Calculer la moyenne des statistiques d'un exercice à partir d'une liste de sessions.
     *
     * @param sessions La liste des sessions d'exercice
     * @return une carte contenant les statistiques moyennes d'un exercice
     */
    private Map<StatisticMetric, Float> calculerMoyenne(List<ExerciceSession> sessions) {
        Map<StatisticMetric, Float> statisticsMap = new HashMap<>();
        Map<StatisticMetric, Integer> statsMapCompteur = new HashMap<>();

        for (ExerciceSession session : sessions) {
            for (Map.Entry<StatisticMetric, String> entry : session.getStatisticsMap().entrySet()) {
                StatisticMetric type = entry.getKey();
                String value = entry.getValue();
                Float floatValue = Float.parseFloat(value);
                statisticsMap.merge(type, floatValue, Float::sum);
                statsMapCompteur.merge(type, 1, Integer::sum);
            }
        }

        for (Map.Entry<StatisticMetric, Float> entry : statisticsMap.entrySet()) {
            StatisticMetric type = entry.getKey();
            Float totalValue = entry.getValue();
            Integer count = statsMapCompteur.get(type);
            Float averageValue = totalValue / count;
            statisticsMap.put(type, averageValue);
        }
        return statisticsMap;
    }

    /**
     * Retourne les statistiques d'un exercice pour une période donnée sous forme de Map dans le but d'afficher des graphes.
     *
     * @param exercice L'exercice pour lequel on veut obtenir des statistiques.
     * @param nbTime Le nombre de fois que l'exercice a été effectué trié par date décroissante
     * @return une carte contenant les dates et les statistiques d'un exercice
     */
    private Map<StatisticMetric, Float> retournerStatistiques(Exercice exercice, int nbTime) {
        if (nbTime <= 0) {
            throw new IllegalArgumentException("Le nombre de temps doit être positif.");
        }
        // Récupérer les sessions d'exercice pour l'exercice donné
        List<ExerciceSession> sessions = exerciceSessionService.getExercicesSessionsByIdExoAndLastXTimes(exercice.getId(), nbTime);
        return calculerMoyenne(sessions);
    }

    /**
     * Retourne les statistiques d'un exercice pour une période donnée sous forme de Map dans le but d'afficher des graphes.
     *
     * @param exercice L'exercice pour lequel on veut obtenir des statistiques.
     * @param dateDebut La date de début de la période
     * @param dateFin La date de fin de la période
     * @return une carte contenant les dates et les statistiques d'un exercice
     */
    private Map<StatisticMetric, Float> retournerStatistiques(Exercice exercice, LocalDate dateDebut, LocalDate dateFin) {
        // Récupérer les sessions d'exercice pour l'exercice donné
        List<ExerciceSession> sessions = exerciceSessionService.getExercicesSessionsByDates(exercice.getId(), dateDebut, dateFin);
        return calculerMoyenne(sessions);
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
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La date de fin ne peut pas être avant la date de début.");
        }
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
