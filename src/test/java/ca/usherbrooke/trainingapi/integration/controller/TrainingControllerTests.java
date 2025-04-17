package ca.usherbrooke.trainingapi.integration.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;

import ca.usherbrooke.trainingapi.controller.TrainingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ca.usherbrooke.trainingapi.model.Training;
import ca.usherbrooke.trainingapi.model.Exercice;
import ca.usherbrooke.trainingapi.Services.TrainingService;

@WebMvcTest(TrainingController.class)
public class TrainingControllerTests {
    private MockMvc mockMvc;

    @MockBean
    private TrainingService trainingService;

    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
    }

    /**
     * Teste la récupération de tous les entraînements.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetTrainings() throws Exception {
        Training t1 = new Training();
        Training t2 = new Training();
        when(trainingService.getTrainings()).thenReturn(Arrays.asList(t1, t2));

        mockMvc.perform(get("/trainings"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0]").exists())
               .andExpect(jsonPath("$[1]").exists());
    }

    /**
     * Teste la récupération d'un entraînement par son ID.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetTrainingById() throws Exception {
        Training training = new Training();
        when(trainingService.getTrainingById(1)).thenReturn(training);

        mockMvc.perform(get("/trainings/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").exists());
    }

    /**
     * Teste la création d'un nouvel entraînement.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testCreateTraining() throws Exception {
        Training training = new Training();
        training.setName("Nouveau Training");
        when(trainingService.saveTraining(any(Training.class))).thenReturn(training);

        mockMvc.perform(post("/trainings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Nouveau Training\",\"description\":\"Description Test\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Nouveau Training"));
    }

    /**
     * Teste la récupération des exercices associés à un entraînement.
     */
    @Test
    public void testGetTrainingExercices() throws Exception {
        Exercice e1 = new Exercice();
        Exercice e2 = new Exercice();
        List<Exercice> exercices = Arrays.asList(e1, e2);
        when(trainingService.getExercicesByTrainingId(1)).thenReturn(exercices);

        mockMvc.perform(get("/trainings/1/exercices"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0]").exists())
               .andExpect(jsonPath("$[1]").exists());
    }

    /**
     * Teste la mise à jour partielle (PATCH) d'un entraînement.
     */
    @Test
    public void testPatchTraining() throws Exception {
        Training updatedTraining = new Training();
        updatedTraining.setName("Training Mis à Jour");
        when(trainingService.patchTraining(eq(1), any(Training.class))).thenReturn(updatedTraining);

        mockMvc.perform(patch("/trainings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Training Mis à Jour\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Training Mis à Jour"));
    }

    /**
     * Teste la mise à jour complète (PUT) d'un entraînement.
     */
    @Test
    public void testUpdateTraining() throws Exception {
        Training updatedTraining = new Training();
        updatedTraining.setName("Training Mis à Jour Complet");
        when(trainingService.updateTraining(eq(1), any(Training.class))).thenReturn(updatedTraining);

        mockMvc.perform(put("/trainings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Training Mis à Jour Complet\",\"description\":\"Nouvelle Description\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Training Mis à Jour Complet"));
    }

    /**
     * Teste la suppression d'un entraînement.
     */
    @Test
    public void testDeleteTraining() throws Exception {
        mockMvc.perform(delete("/trainings/1"))
               .andExpect(status().isOk());
    }
}
