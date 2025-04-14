package ca.usherbrooke.trainingapi.Services.Decorators;

import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.Training;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ObjectWithStatsDecoratorImpl implements DecoratorInterface {

    /**
     * Cette méthode permet de décorer un objet.
     * @param objectToDecorate L'objet à décorer.
     * @return L'objet décoré.
     */
    @Override
    public Object retournerObjetDecore(Object objectToDecorate, Map<String, Object> statsToAdd) {
        Map<String, Object> result = new HashMap<>();
        if(objectToDecorate == null) {
            throw new IllegalArgumentException("L'objet à décorer ne peut pas être null.");
        }
        if(statsToAdd == null) {
            throw new IllegalArgumentException("Les statistiques à ajouter ne peuvent pas être null.");
        }
        if(objectToDecorate instanceof Exercice) {
            Exercice exercice = (Exercice) objectToDecorate;
            result.put("exercice", exercice);
        }
        if(objectToDecorate instanceof Training) {
            Training training = (Training) objectToDecorate;
            result.put("training", training);
        }
        result.putAll(statsToAdd);

        return result;
    }
}
