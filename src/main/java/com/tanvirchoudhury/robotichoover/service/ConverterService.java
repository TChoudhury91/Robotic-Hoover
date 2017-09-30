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
        List<Integer> roomSize = uncleanEnvironmentDto.getRoomSize();
        return UncleanEnvironment.builder()
                .roomSize(extractCoordinates(roomSize))
                .coords(extractCoordinates(uncleanEnvironmentDto.getCoords()))
                .patches(extractPatchCoordinates(uncleanEnvironmentDto.getPatches(), roomSize))
                .instructions(uncleanEnvironmentDto.getInstructions().toUpperCase())
                .build();
    }

    private static Coordinates extractCoordinates(List<Integer> coordinates) {
        return new Coordinates(coordinates.get(0), coordinates.get(1));
    }

    private static Patches extractPatchCoordinates(List<List<Integer>> listOfPatches, List<Integer> roomSize) {
        List<List<Integer>> listOfPatchesThatCanBeCleaned = listOfPatches.stream()
                .filter(coords -> !coords.get(0).equals(roomSize.get(0)))
                .filter(coords -> !coords.get(1).equals(roomSize.get(1)))
                .collect(toList());

        List<Coordinates> coordinates = listOfPatchesThatCanBeCleaned.stream()
                .map(ConverterService::extractCoordinates)
                .collect(toList());

        Patches patches = new Patches(coordinates);
        coordinates.forEach(coords -> coords.setPatches(patches));

        return patches;
    }


}
