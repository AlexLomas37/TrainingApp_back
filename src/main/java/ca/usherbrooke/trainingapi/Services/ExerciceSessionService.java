package ca.usherbrooke.trainingapi.Services;

import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.ExerciceSession;
import ca.usherbrooke.trainingapi.model.TrainingSession;
import ca.usherbrooke.trainingapi.repository.ExerciceSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciceSessionService {

    @Autowired
    private ExerciceSessionRepository exerciceSessionRepository;
    @Autowired
    private TrainingSessionService trainingSessionService;
    @Autowired
    private ExerciceService exerciceService;

    /**
     * Récupère la liste de toutes les sessions d'exercice.
     *
     * @return la liste des sessions d'exercice
     */
    public Iterable<ExerciceSession> getExerciceSessions() {
        return exerciceSessionRepository.findAll();
    }

    /**
     * Récupère une session d'exercice par son identifiant.
     *
     * @param id l'identifiant de la session d'exercice
     * @return la session d'exercice correspondante ou null
     */
    public ExerciceSession getExerciceSessionById(int id) {
        return exerciceSessionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Session d'exercice non trouvée pour l'id: " + id));
    }

    /**
     * Récupère la liste des sessions d'exercice associées à un exercice.
     *
     * @param idExo l'identifiant de l'exercice
     * @return les sessions d'exercice associées à l'exercice
     */
    public List<ExerciceSession> getExercicesSessionsByExoId(int idExo) {
        return (List<ExerciceSession>) exerciceSessionRepository.findByExerciceId(idExo);
    }

    /**
     * Sauvegarde une nouvelle session d'exercice.
     *
     * @param exerciceSession la session d'exercice à sauvegarder
     * @return la session d'exercice sauvegardée
     */
    public ExerciceSession saveExerciceSession(ExerciceSession exerciceSession) {
        Exercice exercice = exerciceService
                .getExerciceById(exerciceSession.getExercice().getId());
        exerciceSession.setExercice(exercice);
        TrainingSession trainingSession = trainingSessionService
                .getTrainingSessionById(exerciceSession.getTrainingSession().getId());
        exerciceSession.setTrainingSession(trainingSession);
        return exerciceSessionRepository.save(exerciceSession);
    }

    /**
     * Supprime une session d'exercice par son identifiant.
     *
     * @param id l'identifiant de la session d'exercice à supprimer
     */
    public void deleteExerciceSession(int id) {
        exerciceSessionRepository.deleteById(id);
    }
}
