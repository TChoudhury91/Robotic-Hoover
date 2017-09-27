package com.tanvirchoudhury.robotichoover.service

import com.tanvirchoudhury.robotichoover.exception.InvalidInputDataException
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
    def "If invalid number of coordinates are passed in, then a validation error is thrown"() {

        when: "Invalid unclean Environment is validated"
        subject.isValid(uncleanEnv)

        then: "Error is thrown with the correct error message"
        InvalidInputDataException ex = thrown()
        ex.getMessage() == message

        where:
        uncleanEnv                                           | message
        aUncleanEnvironmentDto(roomSize: [1, 2, 3, 4, 5])    | "Invalid roomSize, max coordinate pair size is 2"
        aUncleanEnvironmentDto(coords: [1, 2, 3, 4, 5])      | "Invalid coords, max coordinate pair size is 2"
        aUncleanEnvironmentDto(patches: [[1, 2], [4, 5, 9]]) | "Invalid patches, max coordinate pair size is 2"
    }
}
