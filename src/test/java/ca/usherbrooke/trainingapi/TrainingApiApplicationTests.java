package ca.usherbrooke.trainingapi;

import ca.usherbrooke.trainingapi.model.*;
import ca.usherbrooke.trainingapi.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TrainingApiApplicationTests {

    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private ExerciceRepository exerciceRepository;
    @Autowired
    private TrainingSessionRepository trainingSessionRepository;
    @Autowired
    private ExerciceSessionRepository exerciceSessionRepository;

    /**
     * Crée et enregistre une discipline de test.
     *
     * @return La discipline créée et enregistrée.
     */
    private Discipline createAndSaveDiscipline() {
        Discipline discipline = new Discipline();
        discipline.setName("Test");
        discipline.setDescription("Test description");
        return disciplineRepository.save(discipline);
    }

    /**
     * Crée et enregistre un entraînement de test.
     *
     * @param discipline La discipline associée à l'entraînement.
     * @return L'entraînement créé et enregistré.
     */
    private Training createAndSaveTraining(Discipline discipline) {
        Training training = new Training();
        training.setName("Test Entrainement");
        training.setDescription("Test description");
        training.setDiscipline(discipline);
        training.setTime(30);
        return trainingRepository.save(training);
    }

    /**
     * Crée et enregistre un exercice de test.
     *
     * @param training L'entraînement associé à l'exercice.
     * @return L'exercice créé et enregistré.
     */
    private Exercice createAndSaveExercice(Training training) {
        Exercice exercice = new Exercice();
        exercice.setName("Test Exercice");
        exercice.setDescription("Test description");
        exercice.setTime(10);
        exercice.setRepetitions(5);
        exercice.setTraining(training);
        exercice.addStatistic(StatisticMetric.SCORE, "points");
        exercice.addStatistic(StatisticMetric.TIME, "s");
        exercice.addStatistic(StatisticMetric.ACCURACY, "%");
        return exerciceRepository.save(exercice);
    }

    /**
     * Crée et enregistre une session d'entraînement de test.
     *
     * @param training L'entraînement associé à la session.
     * @return La session d'entraînement créée et enregistrée.
     */
    private TrainingSession createAndSaveTrainingSession(Training training) {
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setTraining(training);
        trainingSession.setStart(LocalDateTime.now());
        trainingSession.setEnd(LocalDateTime.now().plusMinutes(30));
        return trainingSessionRepository.save(trainingSession);
    }

    /**
     * Crée et enregistre une session d'exercice de test.
     *
     * @param exercice L'exercice associé à la session.
     * @param trainingSession La session d'entraînement associée.
     * @return La session d'exercice créée et enregistrée.
     */
    private ExerciceSession createAndSaveExerciceSession(Exercice exercice, TrainingSession trainingSession) {
        ExerciceSession exerciceSession = new ExerciceSession();
        exerciceSession.setExercice(exercice);
        exerciceSession.setTrainingSession(trainingSession);
        exerciceSession.setStart(LocalDateTime.now());
        exerciceSession.setEnd(LocalDateTime.now().plusMinutes(10));

        Map<StatisticMetric, String> stats = exerciceSession.getStatisticsMap();
        stats.put(StatisticMetric.SCORE, "100");
        stats.put(StatisticMetric.TIME, "10");
        stats.put(StatisticMetric.ACCURACY, "90");
        exerciceSession.setStatisticsMap(stats);
        return exerciceSessionRepository.save(exerciceSession);
    }

    /**
     * Tests pour vérifier que la discipline peut être créée et enregistrée correctement.
     */
    @Test
    public void testAddDiscipline() {
        Discipline discipline = createAndSaveDiscipline();
        assertNotNull(discipline.getId());
    }

    /**
     * Tests pour vérifier que le training peut être créé et enregistré correctement.
     */
    @Test
    public void testAddTraining() {
        Discipline discipline = createAndSaveDiscipline();
        Training training = createAndSaveTraining(discipline);
        assertNotNull(training.getId());
    }

    /**
     * Tests pour vérifier que l'exercice peut être créé et enregistré correctement.
     */
    @Test
    public void testAddExercice() {
        Discipline discipline = createAndSaveDiscipline();
        Training training = createAndSaveTraining(discipline);
        Exercice exercice = createAndSaveExercice(training);
        assertNotNull(exercice.getId());
    }

    /**
     * Tests pour vérifier que la session d'entraînement peut être créée et enregistrée correctement.
     */
    @Test
    public void testAddTrainingSession() {
        Discipline discipline = createAndSaveDiscipline();
        Training training = createAndSaveTraining(discipline);
        TrainingSession trainingSession = createAndSaveTrainingSession(training);
        assertNotNull(trainingSession.getId());
    }

    /**
     * Tests pour vérifier que la session d'exercice peut être créée et enregistrée correctement.
     */
    @Test
    public void testAddExerciceSession() {
        Discipline discipline = createAndSaveDiscipline();
        Training training = createAndSaveTraining(discipline);
        Exercice exercice = createAndSaveExercice(training);
        TrainingSession trainingSession = createAndSaveTrainingSession(training);
        ExerciceSession exerciceSession = createAndSaveExerciceSession(exercice, trainingSession);
        assertNotNull(exerciceSession.getId());
    }
}
