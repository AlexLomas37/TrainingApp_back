package ca.usherbrooke.trainingapi.Services.Factories;

import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsExerciceAVGStrategy;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsExerciceCurveStrategy;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsStrategyInterface;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.StatisticType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsExerciceFactoryImpl implements StatisticsFactoryInterface {

    private final List<StatisticsStrategyInterface> statisticsStrategyInterfaces;

    /**
     * Constructeur de la classe StatisticsExerciceFactoryImpl.
     *
     * @param statisticsStrategyInterfaces Liste des stratégies de statistiques.
     */
    @Autowired
    public StatisticsExerciceFactoryImpl(List<StatisticsStrategyInterface> statisticsStrategyInterfaces) {
        this.statisticsStrategyInterfaces = statisticsStrategyInterfaces;
    }

    /**
     * Récupère la stratégie de statistiques appropriée en fonction de l'objet et du type de statistique.
     *
     * @param objectToHaveStats L'objet pour lequel on veut obtenir des statistiques.
     * @param type Le type de statistique souhaité.
     * @return La stratégie de statistiques appropriée.
     */
    @Override
    public StatisticsStrategyInterface getStatisticsStrategy(Object objectToHaveStats, StatisticType type) {
        if(objectToHaveStats instanceof Exercice) {
            switch(type) {
                case CURVE -> {
                    return getStrategy(StatisticsExerciceCurveStrategy.class);
                }
                case AVERAGE -> {
                    return getStrategy(StatisticsExerciceAVGStrategy.class);
                }
                default ->
                    throw new IllegalArgumentException("Type de statistique non valide");
            }
        } else {
            throw new IllegalArgumentException("L'objet doit être de type Exercice.");
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
                .orElseThrow(() -> new IllegalArgumentException("Aucune factory trouvée pour le type de statistiques : " + type));
    }
}
