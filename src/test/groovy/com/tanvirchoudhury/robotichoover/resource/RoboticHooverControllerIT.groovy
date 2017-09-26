package com.tanvirchoudhury.robotichoover.resource

import groovyx.net.http.RESTClient
import spock.lang.Specification

class RoboticHooverControllerIT extends Specification {

    RESTClient client

    def setup(){
        client = new RESTClient("http://localhost:8080/robotic-hoover/")
    }

}