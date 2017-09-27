package com.tanvirchoudhury.robotichoover.fixtures

import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment

class UncleanEnvironmentFixtures {

    static UncleanEnvironment uncleanEnvironment(params = [:]) {
        new UncleanEnvironment(
                roomSize: params.roomSize,
                coords: params.coords,
                patches: params.patches,
                instructions: params.instructions)
    }

}
