package com.tanvirchoudhury.robotichoover.service

import spock.lang.Specification
import spock.lang.Subject

class RoboticHooverServiceTest extends Specification {

    @Subject
    RoboticHooverService subject

    ConverterService converterService

    def setup() {
        converterService = Mock()
        subject = new RoboticHooverService(converterService)
    }

}
