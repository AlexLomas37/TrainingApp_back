package ca.usherbrooke.trainingapi.Services.Strategies;

import java.time.LocalDate;

public interface StatisticsStrategyInterface {

    Object retournerStatistiques(Object objectToHaveStats, LocalDate startDate, LocalDate endDate, int nbTime);
}
