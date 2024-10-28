package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("com/example/configurations")
class AutoTradeDataApplication

fun main(args: Array<String>) {
    runApplication<AutoTradeDataApplication>(*args)
}