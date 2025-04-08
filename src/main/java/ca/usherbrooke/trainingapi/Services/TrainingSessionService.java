package ca.usherbrooke.trainingapi.Services;

import ca.usherbrooke.trainingapi.model.TrainingSession;
import ca.usherbrooke.trainingapi.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingSessionService {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;
    @Autowired
    private TrainingService trainingService;

    /**
     * Récupère la liste de toutes les sessions d'entraînement.
     *
     * @return la liste des sessions d'entraînement
     */
    public Iterable<TrainingSession> getTrainingSessions() {
        return trainingSessionRepository.findAll();
    }

    /**
     * Récupère une session d'entraînement par son identifiant.
     *
     * @param id l'identifiant de la session d'entraînement
     * @return la session d'entraînement correspondante ou null
     */
    public TrainingSession getTrainingSessionById(int id) {
        return trainingSessionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Session d'entraînement non trouvée pour l'id: " + id));
    }

    /**
     * Récupère la liste des sessions d'entraînement associées à un entraînement.
     *
     * @param idTraining l'identifiant de l'entraînement
     * @return les sessions d'entraînement associées à l'entraînement
     */
    public Iterable<TrainingSession> getTrainingSessionsByTrainingId(int idTraining) {
        return trainingSessionRepository
                .findByTrainingId(idTraining);
    }

    /**
     * Crée une nouvelle session d'entraînement.
     *
     * @param trainingSession la session d'entraînement à créer
     * @return la session d'entraînement créée
     */
    public TrainingSession saveTrainingSession(TrainingSession trainingSession) {
        return trainingSessionRepository.save(trainingSession);
    }

    /**
     * Met à jour une session d'entraînement existante.
     *
     * @param trainingSession la session d'entraînement à mettre à jour
     * @return la session d'entraînement mise à jour
     */
    public TrainingSession updateTrainingSession(TrainingSession trainingSession) {
        TrainingSession existingSession = getTrainingSessionById(trainingSession.getId());
        existingSession.setStart(trainingSession.getStart());
        existingSession.setEnd(trainingSession.getEnd());
        return saveTrainingSession(existingSession);
    }

    /**
     * Met à jour une session d'entraînement existante.
     *
     * @param id l'identifiant de la session d'entraînement à mettre à jour
     * @param trainingSession les nouvelles données de la session d'entraînement
     * @return la session d'entraînement mise à jour
     */
    public TrainingSession updateTrainingSessionByPatch(int id, TrainingSession trainingSession) {
        TrainingSession existingSession = getTrainingSessionById(id);
        if (trainingSession.getStart() != null) {
            existingSession.setStart(trainingSession.getStart());
        }
        if (trainingSession.getEnd() != null) {
            existingSession.setEnd(trainingSession.getEnd());
        }
        return saveTrainingSession(existingSession);
    }

    /**
     * Supprime une session d'entraînement par son identifiant.
     *
     * @param id l'identifiant de la session d'entraînement à supprimer
     */
    public void deleteTrainingSession(int id) {
        trainingSessionRepository.deleteById(id);
    }
}
