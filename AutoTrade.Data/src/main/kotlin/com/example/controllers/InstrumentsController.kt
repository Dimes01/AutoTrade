package com.example.controllers

import com.example.services.InstrumentsService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import ru.tinkoff.piapi.contract.v1.Share

@RestController
@RequestMapping("/instruments")
class InstrumentsController(
    private val instrumentService: InstrumentsService
) {
    companion object {
        private val logger = LoggerFactory.getLogger(InstrumentsController::class.java)
    }

    @GetMapping("/shares")
    fun getShares(): ResponseEntity<List<Share>> {
        logger.info("Endpoint: /instruments/shares called")
        return ResponseEntity.ok(instrumentService.getShares())
            .also { logger.info("Endpoint: /instruments/shares returned") }
    }
}

