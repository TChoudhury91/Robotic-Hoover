package com.tanvirchoudhury.robotichoover.service

import spock.lang.Specification
import spock.lang.Subject

class HooverCleanServiceTest extends Specification {

    @Subject
    HooverCleanService subject

    def setup() {
        subject = new HooverCleanService()
    }



}
