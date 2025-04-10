package ca.usherbrooke.trainingapi.Services.Factories;

import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsStrategyInterface;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsTrainingMatrixStrategy;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsTrainingTotalStrategy;
import ca.usherbrooke.trainingapi.model.StatisticType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsTrainingFactoryImpl implements StatisticsFactoryInterface {

    private final List<StatisticsStrategyInterface> statisticsStrategyInterfaces;

    /**
     * Constructeur de la classe StatisticsTrainingFactoryImpl.
     *
     * @param statisticsStrategyInterfaces Liste des stratégies de statistiques.
     */
    @Autowired
    private StatisticsTrainingFactoryImpl(List<StatisticsStrategyInterface> statisticsStrategyInterfaces) {
        this.statisticsStrategyInterfaces = statisticsStrategyInterfaces;
    }

    /**
     * Récupère la stratégie de statistiques appropriée en fonction de l'objet et du type de statistique.
     *
     * @param type Le type de statistique souhaité.
     * @return La stratégie de statistiques appropriée.
     */
    @Override
    public StatisticsStrategyInterface getStatisticsStrategy(StatisticType type) {
        switch (type) {
            case HEATMAP -> {
                return getStrategy(StatisticsTrainingMatrixStrategy.class);
            }
            case TOTAL -> {
                return getStrategy(StatisticsTrainingTotalStrategy.class);
            }
            default ->
                throw new IllegalArgumentException("Type de statistique non valide");
        }
    }

    /**
     * Récupère la stratégie de statistiques appropriée en fonction du type.
     *
     * @param type Le type de la stratégie de statistiques.
     * @return La stratégie de statistiques appropriée.
     */
    private StatisticsStrategyInterface getStrategy(Class type) {
        return (StatisticsStrategyInterface) statisticsStrategyInterfaces.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aucune stratégie trouvée pour le type de statistiques : " + type));
    }
}
