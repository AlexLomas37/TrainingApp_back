package ca.usherbrooke.trainingapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ca.usherbrooke.trainingapi.Services.ExerciceService;
import ca.usherbrooke.trainingapi.model.Exercice;

@WebMvcTest(ExerciceController.class)
public class ExerciceControllerTests {

    private MockMvc mockMvc;
    
    @MockBean
    private ExerciceService exerciceService;
    
    @InjectMocks
    private ExerciceController exerciceController;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(exerciceController).build();
    }

    /**
     * Teste la récupération de tous les exercices.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetExercices() throws Exception {
        Exercice e1 = new Exercice();
        Exercice e2 = new Exercice();
        when(exerciceService.getAllExercices()).thenReturn(Arrays.asList(e1, e2));
    
        mockMvc.perform(get("/exercices"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0]").exists())
               .andExpect(jsonPath("$[1]").exists());
    }

    /**
     * Teste la récupération d'un exercice par son ID.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetExerciceById() throws Exception {
        Exercice e = new Exercice();
        when(exerciceService.getExerciceById(1)).thenReturn(e);
    
        mockMvc.perform(get("/exercices/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").exists());
    }

    /**
     * Teste la création d'un exercice.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testCreateExercice() throws Exception {
        Exercice e = new Exercice();
        e.setName("Exercice Test");
        when(exerciceService.saveExercice(any(Exercice.class))).thenReturn(e);
    
        mockMvc.perform(post("/exercices")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Exercice Test\",\"description\":\"Desc Test\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").exists());
    }

    /**
     * Teste la récupération des exercices associés à une discipline.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testUpdateExercice() throws Exception {
        Exercice e = new Exercice();
        e.setName("Exercice Mis à Jour");
        when(exerciceService.updateExercice(eq(1), any(Exercice.class))).thenReturn(e);
    
        mockMvc.perform(put("/exercices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Exercice Mis à Jour\",\"description\":\"Desc Updated\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").exists());
    }

    /**
     * Teste la mise à jour partielle (PATCH) d'un exercice.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testPatchExercice() throws Exception {
        Exercice e = new Exercice();
        e.setName("Exercice Patch");
        when(exerciceService.updateExerciceByPatch(eq(1), any(Exercice.class))).thenReturn(e);
    
        mockMvc.perform(patch("/exercices/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Exercice Patch\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").exists());
    }

    /**
     * Teste la suppression d'un exercice.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testDeleteExercice() throws Exception {
        mockMvc.perform(delete("/exercices/1"))
               .andExpect(status().isOk());
    }
    
}
