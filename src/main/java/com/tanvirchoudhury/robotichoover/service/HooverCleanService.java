package com.tanvirchoudhury.robotichoover.service;

import com.tanvirchoudhury.robotichoover.model.dto.CleanEnvironmentDto;
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto;
import org.springframework.stereotype.Service;

@Service
public class HooverCleanService {

    public CleanEnvironmentDto cleanEnvironment(UncleanEnvironmentDto uncleanEnvironmentDto) {

        return new CleanEnvironmentDto();
    }

}
