package ca.usherbrooke.trainingapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représente une session d'entrainement.
 */
@Entity
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;
    private LocalDateTime start;
    private LocalDateTime end;
    @ElementCollection
    @MapKeyColumn(name = "type_de_stat")
    @Column(name = "valeur")
    private Map<StatisticType, String> statisticsMap;

    /**
     * Constructeur par défaut.
     */
    public TrainingSession() {
        super();
        this.training = null;
        this.start = null;
        this.end = null;
        this.statisticsMap = new HashMap<>();
    }

    /**
     * Retourne l'identifiant de la session d'entraînement.
     *
     * @return l'identifiant de la session
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le training associé à cette session.
     *
     * @return le training de la session
     */
    public Training getTraining() {
        return training;
    }

    /**
     * Définit le training associé à cette session.
     *
     * @param training le training à associer
     */
    public void setTraining(Training training) {
        this.training = training;
    }

    /**
     * Retourne la date et l'heure de début de la session.
     *
     * @return la date et l'heure de début
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Définit la date et l'heure de début de la session.
     *
     * @param start la date et l'heure de début
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Retourne la date et l'heure de fin de la session.
     *
     * @return la date et l'heure de fin
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Définit la date et l'heure de fin de la session.
     *
     * @param end la date et l'heure de fin
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Retourne la carte des statistiques de la session.
     *
     * @return la carte des statistiques
     */
    public Map<StatisticType, String> getStatisticsMap() {
        return statisticsMap;
    }

    /**
     * Définit la carte des statistiques de la session.
     *
     * @param statisticsMap la carte des statistiques à définir
     */
    public void setStatisticsMap(Map<StatisticType, String> statisticsMap) {
        this.statisticsMap = statisticsMap;
    }

    /**
     * Ajoute une statistique à la session.
     *
     * @param type le type de statistique
     * @param value la valeur de la statistique
     */
    public void addStatistic(StatisticType type, String value) {
        this.statisticsMap.put(type, value);
    }

    /**
     * Supprime une statistique de la session.
     *
     * @param type le type de statistique à supprimer
     */
    public void removeStatistic(StatisticType type) {
        this.statisticsMap.remove(type);
    }
}
