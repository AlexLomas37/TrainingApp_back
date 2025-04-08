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

    /**
     * Constructeur par défaut.
     */
    public TrainingSession() {
        super();
        this.training = null;
        this.start = null;
        this.end = null;
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param training le training associé à cette session
     * @param start la date et l'heure de début de la session
     * @param end la date et l'heure de fin de la session
     */
    public TrainingSession(Training training, LocalDateTime start, LocalDateTime end) {
        this.training = training;
        this.start = start;
        this.end = end;
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
}
