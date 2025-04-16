package ca.usherbrooke.trainingapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur de test pour vérifier le fonctionnement de l'API.
 */
@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/")
    public static String index() {
        return "Welcome to the training API !";
    }
}
