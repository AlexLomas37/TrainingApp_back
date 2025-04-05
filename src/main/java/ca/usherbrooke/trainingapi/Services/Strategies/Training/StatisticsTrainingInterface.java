package ca.usherbrooke.trainingapi.Services.Strategies.Training;

import ca.usherbrooke.trainingapi.model.Training;

import java.time.LocalDate;

public interface StatisticsTrainingInterface {

    Object retournerStatistiques(Training training, LocalDate dateDebut, LocalDate dateFin);
}
