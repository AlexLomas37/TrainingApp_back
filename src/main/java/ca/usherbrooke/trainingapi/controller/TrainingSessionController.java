package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.model.TrainingSession;
import ca.usherbrooke.trainingapi.repository.TrainingRepository;
import ca.usherbrooke.trainingapi.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrainingSessionController {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;
    @Autowired
    private TrainingRepository trainingRepository;

    /**
     * Récupère la liste de toutes les sessions d'entraînement.
     *
     * @return la liste des sessions d'entraînement
     */
    @GetMapping("/training-sessions")
    public Iterable<TrainingSession> getTrainingSessions() {
        return trainingSessionRepository.findAll();
    }

    /**
     * Récupère une session d'entraînement par son identifiant.
     *
     * @param id l'identifiant de la session d'entraînement
     * @return la session d'entraînement correspondante ou null
     */
    @GetMapping("/training-sessions/{id}")
    public TrainingSession getTrainingSessionById(@PathVariable int id) {
        return trainingSessionRepository.findById(id).orElse(null);
    }

    /**
     * Récupère la liste des sessions d'entraînement associées à un entraînement.
     *
     * @param idTraining l'identifiant de l'entraînement
     * @return les sessions d'entraînement associées à l'entraînement
     */
    @GetMapping("/training-sessions/training/{idTraining}")
    public Iterable<TrainingSession> getTrainingSessionsByTrainingId(@PathVariable int idTraining) {
        return trainingSessionRepository.findByTrainingId(idTraining);
    }

    /**
     * Crée une nouvelle session d'entraînement.
     *
     * @param trainingSession la session d'entraînement à créer
     * @return la session d'entraînement créée
     */
    @PostMapping("/training-sessions")
    public TrainingSession createTrainingSession(@RequestBody TrainingSession trainingSession) {
        Training training = trainingRepository
                .findById(trainingSession.getTraining().getId())
                .orElseThrow(() -> new RuntimeException("Entraînement non trouvé"));
        trainingSession.setTraining(training);
        return trainingSessionRepository.save(trainingSession);
    }

    /**
     * Met à jour une session d'entraînement existante.
     *
     * @param id l'identifiant de la session d'entraînement à mettre à jour
     * @param trainingSession les nouvelles données de la session d'entraînement
     * @return la session d'entraînement mise à jour
     */
    @PutMapping("/training-sessions/{id}")
    public TrainingSession updateTrainingSession(@PathVariable int id, @RequestBody TrainingSession trainingSession) {
        TrainingSession existingSession = trainingSessionRepository.findById(id).orElse(null);
        if (existingSession != null) {
            existingSession.setStart(trainingSession.getStart());
            existingSession.setEnd(trainingSession.getEnd());
            return trainingSessionRepository.save(existingSession);
        }
        return null;
    }

    /**
     * Met à jour partiellement une session d'entraînement existante.
     *
     * @param id l'identifiant de la session d'entraînement à mettre à jour
     * @param trainingSession les nouvelles données de la session d'entraînement
     * @return la session d'entraînement mise à jour
     */
    @PatchMapping("/training-sessions/{id}")
    public TrainingSession patchTrainingSession(@PathVariable int id, @RequestBody TrainingSession trainingSession) {
        TrainingSession existingSession = trainingSessionRepository.findById(id).orElse(null);
        if (existingSession != null) {
            if (trainingSession.getStart() != null) {
                existingSession.setStart(trainingSession.getStart());
            }
            if (trainingSession.getEnd() != null) {
                existingSession.setEnd(trainingSession.getEnd());
            }
            return trainingSessionRepository.save(existingSession);
        }
        return null;
    }

    /**
     * Supprime une session d'entraînement par son identifiant.
     *
     * @param id l'identifiant de la session d'entraînement à supprimer
     */
    @DeleteMapping("/training-sessions/{id}")
    public void deleteTrainingSession(@PathVariable int id) {
        trainingSessionRepository.deleteById(id);
    }
}
