package com.tanvirchoudhury.robotichoover.service

import com.google.common.collect.Iterables
import com.tanvirchoudhury.robotichoover.RoboticHooverApplication
import com.tanvirchoudhury.robotichoover.model.db.UncleanEnvironment
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

    def "A valid UncleanEnvironmentDto is persisted"() {

        given: "A UncleanEnvironmentDto"
        def uncleanEnvDto = aUncleanEnvironmentDto()

        when: "Cleaning the environment"
        subject.cleanEnvironment(uncleanEnvDto)

        then: "the Unclean Environment data is persisted"
        UncleanEnvironment result = Iterables.getOnlyElement(uncleanEnvironmentRepository.findAll())
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

        result.instructions == uncleanEnvDto.instructions
    }

}
