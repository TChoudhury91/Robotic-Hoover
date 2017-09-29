package com.tanvirchoudhury.robotichoover.fixtures

import com.tanvirchoudhury.robotichoover.model.db.Coordinates
import com.tanvirchoudhury.robotichoover.model.db.Patches
import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment

class UncleanEnvironmentFixtures {

    static UncleanEnvironment uncleanEnvironment(params = [:]) {
        new UncleanEnvironment(
                roomSize: params.roomSize,
                coords: params.coords,
                patches: params.patches,
                instructions: params.instructions)
    }

    static UncleanEnvironment uncleanEnvironmentFromTestExample() {
        new UncleanEnvironment(
                roomSize: getCoords(5, 5),
                coords: getCoords(1, 2),
                patches: getPatches([[1, 0], [2, 2], [2, 3]]),
                instructions: "NNESEESWNWW")
    }

    static Coordinates getCoords(x, y) {
        new Coordinates(x, y)
    }

    static Patches getPatches(List<List<Integer>> listOfCoords) {
        List<Coordinates> coordinates = []
        listOfCoords.each { crds -> coordinates.add(getCoords(crds[0], crds[1])) }
        Patches patches = new Patches(coordinates)

        coordinates.each { crds -> crds.setPatches(patches) }
        patches
    }
}