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

    private static final String EMPTY_INPUT_ERR = "Empty input data inserted";
    private static final String INVALID_ROOM_SIZE_COORD_ERR = "Invalid roomSize, need to provide a single and positive (x,y) coordinates";
    private static final String INVALID_COORDS_COORD_ERR = "Invalid coords, need to provide a single and positive (x,y) coordinates";
    private static final String INVALID_PATCHES_ERR = "Invalid patches, need to provide a list of positive (x,y) coordinates";
    private static final String INVALID_CARDINAL_DIR_ERR = "Invalid cardinal directions, N E S W are permitted";

    public boolean isValid(UncleanEnvironmentDto uncleanEnvironmentDto) {
        if (isMandatoryInputDataEmpty(uncleanEnvironmentDto)) {
            reportError(EMPTY_INPUT_ERR);
        } else if (!isCoordsASinglePair(uncleanEnvironmentDto.getRoomSize())) {
            reportError(INVALID_ROOM_SIZE_COORD_ERR);
        } else if (!isCoordsASinglePair(uncleanEnvironmentDto.getCoords())) {
            reportError(INVALID_COORDS_COORD_ERR);
        } else if (!isListOfCoordsValid(uncleanEnvironmentDto.getPatches())) {
            reportError(INVALID_PATCHES_ERR);
        } else if (!isInstructionsValid(uncleanEnvironmentDto.getInstructions())) {
            reportError(INVALID_CARDINAL_DIR_ERR);
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

    private void reportError(String errorMessage) {
        throw new InvalidInputDataException(errorMessage);
    }

}
