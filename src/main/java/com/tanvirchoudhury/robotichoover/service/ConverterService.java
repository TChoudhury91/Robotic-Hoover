package com.tanvirchoudhury.robotichoover.service;

import com.tanvirchoudhury.robotichoover.model.db.Coordinates;
import com.tanvirchoudhury.robotichoover.model.db.Patches;
import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment;
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@Service
@NoArgsConstructor(access = PRIVATE)
public class ConverterService {

    public static UncleanEnvironment convertToUncleanEnvironment(UncleanEnvironmentDto uncleanEnvironmentDto) {
        return UncleanEnvironment.builder()
                .roomSize(extractCoordinates(uncleanEnvironmentDto.getRoomSize()))
                .coords(extractCoordinates(uncleanEnvironmentDto.getCoords()))
                .patches(extractListOfCoordinates(uncleanEnvironmentDto.getPatches()))
                .instructions(uncleanEnvironmentDto.getInstructions().toUpperCase())
                .build();
    }

    private static Coordinates extractCoordinates(List<Integer> coordinates) {
        return new Coordinates(coordinates.get(0), coordinates.get(1));
    }

    //TODO do not include patches of dirt that are y
    private static Patches extractListOfCoordinates(List<List<Integer>> listOfCoordinates) {
        List<Coordinates> coordinates = listOfCoordinates.stream()
                .map(ConverterService::extractCoordinates)
                .collect(toList());

        Patches patches = new Patches(coordinates);
        coordinates.forEach(coords -> coords.setPatches(patches));

        return patches;
    }


}
