package ca.usherbrooke.trainingapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Représente une session d'exercice comprenant les statistiques associées.
 */
@Entity
public class ExerciceSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "exercice_id")
    private Exercice exercice;
    private LocalDateTime start;
    private LocalDateTime end;
    @OneToMany(mappedBy = "exerciceSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciceStatistic> statistics;

    @ManyToOne
    @JoinColumn(name = "training_session_id")
    private TrainingSession trainingSession;

    /**
     * Constructeur par défaut.
     */
    public ExerciceSession() {
        super();
        this.exercice = null;
        this.start = null;
        this.end = null;
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param exercice l'exercice associé à cette session
     * @param start la date et l'heure de début de la session
     * @param end la date et l'heure de fin de la session
     * @param statistics la liste des statistiques associées à cette session
     */
    public ExerciceSession(Exercice exercice, LocalDateTime start, LocalDateTime end, List<ExerciceStatistic> statistics) {
        this.exercice = exercice;
        this.start = start;
        this.end = end;
        this.statistics = statistics;
    }

    /**
     * Retourne l'identifiant de la session.
     *
     * @return l'identifiant de la session
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne l'exercice associé à cette session.
     *
     * @return l'exercice associé
     */
    public Exercice getExercice() {
        return exercice;
    }

    /**
     * Définit l'exercice associé à cette session.
     *
     * @param exercice l'exercice à associer
     */
    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
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
     * Retourne la liste des statistiques associées à cette session.
     *
     * @return la liste des statistiques
     */
    public List<ExerciceStatistic> getStatistics() {
        return statistics;
    }

    /**
     * Définit la liste des statistiques associées à cette session.
     *
     * @param statistics la liste des statistiques
     */
    public void setStatistics(List<ExerciceStatistic> statistics) {
        this.statistics = statistics;
    }

    /**
     * Ajoute une statistique à la session d'exercice.
     *
     * @param statistic la statistique à ajouter
     */
    public void addStatistic(ExerciceStatistic statistic) {
        this.statistics.add(statistic);
        statistic.setExerciceSession(this);
    }

    /**
     * Supprime une statistique de la session d'exercice.
     *
     * @param statistic la statistique à supprimer
     */
    public void removeStatistic(ExerciceStatistic statistic) {
        this.statistics.remove(statistic);
        statistic.setExerciceSession(null);
    }

    /**
     * Retourne la session d'entraînement associée à cette session d'exercice.
     *
     * @return la session d'entraînement associée
     */
    public TrainingSession getTrainingSession() {
        return trainingSession;
    }

    /**
     * Définit la session d'entraînement associée à cette session d'exercice.
     *
     * @param trainingSession la session d'entraînement à associer
     */
    public void setTrainingSession(TrainingSession trainingSession) {
        this.trainingSession = trainingSession;
    }

    @Override
    public String toString() {
        return "ExerciceSession{" +
                "id=" + id +
                ", exercice=" + exercice +
                ", start=" + start +
                ", end=" + end +
                ", statistics=" + statistics +
                ", trainingSession=" + trainingSession +
                '}';
    }
}

