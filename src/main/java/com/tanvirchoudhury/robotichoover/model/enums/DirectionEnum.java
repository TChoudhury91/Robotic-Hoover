package com.tanvirchoudhury.robotichoover.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DirectionEnum {

    EAST('E'),
    NORTH('N'),
    SOUTH('S'),
    WEST('W');

    private final char value;
}
