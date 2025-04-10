package ca.usherbrooke.trainingapi.Services.Factories;

import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsStrategyInterface;
import ca.usherbrooke.trainingapi.model.StatisticType;

public interface StatisticsFactoryInterface {

    StatisticsStrategyInterface getStatisticsStrategy(Object objectToHaveStats, StatisticType type);
}
