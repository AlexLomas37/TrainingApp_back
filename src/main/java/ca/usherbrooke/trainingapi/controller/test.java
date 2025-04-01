package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.model.Discipline;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.repository.DisciplineRepository;
import ca.usherbrooke.trainingapi.repository.ExerciceRepository;
import ca.usherbrooke.trainingapi.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur de test pour vérifier le fonctionnement de l'API.
 */
@RestController
@RequestMapping("/")
public class test {

    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private ExerciceRepository exerciceRepository;

    /**
     * Endpoint par défaut affichant un message de bienvenue.
     *
     * @return message de bienvenue
     */
    @GetMapping("/")
    public static String index() {
        return "Welcome to the training API !";
    }

    /**
     * Endpoint affichant "Hello World!".
     *
     * @return message "Hello World!"
     */
    @GetMapping("/hello")
    public static String hello() {
        return "Hello World!";
    }

    @GetMapping("/addContent")
    public String addContent() {
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
        return "Content added!";
    }
}
