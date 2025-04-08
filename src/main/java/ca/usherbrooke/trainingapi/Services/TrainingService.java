package ca.usherbrooke.trainingapi.Services;

import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

    @Autowired
    private DisciplineService disciplineService;
    @Autowired
    private TrainingRepository trainingRepository;

    /**
     * Récupère la liste de tous les entraînements.
     *
     * @return la liste des entraînements
     */
    public List<Training> getTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * Récupère un entraînement par son identifiant.
     *
     * @param id l'identifiant de l'entraînement
     * @return l'objet Training correspondant
     */
    public Training getTrainingById(int id) {
        return trainingRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aucun entraînement trouvé avec l'id : " + id));
    }

    /**
     * Récupère la liste des exercices associés à un entraînement.
     *
     * @param id l'identifiant de l'entraînement
     * @return la liste des exercices associés à l'entraînement
     */
    public List<Exercice> getExercicesByTrainingId(int id) {
        return getTrainingById(id).getExercices();
    }

    /**
     * Sauvegarde un entraînement dans la base de données.
     *
     * @param training l'entraînement à sauvegarder
     * @return l'entraînement sauvegardé
     */
    public Training saveTraining(Training training) {
        return trainingRepository.save(training);
    }

    /**
     * Met à jour un entraînement existant.
     *
     * @param id l'identifiant de l'entraînement à mettre à jour
     * @param training les nouvelles données de l'entraînement
     * @return l'entraînement mis à jour
     */
    public Training updateTraining(int id, Training training) {
        Training existingTraining = getTrainingById(id);
        existingTraining.setName(training.getName());
        existingTraining.setDescription(training.getDescription());
        existingTraining.setDiscipline(disciplineService.getDisciplineById(training.getDiscipline().getId()));
        return trainingRepository.save(existingTraining);
    }

    /**
     * Met à jour partiellement un entraînement existant via la méthode patch.
     *
     * @param id l'identifiant de l'entraînement à mettre à jour
     * @param training les nouvelles données de l'entraînement
     * @return l'entraînement mis à jour
     */
    public Training patchTraining(int id, Training training) {
        Training existingTraining = getTrainingById(id);
        if (training.getName() != null) {
            existingTraining.setName(training.getName());
        }
        if (training.getDescription() != null) {
            existingTraining.setDescription(training.getDescription());
        }
        if (training.getDiscipline() != null) {
            existingTraining.setDiscipline(disciplineService.getDisciplineById(training.getDiscipline().getId()));
        }
        return trainingRepository.save(existingTraining);
        }

    /**
     * Supprime un entraînement par son identifiant.
     *
     * @param id l'identifiant de l'entraînement à supprimer
     */
    public void deleteTrainingById(int id) {
        Training training = getTrainingById(id);
        trainingRepository.deleteById(id);
    }
}
