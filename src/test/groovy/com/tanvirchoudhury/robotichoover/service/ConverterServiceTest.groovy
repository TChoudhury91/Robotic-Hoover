package com.tanvirchoudhury.robotichoover.service

import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.tanvirchoudhury.robotichoover.fixtures.UncleanEnvironmentDtoFixtures.aUncleanEnvironmentDto

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

    def "Patches with Y coordiantes matching the room size Y coordinates are not included"() {

        given: "A room size"
        def roomSize = [4, 4]

        and: "A list of patches including a patch that matches Y coordinate of the room size"
        def patches = [[1, 1], [1, 2], [3,4], [1,4]]

        when: "Extracting patches from a list of coordinates"
        def result = subject.extractListOfCoordinates(patches, roomSize)

        then: "Patches that cannot be cleaned are not included"
        def patchCoordinates = result.coordinates
        patchCoordinates.size() == 2
        patchCoordinates.find {it ->
            assert it.y != 4
        }
    }

}
