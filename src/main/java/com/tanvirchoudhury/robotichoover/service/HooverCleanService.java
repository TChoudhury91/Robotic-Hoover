package com.tanvirchoudhury.robotichoover.service;

import com.tanvirchoudhury.robotichoover.dto.CleanEnvironmentDto;
import com.tanvirchoudhury.robotichoover.dto.UncleanEnvironmentDto;
import org.springframework.stereotype.Service;

@Service
public class HooverCleanService {

    public CleanEnvironmentDto cleanEnvironment(UncleanEnvironmentDto uncleanEnvironmentDto) {

        return new CleanEnvironmentDto();
    }

}
