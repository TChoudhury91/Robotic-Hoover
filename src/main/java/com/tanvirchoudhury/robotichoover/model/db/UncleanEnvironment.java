package com.tanvirchoudhury.robotichoover.model.db;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UncleanEnvironment {

    private Coordinates roomSize;
    private Coordinates coords;
    private List<Coordinates> patches;
    private String instructions;
}
