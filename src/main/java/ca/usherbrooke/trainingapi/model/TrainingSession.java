package ca.usherbrooke.trainingapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @OneToMany(mappedBy = "trainingSession")
    private List<ExerciceSession> exercicesSessions;
}
