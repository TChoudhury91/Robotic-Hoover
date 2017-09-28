package com.tanvirchoudhury.robotichoover.fixtures

import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto

class UncleanEnvironmentDtoFixtures {

    static UncleanEnvironmentDto aUncleanEnvironmentDto(params = [:]) {
        new UncleanEnvironmentDto(
                roomSize: params?.roomSize ?: [5, 5],
                coords: params?.coords ?: [4, 4],
                patches: params?.patches ?: [[[1, 1], [2, 2]]],
                instructions: params?.instructions ?: "NNWES")
    }

}
