package com.tanvirchoudhury.robotichoover.service;

import com.tanvirchoudhury.robotichoover.exception.InvalidInputDataException;
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tanvirchoudhury.robotichoover.model.enums.DirectionEnum.*;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.hibernate.validator.internal.util.StringHelper.isNullOrEmptyString;

@Service
public class ValidatorService {

    private static final int MAX_SINGLE_PAIR_COORDS_SIZE = 2;
    private static final int MIN_X_Y = 0;

    public static boolean isValid(UncleanEnvironmentDto uncleanEnvironmentDto) {
        if (isMandatoryInputDataEmpty(uncleanEnvironmentDto)) {
            throw new InvalidInputDataException("Empty input data inserted");
        } else if (!isCoordsASinglePair(uncleanEnvironmentDto.getRoomSize())) {
            throw new InvalidInputDataException("Invalid roomSize, need to provide a single and positive (x,y) coordinates");
        } else if (!isCoordsASinglePair(uncleanEnvironmentDto.getCoords())) {
            throw new InvalidInputDataException("Invalid coords, need to provide a single and positive (x,y) coordinates");
        } else if (!isListOfCoordsValid(uncleanEnvironmentDto.getPatches())) {
            throw new InvalidInputDataException("Invalid patches, need to provide a list of positive (x,y) coordinates");
        } else if (!isInstructionsValid(uncleanEnvironmentDto.getInstructions())) {
            throw new InvalidInputDataException("Invalid cardinal directions, N E S W are permitted");
        }
        return true;
    }

    private static boolean isMandatoryInputDataEmpty(UncleanEnvironmentDto uncleanEnvironmentDto) {
        return isEmpty(uncleanEnvironmentDto.getRoomSize())
                || isEmpty(uncleanEnvironmentDto.getCoords())
                || isEmpty(uncleanEnvironmentDto.getPatches())
                || isNullOrEmptyString(uncleanEnvironmentDto.getInstructions());
    }

    private static boolean isCoordsASinglePair(List<Integer> coordinates) {
        return coordinates.size() == MAX_SINGLE_PAIR_COORDS_SIZE &&
                coordinates.stream()
                        .allMatch(coords -> coords >= MIN_X_Y);
    }

    private static boolean isListOfCoordsValid(List<List<Integer>> coordinates) {
        return coordinates.stream()
                .allMatch(ValidatorService::isCoordsASinglePair);
    }

    private static boolean isInstructionsValid(String instructions) {

        for (char c : instructions.toUpperCase().toCharArray()) {
            if (c != NORTH.getValue() && c != EAST.getValue() && c != SOUTH.getValue() && c != WEST.getValue()) {
                return false;
            }
        }
        return true;
    }
}
