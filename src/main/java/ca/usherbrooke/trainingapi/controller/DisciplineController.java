package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.model.Discipline;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Contrôleur pour gérer les opérations CRUD sur les disciplines.
 */
@RestController
public class DisciplineController {

    @Autowired
    private DisciplineRepository disciplineRepository;

    /**
     * Récupère la liste de toutes les disciplines.
     *
     * @return la liste des disciplines
     */
    @GetMapping("/disciplines")
    public Iterable<Discipline> getDisciplines() {
        return disciplineRepository.findAll();
    }

    /**
     * Crée une nouvelle discipline.
     *
     * @param discipline la discipline à créer
     * @return la discipline créée
     */
    @PostMapping("/disciplines")
    public Discipline createDiscipline(@RequestBody Discipline discipline) {
        return disciplineRepository.save(discipline);
    }

    /**
     * Récupère une discipline par son identifiant.
     *
     * @param id l'identifiant de la discipline
     * @return la discipline correspondante ou null
     */
    @GetMapping("/disciplines/{id}")
    public Discipline getDisciplineById(@PathVariable int id) {
        return disciplineRepository.findById(id).orElse(null);
    }

    /**
     * Met à jour une discipline existante.
     *
     * @param id l'identifiant de la discipline à mettre à jour
     * @param discipline les nouvelles données de la discipline
     * @return la discipline mise à jour ou null si introuvable
     */
    @PutMapping("/disciplines/{id}")
    public Discipline updateDiscipline(@PathVariable int id, @RequestBody Discipline discipline) {
        Discipline existingDiscipline = disciplineRepository.findById(id).orElse(null);
        if (existingDiscipline != null) {
            existingDiscipline.setName(discipline.getName());
            existingDiscipline.setTrainings(discipline.getTrainings());
            existingDiscipline.setDescription(discipline.getDescription());
            return disciplineRepository.save(existingDiscipline);
        }
        return null;
    }

    /**
     * Met à jour partiellement une discipline existante.
     *
     * @param id l'identifiant de la discipline
     * @param discipline les données partiellement mises à jour
     * @return la discipline mise à jour ou null si introuvable
     */
    @PatchMapping("/disciplines/{id}")
    public Discipline patchDiscipline(@PathVariable int id, @RequestBody Discipline discipline) {
        Discipline existingDiscipline = disciplineRepository.findById(id).orElse(null);
        if (existingDiscipline != null) {
            if (discipline.getName() != null) {
                existingDiscipline.setName(discipline.getName());
            }
            if (discipline.getTrainings() != null) {
                existingDiscipline.setTrainings(discipline.getTrainings());
            }
            if (discipline.getDescription() != null) {
                existingDiscipline.setDescription(discipline.getDescription());
            }
            return disciplineRepository.save(existingDiscipline);
        }
        return null;
    }

    /**
     * Supprime une discipline par son identifiant.
     *
     * @param id l'identifiant de la discipline à supprimer
     */
    @DeleteMapping("/disciplines/{id}")
    public void deleteDiscipline(@PathVariable int id) {
        disciplineRepository.deleteById(id);
    }

    /**
     * Récupère les entraînements associés à une discipline.
     *
     * @param id l'identifiant de la discipline
     * @return la liste des entraînements associés
     */
    @GetMapping("/disciplines/{id}/trainings")
    public Iterable<Training> getDisciplineTrainings(@PathVariable int id) {
        return Objects.requireNonNull(disciplineRepository.findById(id).orElse(null)).getTrainings();
    }
}
