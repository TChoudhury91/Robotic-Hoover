package com.tanvirchoudhury.robotichoover.service;

import com.tanvirchoudhury.robotichoover.model.CurrentCleanStatus;
import com.tanvirchoudhury.robotichoover.model.db.CleanEnvironment;
import com.tanvirchoudhury.robotichoover.model.db.Coordinates;
import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment;
import com.tanvirchoudhury.robotichoover.model.dto.CleanEnvironmentDto;
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto;
import com.tanvirchoudhury.robotichoover.repository.UncleanEnvironmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.tanvirchoudhury.robotichoover.service.ConverterService.convertToUncleanEnvironment;
import static com.tanvirchoudhury.robotichoover.service.ValidatorService.isValid;

@Service
@AllArgsConstructor
public class RoboticHooverService {

    private static final char NORTH = 'N';
    private static final char EAST = 'E';
    private static final char SOUTH = 'S';
    private static final char WEST = 'W';

    private final UncleanEnvironmentRepository uncleanEnvironmentRepository;

    public CleanEnvironmentDto cleanEnvironment(UncleanEnvironmentDto uncleanEnvironmentDto) {
        if (isValid(uncleanEnvironmentDto)) {
            UncleanEnvironment uncleanEnvironment = convertToUncleanEnvironment(uncleanEnvironmentDto);
            uncleanEnvironmentRepository.save(uncleanEnvironment);
        }

        return null;
    }

    private CurrentCleanStatus createCurrentCleanStatus(UncleanEnvironment uncleanEnvironment) {
        return new CurrentCleanStatus(uncleanEnvironment.getCoords(),
                uncleanEnvironment.getPatches().getCoordinates(),
                0,
                uncleanEnvironment.getRoomSize());
    }


    private CurrentCleanStatus startCleaningProcess(UncleanEnvironment uncleanEnvironment) {
        CurrentCleanStatus currentCleanStatus = createCurrentCleanStatus(uncleanEnvironment);

        for (char c : uncleanEnvironment.getInstructions().toCharArray()) {
            currentCleanStatus = moveHoover(currentCleanStatus, c);
        }

        return currentCleanStatus;
    }

    private CurrentCleanStatus moveHoover(CurrentCleanStatus currentCleanStatus, char cardinalDirection) {
        Coordinates cardinalDirectionCoords = cardinalDirectionToCoords(cardinalDirection);

        Coordinates currentCoords = currentCleanStatus.getCurrentCoords();
        currentCoords.setX(currentCoords.getX() + cardinalDirectionCoords.getX());
        currentCoords.setY(currentCoords.getY() + cardinalDirectionCoords.getY());

        for (int i = 0; i < currentCleanStatus.getPatches().size(); i++) {
            Coordinates patchCoords = currentCleanStatus.getPatches().get(i);

            if (currentCoords.getX() == patchCoords.getX() && currentCoords.getY() == patchCoords.getY()) {
                currentCleanStatus.getPatches().remove(i);
                currentCleanStatus.setPatchesCleaned(currentCleanStatus.getPatchesCleaned() + 1);
            }
        }

        return currentCleanStatus;
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
