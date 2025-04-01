package ca.usherbrooke.trainingapi;

import ca.usherbrooke.trainingapi.model.Discipline;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.repository.DisciplineRepository;
import ca.usherbrooke.trainingapi.repository.ExerciceRepository;
import ca.usherbrooke.trainingapi.repository.TrainingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrainingApiApplicationTests {

    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private ExerciceRepository exerciceRepository;

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

        // Enregistrement des objets dans la base de données
        disciplineRepository.save(discipline);
        trainingRepository.save(training);
        exerciceRepository.save(exercice);
    }

}
