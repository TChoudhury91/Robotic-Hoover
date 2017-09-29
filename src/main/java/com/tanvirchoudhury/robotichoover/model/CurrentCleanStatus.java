package com.tanvirchoudhury.robotichoover.model;

import com.tanvirchoudhury.robotichoover.model.db.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CurrentCleanStatus {

    @Setter
    private Coordinates currentCoords;

    @Setter
    private List<Coordinates> patches;

    private final Coordinates roomSize;
}
