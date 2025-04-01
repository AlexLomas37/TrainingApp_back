package ca.usherbrooke.trainingapi.model;

import jakarta.persistence.*;

/**
 * Représente une statistique associée à une session d'exercice.
 */
@Entity
public class ExerciceStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private StatisticType keyStat;
    private String value;

    @ManyToOne
    @JoinColumn(name = "exercice_session_id")
    private ExerciceSession exerciceSession;

    public ExerciceStatistic() {
        super();
        this.exerciceSession = null;
    }

    public int getId() {
        return id;
    }

    public StatisticType getKeyStat() {
        return keyStat;
    }

    public void setKeyStat(StatisticType keyStat) {
        this.keyStat = keyStat;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ExerciceSession getExerciceSession() {
        return exerciceSession;
    }

    public void setExerciceSession(ExerciceSession exerciceSession) {
        this.exerciceSession = exerciceSession;
    }

    /**
     * Retourne la statistique sous forme de chaîne.
     *
     * @return la représentation textuelle de la statistique
     */
    @Override
    public String toString() {
        return "ExerciceStatistic{" +
                "id=" + id +
                ", keyStat=" + keyStat +
                ", value='" + value + '\'' +
                ", exerciceSession=" + exerciceSession +
                '}';
    }
}
