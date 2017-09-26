package com.tanvirchoudhury.robotichoover.service

import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment
import spock.lang.Specification
import spock.lang.Subject

import static com.tanvirchoudhury.robotichoover.fixtures.UncleanEnvironmentDtoFixtures.aUncleanEnvironment

class ConverterServiceTest extends Specification {

    @Subject
    ConverterService subject

    def setup() {
        subject = new ConverterService()
    }

    def "UncleanEnvironmentDto is converted to a UncleanEnvironment correctly"() {

        given: "A unclean environment"
        def uncleanEnvDto = aUncleanEnvironment()

        when: "Unclean environment Dto is converted to a unclean enviorment"
        UncleanEnvironment result = subject.convertToUncleanEnvironment(uncleanEnvDto)

        then: "Unclean environment is converted correctly"
        result

        result.roomSize.x == uncleanEnvDto.roomSize.first()
        result.roomSize.y == uncleanEnvDto.roomSize.get(1)
        result.coords.x == uncleanEnvDto.coords.first()
        result.coords.y == uncleanEnvDto.coords.get(1)

        result.patches.size() == uncleanEnvDto.patches.size()
        for (int c = 0; c < uncleanEnvDto.patches.size(); c++) {
            result.patches.get(c).x == uncleanEnvDto.patches.get(c).get(0)
            result.patches.get(c).y == uncleanEnvDto.patches.get(c).get(1)
        }

        result.instructions == uncleanEnvDto.instructions
    }

}
