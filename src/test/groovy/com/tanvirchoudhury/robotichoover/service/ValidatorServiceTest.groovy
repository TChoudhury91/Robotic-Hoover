package com.tanvirchoudhury.robotichoover.service

import com.tanvirchoudhury.robotichoover.exception.InvalidInputDataException
import com.tanvirchoudhury.robotichoover.model.dto.UncleanEnvironmentDto
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.tanvirchoudhury.robotichoover.fixtures.UncleanEnvironmentDtoFixtures.aUncleanEnvironmentDto

class ValidatorServiceTest extends Specification {

    @Subject
    ValidatorService subject

    def setup() {
        subject = new ValidatorService()
    }

    @Unroll
    def "If invalid unclean environment data is passed in, then a validation error with the correct message is thrown"() {

        when: "Invalid unclean Environment is validated"
        subject.validateUncleanEnvironment(uncleanEnv)

        then: "Error is thrown with the correct error message"
        InvalidInputDataException ex = thrown()
        ex.getMessage() == message

        where:
        uncleanEnv                                                          | message
        aUncleanEnvironmentDto(roomSize: [1, 2, 3, 4, 5])                   | "Invalid roomSize, need to provide a single and positive (x,y) coordinates"
        aUncleanEnvironmentDto(coords: [1, 2, 3, 4, 5])                     | "Invalid coords, need to provide a single and positive (x,y) coordinates"
        aUncleanEnvironmentDto(patches: [[1, 2], [2, 3, 5]])                | "Invalid patches, need to provide a list of positive (x,y) coordinates"
        aUncleanEnvironmentDto(instructions: "YOTI")                        | "Invalid cardinal directions, N E S W are permitted"
        aUncleanEnvironmentDto(roomSize: [-1, 2])                           | "Invalid roomSize, need to provide a single and positive (x,y) coordinates"
        aUncleanEnvironmentDto(coords: [3, -4])                             | "Invalid coords, need to provide a single and positive (x,y) coordinates"
        aUncleanEnvironmentDto(patches: [[1, 2], [-2, -3]])                 | "Invalid patches, need to provide a list of positive (x,y) coordinates"
        aUncleanEnvironmentDto(roomSize: [5, 5], coords: [5, 6])            | "Invalid coordinates, outside of room boundary"
        aUncleanEnvironmentDto(roomSize: [4, 4], patches: [[1, 2], [6, 5]]) | "Invalid coordinates, outside of room boundary"

    }

    @Unroll
    def "If empty unclean environment dto is passed in, then a validation error with the correct message is thrown"() {

        when: "An empty input data is in a unclean Environment"
        subject.validateUncleanEnvironment(uncleanEnv)

        then:
        InvalidInputDataException ex = thrown()
        ex.getMessage() == message

        where:
        uncleanEnv                                                                                                 | message
        new UncleanEnvironmentDto(roomSize: null, coords: [4, 4], patches: [[1, 1], [2, 2]], instructions: "N")    | "Empty input data inserted"
        new UncleanEnvironmentDto(roomSize: [], coords: [4, 4], patches: [[1, 1], [2, 2]], instructions: "N")      | "Empty input data inserted"
        new UncleanEnvironmentDto(roomSize: [4, 4], coords: null, patches: [[1, 1], [2, 2]], instructions: "N")    | "Empty input data inserted"
        new UncleanEnvironmentDto(roomSize: [4, 4], coords: [], patches: [[1, 1], [2, 2]], instructions: "N")      | "Empty input data inserted"
        new UncleanEnvironmentDto(roomSize: [4, 4], coords: [4, 4], patches: null, instructions: "N")              | "Empty input data inserted"
        new UncleanEnvironmentDto(roomSize: [4, 4], coords: [4, 4], patches: [], instructions: "N")                | "Empty input data inserted"
        new UncleanEnvironmentDto(roomSize: [4, 4], coords: [4, 4], patches: [[1, 1], [2, 2]], instructions: null) | "Empty input data inserted"
        new UncleanEnvironmentDto(roomSize: [4, 4], coords: [4, 4], patches: [[1, 1], [2, 2]], instructions: "")   | "Empty input data inserted"
    }

    def "Returns true given a valid unclean environment"() {

        when: "Invalid unclean Environment is validated"
        subject.validateUncleanEnvironment(uncleanEnv)

        then: "It is valid"
        noExceptionThrown()

        where:
        uncleanEnv << [aUncleanEnvironmentDto(), aUncleanEnvironmentDto(instructions: "nnwe")]
    }
}