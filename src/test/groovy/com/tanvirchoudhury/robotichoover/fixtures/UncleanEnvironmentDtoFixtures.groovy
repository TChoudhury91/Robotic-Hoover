package com.tanvirchoudhury.robotichoover.fixtures

import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto

class UncleanEnvironmentDtoFixtures {

    static UncleanEnvironmentDto aUncleanEnvironment() {
        new UncleanEnvironmentDto(
                roomSize: [5, 5],
                coords: [4, 4],
                patches: [[1,1], [2,2]],
                instructions: "NNWES")
    }

}
