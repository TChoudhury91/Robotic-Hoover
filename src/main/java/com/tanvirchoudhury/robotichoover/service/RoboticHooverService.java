package com.tanvirchoudhury.robotichoover.service;

import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment;
import com.tanvirchoudhury.robotichoover.model.dto.CleanEnvironmentDto;
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoboticHooverService {

    private final ConverterService converterService;

    public CleanEnvironmentDto cleanEnvironment(UncleanEnvironmentDto uncleanEnvironmentDto) {

        return new CleanEnvironmentDto();
    }

    private UncleanEnvironment startClean(UncleanEnvironment uncleanEnvironment) {

        return null;
    }

}
