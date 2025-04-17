package ca.usherbrooke.trainingapi.integration.controller;

import ca.usherbrooke.trainingapi.controller.StatisticTypeController;
import ca.usherbrooke.trainingapi.model.StatisticMetric;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticTypeController.class)
public class StatisticTypeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test pour vérifier que la méthode getStatisticTypes() retourne une liste de types de statistiques.
     * @throws Exception L'exception levée lors de l'exécution du test.
     */
    @Test
    public void testGetStatisticTypes() throws Exception {
        mockMvc.perform(get("/statistic-types"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$.length()").value(StatisticMetric.values().length));
    }
}
