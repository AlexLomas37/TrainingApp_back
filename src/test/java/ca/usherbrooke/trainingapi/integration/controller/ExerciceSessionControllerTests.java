package ca.usherbrooke.trainingapi.integration.controller;

import ca.usherbrooke.trainingapi.controller.ExerciceSessionController;
import ca.usherbrooke.trainingapi.model.ExerciceSession;
import ca.usherbrooke.trainingapi.Services.ExerciceSessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExerciceSessionController.class)
public class ExerciceSessionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExerciceSessionService exerciceSessionService;

    @Autowired
    private ExerciceSessionController exerciceSessionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(exerciceSessionController)
                   .setMessageConverters(jacksonMessageConverter)
                   .build();
    }

    /**
     * Test pour vérifier que la méthode getAllExerciceSessions() retourne une liste de sessions d'exercice.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetAllExerciceSessions() throws Exception {
        ExerciceSession es1 = new ExerciceSession();
        ExerciceSession es2 = new ExerciceSession();
        when(exerciceSessionService.getExerciceSessions()).thenReturn(Arrays.asList(es1, es2));

        mockMvc.perform(get("/exercice-sessions"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").exists())
               .andExpect(jsonPath("$[1].id").exists());
    }

    /**
     * Test pour vérifier que la méthode getExerciceSessionById() retourne une session d'exercice par son ID.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetExerciceSessionById() throws Exception {
        ExerciceSession es = new ExerciceSession();
        when(exerciceSessionService.getExerciceSessionById(1)).thenReturn(es);

        mockMvc.perform(get("/exercice-sessions/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").exists());
    }

    /**
     * Test pour vérifier que la méthode getExercicesSessionsByExoId() retourne une liste de sessions d'exercice par ID d'exercice.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetExerciceSessionsByExoId() throws Exception {
        ExerciceSession es = new ExerciceSession();
        when(exerciceSessionService.getExercicesSessionsByExoId(10)).thenReturn(Arrays.asList(es));

        mockMvc.perform(get("/exercice-sessions/exercice/10"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").exists());
    }

    /**
     * Test pour vérifier que la méthode createExerciceSession() crée une nouvelle session d'exercice.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testCreateExerciceSession() throws Exception {
        ExerciceSession es = new ExerciceSession();
        when(exerciceSessionService.saveExerciceSession(any(ExerciceSession.class))).thenReturn(es);

        mockMvc.perform(post("/exercice-sessions")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").exists());
    }

    /**
     * Test pour vérifier que la méthode updateExerciceSession() met à jour une session d'exercice.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testDeleteExerciceSession() throws Exception {
        doNothing().when(exerciceSessionService).deleteExerciceSession(eq(1));

        mockMvc.perform(delete("/exercice-sessions/1"))
               .andExpect(status().isOk());
    }
}
