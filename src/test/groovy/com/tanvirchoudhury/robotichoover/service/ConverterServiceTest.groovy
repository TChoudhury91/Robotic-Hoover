package com.tanvirchoudhury.robotichoover.service

import com.tanvirchoudhury.robotichoover.model.db.CleanEnvironmentResult
import com.tanvirchoudhury.robotichoover.model.db.Coordinates
import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.tanvirchoudhury.robotichoover.fixtures.UncleanEnvironmentDtoFixtures.aUncleanEnvironmentDto
import static com.tanvirchoudhury.robotichoover.fixtures.UncleanEnvironmentFixtures.uncleanEnvironmentFromTestExample

class ConverterServiceTest extends Specification {

    @Subject
    ConverterService subject

    def setup() {
        subject = new ConverterService()
    }

    @Unroll
    def "UncleanEnvironmentDto is converted to a UncleanEnvironment correctly"() {

        given: "A unclean environment"
        def uncleanEnvDto = aUncleanEnvironmentDto(instructions: instructions)

        when: "Unclean environment Dto is converted to a unclean enviorment"
        UncleanEnvironment result = subject.convertToUncleanEnvironment(uncleanEnvDto)

        then: "Unclean environment is converted correctly"
        result

        result.roomSize.x == uncleanEnvDto.roomSize.first()
        result.roomSize.y == uncleanEnvDto.roomSize.get(1)
        result.coords.x == uncleanEnvDto.coords.first()
        result.coords.y == uncleanEnvDto.coords.get(1)

        def patchesCoordinates = result.patches.coordinates
        patchesCoordinates.size() == uncleanEnvDto.patches.size()
        for (int c = 0; c < patchesCoordinates.size(); c++) {
            assert patchesCoordinates.get(c).x == uncleanEnvDto.patches.get(c).get(0)
            assert patchesCoordinates.get(c).y == uncleanEnvDto.patches.get(c).get(1)
        }

        result.instructions == expectedInstructions

        where:
        instructions | expectedInstructions
        "NNWES"      | "NNWES"
        "nwse"       | "NWSE"
    }

    def "Patches with X/Y coordiantes matching the room size X/Y coordinates are not included, as they cant be cleaned due to room wall"() {

        given: "A room size"
        def roomSize = [4, 4]

        and: "A list of patches including a patch that matches Y coordinate of the room size"
        def patches = [[1, 1], [3, 4], [1, 4], [4,1], [4,3]]

        when: "Extracting patches from a list of coordinates"
        def result = subject.extractPatchCoordinates(patches, roomSize)

        then: "Patches that cannot be cleaned are not included"
        def patchCoordinates = result.coordinates
        patchCoordinates.size() == 1
        patchCoordinates.forEach { it ->
            assert it.y != 4
        }
    }

    def "Unclean Environment is converted to a CurrentCleanStatus"() {

        given: "Unclean Environment"
        def uncleanEnv = uncleanEnvironmentFromTestExample()

        when: "Converting a Unclean Environment"
        def result = subject.convertToCurrentCleanStatus(uncleanEnv)

        then: "Returns the expected Current Clean Status"
        result.roomSize.x == uncleanEnv.roomSize.getX()
        result.roomSize.y == uncleanEnv.roomSize.getY()
        result.currentCoords.x == uncleanEnv.coords.getX()
        result.currentCoords.y == uncleanEnv.coords.getY()

        def patchesCoordinates = result.patches
        def uncleanEnvPatches = uncleanEnv.patches.coordinates
        patchesCoordinates.size() == uncleanEnvPatches.size()
        for (int c = 0; c < patchesCoordinates.size(); c++) {
            assert patchesCoordinates.get(c).x == uncleanEnvPatches.get(c).getX()
            assert patchesCoordinates.get(c).y == uncleanEnvPatches.get(c).getY()
        }
    }

    def "CleanEnvironmentResult is converted to CleanEnvironmentResultDto"() {

        given: "Clean Environment"
        def cleanEnv = new CleanEnvironmentResult(new Coordinates(4, 5), 2)

        when:
        def result = subject.convertToCleanEnvironmentResultDto(cleanEnv)

        then:
        result.coords[0] == 4
        result.coords[1] == 5

        result.patches == 2
    }
}
