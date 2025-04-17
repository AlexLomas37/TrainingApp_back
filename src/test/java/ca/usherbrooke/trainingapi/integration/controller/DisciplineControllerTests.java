package ca.usherbrooke.trainingapi.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;
import java.util.Arrays;

import ca.usherbrooke.trainingapi.controller.DisciplineController;
import ca.usherbrooke.trainingapi.model.Training;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ca.usherbrooke.trainingapi.model.Discipline;
import ca.usherbrooke.trainingapi.Services.DisciplineService;

/**
 * Tests pour le contrôleur DisciplineController.
 */
@WebMvcTest(DisciplineController.class)
public class DisciplineControllerTests {

    private MockMvc mockMvc;

    @MockBean
    private DisciplineService disciplineService;

    @InjectMocks
    private DisciplineController disciplineController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(disciplineController).build();
    }

    /**
     * Test pour vérifier que la méthode getDisciplines() retourne une liste de disciplines.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetDisciplines() throws Exception {
        when(disciplineService.getAllDisciplines()).thenReturn(Arrays.asList(new Discipline(), new Discipline()));

        mockMvc.perform(get("/disciplines"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0]").exists())
               .andExpect(jsonPath("$[1]").exists());

        verify(disciplineService, times(1)).getAllDisciplines();
    }

    /**
     * Test pour vérifier que la méthode createDiscipline() crée une nouvelle discipline.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testCreateDiscipline() throws Exception {
        Discipline discipline = new Discipline();
        discipline.setName("Test Discipline"); // mise à jour pour définir le nom attendu
        when(disciplineService.saveDiscipline(any(Discipline.class))).thenReturn(discipline);

        mockMvc.perform(post("/disciplines")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Discipline\",\"description\":\"Test Description\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Test Discipline"));

        verify(disciplineService, times(1)).saveDiscipline(any(Discipline.class));
    }

    /**
     * Test pour vérifier que la méthode getDisciplineById() retourne une discipline par son ID.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetDisciplineById() throws Exception {
        Discipline discipline = new Discipline();
        when(disciplineService.getDisciplineById(1)).thenReturn(discipline);

        mockMvc.perform(get("/disciplines/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(0));

        verify(disciplineService, times(1)).getDisciplineById(1);
    }

    /**
     * Test pour vérifier que la méthode updateDiscipline() met à jour une discipline existante.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testUpdateDiscipline() throws Exception {
        Discipline updatedDiscipline = new Discipline();
        updatedDiscipline.setName("Updated Discipline");
        when(disciplineService.updateDiscipline(eq(1), any(Discipline.class))).thenReturn(updatedDiscipline);

        mockMvc.perform(put("/disciplines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Discipline\",\"description\":\"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Discipline"));

            verify(disciplineService, times(1)).updateDiscipline(eq(1), any(Discipline.class));
    }

    /**
     * Test pour vérifier que la méthode updateDisciplineByPatch() met à jour partiellement une discipline.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testPatchDiscipline() throws Exception {
        Discipline patchedDiscipline = new Discipline();
        patchedDiscipline.setName("Patched Discipline");
        when(disciplineService.updateDisciplineByPatch(eq(1), any(Discipline.class))).thenReturn(patchedDiscipline);

        mockMvc.perform(patch("/disciplines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Patched Discipline\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Patched Discipline"));

        verify(disciplineService, times(1)).updateDisciplineByPatch(eq(1), any(Discipline.class));
    }

    /**
     * Test pour vérifier que la méthode deleteDiscipline() supprime une discipline par son ID.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testDeleteDiscipline() throws Exception {
        doNothing().when(disciplineService).deleteDiscipline(1);

        mockMvc.perform(delete("/disciplines/1"))
                .andExpect(status().isOk());

        verify(disciplineService, times(1)).deleteDiscipline(1);
    }

    /**
     * Test pour vérifier que la méthode getDisciplineTrainings() retourne les entraînements associés à une discipline.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetDisciplineTrainings() throws Exception {
        when(disciplineService.getTrainingsByDisciplineId(1)).thenReturn(Arrays.asList(new Training(), new Training()));

        mockMvc.perform(get("/disciplines/1/trainings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(disciplineService, times(1)).getTrainingsByDisciplineId(1);
    }
}
