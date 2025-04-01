package ca.usherbrooke.trainingapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur de test pour vérifier le fonctionnement de l'API.
 */
@RestController
@RequestMapping("/")
public class test {

    /**
     * Endpoint par défaut affichant un message de bienvenue.
     *
     * @return message de bienvenue
     */
    @GetMapping("/")
    public static String index() {
        return "Welcome to the training API !";
    }

    /**
     * Endpoint affichant "Hello World!".
     *
     * @return message "Hello World!"
     */
    @GetMapping("/hello")
    public static String hello() {
        return "Hello World!";
    }
}
