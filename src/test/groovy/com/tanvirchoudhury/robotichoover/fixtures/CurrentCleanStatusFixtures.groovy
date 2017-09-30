package com.tanvirchoudhury.robotichoover.fixtures

import com.tanvirchoudhury.robotichoover.model.CurrentCleanStatus
import com.tanvirchoudhury.robotichoover.model.db.Coordinates

import static com.tanvirchoudhury.robotichoover.fixtures.UncleanEnvironmentFixtures.getCoords

class CurrentCleanStatusFixtures {

    static CurrentCleanStatus aCurrentClean() {
        List<Coordinates> patches = [getCoords(2, 2), getCoords(3, 3)]
        new CurrentCleanStatus(getCoords(1, 1), patches, 0, getCoords(4, 4))
    }

    static CurrentCleanStatus aCurrentCleanWithAGivenPatch(patch) {
        List<Coordinates> patches = [patch]
        new CurrentCleanStatus(getCoords(1, 1), patches, 0, getCoords(4, 4))
    }


}
