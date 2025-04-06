package ca.usherbrooke.trainingapi.controller;

import ca.usherbrooke.trainingapi.model.StatisticType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistic-types")
public class StatisticTypeController {

    @GetMapping
    public List<String> getStatisticTypes() {
        return Arrays.stream(StatisticType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
