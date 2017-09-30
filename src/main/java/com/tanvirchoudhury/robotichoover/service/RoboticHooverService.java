package com.tanvirchoudhury.robotichoover.service;

import com.tanvirchoudhury.robotichoover.model.CurrentCleanStatus;
import com.tanvirchoudhury.robotichoover.model.db.CleanEnvironmentResult;
import com.tanvirchoudhury.robotichoover.model.db.Coordinates;
import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment;
import com.tanvirchoudhury.robotichoover.model.dto.CleanEnvironmentResultDto;
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto;
import com.tanvirchoudhury.robotichoover.repository.CleanEnvironmentResultRepository;
import com.tanvirchoudhury.robotichoover.repository.UncleanEnvironmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RoboticHooverService {

    private static final char NORTH = 'N';
    private static final char EAST = 'E';
    private static final char SOUTH = 'S';
    private static final char WEST = 'W';

    private final CleanEnvironmentResultRepository cleanEnvironmentResultRepository;
    private final ConverterService converterService;
    private final UncleanEnvironmentRepository uncleanEnvironmentRepository;
    private final ValidatorService validatorService;

    @Transactional
    public CleanEnvironmentResultDto cleanEnvironment(UncleanEnvironmentDto uncleanEnvironmentDto) {
        validatorService.validateUncleanEnvironment(uncleanEnvironmentDto);
        UncleanEnvironment uncleanEnvironment = converterService.convertToUncleanEnvironment(uncleanEnvironmentDto);
        uncleanEnvironmentRepository.save(uncleanEnvironment);
        CurrentCleanStatus currentCleanStatus = startCleaningProcess(uncleanEnvironment);
        CleanEnvironmentResult cleanEnvironmentResult = converterService.convertToCleanEnvironment(currentCleanStatus);
        cleanEnvironmentResultRepository.save(cleanEnvironmentResult);
        return converterService.convertToCleanEnvironmentResultDto(cleanEnvironmentResult);
    }

    private CurrentCleanStatus startCleaningProcess(UncleanEnvironment uncleanEnvironment) {
        CurrentCleanStatus currentCleanStatus = converterService.convertToCurrentCleanStatus(uncleanEnvironment);
        for (char c : uncleanEnvironment.getInstructions().toCharArray()) {
            currentCleanStatus = moveHoover(currentCleanStatus, c);
        }
        return currentCleanStatus;
    }

    private CurrentCleanStatus moveHoover(CurrentCleanStatus currentCleanStatus, char cardinalDirection) {
        Coordinates cardinalDirectionCoords = cardinalDirectionToCoords(cardinalDirection);

        Coordinates currentCoords = currentCleanStatus.getCurrentCoords();
        calculateMove(currentCoords, cardinalDirectionCoords, currentCleanStatus.getRoomSize());

        for (int i = 0; i < currentCleanStatus.getPatches().size(); i++) {
            Coordinates patchCoords = currentCleanStatus.getPatches().get(i);

            if (currentCoords.getX() == patchCoords.getX() && currentCoords.getY() == patchCoords.getY()) {
                currentCleanStatus.getPatches().remove(i);
                currentCleanStatus.setPatchesCleaned(currentCleanStatus.getPatchesCleaned() + 1);
            }
        }

        return currentCleanStatus;
    }

    private void calculateMove(Coordinates currentCoords, Coordinates cardinalDirectionCoords, Coordinates roomSize) {
        int newXPositon = currentCoords.getX() + cardinalDirectionCoords.getX();
        int newYPositon = currentCoords.getY() + cardinalDirectionCoords.getY();

        if (newXPositon <= roomSize.getX() && newYPositon <= roomSize.getY()) {
            currentCoords.setX(newXPositon);
            currentCoords.setY(newYPositon);
        }
    }

    private Coordinates cardinalDirectionToCoords(char cardinalDirection) {
        switch (cardinalDirection) {
            case NORTH:
                return new Coordinates(0, 1);
            case EAST:
                return new Coordinates(1, 0);
            case SOUTH:
                return new Coordinates(0, -1);
            case WEST:
                return new Coordinates(-1, 0);
            default:
                return new Coordinates(0, 0);
        }
    }
}
