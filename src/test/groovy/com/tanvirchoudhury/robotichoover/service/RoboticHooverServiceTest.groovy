package com.tanvirchoudhury.robotichoover.service

import com.tanvirchoudhury.robotichoover.model.db.Coordinates
import com.tanvirchoudhury.robotichoover.repository.CleanEnvironmentResultRepository
import com.tanvirchoudhury.robotichoover.repository.UncleanEnvironmentRepository
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.tanvirchoudhury.robotichoover.fixtures.CurrentCleanStatusFixtures.*
import static com.tanvirchoudhury.robotichoover.fixtures.UncleanEnvironmentFixtures.uncleanEnvironmentFromTestExample
import static com.tanvirchoudhury.robotichoover.fixtures.UncleanEnvironmentFixtures.uncleanEnvironmentWithMultipleCleanedPatchesScenario

class RoboticHooverServiceTest extends Specification {

    @Subject
    RoboticHooverService subject

    ConverterService converterService

    CleanEnvironmentResultRepository cleanEnvironmentResultRepository
    UncleanEnvironmentRepository uncleanEnvironmentRepository

    def setup() {
        cleanEnvironmentResultRepository = Mock()
        converterService = new ConverterService()
        uncleanEnvironmentRepository = Mock()

        subject = new RoboticHooverService(cleanEnvironmentResultRepository, converterService, uncleanEnvironmentRepository)
    }

    def "Cleaning process cleans a patch"() {

        when: "Cleaning process is started"
        def result = subject.startCleaningProcess(uncleanEnvironmentFromTestExample())

        then: "Returned result of the cleaning process is as expected"
        result.currentCoords.x == 1
        result.currentCoords.y == 3
        result.patchesCleaned == 1
    }

    def "Cleaning process cleans multiple patches"() {

        when: "Cleaning process is started"
        def result = subject.startCleaningProcess(uncleanEnvironmentWithMultipleCleanedPatchesScenario())

        then: "Returned result of the cleaning process is as expected"
        result.currentCoords.x == 1
        result.currentCoords.y == 1
        result.patchesCleaned == 3
    }

    @Unroll
    def "Cardinal directions are converted to the correct coordinates"() {

        when:
        def result = subject.cardinalDirectionToCoords(cardinalCoords as char)

        then:
        result.x == coordinates[0]
        result.y == coordinates[1]

        where:
        cardinalCoords | coordinates
        'N'            | [0, 1]
        'E'            | [1, 0]
        'S'            | [0, -1]
        'W'            | [-1, 0]
    }

    @Unroll
    def "Given a cardinal direction, it will update the CurrentCleanProcess accordingly"() {

        given:
        def currentClean = aCurrentCleanStatus()

        when:
        def result = subject.moveHoover(aCurrentCleanStatus(), cardinalCoords as char)

        then:
        result.roomSize.x == currentClean.roomSize.x
        result.roomSize.y == currentClean.roomSize.y

        result.currentCoords.x == currentClean.currentCoords.x + expectedCoordinates[0]
        result.currentCoords.y == currentClean.currentCoords.y + expectedCoordinates[1]

        where:
        cardinalCoords | expectedCoordinates
        'N'            | [0, 1]
        'E'            | [1, 0]
        'S'            | [0, -1]
        'W'            | [-1, 0]
    }

    @Unroll
    def "When a hoover goes over a patch, that patch is removed from CurrentCleanProcess and patchesCleaned is incremented"() {

        given:
        def currentClean = aCurrentCleanStatusWithAGivenPatch(patches)

        when:
        def result = subject.moveHoover(currentClean, cardinalDirections as char)

        then:
        result.patches.isEmpty()
        result.patchesCleaned == 1

        where:
        cardinalDirections | patches
        'N'                | new Coordinates(1, 2)
        'E'                | new Coordinates(2, 1)
        'S'                | new Coordinates(1, 0)
        'W'                | new Coordinates(0, 1)

    }

    @Unroll
    def "Hoover will stay in place when cardinal intrustions goes outside of room wall"() {

        given:
        def currentClean = aCurrentCleanStatusOnBoundaryEdge()

        when:
        def result = subject.moveHoover(currentClean, cardinalDirections as char)

        then:
        result.currentCoords.getX() == 4
        result.currentCoords.getY() == 4

        where:
        cardinalDirections << ['N', 'E']
    }
}
