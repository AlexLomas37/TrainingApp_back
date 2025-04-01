package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.model.Discipline;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.repository.DisciplineRepository;
import ca.usherbrooke.trainingapi.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Contrôleur gérant les opérations liées aux entraînements.
 */
@RestController
public class TrainingController {

    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;

    /**
     * Récupère la liste de tous les entraînements.
     *
     * @return la liste des entraînements
     */
    @GetMapping("/trainings")
    public Iterable<Training> getTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * Récupère un entraînement par son identifiant.
     *
     * @param id l'identifiant de l'entraînement
     * @return l'objet Training correspondant ou null
     */
    @GetMapping("/trainings/{id}")
    public Training getTrainingById(@PathVariable int id) {
        return trainingRepository.findById(id).orElse(null);
    }

    /**
     * Récupère la liste des exercices associés à un entraînement.
     *
     * @param id l'identifiant de l'entraînement
     * @return les exercices associés à l'entraînement
     */
    @GetMapping("/trainings/{id}/exercices")
    public Iterable<Exercice> getTrainingExercices(@PathVariable int id) {
        return Objects.requireNonNull(trainingRepository.findById(id).orElse(null)).getExercices();
    }

    @PostMapping("/trainings")
    public Training createTraining(@RequestBody Training training) {
        Discipline discipline = disciplineRepository
                .findById(training.getDiscipline().getId())
                .orElseThrow(() -> new RuntimeException("Discipline non trouvée"));
        training.setDiscipline(discipline);
        return trainingRepository.save(training);
    }

    @PatchMapping("/trainings/{id}")
    public Training patchTraining(@PathVariable int id, @RequestBody Training training) {
        Training existingTraining = trainingRepository.findById(id).orElse(null);
        if (existingTraining != null) {
            if (training.getName() != null) {
                existingTraining.setName(training.getName());
            }
            if (training.getDescription() != null) {
                existingTraining.setDescription(training.getDescription());
            }
            if (training.getTime() != 0) {
                existingTraining.setTime(training.getTime());
            }
            return trainingRepository.save(existingTraining);
        }
        return null;
    }

    @PutMapping("/trainings/{id}")
    public Training updateTraining(@PathVariable int id, @RequestBody Training training) {
        Training existingTraining = trainingRepository.findById(id).orElse(null);
        if (existingTraining != null) {
            existingTraining.setName(training.getName());
            existingTraining.setDescription(training.getDescription());
            existingTraining.setTime(training.getTime());
            return trainingRepository.save(existingTraining);
        }
        return null;
    }

    @DeleteMapping("/trainings/{id}")
    public void deleteTraining(@PathVariable int id) {
        trainingRepository.deleteById(id);
    }
}
