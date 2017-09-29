package com.tanvirchoudhury.robotichoover.service;

import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment;
import com.tanvirchoudhury.robotichoover.model.dto.CleanEnvironmentDto;
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto;
import com.tanvirchoudhury.robotichoover.repository.UncleanEnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.tanvirchoudhury.robotichoover.service.ConverterService.convertToUncleanEnvironment;
import static com.tanvirchoudhury.robotichoover.service.ValidatorService.isValid;

@Service
public class RoboticHooverService {

    @Autowired
    UncleanEnvironmentRepository uncleanEnvironmentRepository;

    public CleanEnvironmentDto cleanEnvironment(UncleanEnvironmentDto uncleanEnvironmentDto) {
        if(isValid(uncleanEnvironmentDto)) {
            UncleanEnvironment uncleanEnvironment = convertToUncleanEnvironment(uncleanEnvironmentDto);
            uncleanEnvironmentRepository.save(uncleanEnvironment);
        }

        return null;
    }
}
