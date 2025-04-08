package ca.usherbrooke.trainingapi.Services.Strategies;

import ca.usherbrooke.trainingapi.model.Discipline;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {

    @Autowired
    private DisciplineRepository disciplineRepository;

    /**
     * Récupère la liste de toutes les disciplines.
     *
     * @return la liste des disciplines
     */
    public List<Discipline> getAllDisciplines() {
        return disciplineRepository.findAll();
    }

    /**
     * Récupère la discipline en fonction de son id.
     *
     * @param id l'id de la discipline
     * @return la discipline correspondante
     */
    public Discipline getDisciplineById(int id) {
        return disciplineRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aucune discipline trouvé avec l'id : " + id));
    }

    /**
     * Sauvegarde la discipline dans la base de données.
     *
     * @param discipline la discipline à sauvegarder
     * @return la discipline sauvegardée
     */
    public Discipline saveDiscipline(Discipline discipline) {
        return disciplineRepository.save(discipline);
    }

    /**
     * Met à jour une discipline existante.
     *
     * @param id l'identifiant de la discipline à mettre à jour
     * @param discipline les nouvelles données de la discipline
     * @return la discipline mise à jour
     */
    public Discipline updateDiscipline(int id, Discipline discipline) {
        Discipline existingDiscipline = getDisciplineById(id);
        existingDiscipline.setName(discipline.getName());
        existingDiscipline.setDescription(discipline.getDescription());
        return disciplineRepository.save(existingDiscipline);
    }

    /**
     * Met à jour partiellement une discipline existante.
     *
     * @param id l'identifiant de la discipline à mettre à jour
     * @param discipline les nouvelles données de la discipline
     * @return la discipline mise à jour
     */
    public Discipline updateDisciplineByPatch(int id, Discipline discipline) {
        Discipline existingDiscipline = getDisciplineById(id);
        if (discipline.getName() != null) {
            existingDiscipline.setName(discipline.getName());
        }
        if (discipline.getDescription() != null) {
            existingDiscipline.setDescription(discipline.getDescription());
        }
        if(discipline.getTrainings() != null && !discipline.getTrainings().isEmpty()) {
            existingDiscipline.setTrainings(discipline.getTrainings());
        }
        return disciplineRepository.save(existingDiscipline);
    }

    /**
     * Récupère la liste des entraînements associés à une discipline.
     *
     * @param id l'identifiant de la discipline
     * @return la liste des entraînements associés
     */
    public List<Training> getTrainingsByDisciplineId(int id) {
        Discipline discipline = getDisciplineById(id);
        return discipline.getTrainings();
    }

    /**
     * Supprime une discipline par son identifiant.
     *
     * @param id l'identifiant de la discipline à supprimer
     */
    public void deleteDiscipline(int id) {
        Discipline discipline = getDisciplineById(id);
        disciplineRepository.delete(discipline);
    }


}
