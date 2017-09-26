package com.tanvirchoudhury.robotichoover.resource

import com.tanvirchoudhury.robotichoover.dto.CleanEnvironmentDto
import com.tanvirchoudhury.robotichoover.dto.UncleanEnvironmentDto
import com.tanvirchoudhury.robotichoover.service.HooverCleanService
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Subject

class RoboticHooverControllerTest extends Specification {

    @Subject
    RoboticHooverController subject

    HooverCleanService hooverCleanService

    def setup() {
        hooverCleanService = Mock(HooverCleanService)
        subject = new RoboticHooverController(hooverCleanService)
    }

    def "A received UncleanEnvironmentDto will start and return the result of the cleaning process"() {

        given: "A unclean environment"
        def uncleanEnvDto = new UncleanEnvironmentDto(
                roomSize: [5, 5],
                coords: [4, 4],
                patches: [[1,1], [2,2]],
                instructions: "NNWES")

        and: "A clean environment"
        def cleanEnvDto = new CleanEnvironmentDto()

        when: "A clean is requested"
        def result = subject.clean(uncleanEnvDto)

        then: "The cleaning process is started"
        1 * hooverCleanService.cleanEnvironment(uncleanEnvDto) >> cleanEnvDto

        and: "The result of the cleaning is returned"
        result.statusCode == HttpStatus.OK
        result.getBody() == cleanEnvDto
    }
}