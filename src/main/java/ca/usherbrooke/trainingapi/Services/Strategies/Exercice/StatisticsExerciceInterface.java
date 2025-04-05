package ca.usherbrooke.trainingapi.Services.Strategies.Exercice;

import ca.usherbrooke.trainingapi.model.Exercice;

import java.time.LocalDate;

public interface StatisticsExerciceInterface {

    Object retournerStatistiques(Exercice exercice, int nbTime);

    Object retournerStatistiques(Exercice exercice, LocalDate dateDebut, LocalDate dateFin);
}
