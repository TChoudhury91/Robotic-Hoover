package com.tanvirchoudhury.robotichoover.service;

import com.tanvirchoudhury.robotichoover.model.db.Coordinates;
import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment;
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ConverterService {

    public static UncleanEnvironment convertToUncleanEnvironment(UncleanEnvironmentDto uncleanEnvironmentDto) {
        return UncleanEnvironment.builder()
                .roomSize(extractCoordinates(uncleanEnvironmentDto.getRoomSize()))
                .coords(extractCoordinates(uncleanEnvironmentDto.getCoords()))
                .patches(extractListOfCoordinates(uncleanEnvironmentDto.getPatches()))
                .instructions(uncleanEnvironmentDto.getInstructions())
                .build();
    }

    private static Coordinates extractCoordinates(List<Integer> coordinates) {
        return new Coordinates(coordinates.get(0), coordinates.get(1));
    }

    private static List<Coordinates> extractListOfCoordinates(List<List<Integer>> listOfCoordinates) {
        return listOfCoordinates.stream()
                .map(ConverterService::extractCoordinates)
                .collect(toList());
    }


}
