package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.Services.TrainingSessionService;
import ca.usherbrooke.trainingapi.model.TrainingSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrainingSessionController {

    @Autowired
    private TrainingSessionService trainingSessionService;

    /**
     * Récupère la liste de toutes les sessions d'entraînement.
     *
     * @return la liste des sessions d'entraînement
     */
    @GetMapping("/training-sessions")
    public Iterable<TrainingSession> getTrainingSessions() {
        return trainingSessionService.getTrainingSessions();
    }

    /**
     * Récupère une session d'entraînement par son identifiant.
     *
     * @param id l'identifiant de la session d'entraînement
     * @return la session d'entraînement correspondante ou null
     */
    @GetMapping("/training-sessions/{id}")
    public TrainingSession getTrainingSessionById(@PathVariable int id) {
        return trainingSessionService.getTrainingSessionById(id);
    }

    /**
     * Récupère la liste des sessions d'entraînement associées à un entraînement.
     *
     * @param idTraining l'identifiant de l'entraînement
     * @return les sessions d'entraînement associées à l'entraînement
     */
    @GetMapping("/training-sessions/training/{idTraining}")
    public Iterable<TrainingSession> getTrainingSessionsByTrainingId(@PathVariable int idTraining) {
        return trainingSessionService.getTrainingSessionsByTrainingId(idTraining);
    }

    /**
     * Crée une nouvelle session d'entraînement.
     *
     * @param trainingSession la session d'entraînement à créer
     * @return la session d'entraînement créée
     */
    @PostMapping("/training-sessions")
    public TrainingSession createTrainingSession(@RequestBody TrainingSession trainingSession) {
        return trainingSessionService.saveTrainingSession(trainingSession);
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
        return trainingSessionService.updateTrainingSession(trainingSession);
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
        return trainingSessionService.updateTrainingSessionByPatch(id, trainingSession);
    }

    /**
     * Supprime une session d'entraînement par son identifiant.
     *
     * @param id l'identifiant de la session d'entraînement à supprimer
     */
    @DeleteMapping("/training-sessions/{id}")
    public void deleteTrainingSession(@PathVariable int id) {
        trainingSessionService.deleteTrainingSession(id);
    }
}
