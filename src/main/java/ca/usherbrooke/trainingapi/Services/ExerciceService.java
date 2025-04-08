package ca.usherbrooke.trainingapi.Services;

import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.repository.ExerciceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciceService {

    @Autowired
    private ExerciceRepository exerciceRepository;
    @Autowired
    private TrainingService trainingService;

    /**
     * Récupère la liste de tous les exercices.
     *
     * @return la liste des exercices
     */
    public Iterable<Exercice> getAllExercices() {
        return exerciceRepository.findAll();
    }

    /**
     * Récupère un exercice par son identifiant.
     *
     * @param id l'identifiant de l'exercice
     * @return l'objet Exercice correspondant
     */
    public Exercice getExerciceById(int id) {
        return exerciceRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aucun exercice trouvé avec l'id : " + id));
    }

    /**
     * Sauvegarde un exercice dans la base de données.
     *
     * @param exercice l'exercice à sauvegarder
     * @return l'exercice sauvegardé
     */
    public Exercice saveExercice(Exercice exercice) {
        Training training = trainingService.getTrainingById(exercice.getTraining().getId());
        exercice.setTraining(training);
        return exerciceRepository.save(exercice);
    }

    /**
     * Met à jour un exercice existant.
     *
     * @param id l'identifiant de l'exercice à mettre à jour
     * @param exercice les nouvelles données de l'exercice
     * @return l'exercice mis à jour
     */
    public Exercice updateExercice(int id, Exercice exercice) {
        Exercice existingExercice = getExerciceById(id);
        existingExercice.setName(exercice.getName());
        existingExercice.setDescription(exercice.getDescription());
        existingExercice.setTime(exercice.getTime());
        existingExercice.setRepetitions(exercice.getRepetitions());
        return exerciceRepository.save(existingExercice);
    }

    /**
     * Met à jour partiellement un exercice existant.
     *
     * @param id l'identifiant de l'exercice à mettre à jour
     * @param exercice les nouvelles données de l'exercice
     * @return l'exercice mis à jour
     */
    public Exercice updateExerciceByPatch(int id, Exercice exercice) {
        Exercice existingExercice = getExerciceById(id);
        if (exercice.getName() != null) {
            existingExercice.setName(exercice.getName());
        }
        if (exercice.getDescription() != null) {
            existingExercice.setDescription(exercice.getDescription());
        }
        if (exercice.getTime() >= 0) {
            existingExercice.setTime(exercice.getTime());
        }
        if (exercice.getRepetitions() > 0) {
            existingExercice.setRepetitions(exercice.getRepetitions());
        }
        if (exercice.getTraining() != null) {
            existingExercice.setTraining(exercice.getTraining());
        }
        return exerciceRepository.save(existingExercice);
    }

    /**
     * Supprime un exercice par son identifiant.
     *
     * @param id l'identifiant de l'exercice à supprimer
     */
    public void deleteExercice(int id) {
        exerciceRepository.deleteById(id);
    }
}
