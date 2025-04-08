package ca.usherbrooke.trainingapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Modèle représentant un exercice.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Exercice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int time;
    private int repetitions;
    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @ElementCollection
    @MapKeyColumn(name = "type_de_stat")
    @Column(name = "valeur")
    private Map<StatisticMetric, String> statisticsMap;

    public Exercice() {
        super();
        this.training = null;
        this.statisticsMap = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Map<StatisticMetric, String> getStatisticsMap() {
        return statisticsMap;
    }

    public void setStatisticsMap(Map<StatisticMetric, String> statisticsMap) {
        this.statisticsMap = statisticsMap;
    }

    public void addStatistic(StatisticMetric type, String value) {
        this.statisticsMap.put(type, value);
    }

    public void removeStatistic(StatisticMetric key) {
        this.statisticsMap.remove(key);
    }
}
