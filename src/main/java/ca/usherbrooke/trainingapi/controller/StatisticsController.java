package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.Services.Strategies.Training.StatisticsTrainingMatriceFactory;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private StatisticsTrainingMatriceFactory statisticsService;

    /**
     * Retourne les statistiques d'un entraînement sous forme de matrice.
     *
     * @param training L'id de l'entraînement
     * @param startDate La date de début de la période
     * @param endDate La date de fin de la période
     * @return une carte contenant les dates et un booléen indiquant si l'entraînement a été effectué ce jour-là
     */
    @GetMapping("/trainings/matrix")
    public Object getStatisticsTrainingMatrix(@RequestParam Training training, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        Training train = trainingRepository
                .findById(training.getId())
                .orElseThrow(() -> new IllegalArgumentException("Aucun entraînement trouvé avec cet ID: " + training.getId()));
        return statisticsService.retournerStatistiques(train, startDate, endDate).toString();
    }
}
