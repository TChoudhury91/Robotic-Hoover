package com.tanvirchoudhury.robotichoover.resource;

import com.tanvirchoudhury.robotichoover.model.dto.CleanEnvironmentResultDto;
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto;
import com.tanvirchoudhury.robotichoover.service.RoboticHooverService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RoboticHooverController {

    @Autowired
    private final RoboticHooverService roboticHooverService;

    @PostMapping(value = "/robotic-hoover/clean", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CleanEnvironmentResultDto> clean(@RequestBody UncleanEnvironmentDto uncleanEnvironmentDto) {
        CleanEnvironmentResultDto cleanEnvironmentResultDto = roboticHooverService.cleanEnvironment(uncleanEnvironmentDto);
        return ResponseEntity.ok(cleanEnvironmentResultDto);
    }
}
