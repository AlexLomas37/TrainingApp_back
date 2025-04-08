package ca.usherbrooke.trainingapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Modèle représentant un entraînement.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int time;
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<Exercice> exercices;

    public Training() {
        super();
        this.discipline = null;
        this.exercices = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Exercice> getExercices() {
        return exercices;
    }

    public void setExercices(List<Exercice> exercices) {
        this.exercices = exercices;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Ajoute un exercice à l'entraînement.
     *
     * @param exercice l'exercice à ajouter
     */
    public void addExercice(Exercice exercice) {
        this.exercices.add(exercice);
    }

    /**
     * Supprime un exercice de l'entraînement.
     *
     * @param exercice l'exercice à supprimer
     */
    public void removeExercice(Exercice exercice) {
        this.exercices.remove(exercice);
    }
}
