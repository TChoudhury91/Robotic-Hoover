package com.tanvirchoudhury.robotichoover.model.db;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UncleanEnvironment {

    private Coordinates roomSize;
    private Coordinates coords;
    private List<Coordinates> patches;
    private String instructions;
}
