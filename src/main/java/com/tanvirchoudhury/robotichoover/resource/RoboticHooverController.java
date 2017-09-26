package com.tanvirchoudhury.robotichoover.resource;

import com.tanvirchoudhury.robotichoover.model.dto.CleanEnvironmentDto;
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto;
import com.tanvirchoudhury.robotichoover.service.HooverCleanService;
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
    private final HooverCleanService hooverCleanService;

    @PostMapping(value = "/robotic-hoover/clean", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CleanEnvironmentDto> clean(@RequestBody UncleanEnvironmentDto uncleanEnvironmentDto) {
        CleanEnvironmentDto cleanEnvironmentDto = hooverCleanService.cleanEnvironment(uncleanEnvironmentDto);
        return ResponseEntity.ok(cleanEnvironmentDto);
    }
}
