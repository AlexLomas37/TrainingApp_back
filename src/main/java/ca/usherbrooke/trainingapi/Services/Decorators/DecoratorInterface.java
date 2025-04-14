package ca.usherbrooke.trainingapi.Services.Decorators;

import java.util.Map;

public interface DecoratorInterface {

    Object retournerObjetDecore(Object objectToDecorate, Map<String, Object> statsToAdd);

}
