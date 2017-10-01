package com.tanvirchoudhury.robotichoover.service

import com.google.common.collect.Iterables
import com.tanvirchoudhury.robotichoover.RoboticHooverApplication
import com.tanvirchoudhury.robotichoover.model.db.CleanEnvironmentResult
import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment
import com.tanvirchoudhury.robotichoover.repository.CleanEnvironmentResultRepository
import com.tanvirchoudhury.robotichoover.repository.UncleanEnvironmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject

import static com.tanvirchoudhury.robotichoover.fixtures.UncleanEnvironmentDtoFixtures.aUncleanEnvironmentDto

@ContextConfiguration
@SpringBootTest(classes = [RoboticHooverApplication.class])
@DataJpaTest
class RoboticHooverServiceIT extends Specification {

    @Subject
    @Autowired
    private RoboticHooverService subject

    @Autowired
    UncleanEnvironmentRepository uncleanEnvironmentRepository

    @Autowired
    CleanEnvironmentResultRepository cleanEnvironmentResultRepository

    def "cleanEnvironment will persist the input aswell as the output of the process"() {

        given: "A UncleanEnvironmentDto"
        def uncleanEnvDto = aUncleanEnvironmentDto()

        when: "Cleaning the environment"
        subject.cleanEnvironment(uncleanEnvDto)

        then: "the Unclean Environment data is persisted"
        UncleanEnvironment uncleanEnvironmentResult = Iterables.getOnlyElement(uncleanEnvironmentRepository.findAll())
        uncleanEnvironmentResult

        uncleanEnvironmentResult.roomSize.x == uncleanEnvDto.roomSize.first()
        uncleanEnvironmentResult.roomSize.y == uncleanEnvDto.roomSize.get(1)
        uncleanEnvironmentResult.coords.x == uncleanEnvDto.coords.first()
        uncleanEnvironmentResult.coords.y == uncleanEnvDto.coords.get(1)

        def patchesCoordinates = uncleanEnvironmentResult.patches.coordinates
        patchesCoordinates.size() == uncleanEnvDto.patches.size()
        for (int c = 0; c < patchesCoordinates.size(); c++) {
            assert patchesCoordinates.get(c).x == uncleanEnvDto.patches.get(c).get(0)
            assert patchesCoordinates.get(c).y == uncleanEnvDto.patches.get(c).get(1)
        }

        uncleanEnvironmentResult.instructions == uncleanEnvDto.instructions

        and: "The expected clean environment is persisted"
        CleanEnvironmentResult cleanEnvironmentResult = Iterables.getOnlyElement(cleanEnvironmentResultRepository.findAll())
        cleanEnvironmentResult.coords.x == 1
        cleanEnvironmentResult.coords.y == 3

        cleanEnvironmentResult.patchesCleaned == 1
    }
}
