package com.tanvirchoudhury.robotichoover.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CleanEnvironmentResultDto {

    private List<Integer> coords;
    private int patches;
}
