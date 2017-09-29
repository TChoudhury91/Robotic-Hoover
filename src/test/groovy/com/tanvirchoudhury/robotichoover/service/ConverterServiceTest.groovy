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
}
