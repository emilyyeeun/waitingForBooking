package me.yeeunhong.waitingforbooking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@EnableWebSecurity
open class WaitingForBookingApplication
fun main(args: Array<String>) {
    runApplication<WaitingForBookingApplication> (*args)
}

