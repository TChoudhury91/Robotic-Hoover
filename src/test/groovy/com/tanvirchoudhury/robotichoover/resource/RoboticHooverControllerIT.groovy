package com.tanvirchoudhury.robotichoover.resource

import com.tanvirchoudhury.robotichoover.RoboticHooverApplication
import com.tanvirchoudhury.robotichoover.model.dto.CleanEnvironmentResultDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static com.tanvirchoudhury.robotichoover.fixtures.UncleanEnvironmentDtoFixtures.aUncleanEnvironmentDto
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.OK

@ContextConfiguration
@SpringBootTest(classes = [RoboticHooverApplication.class], webEnvironment = RANDOM_PORT)
class RoboticHooverControllerIT extends Specification {

    @LocalServerPort
    private int port

    @Autowired
    TestRestTemplate testRestTemplate

    def "Unclean environment is cleaned"() {

        given: "An input"
        def uncleanEnvDto = aUncleanEnvironmentDto()

        when: "When a request is sent"
        ResponseEntity result = testRestTemplate.postForEntity("http://localhost:$port/robotic-hoover/clean",
                uncleanEnvDto,
                CleanEnvironmentResultDto.class)

        then: "Expected result is returned"
        result.statusCode == OK

        def cleanEnvironmentResultDto = result.getBody()
        cleanEnvironmentResultDto.getCoords()[0] == 1
        cleanEnvironmentResultDto.getCoords()[1] == 3
        cleanEnvironmentResultDto.patches == 1
    }

    def "Invalid unclean environment returns error"() {

        given: "An input"
        def uncleanEnvDto = aUncleanEnvironmentDto(roomSize: [-5, 5])

        when: "When a request is sent"
        ResponseEntity result = testRestTemplate.postForEntity("http://localhost:$port/robotic-hoover/clean",
                uncleanEnvDto,
                String.class)

        then: "Expected error with message is returned"
        result.statusCode == BAD_REQUEST
        result.body == "Invalid roomSize, need to provide a single and positive (x,y) coordinates"
    }
}