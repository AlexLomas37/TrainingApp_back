package ca.usherbrooke.trainingapi;

import ca.usherbrooke.trainingapi.model.*;
import ca.usherbrooke.trainingapi.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

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

    @Test
    public void testSaveExerciceSessionWithStatistic() {
        // Exemple de création d'une discipline
        Discipline discipline = new Discipline();
        discipline.setName("Test");
        discipline.setDescription("Test description");

        // Exemple de création d'un entrainement
        Training training = new Training();
        training.setName("Test Entrainement");
        training.setDescription("Test description");
        training.setDiscipline(discipline);
        training.setTime(30);

        // Exemple de création d'un exercice
        Exercice exercice = new Exercice();
        exercice.setName("Test Exercice");
        exercice.setDescription("Test description");
        exercice.setTime(10);
        exercice.setRepetitions(5);
        exercice.setTraining(training);

        // Ajout des statistiques à l'exercice
        exercice.addStatistic(StatisticType.SCORE, "points");
        exercice.addStatistic(StatisticType.TIME, "s");
        exercice.addStatistic(StatisticType.ACCURACY, "%");

        // Enregistrement des objets dans la base de données
        disciplineRepository.save(discipline);
        trainingRepository.save(training);
        exerciceRepository.save(exercice);

        // Test creation d'une session d'entrainements
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setTraining(training);
        trainingSession.setStart(LocalDateTime.now());
        trainingSession.setEnd(LocalDateTime.now().plusMinutes(30));

        // Enregistrement de la session d'entrainement
        trainingSessionRepository.save(trainingSession);

        // Test creation d'une session pour un exercice
        ExerciceSession exerciceSession = new ExerciceSession();
        exerciceSession.setExercice(exercice);
        exerciceSession.setTrainingSession(trainingSession);
        exerciceSession.setStart(LocalDateTime.now());
        exerciceSession.setEnd(LocalDateTime.now().plusMinutes(10));

        // Test Add Statistics for exercice session
        /*ExerciceStatistic stat1 = new ExerciceStatistic();
        stat1.setExerciceSession(exerciceSession);
        stat1.setKeyStat(StatisticType.SCORE);
        stat1.setValue("100");*/

        // Enregistrement de la session d'exercice
        exerciceSessionRepository.save(exerciceSession);
    }
}
