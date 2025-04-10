package ca.usherbrooke.trainingapi.components;

import ca.usherbrooke.trainingapi.Services.Factories.StatisticsExerciceFactoryImpl;
import ca.usherbrooke.trainingapi.Services.Factories.StatisticsFactoryInterface;
import ca.usherbrooke.trainingapi.Services.Factories.StatisticsTrainingFactoryImpl;
import ca.usherbrooke.trainingapi.Services.Strategies.StatisticsStrategyInterface;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.StatisticType;
import ca.usherbrooke.trainingapi.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatisticsFactoryProvider implements StatisticsFactoryInterface {

    private final List<StatisticsFactoryInterface> statisticsFactories;

    /**
     * Constructeur de la classe StatisticsFactoryProvider.
     * @param statisticsFactories Liste des factories de statistiques.
     */
    @Autowired
    public StatisticsFactoryProvider(List<StatisticsFactoryInterface> statisticsFactories) {
        this.statisticsFactories = statisticsFactories;
    }

    /**
     * Cette méthode ne doit pas être utilisée directement, utilisez plutôt getStatisticsStrategyByObject.
     * @return Une exeption indiquant que cette méthode ne doit pas être utilisée.
     */
    @Override
    public StatisticsStrategyInterface getStatisticsStrategy(StatisticType type) {
        throw new UnsupportedOperationException("Cette méthode ne doit pas être utilisée directement, utilisez plutôt getStatisticsStrategy avec l'objet Training/Exercice à étudier en paramètre.");
    }

    /**
     * Récupère la stratégie de statistiques appropriée en fonction de l'objet et du type de statistique.
     *
     * @param objectToHaveStats L'objet pour lequel on veut obtenir des statistiques.
     * @param type Le type de statistique souhaité.
     * @return La stratégie de statistiques appropriée.
     */
    public StatisticsStrategyInterface getStatisticsStrategy(Object objectToHaveStats, StatisticType type) {
        if(objectToHaveStats instanceof Training) {
            return getFactory(StatisticsTrainingFactoryImpl.class).getStatisticsStrategy(type);
        } else if(objectToHaveStats instanceof Exercice) {
            return getFactory(StatisticsExerciceFactoryImpl.class).getStatisticsStrategy(type);
        } else {
            throw new IllegalArgumentException("L'objet doit être de type Training ou Exercice.");
        }
    }

    /**
     * Récupère la factory de statistiques appropriée en fonction du type.
     *
     * @param type Le type de la factory de statistiques.
     * @return La factory de statistiques appropriée.
     */
    private StatisticsFactoryInterface getFactory(Class type) {
        return (StatisticsFactoryInterface) statisticsFactories.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aucune factory trouvée pour le type de statistiques : " + type));
    }


}
