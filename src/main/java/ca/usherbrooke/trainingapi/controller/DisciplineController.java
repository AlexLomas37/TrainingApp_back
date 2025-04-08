package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.Services.DisciplineService;
import ca.usherbrooke.trainingapi.model.Discipline;
import ca.usherbrooke.trainingapi.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur pour gérer les opérations CRUD sur les disciplines.
 */
@RestController
@RequestMapping("/disciplines")
public class DisciplineController {

    @Autowired
    private DisciplineService disciplineService;

    /**
     * Récupère la liste de toutes les disciplines.
     *
     * @return la liste des disciplines
     */
    @GetMapping
    public Iterable<Discipline> getDisciplines() {
        return disciplineService.getAllDisciplines();
    }

    /**
     * Crée une nouvelle discipline.
     *
     * @param discipline la discipline à créer
     * @return la discipline créée
     */
    @PostMapping
    public Discipline createDiscipline(@RequestBody Discipline discipline) {
        return disciplineService.saveDiscipline(discipline);
    }

    /**
     * Récupère une discipline par son identifiant.
     *
     * @param id l'identifiant de la discipline
     * @return la discipline correspondante ou null
     */
    @GetMapping("/{id}")
    public Discipline getDisciplineById(@PathVariable int id) {
        return disciplineService.getDisciplineById(id);
    }

    /**
     * Met à jour une discipline existante.
     *
     * @param id l'identifiant de la discipline à mettre à jour
     * @param discipline les nouvelles données de la discipline
     * @return la discipline mise à jour ou null si introuvable
     */
    @PutMapping("/{id}")
    public Discipline updateDiscipline(@PathVariable int id, @RequestBody Discipline discipline) {
        return disciplineService.updateDiscipline(id, discipline);
    }

    /**
     * Met à jour partiellement une discipline existante.
     *
     * @param id l'identifiant de la discipline
     * @param discipline les données partiellement mises à jour
     * @return la discipline mise à jour ou null si introuvable
     */
    @PatchMapping("/{id}")
    public Discipline patchDiscipline(@PathVariable int id, @RequestBody Discipline discipline) {
        return disciplineService.updateDisciplineByPatch(id, discipline);
    }

    /**
     * Supprime une discipline par son identifiant.
     *
     * @param id l'identifiant de la discipline à supprimer
     */
    @DeleteMapping("/{id}")
    public void deleteDiscipline(@PathVariable int id) {
        disciplineService.deleteDiscipline(id);
    }

    /**
     * Récupère les entraînements associés à une discipline.
     *
     * @param id l'identifiant de la discipline
     * @return la liste des entraînements associés
     */
    @GetMapping("/{id}/trainings")
    public Iterable<Training> getDisciplineTrainings(@PathVariable int id) {
        return disciplineService.getTrainingsByDisciplineId(id);
    }
}
