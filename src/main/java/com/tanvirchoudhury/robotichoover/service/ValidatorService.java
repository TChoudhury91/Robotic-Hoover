package com.tanvirchoudhury.robotichoover.service;

import com.tanvirchoudhury.robotichoover.exception.InvalidInputDataException;
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidatorService {

    private static final int MAX_SINGLE_PAIR_COORDS_SIZE = 2;

    public boolean isValid(UncleanEnvironmentDto uncleanEnvironmentDto) {
        if (!isCoordsASinglePair(uncleanEnvironmentDto.getRoomSize())) {
            throw new InvalidInputDataException("Invalid roomSize, max coordinate pair size is 2");
        } else if (!isCoordsASinglePair(uncleanEnvironmentDto.getCoords())) {
            throw new InvalidInputDataException("Invalid coords, max coordinate pair size is 2");
        } else if (!isListOfCoordsValid(uncleanEnvironmentDto.getPatches())) {
            throw new InvalidInputDataException("Invalid patches, max coordinate pair size is 2");
        }

        return true;
    }

    private static boolean isCoordsASinglePair(List<Integer> coordinates) {
        return coordinates.size() == MAX_SINGLE_PAIR_COORDS_SIZE;
    }

    private static boolean isListOfCoordsValid(List<List<Integer>> coordinates) {
        return coordinates.stream()
                .allMatch(ValidatorService::isCoordsASinglePair);
    }
}
