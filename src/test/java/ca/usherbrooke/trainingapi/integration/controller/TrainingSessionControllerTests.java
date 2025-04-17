package ca.usherbrooke.trainingapi.integration.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ca.usherbrooke.trainingapi.Services.TrainingSessionService;
import ca.usherbrooke.trainingapi.controller.TrainingSessionController;
import ca.usherbrooke.trainingapi.model.TrainingSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@WebMvcTest(TrainingSessionController.class)
public class TrainingSessionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingSessionService trainingSessionService;

    @InjectMocks
    private TrainingSessionController trainingSessionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(trainingSessionController)
                   .setMessageConverters(jacksonMessageConverter)
                   .build();
    }

    /**
     * Test pour vérifier que la méthode getTrainingSessions() retourne une liste de sessions d'entraînement.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetTrainingSessions() throws Exception {
        TrainingSession ts1 = new TrainingSession();
        TrainingSession ts2 = new TrainingSession();
        when(trainingSessionService.getTrainingSessions()).thenReturn(Arrays.asList(ts1, ts2));

        mockMvc.perform(get("/training-sessions"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").exists())
               .andExpect(jsonPath("$[1].id").exists());
    }

    /**
     * Test pour vérifier que la méthode getTrainingSessionById() retourne une session d'entraînement par son ID.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetTrainingSessionById() throws Exception {
        TrainingSession ts = new TrainingSession();
        when(trainingSessionService.getTrainingSessionById(1)).thenReturn(ts);

        mockMvc.perform(get("/training-sessions/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").exists());
    }

    /**
     * Test pour vérifier que la méthode getTrainingSessionsByTrainingId() retourne une liste de sessions d'entraînement par ID d'entraînement.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetTrainingSessionsByTrainingId() throws Exception {
        TrainingSession ts = new TrainingSession();
        when(trainingSessionService.getTrainingSessionsByTrainingId(10)).thenReturn(Arrays.asList(ts));

        mockMvc.perform(get("/training-sessions/training/10"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").exists());
    }

    /**
     * Test pour vérifier que la méthode createTrainingSession() crée une nouvelle session d'entraînement.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testCreateTrainingSession() throws Exception {
        // Initialisation avec valeurs start, end et un id pour le test
        TrainingSession ts = new TrainingSession();
        ts.setStart(java.time.LocalDateTime.parse("2023-10-10T12:00:00"));
        ts.setEnd(java.time.LocalDateTime.parse("2023-10-10T13:00:00"));

        when(trainingSessionService.saveTrainingSession(any(TrainingSession.class))).thenReturn(ts);

        mockMvc.perform(post("/training-sessions")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"start\":\"2023-10-10T12:00:00\",\"end\":\"2023-10-10T13:00:00\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").exists())
               .andExpect(jsonPath("$.start").value("2023-10-10T12:00:00"))
               .andExpect(jsonPath("$.end").value("2023-10-10T13:00:00"));
    }

    /**
     * Test pour vérifier que la méthode updateTrainingSession() met à jour une session d'entraînement existante.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testUpdateTrainingSession() throws Exception {
        TrainingSession ts = new TrainingSession();
        ts.setStart(java.time.LocalDateTime.parse("2025-04-04T09:41:46"));
        ts.setEnd(java.time.LocalDateTime.parse("2025-04-04T10:41:46"));
        when(trainingSessionService.updateTrainingSession(eq(1), any(TrainingSession.class))).thenReturn(ts);

        mockMvc.perform(put("/training-sessions/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"start\":\"2025-04-04T09:41:46\",\"end\":\"2025-04-04T10:41:46\"}"))
               .andExpect(status().isOk())
                .andExpect(jsonPath("$.start").value("2025-04-04T09:41:46"));
    }

    /**
     * Test pour vérifier que la méthode updateTrainingSessionByPatch() met à jour partiellement une session d'entraînement existante.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testPatchTrainingSession() throws Exception {
        TrainingSession ts = new TrainingSession();
        ts.setStart(java.time.LocalDateTime.parse("2023-10-10T12:00:00"));
        ts.setEnd(java.time.LocalDateTime.parse("2023-10-10T13:00:00"));
        when(trainingSessionService.updateTrainingSessionByPatch(eq(1), any(TrainingSession.class))).thenReturn(ts);

        mockMvc.perform(patch("/training-sessions/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"start\":\"2023-10-10T12:00:00\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.start").value("2023-10-10T12:00:00"))
               .andExpect(jsonPath("$.end").value("2023-10-10T13:00:00"));
    }

    /**
     * Test pour vérifier que la méthode deleteTrainingSession() supprime une session d'entraînement existante.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testDeleteTrainingSession() throws Exception {
        mockMvc.perform(delete("/training-sessions/1"))
               .andExpect(status().isOk());
    }
}
