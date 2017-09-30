package com.tanvirchoudhury.robotichoover.fixtures

import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto

class UncleanEnvironmentDtoFixtures {

    static UncleanEnvironmentDto aUncleanEnvironmentDto(params = [:]) {
        new UncleanEnvironmentDto(
                roomSize: params?.roomSize ?: [5, 5],
                coords: params?.coords ?: [1, 2],
                patches: params?.patches ?: [[1, 0], [2, 2], [2,3]],
                instructions: params?.instructions ?: "NNESEESWNWW")
    }

}
